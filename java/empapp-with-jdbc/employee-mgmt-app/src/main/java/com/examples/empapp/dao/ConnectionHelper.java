package com.examples.empapp.dao;

import java.sql.*;

public class ConnectionHelper {
	private static Connection conn = null ; 
	
	static final String dburl = "jdbc:mysql://localhost:3307/springjdbc";
	
	public static Connection createConnection() {
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(dburl,"root","Purna@123");
				System.out.println("Connection Created");
				return conn ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return conn ; 
	}
}
