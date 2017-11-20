package com.example.jeiro.calmapp.Modelo;

import java.io.Serializable;

/**
 * Created by rcrodriguez on 8/10/2017.
 */

public class entidad_categoria implements Serializable
{
    private int Id;
    private String Descripcion;

    public entidad_categoria() {}

    public entidad_categoria(int id,String descripcion)
    {
        Id = id;
        Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
