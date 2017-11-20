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
        public static final String COLUMN_NAME_DESCRIPCION  = "Descripcion";// 5
    }

    public static class tabla_iconos implements BaseColumns
    {
        public static final String TABLE_NAME           = "Iconos";      //
        public static final String COLUMN_NAME_CAT      = "Categoria";   // 1
        public static final String COLUMN_NAME_NOMBRE   = "Nombre";      // 2
    }

    public static class tabla_evaluacion implements BaseColumns {
        public static final String TABLE_NAME = "Evaluacion";   //
        public static final String COLUMN_NAME_CURSO = "Curso";        // 1
        public static final String COLUMN_NAME_RUBRO = "Rubro";        // 2
        public static final String COLUMN_NAME_VALOR = "Valor";        // 3
        public static final String COLUMN_NAME_NIVEL = "Nivel";        // 4
        public static final String COLUMN_NAME_PERIODO = "Periodo";      // 5
    }

    public static class tabla_contenido implements BaseColumns
    {
        public static final String TABLE_NAME          = "Contenido";    //
        public static final String COLUMN_NAME_CURSO   = "Curso";        // 1
        public static final String COLUMN_NAME_TIPO    = "Tipo";         // 3
        public static final String COLUMN_NAME_NOMBRE  = "Nombre";       // 4
        public static final String COLUMN_NAME_PERIODO = "Periodo";      // 5
        public static final String COLUMN_NAME_FECHA   = "Fecha";      // 5
    }

    public static class tabla_configuracion implements BaseColumns
    {
        public static final String TABLE_NAME          = "Configuracion";  //
        public static final String COLUMN_NAME_CURSO   = "Colores";        // 1
        public static final String COLUMN_NAME_FECHA   = "Imagenes";       // 2
        public static final String COLUMN_NAME_TIPO    = "Salir";          // 3
    }
}
