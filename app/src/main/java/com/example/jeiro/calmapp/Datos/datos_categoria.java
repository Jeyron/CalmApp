package com.example.jeiro.calmapp.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeiro.calmapp.BD.base_de_datos;
import com.example.jeiro.calmapp.BD.tablas;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;

import java.util.ArrayList;

/**
 * Created by rcrodriguez on 8/10/2017.
 */

public class datos_categoria
{
    public datos_categoria() {}

    public boolean insertar_categoria(entidad_categoria categoria, boolean insertar, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            ContentValues values = new ContentValues();
            values.put(tablas.tabla_categoria.COLUMN_NAME_DESCRIPCION,  categoria.getDescripcion());
            if (insertar) // insertar
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.insert(tablas.tabla_categoria.TABLE_NAME, null, values);
                db.close();
            }
            else // modificar
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.update(tablas.tabla_categoria.TABLE_NAME, values, "_id=" + categoria.getId(), null);
                db.close();
            }

        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }

    public ArrayList<entidad_categoria> obtener_categorias(Context context)
    {
        ArrayList datos = new ArrayList<entidad_categoria>();
        base_de_datos helper = new base_de_datos(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        try
        {
            Cursor c = db.query
                    (
                            tablas.tabla_categoria.TABLE_NAME, // The table to query
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
                    entidad_categoria a = new entidad_categoria(c.getInt(0), c.getString(1));
                    datos.add(a);
                } while (c.moveToNext());
        }
        catch (Exception exc)
        {
            db.close();
            return new ArrayList<entidad_categoria>();
        }
        db.close();
        return datos;
    }

    public boolean eliminar_categoria(entidad_categoria categoria, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            SQLiteDatabase db = helper.getWritableDatabase();
            String whereClause = "_id=?";
            String[] whereArgs = new String[] { String.valueOf(categoria.getId()) };
            db.delete(tablas.tabla_contenido.TABLE_NAME, whereClause, whereArgs);
        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }

    public entidad_categoria obtener_categoria (Context context, String descripcion)
    {
        ArrayList<entidad_categoria> datos = obtener_categorias(context);
        for (entidad_categoria temp : datos)
        {
            if(temp.getDescripcion().equals(descripcion))
                return temp;
        }
        return null;
    }
}
