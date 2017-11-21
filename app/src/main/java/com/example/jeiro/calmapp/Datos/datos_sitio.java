package com.example.jeiro.calmapp.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeiro.calmapp.BD.base_de_datos;
import com.example.jeiro.calmapp.BD.tablas;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;
import com.example.jeiro.calmapp.Modelo.entidad_sitio;

import java.util.ArrayList;

/**
 * Created by rcrodriguez on 20/11/2017.
 */

public class datos_sitio
{
    public datos_sitio() {}

    public boolean insertar_sitio(entidad_sitio sitio, boolean insertar, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            ContentValues values = new ContentValues();
            values.put(tablas.tabla_sitio.COLUMN_NAME_LATITUD,  sitio.getLatitud());
            values.put(tablas.tabla_sitio.COLUMN_NAME_LONGITUD,  sitio.getLongitud());
            values.put(tablas.tabla_sitio.COLUMN_NAME_NOMBRE,  sitio.getNombre());
            values.put(tablas.tabla_sitio.COLUMN_NAME_CAT,  sitio.getCategoria());
            values.put(tablas.tabla_sitio.COLUMN_NAME_TELEFONO,  sitio.getTelefono());
            if (insertar) // insertar
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.insert(tablas.tabla_sitio.TABLE_NAME, null, values);
                db.close();
            }
            else // modificar
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.update(tablas.tabla_sitio.TABLE_NAME, values, "_id=" + sitio.getId(), null);
                db.close();
            }

        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }

    public ArrayList<entidad_sitio> obtener_sitios(Context context)
    {
        ArrayList datos = new ArrayList<entidad_sitio>();
        base_de_datos helper = new base_de_datos(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        try
        {
            Cursor c = db.query
                    (
                            tablas.tabla_sitio.TABLE_NAME, // The table to query
                            null, // The columns to return
                            null, // The columns for the WHERE clause
                            null, // The values for the WHERE clause
                            null, // don't group the rows
                            null, // don't filter by row groups
                            null // The sort order
                    );
            if (c.moveToFirst())
                do
                {
                    entidad_sitio a = new entidad_sitio(c.getInt(0), c.getDouble(1), c.getDouble(2), c.getString(3), c.getInt(4), c.getInt(5));
                    datos.add(a);
                } while (c.moveToNext());
        }
        catch (Exception exc)
        {
            db.close();
            return new ArrayList<entidad_sitio>();
        }
        db.close();
        return datos;
    }

    public boolean eliminar_sitio(entidad_sitio sitio, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            SQLiteDatabase db = helper.getWritableDatabase();
            String whereClause = "_id=?";
            String[] whereArgs = new String[] { String.valueOf(sitio.getId()) };
            db.delete(tablas.tabla_contenido.TABLE_NAME, whereClause, whereArgs);
        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }

    public ArrayList<entidad_sitio> obtener_sitios_por_categoria (Context context, entidad_categoria categoria)
    {
        ArrayList<entidad_sitio> datos = obtener_sitios(context);
        ArrayList<entidad_sitio> resultado = new ArrayList<>();
        for(entidad_sitio temp : datos)
            if(temp.getCategoria() == categoria.getId())
                resultado.add(temp);

        return resultado;
    }

    public entidad_sitio obtener_sitio (Context context, String nombre)
    {
        ArrayList<entidad_sitio> datos = obtener_sitios(context);
        for(entidad_sitio temp : datos)
            if(temp.getNombre().equals(nombre))
                return temp;
        return null;
    }
}

