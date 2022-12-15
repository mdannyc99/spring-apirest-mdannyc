

#Regiones

INSERT INTO regiones(name) VALUES ('Europa');
INSERT INTO regiones(name) VALUES ('África');
INSERT INTO regiones(name) VALUES ('Asia');
INSERT INTO regiones(name) VALUES ('América del Sur');
INSERT INTO regiones(name) VALUES ('América del Norte');
INSERT INTO regiones(name) VALUES ('América Central');
INSERT INTO regiones(name) VALUES ('Oceanía');
INSERT INTO regiones(name) VALUES ('Antártida');

#Clientes

INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Daniel','Ruiz','daniapp12@gmail.com','+022 635892643',NOW(),1)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Isabel','Pantoja','pantoja33@gmail.com','+76 195847364',NOW(),2)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Belén','Esteban','belenesteban@gmail.com','+123 839465732',NOW(),7)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Enrique','Iglesias','enrikei@gmail.com','+98 928374612',NOW(),3)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Mariano','Rajoy','rajoyrajao@gmail.com','+76 125768976',NOW(),8)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Luis','García','luisg@gmail.com','+12 473859472',NOW(),6)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Raúl','López','rlopz@gmail.com','+67 928387512',NOW(),4)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Sandra','Martinez','sandram@gmail.com','+34 725348972',NOW(),5)
INSERT INTO clientes(nombre,apellido,email,telefono,created_at,id_region) VALUES('Gonzalo','Perez','gonzp12@gmail.com','+764 758859472',NOW(),6)

INSERT INTO productos(nombre) VALUES("MONITOR AOC");
INSERT INTO productos(nombre) VALUES("MOUSE LINK");
INSERT INTO productos(nombre) VALUES("ORDENADOR DELL");
INSERT INTO productos(nombre) VALUES("DISCO DURO SSD");
INSERT INTO productos(nombre) VALUES("DISCO EXTERNO SATELLITE");
INSERT INTO productos(nombre) VALUES("SSD 1TB");
INSERT INTO productos(nombre) VALUES("TARJETA GRÁFICA RTX 3060");
INSERT INTO productos(nombre) VALUES("SAMSUMG S11+");
INSERT INTO productos(nombre) VALUES("PORTÁTIL HP");
INSERT INTO productos(nombre) VALUES("CÁRAMA HD");

#INSERT INTO ventas(iva,subtotal,total,cliente_id) VALUES(21.0,200.0,221.0,5);
#INSERT INTO ventas_producto(venta_id,producto_id) VALUES(1,1)
#INSERT INTO ventas_producto(venta_id,producto_id) VALUES(1,2)
#INSERT INTO ventas_producto(venta_id,producto_id) VALUES(1,3)



#Usuarios

INSERT INTO usuarios (username, password, enabled) VALUES('Danny','$2a$10$mKesbTBQekJSziuOg0dx2.8CFKMYKC7I8wmIPKZC0UeU3LEKPEGmu',1); 
INSERT INTO usuarios (username, password, enabled) VALUES('admin','$2a$10$DHITJIMve5qpx6QiB4mbLez93Wl/f6lMZJJhXkXm1O9u82PlF8JIa',1); 

#Roles

INSERT INTO roles (nombre) VALUES('ROLE_USER');
INSERT INTO roles (nombre) VALUES('ROLE_ADMIN');

#Usuarios_roles

INSERT INTO usuarios_roles(usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_roles(usuario_id, role_id) VALUES (2,2);
