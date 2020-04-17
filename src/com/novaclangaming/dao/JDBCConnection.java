package com.novaclangaming.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	
	
	public static void initDrivers() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection openConnection() {
		try {
			initDrivers();
			Connection conn = DriverManager
					.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "thomas", "Tgbtgb_20");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
