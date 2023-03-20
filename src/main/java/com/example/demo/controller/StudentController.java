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

import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepository;

@RestController
@RequestMapping("/v2")
public class StudentController {
	@Autowired
	private StudentRepository repo;
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getStudents(){
		try {
			return new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") long id){
		try {
			Student emp = getStdRec(id);
			if(emp != null) {
				return new ResponseEntity<>(emp,HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/student")
	public Student newStudent(@RequestBody Student emp){
		return repo.save(emp);
	}
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") long id,@RequestBody Student emp){
		
		Student em = getStdRec(id);
		if(em != null) {
			em.setName(emp.getName());
			em.setEmail(emp.getEmail());
			return new ResponseEntity<>(repo.save(em),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/students")
	public ResponseEntity<HttpStatus> deleteAllStudent(){
		try {
			repo.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/student/{id}")
	public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("id") long id){
		try {
		Student em = getStdRec(id);
		if(em != null) {
			repo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	private Student getStdRec(long id) {
		
		Optional<Student> emp = repo.findById(id);
		
		if(emp.isPresent()) {
			return emp.get();
		}
		 return null;
		}
	
}
