package com.snapdeal.constants;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class Constants {

	
	public static String LOCAL_JDBC_DRIVER; 
	public static String LOCAL_DATABASE;
	public static String LOCAL_DB_URL; 
	public static String LOCAL_USER;
	public static String LOCAL_PASS;
	
	public static String SHIPPING_JDBC_DRIVER; 
	public static String SHIPPING_DATABASE;
	public static String SHIPPING_DB_URL; 
	public static String SHIPPING_USER;
	public static String SHIPPING_PASS;


	static {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			//input = new FileInputStream("/home/ap7439/deduplication_report/config/config.properties");
			input = new FileInputStream(System.getProperty("user.dir")+"/config.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception in loading properties");
		}

		
		LOCAL_JDBC_DRIVER = prop.getProperty("LOCAL_JDBC_DRIVER");
		LOCAL_DATABASE = prop.getProperty("LOCAL_DATABASE");
		LOCAL_DB_URL = prop.getProperty("LOCAL_DB_URL");
		LOCAL_USER = prop.getProperty("LOCAL_USER");
		LOCAL_PASS = prop.getProperty("LOCAL_PASS");
		
		
		SHIPPING_JDBC_DRIVER = prop.getProperty("SHIPPING_JDBC_DRIVER");
		SHIPPING_DATABASE = prop.getProperty("SHIPPING_DATABASE");
		SHIPPING_DB_URL = prop.getProperty("SHIPPING_DB_URL");
		SHIPPING_USER = prop.getProperty("SHIPPING_USER");
		SHIPPING_PASS = prop.getProperty("SHIPPING_PASS");
		
		
		
	}

}
