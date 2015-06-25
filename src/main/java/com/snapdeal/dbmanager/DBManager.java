package com.snapdeal.dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.snapdeal.constants.Constants;

public class DBManager {

	public static Connection getLocalConnection() {
		Connection connection = null;
		try {
			
			Class.forName(Constants.LOCAL_JDBC_DRIVER);
			
			connection = DriverManager.getConnection(Constants.LOCAL_DB_URL,
					Constants.LOCAL_USER, Constants.LOCAL_PASS);
			//System.out.println("local connection successfull");
			
		} catch (ClassNotFoundException ex) {

			System.out.println("Class Not Found Exception -->" + ex);
		} catch (SQLException e) {
			System.out.println("local Connection Failed! ");
			e.printStackTrace();

		}

		return connection;
	}
	
	
	public static Connection getShippingConnection() {
		Connection connection = null;
		try {
			System.out.println("here");
			Class.forName(Constants.LOCAL_JDBC_DRIVER);
			
			connection = DriverManager.getConnection(Constants.LOCAL_DB_URL,
					Constants.LOCAL_USER, Constants.LOCAL_PASS);
			System.out.println("local connection successfull");
			
		} catch (ClassNotFoundException ex) {

			System.out.println("Class Not Found Exception -->" + ex);
		} catch (SQLException e) {
			System.out.println("local Connection Failed! ");
			e.printStackTrace();

		}

		return connection;
	}

	

}
