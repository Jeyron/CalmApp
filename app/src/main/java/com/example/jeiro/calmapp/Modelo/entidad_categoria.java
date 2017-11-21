package com.example.jeiro.calmapp.Modelo;

import java.io.Serializable;

/**
 * Created by rcrodriguez on 8/10/2017.
 */

public class entidad_categoria implements Serializable
{
    private int Id;
    private String Descripcion;
    private int Icono;

    public int getIcono() {
        return Icono;
    }

    public void setIcono(int icono) {
        Icono = icono;
    }

    public entidad_categoria(int id, String descripcion, int icono)
    {
        Id = id;
        Descripcion = descripcion;
        Icono = icono;

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
