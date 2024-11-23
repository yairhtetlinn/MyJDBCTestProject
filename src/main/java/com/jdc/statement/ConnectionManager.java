package com.jdc.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionManager {
	
	Connection getConnection() throws SQLException;
	
	String URL = "jdbc:mysql://localhost:3306/message_db";
	String USER = "root";
	String PASS = "Yh!260901";
	
	static ConnectionManager getInstance() {
		return ()-> DriverManager.getConnection(URL,USER,PASS);
	}
}
