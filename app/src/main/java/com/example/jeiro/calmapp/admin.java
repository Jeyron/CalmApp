package com.example.jeiro.calmapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.jeiro.calmapp.Datos.datos_categoria;
import com.example.jeiro.calmapp.Datos.datos_contenido;
import com.example.jeiro.calmapp.Modelo.entidad_categoria;

import java.util.ArrayList;

public class admin extends Fragment {

    private OnFragmentInteractionListener mListener;

    public admin() {
        // Required empty public constructor
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spn_lista_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String selected_item = spn_lista_categorias.getItemAtPosition(position).toString();
            }
            @Override public void onNothingSelected(AdapterView<?> arg0) { }
        });
    }

    private void cargar_spinner_categorias ()
    {
        datos_categoria datos_categoria = new datos_categoria();
        ArrayList<entidad_categoria> lista_categorias = datos_categoria.obtener_categorias(getActivity());
        String array_categorias[] = new String[lista_categorias.size()];
        for (int i = 0; i < lista_categorias.size(); i++) array_categorias[i] = lista_categorias.get(i).getDescripcion();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, array_categorias);
        spn_lista_categorias.setAdapter(adapter);
    }

    private void cargar_spinner_iconos ()
    {
        datos_categoria datos_categoria = new datos_categoria();
        String selected_item = spn_lista_categorias.getSelectedItem().toString();
        entidad_categoria entidad_categoria = datos_categoria.obtener_categoria(getActivity(), selected_item);

        datos_contenido datos_iconos = new datos_contenido();
        ArrayList<entidad_categoria> lista_categorias = datos_categoria.obtener_categorias(getActivity());
        String array_categorias[] = new String[lista_categorias.size()];
        for (int i = 0; i < lista_categorias.size(); i++) array_categorias[i] = lista_categorias.get(i).getDescripcion();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, array_categorias);
        spn_lista_categorias.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin, container, false);
        btn_agregar_categoria = v.findViewById(R.id.btn_agregar_categoria);
        btn_eliminar_categoria = v.findViewById(R.id.btn_eliminar_categoria);
        btn_guardar_categoria = v.findViewById(R.id.btn_guardar_categoria);

        btn_agregar_icono = v.findViewById(R.id.btn_agregar_icono);
        btn_eliminar_icono = v.findViewById(R.id.btn_eliminar_icono);
        btn_guardar_icono = v.findViewById(R.id.btn_guardar_icono);


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
