package Sesion6.Reto1;

package com.ejemplo.inventario;

import com.ejemplo.inventario.modelo.Producto;
import com.ejemplo.inventario.repositorio.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventarioApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductoRepository repositorio) {
        return args -> {
            // Guardar productos
            repositorio.save(new Producto("Laptop Lenovo", "Laptop de alto rendimiento", 12500));
            repositorio.save(new Producto("Mouse Logitech", "Mouse inalámbrico", 350));
            repositorio.save(new Producto("Teclado Mecánico", "Teclado retroiluminado", 950));
            repositorio.save(new Producto("Monitor", "Monitor LED 24 pulgadas", 3200));

            // Consultas personalizadas
            System.out.println(" Productos con precio mayor a 500:");
            repositorio.findByPrecioGreaterThan(500).forEach(System.out::println);

            System.out.println("\n Productos que contienen 'lap':");
            repositorio.findByNombreContainingIgnoreCase("lap").forEach(System.out::println);

            System.out.println("\n Productos con precio entre 400 y 1000:");
            repositorio.findByPrecioBetween(400, 1000).forEach(System.out::println);

            System.out.println("\n Productos cuyo nombre empieza con 'm':");
            repositorio.findByNombreStartingWithIgnoreCase("m").forEach(System.out::println);
        };
    }
}
