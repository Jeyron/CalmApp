package com.example.jeiro.calmapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jeiro.calmapp.Datos.datos_categoria;
import com.example.jeiro.calmapp.Datos.datos_contenido;
import com.example.jeiro.calmapp.Datos.datos_sitio;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;
import com.example.jeiro.calmapp.Modelo.entidad_contenido;
import com.example.jeiro.calmapp.Modelo.entidad_sitio;
import com.example.jeiro.calmapp.Negocio.Function;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mapa;
    private final LatLng Zent = new LatLng(10.020221, -83.265066);
    private int swap = 1;
    private int swap2 = 0;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

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
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(false);
            mapa.getUiSettings().setCompassEnabled(true);
        }
        mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                try {
                    datos_sitio datos_sitio = new datos_sitio();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + marker.getSnippet()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error, nombre existente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapa.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker)
            {
                datos_sitio datos_sitio = new datos_sitio();
                entidad_sitio entidad_sitio = datos_sitio.obtener_sitio(context, marker.getTitle());
                dialog_sitio dialog_sitio = new dialog_sitio(context, entidad_sitio);
                dialog_sitio.show();
            }
        });
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
            datos_sitio datos_sitio = new datos_sitio();
            datos_categoria datos_categoria = new datos_categoria();

            ArrayList<entidad_sitio> datos = datos_sitio.obtener_sitios(this);
            for(entidad_sitio temp : datos)
            {
                entidad_categoria entidad = datos_categoria.obtener_categoria_por_id(this, temp.getCategoria());
                LatLng p = new LatLng(temp.getLatitud(), temp.getLongitud());
                mapa.addMarker(new MarkerOptions()
                        .position(p)
                        .title(temp.getNombre())
                        .snippet(Integer.toString(temp.getTelefono()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(Function.obtener_icono(entidad.getIcono())))
                        .anchor(0.5f, 0.5f));
            }
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

    class dialog_sitio extends Dialog {
        Context context;
        entidad_sitio Sitio;
        GridView gridview_contenido;
        cargar_contenido cargar_contenido;
        ArrayList<HashMap<String, String>> lista_contenido = new ArrayList<HashMap<String, String>>();

        public dialog_sitio(final Context context, entidad_sitio sitio) {
            super(context);
            this.context = context;
            Sitio = sitio;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_sitio);

            gridview_contenido = findViewById(R.id.gridview_contenidos);
            int iDisplayWidth = getResources().getDisplayMetrics().widthPixels;
            Resources resources = context.getApplicationContext().getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = iDisplayWidth / (metrics.densityDpi / 160f);

            if (dp < 360) {
                dp = (dp - 17) / 2;
                float px = Function.convertDpToPixel(dp, context.getApplicationContext());
                gridview_contenido.setColumnWidth(Math.round(px));
            }

            cargar_contenido = new cargar_contenido();
            cargar_contenido.execute();

            final datos_categoria datos_categoria = new datos_categoria();

            final ArrayList<entidad_categoria> lista_categorias = datos_categoria.obtener_categorias(context);
            String array_categorias[] = new String[lista_categorias.size()];
            for (int i = 0; i < lista_categorias.size(); i++)
                array_categorias[i] = lista_categorias.get(i).getDescripcion();

            final Spinner spn_categoria = findViewById(R.id.spn_categoria);
            final Button btn_sitio_1 = findViewById(R.id.btn_sitio_1);
            final Button btn_sitio_2 = findViewById(R.id.btn_sitio_2);
            final android.support.design.widget.FloatingActionButton btn_agregar = findViewById(R.id.btn_agregar_fotos);
            final android.support.design.widget.FloatingActionButton btn_importar = findViewById(R.id.btn_importar_fotos);
            final EditText txt_nombre = findViewById(R.id.txt_nombre);
            final EditText txt_telefono = findViewById(R.id.txt_telefono);

            final TextView lbl_titulo = findViewById(R.id.dialog_title_sitio);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, array_categorias);
            spn_categoria.setAdapter(adapter);

            btn_sitio_2.setText(context.getResources().getString(R.string.btn_eliminar));

            btn_sitio_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean insertar = false;

                    if (Sitio.getId() == 0)
                        insertar = true;

                    datos_categoria datos_categoria = new datos_categoria();

                    entidad_categoria entidad = datos_categoria.obtener_categoria(v.getContext(), spn_categoria.getSelectedItem().toString());
                    Sitio.setCategoria(entidad.getId());
                    // set textos
                    if
                            (
                            txt_nombre.getText().toString().equals("") ||
                                    txt_telefono.getText().toString().equals("")
                            ) {
                        Toast.makeText(context, "Error, campo vacío", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        Sitio.setTelefono(Integer.parseInt(txt_telefono.getText().toString()));
                        Sitio.setNombre(txt_nombre.getText().toString());

                        datos_sitio datos_sitio = new datos_sitio();
                        entidad_sitio temp = datos_sitio.obtener_sitio(context, txt_nombre.getText().toString());

                        if (temp != null) {
                            Toast.makeText(context, "Error, nombre existente", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (datos_sitio.insertar_sitio(Sitio, insertar, context)) {
                            Toast.makeText(context, "Éxito, sitio agregado", Toast.LENGTH_SHORT).show();
                            LatLng p = new LatLng(Sitio.getLatitud(), Sitio.getLongitud());
                            mapa.addMarker(new MarkerOptions()
                                    .position(p)
                                    .title(Sitio.getNombre())
                                    .snippet(Integer.toString(Sitio.getTelefono()))
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(Function.obtener_icono(entidad.getIcono())))
                                    .anchor(0.5f, 0.5f));
                            ArrayList<ContentProviderOperation> ops =
                                    new ArrayList<ContentProviderOperation>();

                            int rawContactID = ops.size();

                            // Adding insert operation to operations list
                            // to insert a new raw contact in the table ContactsContract.RawContacts
                            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                    .build());

                            // Adding insert operation to operations list
                            // to insert display name in the table ContactsContract.Data
                            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, txt_nombre.getText().toString())
                                    .build());

                            // Adding insert operation to operations list
                            // to insert Mobile Number in the table ContactsContract.Data
                            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, txt_telefono.getText().toString())
                                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                    .build());
                            try {
                                // Executing all the insert operations as a single database transaction
                                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                                Toast.makeText(getBaseContext(), "Contacto agregado", Toast.LENGTH_SHORT).show();
                                dismiss();
                            } catch (RemoteException | OperationApplicationException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Error, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            });
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Obtener.class);
                    startActivity(intent);
                }
            });
//*
            btn_sitio_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        datos_sitio datos_sitio = new datos_sitio();
                        entidad_sitio temp = datos_sitio.obtener_sitio(context, txt_nombre.getText().toString());

                        if (temp != null) {
                            Toast.makeText(context, "Error, nombre existente", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (datos_sitio.eliminar_sitio(Sitio, context)) {
                            Toast.makeText(context, "Éxito, sitio eliminado", Toast.LENGTH_SHORT).show();
                            mapa.clear();
                            swap2 = 1;
                            ver_sitios(null);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Error, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }

            });


            if (Sitio.getId() == 0) {
                btn_sitio_1.setText(context.getResources().getString(R.string.btn_agregar));
                lbl_titulo.setText(context.getResources().getString(R.string.lbl_agregar_sitio));
                btn_sitio_2.setVisibility(View.GONE);
            } else {
                btn_sitio_1.setText(context.getResources().getString(R.string.btn_guardar));
                lbl_titulo.setText(context.getResources().getString(R.string.lbl_editar_sitio));
                btn_sitio_2.setVisibility(View.VISIBLE);
                entidad_categoria entidad_categoria = datos_categoria.obtener_categoria_por_id(context, Sitio.getCategoria());
                for (int i = 0; i < array_categorias.length; i++) {
                    if (entidad_categoria.getDescripcion().equals(array_categorias[i])) {
                        spn_categoria.setSelection(i);
                        break;
                    }
                }
                txt_nombre.setText(Sitio.getNombre());
                txt_telefono.setText(Integer.toString(Sitio.getTelefono()));
            }
            //*/
        }

        class cargar_contenido extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                lista_contenido.clear();
            }

            /**
             * Se ejecuta en segundo plano, permite cargar los datos de los períodos
             * Sin entradas obligatorias, sin salida de parémetros, sin restricciones
             * @param args
             * @return
             */
            protected String doInBackground(String... args)
            {
                datos_contenido datos_contenido = new datos_contenido();
                ArrayList<entidad_contenido> datos = datos_contenido.obtener_contenido_por_sitio(context, Sitio);
                for (entidad_contenido temp : datos)
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("n", temp.getNombre());
                    lista_contenido.add(map);
                }
                return "";
            }

            /**
             * Se ejecuta luego de acabar en background, configura el adaptador en el gridview y establece eventos
             * Sin entradas por parte del programador
             * Sin salida de parámetros y sin restricciones
             */
            @Override
            protected void onPostExecute(String xml)
            {
                adaptador_images adapter = new adaptador_images((Activity) context, lista_contenido);
                gridview_contenido.setAdapter(adapter);
                //*
                gridview_contenido.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
                    {
                            Intent intent = new Intent(context, Visualizar.class);
                            intent.putExtra("path",MainActivity.rutaProyecto + File.separator + lista_contenido.get(position).get("n"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        //*/
                    }
                });
                //*/
            }
        }
    }



    class adaptador_images extends BaseAdapter
    {
        private Activity activity;
        private ArrayList<HashMap< String, String >> data;

        public adaptador_images(Activity a, ArrayList < HashMap < String, String >> d) {
            activity = a;
            data = d;
        }
        @Override
        public int getCount() {
            return data.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            vista_images holder;
            HashMap < String, String > song;
            song = data.get(position);

            String nombre = song.get("n");

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try {
                if (convertView == null)
                    holder = new vista_images();
                else
                    holder = (vista_images) convertView.getTag();

                convertView = inflater.inflate(R.layout.layout_imagen, parent, false);
                holder.imagen = convertView.findViewById(R.id.imagen_images);
                Glide.with(activity)
                        .load(Uri.fromFile(new File(MainActivity.rutaProyecto + File.separator + nombre))) // Uri of the picture
                        .into(holder.imagen);
                convertView.setTag(holder);


            }

            catch (Exception e) {}
            return convertView;
        }
    }

    /**
     * Funciona como entidad para los componentes del layout_periodo
     * Sin entradas, salidas o restricciones
     */
    class vista_images
    {
        ImageView imagen;
    }
}