package com.krakedev.apijdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Videojuego> listar() {
		List<Videojuego> videojuegos = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM videojuegos";

		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Videojuego v = new Videojuego(rs.getString("codigo"), rs.getString("nombre"),
						rs.getString("plataforma"), rs.getDouble("precio"), rs.getBoolean("disponible"),
						rs.getString("genero"));
				videojuegos.add(v);
			}
		} catch (SQLException e) {
			log.error("Error al listar clientes: {}", e.getMessage(), e);
			throw new RuntimeException("Error al listar clientes");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error("Error al cerrar los recursos de la consulta: {}", e.getMessage());
				throw new RuntimeException("Error al cerrar los recursos de la consulta.");
			}
		}

		return videojuegos;
	}

	public static Videojuego buscar(String codigo) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM videojuegos WHERE codigo = ?";
		Videojuego videojuego = null;

		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				videojuego = new Videojuego(rs.getString("codigo"), rs.getString("nombre"), rs.getString("plataforma"),
						rs.getDouble("precio"), rs.getBoolean("disponible"), rs.getString("genero"));
			}
		} catch (SQLException e) {
			log.error("Error al buscar videojuego por código: {}", e.getMessage(), e);
			throw new RuntimeException("Error al buscar videojuego por codigo");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error("Error al cerrar los recursos de búsqueda: {}", e.getMessage(), e);
				throw new RuntimeException("Error al cerrar los recursos de búsquedad");
			}
		}
		
		return videojuego;
	}
}
