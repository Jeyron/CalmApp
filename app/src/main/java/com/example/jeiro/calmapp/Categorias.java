package com.example.jeiro.calmapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jeiro.calmapp.Datos.datos_categoria;
import com.example.jeiro.calmapp.Datos.datos_contenido;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;
import com.example.jeiro.calmapp.Modelo.entidad_contenido;

import java.util.ArrayList;

public class Categorias extends AppCompatActivity
{
    int item_selected_id;

    Button btn_eliminar_categoria;
    Button btn_agregar_categoria;
    Button btn_guardar_categoria;

    Button btn_eliminar_icono;
    Button btn_agregar_icono;
    Button btn_guardar_icono;

    Spinner spn_lista_categorias;
    Spinner spn_lista_iconos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        btn_agregar_categoria = (Button) findViewById(R.id.btn_agregar_categoria);
        btn_eliminar_categoria = (Button) findViewById(R.id.btn_eliminar_categoria);
        btn_guardar_categoria = (Button) findViewById(R.id.btn_guardar_categoria);

        btn_agregar_icono = (Button) findViewById(R.id.btn_agregar_icono);
        btn_eliminar_icono = (Button) findViewById(R.id.btn_eliminar_icono);
        btn_guardar_icono = (Button) findViewById(R.id.btn_guardar_icono);

        spn_lista_iconos = (Spinner) findViewById(R.id.spn_lista_iconos);
        spn_lista_categorias = (Spinner) findViewById(R.id.spn_lista_categorias);

        spn_lista_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String selected_item = spn_lista_categorias.getItemAtPosition(position).toString();
            }
            @Override public void onNothingSelected(AdapterView<?> arg0) { }
        });

        btn_agregar_categoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

             }
         });

        cargar_spinner_categorias();
        cargar_spinner_iconos();
    }

    private void cargar_spinner_categorias ()
    {
        try
        {
            datos_categoria datos_categoria = new datos_categoria();
            ArrayList<entidad_categoria> lista_categorias = datos_categoria.obtener_categorias(this);
            String array_categorias[] = new String[lista_categorias.size()];
            for (int i = 0; i < lista_categorias.size(); i++) array_categorias[i] = lista_categorias.get(i).getDescripcion();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, array_categorias);
            spn_lista_categorias.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargar_spinner_iconos ()
    {
        try {
            datos_categoria datos_categoria = new datos_categoria();
            String selected_item = spn_lista_categorias.getSelectedItem().toString();
            entidad_categoria entidad_categoria = datos_categoria.obtener_categoria(this, selected_item);

            datos_contenido datos_iconos = new datos_contenido();
            ArrayList<entidad_contenido> lista_contenidos = datos_iconos.obtener_contenido_por_categoria(this, entidad_categoria);
            String array_contenidos[] = new String[lista_contenidos.size()];
            for (int i = 0; i < lista_contenidos.size(); i++)
                array_contenidos[i] = lista_contenidos.get(i).getNombre();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_contenidos);
            spn_lista_iconos.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
