package com.proyecto.udata.retico;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.ManejadorEquipo;

import java.util.ArrayList;

public class ListaEquipos extends AppCompatActivity implements ListadoEquiposFragment.OnItemSelectedListener{
    ArrayList<Equipo> listaDeEquipos = null;
    android.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipos);

        ObtenerListaDeWS();
    }

    private void ObtenerListaDeWS(){
        Thread tr = new Thread(){
            @Override
            public void run() {
                ManejadorEquipo manejadorEquipo = new ManejadorEquipo();
                manejadorEquipo.cargarListaEquipos();
                listaDeEquipos = manejadorEquipo.getListaEquipos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!listaDeEquipos.isEmpty()){
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("lista", convertirListaDeObjectAString());
                            ListadoEquiposFragment listadoEquiposFrag = new ListadoEquiposFragment();
                            listadoEquiposFrag.setArguments(bundle);

                            fragmentManager = getFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_Content, listadoEquiposFrag);
                            fragmentTransaction.commit();

                        }
                    }
                });
            }
        };
        tr.start();
    }

    private ArrayList<String> convertirListaDeObjectAString(){
        ArrayList<String> lista = new ArrayList<>();
        for (Equipo e: listaDeEquipos) {
            lista.add(e.getNombre());
        }
        return lista;
    }

    @Override
    public void itemSelected(int item) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        InfoEquiposFragment infoEquiposFrag = new InfoEquiposFragment();
        fragmentTransaction.replace(R.id.fragment_Content, infoEquiposFrag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
