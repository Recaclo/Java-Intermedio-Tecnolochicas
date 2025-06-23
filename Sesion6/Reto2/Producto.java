package Sesion6.Reto2;

package com.ejemplo.inventario.modelo;

import jakarta.persistence.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    // Constructores
    public Producto() {}

    public Producto(String nombre) {
        this.nombre = nombre;
    }

    public Producto(String nombre, Marca marca) {
        this.nombre = nombre;
        this.marca = marca;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
