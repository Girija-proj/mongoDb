package com.pim.poc.mongodb.service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.pim.poc.mongodb.connection.MongoDbConnection;
import com.pim.poc.mongodb.model.Address;
import com.pim.poc.mongodb.model.Employee;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class EmployeeCollectionService {

    @Autowired
    MongoDbConnection mongoDbConnection;

    public MongoDbConnection getMongoDbConnection () {
        return mongoDbConnection;
    }


    public void populateEmployeeDocs () {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient mongoClient = createMangoClient(settings);
        MongoDatabase database = mongoClient.getDatabase("company");
        database.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Employee> employeeMongoCollection = database.getCollection("employee", Employee.class);
        // get a handle to the "test" collection
        getCollection(database).withWriteConcern(WriteConcern.ACKNOWLEDGED);
        insertMultipleEmployeeDocuments(employeeMongoCollection);
    }

    public   List< List<Document>>  getEmployeesAddressInfo() {
        List<Document> employeeList = new ArrayList<>();
        IntStream.range(11, 15).forEach((val) -> {
        Document nestedEmployeeDoc = new Document("EmpId", val).append("Name", "user_" + val).append("Skills", Stream.of("node", "react", "express").collect(Collectors.toList())).append("Department", "IT").
                append("customer-address",
                        Arrays.asList(new Document( new Document("primary",Arrays.asList(new Document("houseNo", val).append("streetNo", "street " + val).append("block", "A").append("city", "delhi").
                                append("state", "delhi").append("country", "India"))))));
            employeeList.add(nestedEmployeeDoc);
        });
        mongoDbConnection.getMongoClient().getDatabase("company").getCollection("employee").insertMany(employeeList);

        FindIterable<Document> findIterable = mongoDbConnection.getMongoClient().getDatabase("company").getCollection("employee").find();
        List< List<Document>> alEmployees = new ArrayList<>();

        findIterable.iterator().forEachRemaining(employeeDoc -> {
            if( employeeDoc.containsKey("customer-address")){
                List<Document> documentList = (List<Document> ) employeeDoc.get("customer-address");
                alEmployees.add(documentList);
            }
        });
      return alEmployees ;
    }


    private void insertMultipleEmployeeDocuments (MongoCollection<Employee> employeeMongoCollection) {
        List<Employee> employees = new ArrayList<>();
        IntStream.range(1,10).forEach((value) -> {
            employees.add(createEmployeeByEntityMapping (value));
        });
        employeeMongoCollection.insertMany(employees);
    }


    private MongoClient createMangoClient (MongoClientSettings settings) {
        MongoClient mongoClients = mongoDbConnection.getMongoClientWtCodecRegistry(settings);
        return mongoClients;
    }


    private Employee createEmployeeByEntityMapping (int id) {
        Employee employee = new Employee();
        employee.setEid(id);
        employee.setName("user_"+id);
        employee.setDept("IT");
        employee.setSkills(Stream.of("java", "webservice", "react", "jenkins").collect(Collectors.toList()));
        return employee;
    }


    public List<Document> getEmployeeDocs () {
        MongoDatabase company = MongoClients.create().getDatabase("company");
        FindIterable<Document> documents = getCollection(company).find();
        List<Document> employeeList = new ArrayList<>();
        documents.iterator().forEachRemaining(employeeList::add);
        return employeeList;
    }

    public String findEmployeeById (String id) {
      Document document =  getCollection(MongoClients.create().getDatabase("company")).find(eq("EmpId",Integer.parseInt(id))).first();
      if(Optional.of(document).isPresent()){
          return  document.toJson();
        }
      else return "failure";
    }

    private MongoCollection<Document> getCollection (MongoDatabase database) {
        return database.getCollection("employee");
    }

    public boolean deleteEmployeebyId (int id) {
        MongoDatabase database = MongoClients.create().getDatabase("company");
        DeleteResult deleteResult = getCollection(database).deleteOne(eq("eid", id));
       return deleteResult.wasAcknowledged();
    }

    public long updateEmployeeAddressById (String id, Address address) {

        BasicDBObject query = new BasicDBObject();
        query.put("eid",Integer.parseInt(id));

        BasicDBObject update = new BasicDBObject();
        update.put("$set",new BasicDBObject("Customer Address.0.houseNo",address.getHouseNo()));
        UpdateResult updateResult = mongoDbConnection.getMongoClient().getDatabase("company").getCollection("employee").updateOne(query, update);
        return updateResult.getModifiedCount();
    }

    public List<Document> getEmployeesByName (String name) {

        Bson matcher = Aggregates.match(eq("Name", name));
        Bson project = Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("Name", "Department")));
               List<Document> alFilteredDocs = new ArrayList<>();
        mongoDbConnection.getMongoClient().getDatabase("company").getCollection("employee").aggregate(Arrays.asList(matcher,project)).
               forEach((Consumer<Document>) document -> alFilteredDocs.add(document));
        return alFilteredDocs;
    }


    
    
    
    /*
    public void initializeColllection () {

        MongoClient mongoDbConnection = this.mongoDbConnection.getMongoClient(settings);
        System.out.println("Mongo Db Connection :" + mongoDbConnection);
        MongoDatabase database = mongoDbConnection.getDatabase("company");

        database.createCollection("employee", new CreateCollectionOptions().capped(true).maxDocuments(10));
        MongoCollection<Employee> employees = database.getCollection("employee", Employee.class);

        Employee employee = createEmployee();
        employees.insertOne(employee);
        long l = employees.countDocuments();
        System.out.println("long:" + l);

    }
*/


}
