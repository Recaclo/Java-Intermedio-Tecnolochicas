package Sesion6.Reto2;

package com.ejemplo.inventario.repositorio;

import com.ejemplo.inventario.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
