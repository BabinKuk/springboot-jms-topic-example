package org.babinkuk.listener;

import lombok.extern.slf4j.Slf4j;

import org.babinkuk.model.Employee;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeListener {
    
	@JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
	public void getEmployeeListener1(Employee emp) {
		System.out.println("Employee listener1: " + emp);
	}
	
	@JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
	public void getEmployeeListener2(Employee emp) {
		System.out.println("Employee Listener2: " + emp);
	}
	
	@JmsListener(destination = "${emp.jms.queue}")
	public void getEmployeeQueueListener1(Employee emp) {
		System.out.println("Employee queue listener1: " + emp);
	}
	
	@JmsListener(destination = "${emp.jms.queue}")
	public void getEmployeeQueueListener2(Employee emp) {
		System.out.println("Employee queue Listener2: " + emp);
	}
}