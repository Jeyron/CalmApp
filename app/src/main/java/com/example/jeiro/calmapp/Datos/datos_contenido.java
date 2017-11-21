package com.example.jeiro.calmapp.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeiro.calmapp.BD.base_de_datos;
import com.example.jeiro.calmapp.BD.tablas;
import com.example.jeiro.calmapp.Modelo.entidad_contenido;

import java.util.ArrayList;

/**
 * Created by rcrodriguez on 20/11/2017.
 */

public class datos_contenido
{
    public boolean insertar_contenido(entidad_contenido contenido, boolean insertar, Context context)
    {
    base_de_datos helper = new base_de_datos(context);
    try
    {
        ContentValues values = new ContentValues();
        values.put(tablas.tabla_contenido.COLUMN_NAME_CAT,    contenido.getCategoria());
        values.put(tablas.tabla_contenido.COLUMN_NAME_NOMBRE, contenido.getNombre());
        values.put(tablas.tabla_contenido.COLUMN_NAME_TIPO,   contenido.getTipo());
        if (insertar) // insertar
        {
            SQLiteDatabase db = helper.getWritableDatabase();
            db.insert(tablas.tabla_contenido.TABLE_NAME, null, values);
            db.close();
        }
        else // modificar
        {
            SQLiteDatabase db = helper.getWritableDatabase();
            db.update(tablas.tabla_contenido.TABLE_NAME, values, "_id=" + contenido.getId(), null);
            db.close();
        }

    }
    catch (Exception exc)
    {
        return false;
    }
    return true;
}

    public ArrayList<entidad_contenido> obtener_contenidos(Context context)
    {
        ArrayList datos = new ArrayList<entidad_contenido>();
        base_de_datos helper = new base_de_datos(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        try
        {
            Cursor c = db.query
                    (
                            tablas.tabla_contenido.TABLE_NAME, // The table to query
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
                    entidad_contenido a = new entidad_contenido(c.getInt(0), c.getInt(1), c.getString(2), c.getInt(3));
                    datos.add(a);
                } while (c.moveToNext());
        }
        catch (Exception exc)
        {
            db.close();
            return new ArrayList<entidad_contenido>();
        }
        db.close();
        return datos;
    }

    public boolean eliminar_contenido(entidad_contenido contenido, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            SQLiteDatabase db = helper.getWritableDatabase();
            String whereClause = "_id=?";
            String[] whereArgs = new String[] { String.valueOf(contenido.getId()) };
            db.delete(tablas.tabla_contenido.TABLE_NAME, whereClause, whereArgs);
        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }
}
