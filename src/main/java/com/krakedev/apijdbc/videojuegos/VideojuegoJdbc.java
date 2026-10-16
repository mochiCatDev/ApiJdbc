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
			log.info("Videojuego insertado correctamente. Filas afectadas: {}", filas);

			videojuego = new Videojuego(codigo, nombre, plataforma, precio, disponible, genero);
		} catch (SQLException e) {
			log.error("Error al insertar el videojuego en la base de datos: {}", e.getMessage(), e);
			throw new RuntimeException("Error al insertar el videojuego.");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error("Error al cerrar los recursos de la base de datos tras la inserción: {}", e.getMessage(), e);
				throw new RuntimeException("Error al cerrar los recursos de inserción.");
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
			log.error("Error al listar los videojuegos de la base de datos: {}", e.getMessage(), e);
			throw new RuntimeException("Error al listar los videojuegos.");
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
				log.error("Error al cerrar los recursos de la base de datos tras la consulta: {}", e.getMessage());
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
			ps.setString(1, codigo);
			rs = ps.executeQuery();

			if (rs.next()) {
				videojuego = new Videojuego(rs.getString("codigo"), rs.getString("nombre"), rs.getString("plataforma"),
						rs.getDouble("precio"), rs.getBoolean("disponible"), rs.getString("genero"));
			}
		} catch (SQLException e) {
			log.error("Error al buscar el videojuego por código [{}]: {}", codigo, e.getMessage(), e);
			throw new RuntimeException("Error al buscar el videojuego por código.");
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
				log.error("Error al cerrar los recursos de la base de datos tras la búsqueda: {}", e.getMessage(), e);
				throw new RuntimeException("Error al cerrar los recursos de búsqueda.");
			}
		}

		return videojuego;
	}

	public static Videojuego actualizar(String codigo, Videojuego videojuegoActualizado) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE videojuegos SET nombre = ?, plataforma = ?, precio = ?, disponible = ?, genero = ? WHERE codigo = ?";
		Videojuego videojuego = null;

		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, videojuegoActualizado.getNombre());
			ps.setString(2, videojuegoActualizado.getPlataforma());
			ps.setDouble(3, videojuegoActualizado.getPrecio());
			ps.setBoolean(4, videojuegoActualizado.isDisponible());
			ps.setString(5, videojuegoActualizado.getGenero());
			ps.setString(6, codigo);

			int filas = ps.executeUpdate();
			log.info("Videojuego actualizado exitosamente. Filas afectadas: {}", filas);

			videojuego = new Videojuego(codigo, videojuegoActualizado.getNombre(),
					videojuegoActualizado.getPlataforma(), videojuegoActualizado.getPrecio(),
					videojuegoActualizado.isDisponible(), videojuegoActualizado.getGenero());
		} catch (SQLException e) {
			log.error("Error al actualizar el videojuego en la base de datos: {}", e.getMessage(), e);
			throw new RuntimeException("Error al actualizar el videojuego.");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error("Error al cerrar los recursos de la base de datos tras la actualización: {}", e.getMessage(),
						e);
				throw new RuntimeException("Error al cerrar los recursos de actualización.");
			}
		}

		return videojuego;
	}

	public static boolean eliminar(String codigo) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM videojuegos WHERE codigo = ?";
		boolean eliminado = false;

		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, codigo);
			int filas = ps.executeUpdate();

			if (filas > 0) {
				log.info("Videojuego eliminado exitosamente. Filas afectadas: {}", filas);
				eliminado = true;
			}
		} catch (Exception e) {
			log.error("Error al eliminar el videojuego de la base de datos: {}", e.getMessage(), e);
			throw new RuntimeException("Error al eliminar el videojuego.");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error("Error al cerrar los recursos de la base de datos tras la eliminación: {}", e.getMessage(),
						e);
				throw new RuntimeException("Error al cerrar los recursos de eliminación.");
			}
		}

		return eliminado;
	}
}