package com.proyecto.udata.retico;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Equipo;

import java.util.ArrayList;

/**
 * Created by James on 4/6/2017.
 */

public class ListadoEquiposFragment extends Fragment{
    ListView listaEquipos;
    OnItemSelectedListener onItemSelectedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_listado_equipos, container, false);
        listaEquipos = (ListView) view.findViewById(R.id.listaEquipos);
        ArrayList<String> elementosLista = getArguments().getStringArrayList("lista");
        //String[] items = {"Jose", "Fernando", "Walter"};

        ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, elementosLista);

        listaEquipos.setAdapter(adaptador);
        listaEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedListener.itemSelected(position);
                Toast.makeText(getActivity(), "Selecciono la opcion " + (position+1), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onItemSelectedListener = (OnItemSelectedListener) context;
        }catch (Exception e){}
    }


    public interface OnItemSelectedListener {
        void itemSelected(int item);
    }
}
