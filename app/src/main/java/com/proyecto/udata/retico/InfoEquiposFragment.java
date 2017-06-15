package com.proyecto.udata.retico;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by James on 4/6/2017.
 */

public class InfoEquiposFragment extends Fragment implements View.OnClickListener{
    EditText txtNombreEquipo, txtEncargado, txtPromedioEdad, txtLocalizacion, txtContacto;
    ListView listaJugadores;
    ImageButton btnBackSpaceMostrarEquipos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_info_equipos, container, false);
        btnBackSpaceMostrarEquipos = (ImageButton)view.findViewById(R.id.btnBackSpaceMostrarEquipos);
        btnBackSpaceMostrarEquipos.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackSpaceMostrarEquipos:
                startActivity(new Intent(v.getContext(),ListaEquipos.class));
                break;
        }
    }
}
