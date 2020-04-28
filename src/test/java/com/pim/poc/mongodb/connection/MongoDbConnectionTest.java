package com.pim.poc.mongodb.connection;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Assertions;

class MongoDbConnectionTest {

	@org.junit.jupiter.api.Test
	void createConection() {
		MongoDbConnection mongoDbConnection = new MongoDbConnection();
		MongoClient connection = mongoDbConnection.getMongoClient();
		Assertions.assertNotNull(connection);
	}
}