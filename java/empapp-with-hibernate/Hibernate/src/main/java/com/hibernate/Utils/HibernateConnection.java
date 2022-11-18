package com.hibernate.Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.model.Employee;

public class HibernateConnection {
	
	private static final SessionFactory factory = buildSessionFactory();
			
	private static SessionFactory buildSessionFactory() {
		SessionFactory sessionFactory=null;
	
				 try {
					 sessionFactory=new Configuration().addAnnotatedClass(Employee.class).buildSessionFactory();
					System.out.println("Connected to databased - " + sessionFactory);
				 }catch(Exception e) {
					 System.out.println("Intial sessionfactory creation failed"+e.getMessage());
				 }
				return sessionFactory;
	}
	
	public static SessionFactory getSessionFactory() {
		return factory;
	}
	public static void closeSession() {
		getSessionFactory().close();
	}

}
