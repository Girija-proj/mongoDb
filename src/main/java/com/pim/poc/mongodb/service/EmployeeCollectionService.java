package com.pim.poc.mongodb.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.pim.poc.mongodb.connection.MongoDbConnection;
import com.pim.poc.mongodb.model.Employee;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    private void insertMultipleEmployeeDocuments (MongoCollection<Employee> employeeMongoCollection) {
        List<Employee> employees = new ArrayList<>();
        IntStream.range(1,10).forEach((value) -> {
            employees.add(createEmployee(value));
        });
        employeeMongoCollection.insertMany(employees);
    }


    private MongoClient createMangoClient (MongoClientSettings settings) {
        MongoClient mongoClients = mongoDbConnection.getMongoClientWtCodecRegistry(settings);
        return mongoClients;
    }


    private Employee createEmployee (int id) {
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
      return  getCollection(MongoClients.create().getDatabase("company")).find(eq("EmpId",Integer.parseInt(id))).first().toJson();

    }

    private MongoCollection<Document> getCollection (MongoDatabase database) {
        return database.getCollection("employee");
    }

    public boolean deleteEmployeebyId (int id) {
        MongoDatabase database = MongoClients.create().getDatabase("company");
        DeleteResult deleteResult = getCollection(database).deleteOne(eq("eid", id));
       return deleteResult.wasAcknowledged();
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
