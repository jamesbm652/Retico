package com.proyecto.udata.retico;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by James on 4/6/2017.
 */

public class InfoEquiposFragment extends Fragment{
    EditText txtNombreEquipo, txtEncargado, txtPromedioEdad, txtLocalizacion, txtContacto;
    ListView listaJugadores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_info_equipos, container, false);

        inicializarComponentes(view);
        txtNombreEquipo.setText(getArguments().getString("nombreEquipo"));
        txtEncargado.setText(getArguments().getString("nombreEncargado"));
        txtPromedioEdad.setText(""+getArguments().getInt("edadPromedio"));
        txtLocalizacion.setText(getArguments().getString("localizacion"));
        txtContacto.setText(getArguments().getString("contacto"));

        ArrayList<String> elementosLista = getArguments().getStringArrayList("listaJugadores");
        ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, elementosLista);
        listaJugadores.setAdapter(adaptador);

        return view;
    }

    private void inicializarComponentes(View v){
        txtNombreEquipo = (EditText)v.findViewById(R.id.txtNombreEquipo);
        txtEncargado = (EditText)v.findViewById(R.id.txtEncargado);
        txtPromedioEdad = (EditText)v.findViewById(R.id.txtPromedioEdad);
        txtLocalizacion = (EditText)v.findViewById(R.id.txtLocalizacion);
        txtContacto = (EditText)v.findViewById(R.id.txtContacto);
        listaJugadores = (ListView)v.findViewById(R.id.listaJugadores);
    }
}
