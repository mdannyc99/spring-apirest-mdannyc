package com.formacion.apirest.service;

import java.util.List;

import com.formacion.apirest.entity.Producto;


public interface ProductoService {
	public List<Producto> mostrarProductos();
	public Producto buscarProducto(long id);
	public Producto guardarProducto(Producto producto);
	public void borrarProducto(long id);
	
}
