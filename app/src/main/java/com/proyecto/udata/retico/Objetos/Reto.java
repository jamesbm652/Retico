package com.proyecto.udata.retico.Objetos;

import com.proyecto.udata.retico.Modelos.RetoModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by James on 3/7/2017.
 */

public class Reto {
    private int Id;
    private Date FechaReto;
    private String Hora;
    private Equipo EquipoRetador;
    private Equipo EquipoRetado;
    private String Descripcion;

    public Reto(){}

    public Reto(Date fechaReto, String hora, Equipo equipoRetador, Equipo equipoRetado, String descripcion) {
        FechaReto = fechaReto;
        Hora = hora;
        EquipoRetador = equipoRetador;
        EquipoRetado = equipoRetado;
        Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getFechaReto() {
        return FechaReto;
    }

    public void setFechaReto(Date fechaReto) {
        FechaReto = fechaReto;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public Equipo getEquipoRetador() {
        return EquipoRetador;
    }

    public void setEquipoRetador(Equipo equipoRetador) {
        EquipoRetador = equipoRetador;
    }

    public Equipo getEquipoRetado() {
        return EquipoRetado;
    }

    public void setEquipoRetado(Equipo equipoRetado) {
        EquipoRetado = equipoRetado;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Boolean insertarReto(){
        return new RetoModel().insertarReto(this);
    }

    public boolean cambiarEstadoReto(int idEstado){ return new RetoModel().cambiarEstadoReto(this.Id, idEstado);}

}
