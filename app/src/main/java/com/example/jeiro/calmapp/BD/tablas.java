package com.example.jeiro.calmapp.BD;

import android.provider.BaseColumns;

/**
 * Created by rcrodriguez on 20/11/2017.
 */
public final class tablas
{
    private tablas() {}
    /* Tablas de la base de datos */
    public static class tabla_categoria implements BaseColumns
    {
        public static final String TABLE_NAME               = "Categoria";    //
        public static final String COLUMN_NAME_DESCRIPCION  = "Descripcion";  // 1
        public static final String COLUMN_NAME_ICONO        = "Icono";        // 2
    }

    public static class tabla_contenido implements BaseColumns
    {
        public static final String TABLE_NAME           = "Contenido";  //
        public static final String COLUMN_NAME_SITIO    = "Sitio";      // 1
        public static final String COLUMN_NAME_NOMBRE   = "Nombre";     // 2
        public static final String COLUMN_NAME_TIPO     = "Tipo";       // 3
    }

    public static class tabla_sitio implements BaseColumns
    {
        public static final String TABLE_NAME           = "Sitio";      //
        public static final String COLUMN_NAME_LATITUD  = "Latutud";    // 1
        public static final String COLUMN_NAME_LONGITUD = "Longitud";   // 2
        public static final String COLUMN_NAME_NOMBRE   = "Nombre";     // 3
        public static final String COLUMN_NAME_CAT      = "Categoria";  // 4
        public static final String COLUMN_NAME_TELEFONO = "Telefono";   // 5
    }
}
