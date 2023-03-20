package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepository;


@RestController
@RequestMapping("/v1")
public class EmployeeController {
		
		@Autowired
		private EmployeeRepository repo;
		
		@GetMapping("/employees")
		public ResponseEntity<List<Employee>> getAllEmployees(){
			try {
			return new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/employee/{id}")
		public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
			try {
				Employee emp = getEmpRec(id);
				if(emp != null) {
					return new ResponseEntity<>(emp,HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				
			}catch(Exception e) {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		@PostMapping("/employee")
		public Employee newEmployee(@RequestBody Employee emp){
			return repo.save(emp);
		}
		@PutMapping("/employee/{id}")
		public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id,@RequestBody Employee emp){
			
			Employee em = getEmpRec(id);
			if(em != null) {
				em.setName(emp.getName());
				em.setRole(emp.getRole());
				return new ResponseEntity<>(repo.save(em),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		@DeleteMapping("/employees")
		public ResponseEntity<HttpStatus> deleteAllEmployee(){
			try {
				repo.deleteAll();
				return new ResponseEntity<>(HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		@DeleteMapping("/employee/{id}")
		public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") long id){
			try {
			Employee em = getEmpRec(id);
			if(em != null) {
				repo.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		private Employee getEmpRec(long id) {
			
			Optional<Employee> emp = repo.findById(id);
			
			if(emp.isPresent()) {
				return emp.get();
			}
			 return null;
			}
		
		
}
