package com.hibernate.DAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hibernate.Utils.HibernateConnection;
import com.hibernate.model.Employee;

public class EmployeeDAO {
	private static SessionFactory factory;
	
	// HibernateConnection hibernateconnection= (HibernateConnection) HibernateConnection.getSessionFactory();

//	public SessionFactory getConnection() {
//		// STEP 1: Create Configuration
//		Configuration config = new Configuration();
//		// loads hibernate mapping configs from annotated class
//		config.addAnnotatedClass(Employee.class);
//
//		// STEP 2: Create SessionFactory
//		factory = config.buildSessionFactory();
//		System.out.println("Connected to databased - " + factory);
//		return factory;
//
//	}

	public int create(Employee emp) {

		factory = HibernateConnection.getSessionFactory();
		Transaction tnx = null;
		Integer id = -1;

		try (Session session = factory.openSession()) {
			tnx = session.beginTransaction();

			// Insert data into table by supplying the persistent object
			id = (Integer) session.save(emp);
			

			System.out.println("\nEmployee inserted successfully with ID - " + id);

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}

		return id;
	}

	public int update(int id, String dept, String desig) {
		factory = HibernateConnection.getSessionFactory();
		Transaction tnx = null;

		try (Session session = factory.openSession()) {
			tnx = session.beginTransaction();

			// update
			Employee emp = session.get(Employee.class, id);
			emp.setDepartment(dept);
			emp.setDesignation(desig);
			session.update(emp);

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}

		return id;

	}

	public int delete(int id) {

		factory = HibernateConnection.getSessionFactory();
		Transaction tnx = null;

		try {
			Session session = factory.openSession();
			tnx = session.beginTransaction();

			// Delete
			Employee emp = session.get(Employee.class, id);

			session.delete(emp);

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}

		return id;
	}

	public List<Employee> getAll() {
		factory = HibernateConnection.getSessionFactory();
		Transaction tnx = null;
		List<Employee> emp = null;

		try (Session session = factory.openSession()) {
			tnx = session.beginTransaction();

			// get all
			emp = session.createQuery("From Employee").list();
			System.out.println("ID \tName \tAge \tDepartment \tDesignation");
			Iterator<Employee> iterator = emp.iterator();
			while (iterator.hasNext()) {
				Employee employee = (Employee) iterator.next();

				System.out.println(employee.getId() + "\t" + employee.getName() + "\t" + employee.getAge() + "\t" + "\t"
						+ employee.getDepartment() + "\t" + employee.getDesignation());
			}

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}
		return emp;
	}

	public void getEmployee(int id) {
		factory = HibernateConnection.getSessionFactory();
		Transaction tnx = null;

		try (Session session = factory.openSession()) {
			tnx = session.beginTransaction();

			// get one employee
			Employee emp = session.get(Employee.class, id);
			System.out.println("ID \tName \tAge \tDepartment \tDesignation");
			System.out.println(emp.getId() + "\t" + emp.getName() + "\t" + emp.getAge() + "\t" + "\t"
					+ emp.getDepartment() + "\t" + emp.getDesignation());

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}

	}
	public synchronized void bulkImport() {
		System.out.format("%n%s - Import started %n", Thread.currentThread().getName());
		int counter = 0;
		// windows path - .\\input\\employee-input.txt
		// mac/linux path - /input/employee-input.txt
		try (Scanner in = new Scanner(new FileReader(".\\input\\employee-input.txt"))) {
			System.out.println("Implorting file...");
			while (in.hasNextLine()) {
				String emp = in.nextLine();
				System.out.println("Importing employee - " + emp);
				Employee employee = new Employee();
				StringTokenizer tokenizer = new StringTokenizer(emp, ",");

				// Emp ID
//				employee.setEmpId(Integer.parseInt(tokenizer.nextToken()));
				// Name
				employee.setName(tokenizer.nextToken());
				// Age
				employee.setAge(Integer.parseInt(tokenizer.nextToken()));
				// Designation
				employee.setDesignation(tokenizer.nextToken());
				// Department
				employee.setDepartment(tokenizer.nextToken());
				// Country
				employee.setCountry(tokenizer.nextToken());

//				employees.put(employee.getEmpId(), employee);
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
		// windows path - .\\output\\employee-output.txt
		// mac/linux path - /output/employee-output.txt
		try (FileWriter out = new FileWriter(".\\output\\employee-output.txt")) {
			
				this.getAll().stream().map(emp -> emp.getId() + "," + emp.getName() + "," + emp.getAge() + ","
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
			System.out.format("%d Employees are exported successfully.", this.getAll().size());
		} catch (IOException e) {
			System.out.println("Error occured while exporting employee data. " + e.getMessage());
		}
	}
}
