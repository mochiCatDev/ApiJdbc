package com.krakedev.apijdbc.videojuegos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.apijdbc.videojuegos.entidades.Videojuego;
import com.krakedev.apijdbc.videojuegos.services.ServicioVideojuegoJdbc;

@RestController
@RequestMapping("/jdbc/videojuegos")
public class VideojuegoJdbcController {
	private final ServicioVideojuegoJdbc servicio;
	
	public VideojuegoJdbcController(ServicioVideojuegoJdbc servicio) {
		super();
		this.servicio = servicio;
	}
	
	@PostMapping
	public Videojuego crear(@RequestBody Videojuego videojuego) {
		return servicio.crear(videojuego);
	}
	
	@GetMapping
	public List<Videojuego> listar() {
		return servicio.listar();
	}
	
	@GetMapping("/{codigo}")
	public Videojuego buscar(@PathVariable String codigo) {
		return servicio.buscarPorCodigo(codigo);
	}
	
	@PutMapping("/{codigo}")
	public Videojuego actualizar(@PathVariable String codigo, @RequestBody Videojuego videojuegoActualizado) {
		return servicio.actualizar(codigo, videojuegoActualizado);
	}
	
	@DeleteMapping("/{codigo}")
	public boolean eliminar(@PathVariable String codigo) {
		return servicio.eliminar(codigo);
	}
}
