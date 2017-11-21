package com.example.jeiro.calmapp.Negocio;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.example.jeiro.calmapp.R;

/**
 * Created by SHAJIB on 7/16/2017.
 */

public class Function
{

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

    public static int obtener_icono (int categoria)
    {
        switch (categoria)
        {
            case 0:
                return R.drawable.supermercado;
            case 1:
                return R.drawable.banco;
            case 2:
                return R.drawable.hospital;
            case 3:
                return R.drawable.comida;
            case 4:
                return R.drawable.parqueo;
            case 5:
                return R.drawable.aeropuerto;
            case 6:
                return R.drawable.tienda;
            case 7:
                return R.drawable.puerto;
            case 8:
                return R.drawable.parque;
            default:
                return R.drawable.monumento;
        }
    }
}
