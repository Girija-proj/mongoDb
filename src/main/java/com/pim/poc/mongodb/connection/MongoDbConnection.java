package com.pim.poc.mongodb.connection;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Repository
public class MongoDbConnection {

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MongoClient getMongoClient(){
        return  MongoClients.create();
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MongoClient getMongoClientWtCodecRegistry (MongoClientSettings settings) {
        MongoClient mongoClients = MongoClients.create(settings);
        return  mongoClients;
    }

   /* public void createEmbeddedMongoDbConnection () {

        try {
            MongodStarter starter = MongodStarter.getDefaultInstance();
            IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                    .net(new Net(12345, Network.localhostIsIPv6()))
                    .pidFile(new File("target/process.pid").getAbsolutePath())
                    .replication(new Storage(new File("target/tmp/mongodb/").getAbsolutePath(), null, 0)).build();
            System.out.println("Would download MongoDB if not yet downloaded.");
            MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
            System.out.println("Done with downloading MongoDB exec.");
            mongodExecutable.start();



        } catch (Exception ex) {
            System.out.println("Failed to start MongoDB" + ex);
            throw new RuntimeException(ex);
        }
    }*/


   /* public MongoClient getDatabaseConnection() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);


        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).codecRegistry(codecRegistry).build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);

        return mongoClient;
    }*/



}
