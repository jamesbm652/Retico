package com.proyecto.udata.retico.Objetos;

import java.util.ArrayList;

/**
 * Created by James on 4/6/2017.
 */

public class Equipo {
    private int Id;
    private String Nombre;
    private String Contrsaena;
    private JugadorEquipo Encargado;
    private ArrayList<JugadorEquipo> listaJugadores;

    public Equipo(){}

    public Equipo(String nombre, String contrsaena, JugadorEquipo encargado, ArrayList<JugadorEquipo> listaJugadores) {
        Nombre = nombre;
        Contrsaena = contrsaena;
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
        return true;
    }

    public Boolean modificarEquipo(){
        return true;
    }
}
