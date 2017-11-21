package com.example.jeiro.calmapp;

/**
 * Created by rcrodriguez on 6/11/2017.
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jeiro.calmapp.Datos.datos_contenido;
import com.example.jeiro.calmapp.Modelo.entidad_contenido;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Obtener extends AppCompatActivity
{
    File mediaFile;
    String name;
    int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener);

        Intent intent = getIntent();
        t =  intent.getIntExtra("t",0);
        //Permisos para poder escribir en ruta Externa
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CapturadorImagen();

    }

    private void CapturadorImagen() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        generarRutaArchivo();
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mediaFile));
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takeVideoIntent, 0);
        }
    }

    public void generarRutaArchivo(){
        java.util.Date date= new java.util.Date();
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(date.getTime());
        name = "IMG_" + timeStamp + ".png";
        mediaFile = new File(MainActivity.rutaProyecto + File.separator + name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(resultCode == RESULT_OK)

            {
                    mediaFile.createNewFile();

                    entidad_contenido imagen = new entidad_contenido(0, MainActivity.categoria, name, 0);
                    datos_contenido datos_contenido = new datos_contenido();
                    datos_contenido.insertar_contenido(imagen, true, this);
                    Toast.makeText(this, "Éxito", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e)
        {
            Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        finish();
    }
}
