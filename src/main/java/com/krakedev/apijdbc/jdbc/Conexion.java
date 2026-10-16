package com.krakedev.apijdbc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conexion {
	private static final Logger log = LoggerFactory.getLogger(Conexion.class);
	private static final String URL = "jdbc:postgresql://localhost:5432/apijdbc";
	private static final String USER = "postgres";
	private static final String PASSWORD = "dilan428";
	
	public static Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			log.info("¡Conexión Exitosa!");
			return con;
		} catch (SQLException e) {
			throw new RuntimeException("¡Error en el intento de conexión: " + e.getMessage());
		}
	}
}
