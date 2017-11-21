package com.example.jeiro.calmapp.Modelo;

import java.io.Serializable;

/**
 * Created by rcrodriguez on 20/11/2017.
 */

public class entidad_sitio implements Serializable {
    private int Id;
    private float Latitud;
    private float Longitud;
    private String Nombre;
    private String Categoria;
    private int Telefono;

    public entidad_sitio() {
    }

    public entidad_sitio(int id, float latitud, float longitud, String nombre, String categoria, int telefono) {
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

    public float getLatitud() {
        return Latitud;
    }

    public void setLatitud(float latitud) {
        Latitud = latitud;
    }

    public float getLongitud() {
        return Longitud;
    }

    public void setLongitud(float longitud) {
        Longitud = longitud;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }
}