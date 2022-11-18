package com.hibernate.service;

import java.util.List;

import com.hibernate.model.Employee;

public interface EmployeeService {
	public int create(Employee emp);
	
	public int update(int id,String dept,String desig);
	
	public int delete(int id);
	
	public List<Employee>  getAll();
	
	public void getEmployee(int id);
	
	public  void bulkImport();
	
	public void bulkExport();
}
