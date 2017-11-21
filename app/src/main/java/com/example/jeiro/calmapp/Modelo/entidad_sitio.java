package com.example.jeiro.calmapp.Modelo;

import java.io.Serializable;

/**
 * Created by rcrodriguez on 20/11/2017.
 */

public class entidad_sitio implements Serializable {
    private int Id;
    private double Latitud;
    private double Longitud;
    private String Nombre;
    private int Categoria;
    private int Telefono;

    public entidad_sitio() {
    }

    public entidad_sitio(int id, double latitud, double longitud, String nombre, int categoria, int telefono) {
        Id = id;
        Latitud = latitud;
        Longitud = longitud;
        Nombre = nombre;
        Categoria = categoria;
        Telefono = telefono;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCategoria() {
        return Categoria;
    }

    public void setCategoria(int categoria) {
        Categoria = categoria;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }
}