package com.example.jeiro.calmapp.Negocio;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

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
                return 1; // Supermercado
            case 1:
                return 2; // Banco
            case 2:
                return 3; // Hospital
            case 3:
                return 4; // Restaurante
            case 4:
                return 5; // Paqueo
            case 5:
                return 6; // Aeropuerto
            case 6:
                return 7; // Tienda
            case 7:
                return 8; // Puerto
            case 8:
                return 9; // Parque
            default:
                return 10;// Monumento
        }
    }
}
