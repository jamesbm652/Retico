package com.proyecto.udata.retico.Objetos;

import java.util.Date;

/**
 * Created by James on 4/6/2017.
 */

public class JugadorEquipo {
    private int Id;
    private String Nombre;
    private String Apellido1;
    private String Apellido2;
    private Date FechaNacimiento;
    private String Correo;
    private String Contrasena;
    private String Telefono;

    public JugadorEquipo(String nombre, String apellido1, String apellido2, Date fechaNacimiento, String correo, String contrasena, String telefono) {
        Nombre = nombre;
        Apellido1 = apellido1;
        Apellido2 = apellido2;
        FechaNacimiento = fechaNacimiento;
        Correo = correo;
        Contrasena = contrasena;
        Telefono = telefono;
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
        Telefono = telefono;
    }
}
