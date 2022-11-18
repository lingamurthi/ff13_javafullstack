package com.examples.empapp.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.examples.empapp.dao.EmployeeDao;
import com.examples.empapp.dao.EmployeeDaoImpl;
import com.examples.empapp.exception.EmployeeException;
import com.examples.empapp.model.Employee;

public class EmployeeServiceColImpl implements EmployeeService {
		EmployeeDao empDao = new EmployeeDaoImpl();
		Map<Integer, Employee> employees = new HashMap<>();

	Comparator<Employee> EMPLOYEE_NAME_ASC_SORT = new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getName().compareTo(o2.getName());

		}
	};

	public EmployeeServiceColImpl() {
		employees.put(1, new Employee(1, "Anil", 45, "Delivery Manager", "Operations", "India"));
		employees.put(2, new Employee(2, "Swapnil", 35, "Quality Analyst", "Quality", "India"));
		employees.put(3, new Employee(3, "Georgil", 42, "Manager", "Operations", "USA"));
		employees.put(4, new Employee(4, "Sunil", 26, "Associate", "Operations", "India"));
		employees.put(5, new Employee(5, "Saril", 30, "Lead Associate", "Operations", "UK"));
		employees.put(6, new Employee(6, "Vinil", 36, "Manager", "Admin", "Australia"));
	}

	public boolean create(Employee employee) {
		return  empDao.insert(employee);
	}

	public Employee get(int id) throws EmployeeException {
		Employee emp = employees.get(id);
		
		if(emp == null) {
			throw new EmployeeException("No employee found for given id.");
		}
		return emp;
	}

	public List<Employee> getAll() {
		ArrayList<Employee> empList = new ArrayList<Employee>(employees.values());
		return empList;

	}

	public boolean update(Employee employee) {
		return employees.put(employee.getEmpId(), employee) != null ? true : false;
	}

	public boolean delete(int id) {
		return employees.remove(id) != null ? true : false;
	}

	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation) {
		if (!condition.test(emp)) {
			return operation.apply(msg);
		}
		return true;
	}

	public long getEmployeeCountAgeGreaterThan(int age) {
		long count = 0;
		for (Employee emp: employees.values()) {
			if(emp.getAge() > age) {
				count++;
			}
		}
		return count;
	}

	public List<Integer> getEmployeeIdsAgeGreaterThan(int age) {
		List<Integer> empIds = new ArrayList<>();
		for(Employee emp: employees.values()) {
			if(emp.getAge() > age) {
				empIds.add(emp.getEmpId());
			}
		}
		return empIds;
	}

	public Map<String, Long> getEmployeeCountByDepartment() {
		Map<String, Long> empCountByDept = new HashMap<>();
		for (Employee emp: employees.values()) {
			if(empCountByDept.containsKey(emp.getDepartment())) {
				long count = empCountByDept.get(emp.getDepartment());
				empCountByDept.put(emp.getDepartment(), ++count);
			} else {
				empCountByDept.put(emp.getDepartment(), 1L);
			}
		}
		return empCountByDept;
	}

	public Map<String, Long> getEmployeeCountByDepartmentOdered() {
		Map<String, Long> empCountByDept = new TreeMap<>();
		for (Employee emp: employees.values()) {
			if(empCountByDept.containsKey(emp.getDepartment())) {
				long count = empCountByDept.get(emp.getDepartment());
				empCountByDept.put(emp.getDepartment(), ++count);
			} else {
				empCountByDept.put(emp.getDepartment(), 1L);
			}
		}
		return empCountByDept;
	}

	public synchronized void bulkImport() {
		System.out.format("%n%s - Import started %n", Thread.currentThread().getName());
		int counter = 0;
		try (Scanner in = new Scanner(new FileReader(".\\input\\employee-input.txt"))) {
			System.out.println("Implorting file...");
			while (in.hasNextLine()) {
				String emp = in.nextLine();
				System.out.println("Importing employee - " + emp);
				Employee employee = new Employee();
				StringTokenizer tokenizer = new StringTokenizer(emp, ",");

				employee.setName(tokenizer.nextToken());
				employee.setAge(Integer.parseInt(tokenizer.nextToken()));
				employee.setDesignation(tokenizer.nextToken());
				employee.setDepartment(tokenizer.nextToken());
				employee.setCountry(tokenizer.nextToken());
				this.create(employee);
				counter++;
			}
			System.out.format("%s - %d Employees are imported successfully.", Thread.currentThread().getName(),
					counter);
		} catch (Exception e) {
			System.out.println("Error occured while importing employee data. " + e.getMessage());
		}
	}

	public void bulkExport() {
		System.out.format("%n%s - Export started %n", Thread.currentThread().getName());
		try (FileWriter out = new FileWriter(".\\output\\employee-output.txt")) {
			empDao.getAllEmp().stream().map(emp -> emp.getEmpId() + "," + emp.getName() + "," + emp.getAge() + ","
							+ emp.getDesignation() + "," + emp.getDepartment() + "," + emp.getCountry() + "\n")
					.forEach(rec -> {
						try {
							out.write(rec);
						} catch (IOException e) {
							System.out
									.println("Error occured while writing employee data into file. " + e.getMessage());
							e.printStackTrace();
						}
					});
			System.out.format("%d Employees are exported successfully.", employees.values().size());
		} catch (IOException e) {
			System.out.println("Error occured while exporting employee data. " + e.getMessage());
		}
	}
}