package com.proyecto.udata.retico.Objetos;

import java.util.List;

/**
 * Created by James on 4/6/2017.
 */

public class ManejadorEquipo {
    private List<Equipo> listaEquipos;

    public ManejadorEquipo() {}

    public ManejadorEquipo(List<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public List<Equipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }
}
