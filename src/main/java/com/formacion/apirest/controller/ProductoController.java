package com.formacion.apirest.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.apirest.entity.Producto;
import com.formacion.apirest.service.ProductoService;

@RestController
@RequestMapping("api") // Añade subruta /api/productos
@CrossOrigin(origins="http://localhost:4200/")
public class ProductoController {

	@Autowired //Inyección de objetos
	private ProductoService servicio;
	
	@GetMapping("productos")
	public List<Producto> index(){
		return servicio.mostrarProductos();
	}
	
	@GetMapping("productos/{id}")
	public ResponseEntity<?> show(@PathVariable long id) {
		Producto prod = null;
		Map<String, Object> response = new HashMap<>();
		try {
			prod = servicio.buscarProducto(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error de consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(prod == null) { 
			response.put("mensaje", "ID " + id + " no registrado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(prod, HttpStatus.OK);
	}
	
	@PostMapping("productos")
	public ResponseEntity<?> save(@RequestBody Producto producto) {
		Producto productoNew = null;
		Map<String,Object> response =new HashMap<>();
		
		try {
			productoNew = servicio.guardarProducto(producto);
		} catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al realizar insert en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El producto ha sido creado con éxito!");
		response.put("producto",productoNew);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("productos/{id}")
	public ResponseEntity<?> update(@PathVariable long id,@RequestBody Producto producto) {
		Producto productoUpdate = null;
		Map<String,Object> response =new HashMap<>();
		
		try {
			
			productoUpdate = servicio.buscarProducto(id);
			if(productoUpdate != null) {
				productoUpdate.setNombre(producto.getNombre());
			}else {
				response.put("mensaje","Error: no se puede editar, el producto con ID: "+id+" no existe en la base de datos");	
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			servicio.guardarProducto(productoUpdate);
			
		} catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al realizar update en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje","El producto ha sido actualizado con éxito!");
		response.put("producto",productoUpdate);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("productos/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		Producto prod = null;
		Map<String,Object> response =new HashMap<>();
		
		try {
			prod = servicio.buscarProducto(id);
			
			servicio.borrarProducto(id);
		} catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al borrar producto con ID -> "+id);
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto con ID " + id + " se ha borrado con ÉXITO");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}
	
	
}
