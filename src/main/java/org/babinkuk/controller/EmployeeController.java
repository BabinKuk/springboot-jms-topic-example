package org.babinkuk.controller;

import lombok.extern.slf4j.Slf4j;

import org.babinkuk.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

@Slf4j
@RestController
public class EmployeeController {
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	/**
	 * Create new employee
	 *
	 * @param employee
	 * @return ResponseEntity
	 */
	@PostMapping("/employee")
	public ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) {
		
		try {
			Topic empTopic = jmsTemplate.getConnectionFactory().createConnection()
					.createSession().createTopic("EmpTopic");
			int empId = (int)(Math.random() * 50 + 1);
			
			Employee emp = new Employee(empId,employee.getName(),employee.getRole());
			
			System.out.println("Sending Employee Object: " + emp);
			
			jmsTemplate.convertAndSend(empTopic, emp);
			
			return new ResponseEntity<>(emp, HttpStatus.OK);
			
		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Create new employee (using queue)
	 *
	 * @param employee
	 * @return ResponseEntity
	 */
	@PostMapping("/employee/mq")
	public ResponseEntity<Employee> newEmployeeQueue(@RequestBody Employee employee) {
		
		try {
			Queue empQueue = jmsTemplate.getConnectionFactory().createConnection()
					.createSession().createQueue("EmpQueue");
			int empId = (int)(Math.random() * 50 + 1);
			
			Employee emp = new Employee(empId,employee.getName(),employee.getRole());
			
			System.out.println("Sending Employee Object: " + emp);
			
			jmsTemplate.convertAndSend(empQueue, emp);
			
			return new ResponseEntity<>(emp, HttpStatus.OK);
			
		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
