package com.example.jeiro.calmapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeiro.calmapp.Datos.datos_categoria;
import com.example.jeiro.calmapp.Datos.datos_contenido;
import com.example.jeiro.calmapp.Datos.datos_sitio;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;
import com.example.jeiro.calmapp.Modelo.entidad_contenido;
import com.example.jeiro.calmapp.Modelo.entidad_sitio;

import java.util.ArrayList;

public class Categorias extends AppCompatActivity
{
    int item_selected_id;

    Button btn_eliminar_categoria;
    Button btn_agregar_categoria;
    Button btn_guardar_categoria;


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

        spn_lista_iconos = (Spinner) findViewById(R.id.spn_lista_iconos);
        spn_lista_categorias = (Spinner) findViewById(R.id.spn_lista_categorias);

        spn_lista_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String selected_item = spn_lista_categorias.getItemAtPosition(position).toString();
                EditText texto = (EditText) findViewById(R.id.txt_descripcion);
                texto.setText(selected_item);
                cargar_spinner_iconos();
            }
            @Override public void onNothingSelected(AdapterView<?> arg0) { }
        });

        btn_agregar_categoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    EditText texto = (EditText) findViewById(R.id.txt_descripcion);
                    if (texto.getText().toString().equals(""))
                    {
                        Toast.makeText(v.getContext(), "Error, vacío", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    datos_categoria datos_categoria = new datos_categoria();

                    entidad_categoria entidad = datos_categoria.obtener_categoria(v.getContext(), texto.getText().toString());
                    if (entidad != null) {
                        Toast.makeText(v.getContext(), "Error, nombre existente", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                        entidad = new entidad_categoria(0, texto.getText().toString(), spn_lista_iconos.getSelectedItemPosition());

                    if(datos_categoria.insertar_categoria(entidad,true, v.getContext()))
                    {
                        cargar_spinner_categorias();
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "Error, algo pasó", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(v.getContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
         });

        btn_guardar_categoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    EditText texto = (EditText) findViewById(R.id.txt_descripcion);
                    if (texto.getText().toString().equals("")) {
                        Toast.makeText(v.getContext(), "Error, vacío", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    datos_categoria datos_categoria = new datos_categoria();

                    entidad_categoria entidad = datos_categoria.obtener_categoria(v.getContext(), texto.getText().toString());
                    if (entidad != null)
                    {
                        Toast.makeText(v.getContext(), "Error, nombre existente", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                        entidad = datos_categoria.obtener_categoria(v.getContext(), spn_lista_categorias.getSelectedItem().toString());

                    entidad.setDescripcion(texto.getText().toString());
                    entidad.setIcono(spn_lista_iconos.getSelectedItemPosition());
                    if(datos_categoria.insertar_categoria(entidad,false, v.getContext()))
                    {
                        cargar_spinner_categorias();
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "Error, algo pasó", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(v.getContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_eliminar_categoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    final View v2 = v;
                    final datos_categoria datos_categoria = new datos_categoria();
                    datos_sitio datos_sitio = new datos_sitio();
                    final entidad_categoria entidad = datos_categoria.obtener_categoria(v.getContext(), spn_lista_categorias.getSelectedItem().toString());
                    ArrayList<entidad_sitio> sitios = datos_sitio.obtener_sitios_por_categoria(v.getContext(), entidad);
                    if(sitios.size() != 0)
                    {
                        Toast.makeText(v.getContext(), "Error, categoria en uso", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    if(datos_categoria.eliminar_categoria(entidad, v2.getContext()))
                                    {
                                        cargar_spinner_categorias();
                                    }
                                    else
                                    {
                                        Toast.makeText(v2.getContext(), "Error, algo pasó", Toast.LENGTH_SHORT).show();
                                    }
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new  AlertDialog.Builder(v.getContext());
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(v.getContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cargar_spinner_categorias();
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
            String selected_item = spn_lista_categorias.getItemAtPosition(0).toString();
            EditText texto = (EditText) findViewById(R.id.txt_descripcion);
            texto.setText(selected_item);
            cargar_spinner_iconos();
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

            String array_contenidos[] = {"Supermercado", "Banco", "Hospital", "Restaurante", "Parqueo", "Aeropuerto", "Tienda", "Puerto", "Parque", "Monumento"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_contenidos);
            spn_lista_iconos.setAdapter(adapter);

            spn_lista_iconos.setSelection(entidad_categoria.getIcono());
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navegacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sensor_temperatura) {
            return true;
        }
        if (id == R.id.sensor_temperatura) {
            return true;
        }
        if (id == R.id.datos_sistema) {
            Intent intent = new Intent(Categorias.this, Stats.class);
            startActivity(intent);
        }
        if (id == R.id.opc_mapa) {
            Intent intent = new Intent(Categorias.this, MapsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
