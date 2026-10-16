package com.krakedev.apijdbc.videojuegos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krakedev.apijdbc.videojuegos.VideojuegoJdbc;
import com.krakedev.apijdbc.videojuegos.entidades.Videojuego;

@Service
public class ServicioVideojuegoJdbc {
	public Videojuego crear(Videojuego videojuego) {
		return VideojuegoJdbc.insertar(videojuego.getCodigo(), videojuego.getNombre(), videojuego.getPlataforma(),
				videojuego.getPrecio(), videojuego.isDisponible(), videojuego.getGenero());
	}
	
	public List<Videojuego> listar() {
		return VideojuegoJdbc.listar();
	}
	
	public Videojuego buscarPorCodigo(String codigo) {
		return VideojuegoJdbc.buscar(codigo);
	}
	
	public Videojuego actualizar(String codigo, Videojuego videojuego) {
		return VideojuegoJdbc.actualizar(codigo, videojuego);
	}
	
	public boolean eliminar(String codigo) {
		return VideojuegoJdbc.eliminar(codigo);
	}
}
