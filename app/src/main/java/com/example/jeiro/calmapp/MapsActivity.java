package com.example.jeiro.calmapp;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeiro.calmapp.Datos.datos_categoria;
import com.example.jeiro.calmapp.Datos.datos_sitio;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;
import com.example.jeiro.calmapp.Modelo.entidad_sitio;
import com.example.jeiro.calmapp.Negocio.Function;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener
{
    private GoogleMap mapa;
    private final LatLng Zent = new LatLng(10.020221, -83.265066);
    private int swap = 1;
    private int swap2 = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override public void onMapReady(GoogleMap googleMap)
    {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Zent, 15));
        /*
        mapa.addMarker(new MarkerOptions()
                .position(Zent)
                .title("Zent")
                .snippet("Zent")
                .icon(BitmapDescriptorFactory
                        .fromResource(android.R.drawable.ic_menu_compass))
                .anchor(0.5f, 0.5f));
        //*/
        mapa.setOnMapClickListener(this);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
        {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(false);
            mapa.getUiSettings().setCompassEnabled(true);
        }
    }

    public void ver_sitios(View view)
    {
        if(swap2%2 == 0)
        {
            mapa.clear();
            swap2++;
        }
        else
        {
            swap2++;
        }
    }

    public void cambiar_mapa(View view)
    {
        switch (swap%5)
        {
            case 0:
                mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                swap++;
                break;
            case 1:
                mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                swap++;
                break;
            case 2:
                mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                swap++;
                break;
            case 3:
                mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                swap++;
                break;
            default:
                mapa.setMapType(GoogleMap.MAP_TYPE_NONE);
                swap++;
                break;
        }
    }

    @Override public void onMapClick(LatLng puntoPulsado)
    {
        entidad_sitio sitio = new entidad_sitio();
        sitio.setLatitud(puntoPulsado.latitude);
        sitio.setLongitud(puntoPulsado.longitude);
        sitio.setId(0);

        dialog_sitio dialog_sitio = new dialog_sitio(this, sitio);
        dialog_sitio.show();
    }

    class dialog_sitio extends Dialog
    {
        Context context;
        entidad_sitio Sitio;

        public dialog_sitio(final Context context, entidad_sitio sitio)
        {
            super(context);
            this.context = context;
            Sitio = sitio;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_sitio);

            datos_categoria datos_categoria = new datos_categoria();
            //entidad_categoria entidad_categoria = new entidad_categoria(;)
            final ArrayList<entidad_categoria> lista_categorias = datos_categoria.obtener_categorias(context);
            String array_categorias[] = new String[lista_categorias.size()];
            for (int i = 0; i < lista_categorias.size(); i++) array_categorias[i] = lista_categorias.get(i).getDescripcion();

            final Spinner spn_categoria     = findViewById(R.id.spn_categoria);
            final Button btn_periodo_1      = findViewById(R.id.btn_sitio_1);
            final Button btn_periodo_2      = findViewById(R.id.btn_sitio_2);
            final EditText txt_nombre       = findViewById(R.id.txt_nombre);
            final EditText txt_telefono     = findViewById(R.id.txt_telefono);

            final TextView lbl_titulo       = findViewById(R.id.dialog_title_sitio);

            txt_nombre.setEnabled(false);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, array_categorias);
            spn_categoria.setAdapter(adapter);

            /*
            for(int i = 0; i < adapter.getCount(); i++)
            {
                if (adapter.getItemId(i))
                {
                    this.spinner_listino.setSelection(i);
                    break;
                }
            }

            //*/

            btn_periodo_1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    boolean insertar = false;

                    if(Sitio.getId() == 0)
                        insertar = true;

                    datos_categoria datos_categoria = new datos_categoria();

                    entidad_categoria entidad = datos_categoria.obtener_categoria(v.getContext(), spn_categoria.getSelectedItem().toString());
                    Sitio.setCategoria(entidad.getId());
                    // set textos
                    if
                            (
                            txt_nombre.getText().toString().equals("") ||
                            txt_telefono.getText().toString().equals("")
                            )
                    {
                        Toast.makeText(context,"Error, campo vacío", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try
                    {
                        Sitio.setTelefono(Integer.parseInt(txt_telefono.getText().toString()));
                        Sitio.setNombre(txt_nombre.getText().toString());

                        datos_sitio datos_sitio = new datos_sitio();
                        entidad_sitio temp = datos_sitio.obtener_sitio(context, txt_nombre.getText().toString());

                        if(temp != null)
                        {
                            Toast.makeText(context, "Error, nombre existente", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(datos_sitio.insertar_sitio(Sitio, insertar, context))
                        {
                            Toast.makeText(context, "Éxito, sitio agregado", Toast.LENGTH_SHORT).show();
                            LatLng p = new LatLng(Sitio.getLatitud(),Sitio.getLongitud());
                            mapa.addMarker(new MarkerOptions()
                                    .position(p)
                                    .title(Sitio.getNombre())
                                    .snippet(Integer.toString(Sitio.getTelefono()))
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(Function.obtener_icono(entidad.getIcono())))
                                    .anchor(0.5f, 0.5f));
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context,"Error, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            });
/*
            btn_periodo_2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }

            });

            if(Sitio == null)
            {
                btn_periodo_1.setText(context.getResources().getString(R.string.btn_agregar));
                lbl_titulo.setText(context.getResources().getString(R.string.lbl_titulo_nuevo_periodo_1));
                txt_nombre.setText(Integer.toString(anno));
                btn_periodo_2.setVisibility(View.GONE);
            }
            else
            {
                btn_periodo_1.setText(context.getResources().getString(R.string.btn_guardar));
                lbl_titulo.setText(context.getResources().getString(R.string.lbl_titulo_nuevo_periodo_2));
                btn_periodo_2.setVisibility(View.VISIBLE);

                for(int i = 0; i < array_modalidad.length; i++)
                {
                    if(Sitio.getModalidad().equals(array_modalidad[i]))
                    {
                        spn_categoria.setSelection(i);
                        break;
                    }
                }

                for(int i = 0; i < array_estados.length; i++)
                {
                    if(Sitio.getEstado().equals(array_estados[i]))
                    {
                        spn_estados.setSelection(i);
                        break;
                    }
                }

                spn_periodos.setSelection(Sitio.getPeriodo() - 1);

                spn_categoria.setEnabled(false);
                spn_periodos.setEnabled(false);

                txt_nombre.setText(Integer.toString(Sitio.getAnno()));
                txt_descripcion.setText(Sitio.getDescripcion());
                txt_telefono.setText(Sitio.getFrase());
            }
            //*/
        }
    }
}