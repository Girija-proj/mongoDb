package com.pim.poc.mongodb.rest;

import com.pim.poc.mongodb.model.Address;
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
		if(!employeeCollectionService.findEmployeeById(id).equalsIgnoreCase("failure")){
			return new ResponseEntity<String>(employeeCollectionService.findEmployeeById(id),HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("No Employee found",HttpStatus.OK);
		}

	}

	@PutMapping("/employees")
	public void insertEmployeeRecords(){
		employeeCollectionService.populateEmployeeDocs();
	}

	@GetMapping("/employees/address")
    public ResponseEntity< List< List<Document>>> getEmployeeAddress(){
		List< List<Document>> employeesAddressInfo = employeeCollectionService.getEmployeesAddressInfo();
		return new ResponseEntity< List< List<Document>>> (employeesAddressInfo,HttpStatus.OK);
	}

	@PutMapping("/employees/{id}/address")
	public long updateEmployeeAddressById(@PathVariable("id") String id, @RequestParam Address address){
        return    employeeCollectionService.updateEmployeeAddressById(id,address);
	}

	@GetMapping("/employeesByName")
	public ResponseEntity<List<Document>> getEmployeesByName(@RequestParam String name){
		List<Document> employeesByName = employeeCollectionService.getEmployeesByName(name);
		return new ResponseEntity<List<Document>>(employeesByName,HttpStatus.OK);
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
