package com.example.jeiro.calmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class Stats extends AppCompatActivity {


    private TextView batteryTxt;
    /**
     *
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        batteryTxt = (TextView) this.findViewById(R.id.txt_nivel_bateria);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        /**
         * Parte donde se encarga de dividir los procesos que actualmente se encuentran utilzando
         * memoria del CPU
         */
        String tempString = executeTop();

        tempString = tempString.replaceAll(",", "");
        tempString = tempString.replaceAll("User", "");
        tempString = tempString.replaceAll("System", "");
        tempString = tempString.replaceAll("IOW", "");
        tempString = tempString.replaceAll("IRQ", "");
        tempString = tempString.replaceAll("%", "");
        for (int i = 0; i < 10; i++) {
            tempString = tempString.replaceAll("  ", " ");
        }
        tempString = tempString.trim();
        String[] myString = tempString.split(" ");
        int[] cpuUsageAsInt = new int[myString.length];
        for (int i = 0; i < myString.length; i++) {
            myString[i] = myString[i].trim();
            cpuUsageAsInt[i] = Integer.parseInt(myString[i]);
        }


        /**
         * USO DEL CPU
         */
        TextView t2 = (TextView) findViewById(R.id.txt_dato_cpu);
        t2.setText(String.valueOf(cpuUsageAsInt[0])+"%");

        /**
         * Memoria Total
         */
        long tot = Runtime.getRuntime().totalMemory() / 1024;
        String total = String.valueOf(tot);
        TextView t3 = (TextView) findViewById(R.id.txt_memoria_total);
        t3.setText(total+"MB");

        /**
         * Memoria Libre
         */
        long tot2 = Runtime.getRuntime().freeMemory() / 1024;
        String total2 = String.valueOf(tot2);
        TextView t4 = (TextView) findViewById(R.id.txt_memoria_libre);
        t4.setText(total2+"MB");

        /**
         * Memoria Ocupada
         */
        long tot3 = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
        String total3 = String.valueOf(tot3);
        TextView t5 = (TextView) findViewById(R.id.txt_memoria_ocupada);
        t5.setText(total3+"MB");

        /**
         * Extrae la versión del android del dispositivo
         */
        String myVersion = Build.VERSION.RELEASE;
        TextView t6 = (TextView) findViewById(R.id.txt_version_so);
        t6.setText(myVersion);


        /**
         * Información del Almacenamiento Interno
         */
        TextView free = (TextView) findViewById(R.id.txt_interna_libre);
        TextView use = (TextView) findViewById(R.id.txt_interna_ocupado);
        TextView Total = (TextView) findViewById(R.id.txt_interno_total);

        TextView free2 = (TextView) findViewById(R.id.txt_externa_libre);
        TextView use2 = (TextView) findViewById(R.id.txt_externo_ocupado);
        TextView Total2 = (TextView) findViewById(R.id.txt_externo_total);


        /**
         * Información del Almacenamiento Externo
         */
        free.setText(String.valueOf(bytesToHuman(InternalFreeMemory())));
        use.setText(String.valueOf(bytesToHuman(InternalBusyMemory())));
        Total.setText(String.valueOf(bytesToHuman(InternalTotalMemory())));

        free2.setText(String.valueOf(bytesToHuman(ExternalFreeMemory())));
        use2.setText(String.valueOf(bytesToHuman(ExternalBusyMemory())));
        Total2.setText(String.valueOf(bytesToHuman(ExternalTotalMemory())));




    }

    /**
     *
     * @param d
     * @return
     */
    public static String floatForm (double d)
    {
        return new DecimalFormat("#.##").format(d);
    }

    /**
     * Función que se encarga de la conversion entre datos
     * @param size
     * @return
     */
    public static String bytesToHuman (long size)
    {
        /**
         * Diferentes "Divisas"
         */
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        /**
         * Comparaciones con las debidas conversiones entre datos
         */
        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " Kb";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " Mb";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " Gb";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " Tb";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " Pb";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " Eb";

        return "???";
    }

    /**
     * Memoria Total Interna
     * @return
     */
    public long InternalTotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        return Total;
    }

    /**
     * Memoria Disponible Interna
     * @return
     */
    public long InternalFreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return Free;
    }

    /**
     * Memoria Ocupada Interna
     * @return
     */
    public long InternalBusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;
        return Busy;
    }

    /**
     * Memoria Externa Total
     * @return
     */
    public long ExternalTotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        return Total;
    }

    /**
     * Memoria Externa Libre
     * @return
     */
    public long ExternalFreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return Free;
    }

    /**
     * Memoria Externa Ocupada
     * @return
     */
    public long ExternalBusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;
        return Busy;
    }

    /**
     * Funcion que actualiza la bateria
     */
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryTxt.setText(String.valueOf(level) + "%");
        }
    };

    /**
     * Calcula cantidad de tiempo en ejecutar un proceso
     * @return
     */
    private String executeTop() {
        Process p = null;
        BufferedReader in = null;
        String returnString = null;
        try {
            p = Runtime.getRuntime().exec("top -n 1");
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (returnString == null || returnString.contentEquals("")) {
                returnString = in.readLine();
            }
        } catch (IOException e) {
            Log.e("executeTop", "error in getting first line of top");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                p.destroy();
            } catch (IOException e) {
                Log.e("executeTop",
                        "error in closing and destroying top process");
                e.printStackTrace();
            }
        }
        return returnString;
    }
}
