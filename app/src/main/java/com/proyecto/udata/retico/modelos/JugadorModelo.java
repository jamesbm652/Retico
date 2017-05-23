package com.proyecto.udata.retico.modelos;

import java.util.Date;

/**
 * Created by James on 22/5/2017.
 */

public class JugadorModelo {
    public String Nombre;
    public String Apellido1;
    public String Apellido2;
    public Date FechaNacimiento;
    public String Correo;
    public String Contrasena;
    public String Telefono;

    public JugadorModelo(){}

    public JugadorModelo(String nombre, String apellido1, String apellido2, Date fechaNacimiento,String correo, String contrasena, String telefono){
        this.Nombre = nombre;
        this.Apellido1 = apellido1;
        this.Apellido2 = apellido2;
        this.FechaNacimiento = fechaNacimiento;
        this.Correo = correo;
        this.Contrasena = contrasena;
        this.Telefono = telefono;
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
        Telefono = telefono;
    }
}
