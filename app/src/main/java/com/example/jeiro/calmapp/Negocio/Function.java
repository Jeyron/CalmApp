package com.example.jeiro.calmapp.Negocio;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by SHAJIB on 7/16/2017.
 */

public class Function {


    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_ESTADO = "estado";
    public static final String KEY_FRASE  = "frase";
    public static final String KEY_NOTA   = "nota";

    public static final String KEY_PERIODO = "perido";
    public static final String KEY_CURSO   = "curso";
    public static final String KEY_TIPO    = "tipo";

    public static final String FOTO_TYPE  = ".png";
    public static final String VIDEO_TYPE = ".mp4";
    public static final String AUDIO_TYPE = ".mp3";
    public static final String NOTA_TYPE  = ".txt";
    public static final String ASIG_TYPE  = ".asig";

    public static  boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
