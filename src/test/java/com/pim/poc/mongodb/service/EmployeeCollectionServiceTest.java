/*package com.pim.poc.mongodb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import static org.mockito.Mockito.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pim.poc.mongodb.connection.MongoDbConnection;

//@SpringBootTest
@Ignore
@RunWith(SpringRunner.class)
public class EmployeeCollectionServiceTest {

	@Mock
	private EmployeeCollectionService employeeCollectionService;

	@Mock
	private MongoDbConnection mongoDbConnection;

	@Mock
	private MongoClient mongoClient;

	@Mock
	private MongoDatabase mongoDb;

	@Mock
	private MongoCollection mongoCollection;

	
	 * @InjectMocks private Mongo wrapper;
	 

	@Before
	public void setUp() {
		// employeeCollectionService =
		// Mockito.mock(EmployeeCollectionService.class);
		// mongoDbConnection = Mockito.mock(MongoDbConnection.class);
		// Mock
	}

	@Test
    public void createEmployeeCollection () {
        System.out.println("employeeCollectionService ..." +employeeCollectionService);
        OngoingStubbing<MongoDbConnection> moOngoingStubbing = when(employeeCollectionService.getMongoDbConnection()).thenReturn((MongoDbConnection) mongoCollection);
    //    when(methodCall)
        assertEquals(employeeCollectionService.createEmployeeCollection(),1);
    //    System.out.println(l);
    }
}*/