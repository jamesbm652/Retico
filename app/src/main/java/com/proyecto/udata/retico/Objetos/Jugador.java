package com.proyecto.udata.retico.Objetos;

import android.os.Parcel;
import android.os.Parcelable;

import com.proyecto.udata.retico.Modelos.JugadorModel;
import com.proyecto.udata.retico.Modelos.RegistroModel;

import java.util.Date;

/**
 * Created by James on 22/5/2017.
 */

public class Jugador{
    private static int Id;
    private static String Nombre;
    private static String Apellido1;
    private static String Apellido2;
    private static Date FechaNacimiento;
    private static String Correo;
    private static String Contrasena;
    private static String Telefono;

    public Jugador(){}

    public Jugador(String nombre, String apellido1, String apellido2, Date fechaNacimiento, String correo, String contrasena, String telefono){
        this.Nombre = nombre;
        this.Apellido1 = apellido1;
        this.Apellido2 = apellido2;
        this.FechaNacimiento = fechaNacimiento;
        this.Correo = correo;
        this.Contrasena = contrasena;
        this.Telefono = telefono;
    }

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

    public static int getId() {
        return Id;
    }

    public static void setId(int id) {
        Id = id;
    }

    public static String getNombre() {
        return Nombre;
    }

    public static void setNombre(String nombre) {
        Nombre = nombre;
    }

    public static String getNombreCompleto(){
        return Nombre + " " + Apellido1 + " " + Apellido2;
    }

    public static String getApellido1() {
        return Apellido1;
    }

    public static void setApellido1(String apellido1) {
        Apellido1 = apellido1;
    }

    public static String getApellido2() {
        return Apellido2;
    }

    public static void setApellido2(String apellido2) {
        Apellido2 = apellido2;
    }

    public static Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public static void setFechaNacimiento(Date fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public static String getCorreo() {
        return Correo;
    }

    public static void setCorreo(String correo) {
        Correo = correo;
    }

    public static String getContrasena() {
        return Contrasena;
    }

    public static void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public static String getTelefono() {
        return Telefono;
    }

    public static void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public Jugador obtenerJugador(String correo, String contrasena){
        return new JugadorModel().obtenerJugador(correo,contrasena);
    }

    public Boolean insertarJugador(){
        return new RegistroModel().insertarJugador(this);
    }

    public Boolean modificarJugador(int id,String nombre,String apellido1,String apellido2,Date fechaNac,String correo,String contrasena,String telefono){
        return new JugadorModel().modificarJugador(id,nombre,apellido1,apellido2,fechaNac,correo,contrasena,telefono);
    }
}
