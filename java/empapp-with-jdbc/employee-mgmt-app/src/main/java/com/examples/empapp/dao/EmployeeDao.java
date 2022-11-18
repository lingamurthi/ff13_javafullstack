package com.examples.empapp.dao;

import java.util.*;

import com.examples.empapp.model.Employee;

public interface EmployeeDao {
	
	public boolean insert(Employee emp);

	public boolean update(Employee emp);

	public boolean delete(int empId);
	
	public Employee viewEmp(int empId);
	
	public ArrayList<Employee> getAllEmp();
	
	public void statistics() ;
	
	public void export() ;
	public void exit();

}
