package com.formacion.apirest.service;

import java.util.List;

import com.formacion.apirest.entity.Cliente;
import com.formacion.apirest.entity.Region;

public interface ClienteService {
	public List<Cliente> mostrarClientes();
	public Cliente buscarCliente(long id);
	public Cliente guardarCliente(Cliente cliente);
	public void borrarCliente(long id);
	
	public Cliente buscarClienteEmail(String email);
	public List<Region> mostrarTodasRegiones();
}
