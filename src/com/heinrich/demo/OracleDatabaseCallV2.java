package com.heinrich.demo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

/*
 * The alternative oracle database call.
 */
public class OracleDatabaseCallV2 {
	
	
	public static String CallLogic(String username) {
		
		String oracleResponse = "";
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			/*
			 * URL breakdown:
			 * Host		= The host you are trying to connect to eg. localhost
			 * Port		= The port of the database host eg. 1521
			 * Database = The name of the database you would like to connect to eg. ORCLCDB
			 */
			String url = "jdbc:oracle:thin:@localhost:1521:ORCLCDB";
			
			
			String db_user = username;
			String password = "password";
			
			Connection con = DriverManager.getConnection(url, db_user, password);
            System.out.println("Connected to database");
			
            /*
			 * How to build the query. Replace the following variables
			 * SCHEMA 		= The appropriate schema name eg. ANONYMOUS
			 * PROCEDURE	= The appropriate procedure name.
			 * 
			 * The number of question marks equals to the number of variables you pass IN and OUT.
			 */
            String command = "{CALL SCHEMA.PROCEDURE(?,?,?,?)}";
            CallableStatement cstmt = con.prepareCall(command);
            
            cstmt.setInt(1, 1234);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            
            cstmt.execute();
            
            oracleResponse = cstmt.getString(2);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return oracleResponse;
		
	}
	
	
	public String InvokeLogic(String username) {
		
		String invokeRepsonse = CallLogic(username);
		
		return invokeRepsonse;	
	}
	
	
	
	public static void main(String[] args) {
		String finalResult = CallLogic("system");
		System.out.println(finalResult);
	}

}
