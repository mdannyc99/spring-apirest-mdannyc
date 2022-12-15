package com.formacion.apirest.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacion.apirest.entity.Cliente;
import com.formacion.apirest.entity.Region;
import com.formacion.apirest.service.ClienteService;

@RestController
@RequestMapping("api") // Añade subruta /api/clientes
@CrossOrigin(origins="http://localhost:4200/")
public class ClienteController {
	
	@Autowired //Inyección de objetos
	private ClienteService servicio;
	
	
	// GESTIÓN CLIENTES
	
	@GetMapping("clientes")
	public List<Cliente> index(){
		return servicio.mostrarClientes();
	}
	
	@GetMapping("clientes/{id}")
	public ResponseEntity<?> show(@PathVariable long id) {
		Cliente cli = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cli = servicio.buscarCliente(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error de consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(cli == null) { 
			response.put("mensaje", "ID " + id + " no registrado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cli, HttpStatus.OK);
	}
	
	/*
	@PostMapping("clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@RequestBody Cliente cliente) {
		System.out.println(cliente.toString());
		Cliente cli = null;
		Map<String, Object> response = new HashMap<>();
		try {
			
			//if (cli.getCreatedAt() == null) {
				cli.setCreatedAt(new Date());
			//}
			
			cli = servicio.guardarCliente(cliente);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Cliente>(cli, HttpStatus.CREATED);
	}
	*/
	
	@PostMapping("clientes")
	public ResponseEntity<?> save(@RequestBody Cliente cliente) {
		Cliente clienteNew = null;
		Map<String,Object> response =new HashMap<>();
		
		try {
			clienteNew = servicio.guardarCliente(cliente);
		} catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al realizar insert en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente ha sido creado con éxito!");
		response.put("cliente",clienteNew);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	
	
	@PutMapping("clientes/{id}")
	public ResponseEntity<?> update(@PathVariable long id,@RequestBody Cliente cliente) {
		Cliente clienteUpdate = null;
		Map<String,Object> response =new HashMap<>();
		
		try {
			
			clienteUpdate = servicio.buscarCliente(id);
			if(clienteUpdate != null) {
				clienteUpdate.setNombre(cliente.getNombre());
				clienteUpdate.setApellido(cliente.getApellido());
				clienteUpdate.setEmail(cliente.getEmail());
				clienteUpdate.setTelefono(cliente.getTelefono());
				clienteUpdate.setCreatedAt(cliente.getCreatedAt());
				clienteUpdate.setRegion(cliente.getRegion());
			}else {
				response.put("mensaje","Error: no se puede editar, el cliente con ID: "+id+" no existe en la base de datos");	
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			servicio.guardarCliente(clienteUpdate);
			
		} catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al realizar update en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje","El cliente ha sido actualizado con éxito!");
		response.put("cliente",clienteUpdate);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	
	
	@DeleteMapping("clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		Cliente cli = null;
		Map<String,Object> response =new HashMap<>();
		
		try {
			cli = servicio.buscarCliente(id);
			String nombreImagenAnterior = cli.getImagen();
			
			if (nombreImagenAnterior != null && nombreImagenAnterior.length()>0) {
				Path rutaImagenAnterior = Paths.get("uploads").resolve(nombreImagenAnterior).toAbsolutePath();
				File archivoImagenAnterior = rutaImagenAnterior.toFile();
				
				if(archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()) {
					archivoImagenAnterior.delete();
				}
			}
			
			servicio.borrarCliente(id);
		} catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al borrar usuario con ID -> "+id);
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente con ID " + id + " se ha borrado con ÉXITO");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}
	
	
	// GESTIÓN ARCHIVOS
	
	@PostMapping("clientes/uploads")
	public ResponseEntity<?> uploadFile(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") long id){
		Map<String, Object> response = new HashMap<>();
		
		Cliente cli = servicio.buscarCliente(id);
		String nombreArchivo = "";
		
		try {
			cli = servicio.buscarCliente(id);
			
			if(!archivo.isEmpty() && cli != null) {
				String nombreImagenAnterior = cli.getImagen();
				
				if (nombreImagenAnterior != null && nombreImagenAnterior.length()>0) {
					Path rutaImagenAnterior = Paths.get("uploads").resolve(nombreImagenAnterior).toAbsolutePath();
					File archivoImagenAnterior = rutaImagenAnterior.toFile();
					
					if(archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()) {
						archivoImagenAnterior.delete();
						
					}
				}
				
				//Randomizamos para que todos los nombres sean diferentes
				nombreArchivo = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "");
				
				Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
				
				try {
					Files.copy(archivo.getInputStream(), rutaArchivo);
				} catch (IOException e) {
					response.put("mensaje","Error al subir la imagen del cliente");
					response.put("error: ", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
					return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

				}
			}	
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		cli.setImagen(nombreArchivo);
		
		try {
			servicio.guardarCliente(cli);
		}catch (DataAccessException e) {
			//si hay error desde la base de datos
			response.put("mensaje","Error al realizar guardar cliente en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("cliente",cli);
		response.put("mensaje","Archivo subido correctamente: "+nombreArchivo);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

	}
	
	@GetMapping("clientes/img/{nombreImagen:.+}")
	public ResponseEntity<?> showImage(@PathVariable String nombreImagen){
		
		// Enviar archivo por cabecera
		Path rutaArchivo = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: No se puede cargar la imagen "+nombreImagen);
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;FILENAME=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	
	
	// GESTIÓN REGIONES
	@GetMapping("clientes/regiones")
	public List<Region> showRegions(){
		return servicio.mostrarTodasRegiones();
	}
	
	@GetMapping("clientes/email/{email}")
	public ResponseEntity<?> showClientEmail(@PathVariable String email) {
		Map<String, Object> response = new HashMap<>();
		Cliente clienteEmail = null;
		
		try {
			clienteEmail = servicio.buscarClienteEmail(email);
			
			if(clienteEmail == null) {
				response.put("mensaje","Error el email "+email+" no existe en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}

			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta por email a cliente");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Cliente>(clienteEmail, HttpStatus.OK);
	}
	
}
