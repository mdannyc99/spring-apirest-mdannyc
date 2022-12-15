package com.formacion.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.apirest.entity.Cliente;
import com.formacion.apirest.entity.Region;
import com.formacion.apirest.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repositorio;
	
	@Override
	@Transactional(readOnly = true) // Solo para los métodos GET
	public List<Cliente> mostrarClientes() {
		return (List<Cliente>) repositorio.findAll();
	}

	// Transactional(readOnly = true) es para los métodos GET
	@Override
	@Transactional(readOnly = true)
	public Cliente buscarCliente(long id) {
		return repositorio.findById(id).orElseGet(null);
	}

	@Override
	@Transactional
	public Cliente guardarCliente(Cliente cliente) {
		return repositorio.save(cliente);
	}

	@Override
	@Transactional
	public void borrarCliente(long id) {
		repositorio.deleteById(id);
		
	}

	@Override
	public Cliente buscarClienteEmail(String email) {
		return repositorio.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> mostrarTodasRegiones() {
		return repositorio.mostrarRegiones();
	}

}
