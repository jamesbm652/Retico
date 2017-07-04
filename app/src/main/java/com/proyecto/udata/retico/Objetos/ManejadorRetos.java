package com.proyecto.udata.retico.Objetos;

import com.proyecto.udata.retico.Modelos.RetoModel;

import java.util.ArrayList;

/**
 * Created by oscal on 4/7/2017.
 */

public class ManejadorRetos {
    private ArrayList<Reto> listaRetos = new ArrayList<>();

    public ManejadorRetos(){}

    public void agregar(Reto reto){
        listaRetos.add(reto);
    }

    public ArrayList<Reto> obtenerListaRetos(){
        return listaRetos;
    }

    public void obtenerRetosARCEquipos(int idEstado, int idEquipo){
        listaRetos = new RetoModel().obtenerRetosARCEquipos(idEstado,idEquipo);
    }

    public void obtenerRetosPendientesEquipos(int idEstado, int idEquipo){
        listaRetos = new RetoModel().obtenerRetosPendientesEquipos(idEstado,idEquipo);
    }

    public void obtenerRetosEnviadosEquipos(int idEstado, int idEquipo){
        listaRetos = new RetoModel().obtenerRetosEnviadosEquipos(idEstado,idEquipo);
    }
}
