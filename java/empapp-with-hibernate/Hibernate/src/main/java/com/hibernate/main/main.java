package com.hibernate.main;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.hibernate.model.Employee;
import com.hibernate.service.EmployeeServiceImpl;

public class main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		EmployeeServiceImpl emp = new EmployeeServiceImpl();
		Employee e = new Employee();
		while (true) {
			System.out.println("\n");
			System.out.println("1. Add Employee");
			System.out.println("2. Update Employee");
			System.out.println("3. Delete Employee");
			System.out.println("4. View  All Employees");
			System.out.println("5. View Employee");
			System.out.println("6. Import");
			System.out.println("7. Export");
			System.out.println("8. Export");
			System.out.println("9. Exit");

			System.out.print("Enter the option: ");
			int option = 0;
			option = sc.nextInt();

			switch (option) {

			case 1:
				System.out.println("Enter employee Details");
				System.out.println("Enter age");
				int age = sc.nextInt();
				e.setAge(age);
				System.out.println("Enter name");
				String name = sc.next();

				e.setName(name);

				System.out.println("Enter country");
				String country = sc.next();
				e.setCountry(country);

				System.out.println("Enter Department");
				String dept1 = sc.next();

				e.setDepartment(dept1);

				System.out.println("Enter designation");
				String desi = sc.next();
				e.setDesignation(desi);
				int id = emp.create(e);
				System.out.println("Employee added Successfully with" + id);
				break;

			case 2:
				System.out.println("Enter id you want to update");
				int id1 = sc.nextInt();
				System.out.println("Enter designation");
				String des = sc.next();
				System.out.println("Enter department");
				String dept = sc.next();
				int updateid = emp.update(id1, des, dept);
				System.out.println("Employee updated Successfully with" + updateid);
				break;

			case 3:
				System.out.println("Enter id");
				int idDelete = sc.nextInt();
				int deleteId = emp.delete(idDelete);
				System.out.println("Employee deleted Successfully with" + deleteId);
				break;

			case 4:
				List<Employee> employee = emp.getAll();

				break;
			case 5:
				System.out.println("Enter id");
				int viewid = sc.nextInt();
				emp.getEmployee(viewid);
				break;

			case 6:
				emp.bulkImport();
				break;

			case 7:
				emp.bulkExport();
				// Future<Boolean> exportFuture = executor.submit(exportThread);

				break;

			}
		}

	}

}
