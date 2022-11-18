package com.hibernate.service;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.hibernate.DAO.EmployeeDAO;
import com.hibernate.model.Employee;

import java.util.*;

public class EmployeeServiceImpl implements EmployeeService {

	EmployeeDAO emp = new EmployeeDAO();

	public EmployeeServiceImpl() {

	}

	public int create(Employee e) {

		int id = emp.create(e);

		return id;
	}

	@Override
	public int update(int i, String dept, String desig) {

		int id = emp.update(i, dept, desig);
		return id;

	}

	@Override
	public int delete(int i) {

		int id = emp.delete(i);

		return id;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> employees = emp.getAll();
		return employees;
	}

	@Override
	public void getEmployee(int id) {
		emp.getEmployee(id);

	}

	@Override
	public synchronized void bulkImport() {
		emp.bulkImport();
		
	}
	
	public void bulkExport() {
		emp.bulkExport();
	}

}
