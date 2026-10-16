package com.krakedev.apijdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakedev.apijdbc.jdbc.Conexion;
import com.krakedev.apijdbc.videojuegos.entidades.Videojuego;

public class VideojuegoJdbc {
	private static final Logger log = LoggerFactory.getLogger(VideojuegoJdbc.class);

	public static Videojuego insertar(String codigo, String nombre, String plataforma, double precio,
			boolean disponible, String genero) {
		Videojuego videojuego = null;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO videojuegos(codigo, nombre, plataforma, precio, disponible, genero) VALUES(?, ?, ?, ?, ?, ?)";

		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, codigo);
			ps.setString(2, nombre);
			ps.setString(3, plataforma);
			ps.setDouble(4, precio);
			ps.setBoolean(5, disponible);
			ps.setString(6, genero);

			int filas = ps.executeUpdate();
			log.info("Filas insertadas: {}", filas);

			videojuego = new Videojuego(codigo, nombre, plataforma, precio, disponible, genero);
		} catch (SQLException e) {
			log.error("Error al insertar un videojuego: {}", e.getMessage(), e);
			throw new RuntimeException("Error al insertar un videojuego.");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error("Error al cerrar los recursos de inserción: {}", e.getMessage(), e);
				throw new RuntimeException("Error al cerrar los recursos de inserción");
			}
		}

		return videojuego;
	}
}
