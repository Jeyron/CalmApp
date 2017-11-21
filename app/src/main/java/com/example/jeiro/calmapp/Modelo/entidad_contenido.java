package com.example.jeiro.calmapp.Modelo;

import java.io.Serializable;

/**
 * Created by rcrodriguez on 20/11/2017.
 */

public class entidad_contenido implements Serializable
{
    private int Id;
    private int Categoria;
    private String Nombre;
    private int Tipo;

    public entidad_contenido() {}

    public entidad_contenido(int id, int categoria, String nombre, int tipo)
    {
        Id = id;
        Categoria = categoria;
        Nombre = nombre;
        Tipo = tipo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoria() {
        return Categoria;
    }

    public void setCategoria(int categoria) {
        Categoria = categoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        this.Tipo = tipo;
    }
}
