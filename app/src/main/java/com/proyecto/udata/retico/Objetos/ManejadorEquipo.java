package com.proyecto.udata.retico.Objetos;

import com.proyecto.udata.retico.Modelos.EquipoModel;

import java.util.ArrayList;


/**
 * Created by James on 4/6/2017.
 */

public class ManejadorEquipo {
    private ArrayList<Equipo> listaEquipos;

    public ManejadorEquipo() {}

    public ManejadorEquipo(ArrayList<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public ArrayList<Equipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(ArrayList<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public void cargarListaEquipos(){
        listaEquipos = new EquipoModel().obtenerListaEquipos();
    }
}
