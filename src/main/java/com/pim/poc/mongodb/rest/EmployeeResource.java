package com.pim.poc.mongodb.rest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pim.poc.mongodb.service.EmployeeCollectionService;

import java.util.List;

@RestController
@RequestMapping("/api/mongo")
class EmployeeResource {
	
	@Autowired
	private EmployeeCollectionService employeeCollectionService;

	@GetMapping("/employees")
	public ResponseEntity<List<Document>> getEmployeeRecords(){
	 	return  new ResponseEntity<>(employeeCollectionService.getEmployeeDocs(), HttpStatus.OK);
	}

	@GetMapping("/employees/{id}")
	public  ResponseEntity<String> getEmployeeById(@PathVariable("id") String id){
	  return new ResponseEntity<String>(employeeCollectionService.findEmployeeById(id),HttpStatus.OK);
	}

	@PutMapping("/employees")
	public void insertEmployeeRecords(){
		employeeCollectionService.populateEmployeeDocs();
	}

     @DeleteMapping("/employees/id")
	 public void deleteEmployeeById(@PathVariable("id") int id){
       employeeCollectionService.deleteEmployeebyId(id);

	 }

	@GetMapping("/greeting")
	public String hello(){
		return "hello";
	}
	

}
