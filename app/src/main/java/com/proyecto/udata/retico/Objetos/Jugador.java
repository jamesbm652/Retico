package com.proyecto.udata.retico.Objetos;

import com.proyecto.udata.retico.Modelos.JugadorModel;

import java.util.Date;

/**
 * Created by James on 22/5/2017.
 */

public class Jugador {
    public int Id;
    public String Nombre;
    public String Apellido1;
    public String Apellido2;
    public Date FechaNacimiento;
    public String Correo;
    public String Contrasena;
    public String Telefono;

    public Jugador(){}

    public Jugador(int id, String nombre, String apellido1, String apellido2, Date fechaNacimiento, String correo, String contrasena, String telefono){
        this.Id = id;
        this.Nombre = nombre;
        this.Apellido1 = apellido1;
        this.Apellido2 = apellido2;
        this.FechaNacimiento = fechaNacimiento;
        this.Correo = correo;
        this.Contrasena = contrasena;
        this.Telefono = telefono;
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

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String apellido1) {
        Apellido1 = apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String apellido2) {
        Apellido2 = apellido2;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;//
    }

    public Jugador obtenerJugador(String correo, String contrasena){
        return new JugadorModel().obtenerJugador(correo,contrasena);
    }
}
