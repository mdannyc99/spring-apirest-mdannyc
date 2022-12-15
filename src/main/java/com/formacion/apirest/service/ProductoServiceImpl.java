package com.formacion.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.apirest.entity.Producto;
import com.formacion.apirest.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository repositorio;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> mostrarProductos() {
		return (List<Producto>) repositorio.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto buscarProducto(long id) {
		return repositorio.findById(id).orElseGet(null);
	}

	@Override
	@Transactional
	public Producto guardarProducto(Producto producto) {
		return repositorio.save(producto);
	}

	@Override
	public void borrarProducto(long id) {
		repositorio.deleteById(id);
		
	}

}
