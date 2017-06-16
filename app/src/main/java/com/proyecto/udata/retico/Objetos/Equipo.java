package com.proyecto.udata.retico.Objetos;

import com.proyecto.udata.retico.Modelos.EquipoModel;

import java.util.ArrayList;

/**
 * Created by James on 4/6/2017.
 */

public class Equipo {
    private int Id;
    private String Nombre;
    private String Contrsaena;
    private String Localizacion;
    private JugadorEquipo Encargado;
    private ArrayList<JugadorEquipo> listaJugadores;

    public Equipo(){}

    public Equipo(String nombre, String contrsaena, String localizacion, JugadorEquipo encargado, ArrayList<JugadorEquipo> listaJugadores) {
        Nombre = nombre;
        Contrsaena = contrsaena;
        Localizacion = localizacion;
        Encargado = encargado;
        this.listaJugadores = listaJugadores;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContrsaena() {
        return Contrsaena;
    }

    public void setContrsaena(String contrsaena) {
        Contrsaena = contrsaena;
    }

    public String getLocalizacion() {
        return Localizacion;
    }

    public void setLocalizacion(String localizacion) {
        Localizacion = localizacion;
    }

    public JugadorEquipo getEncargado() {
        return Encargado;
    }

    public void setEncargado(JugadorEquipo encargado) {
        Encargado = encargado;
    }

    public ArrayList<JugadorEquipo> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ArrayList<JugadorEquipo> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public Boolean insertarEquipo(){
        return new EquipoModel().insertarEquipo(this);
    }

    public Boolean modificarEquipo(){
        return true;
    }

    public Boolean unirJugador(int idJugador,int idEquipo){return new EquipoModel().unirJugador(idJugador,idEquipo);}
}
