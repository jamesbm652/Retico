package com.proyecto.udata.retico.Modelos;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.JugadorEquipo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by James on 4/6/2017.
 */

public class EquipoModel {
    private HttpURLConnection conexion;

    public boolean conexionConServidor(String url){
        int respuesta;

        try {
            URL miUrl = new URL(url);
            conexion = (HttpURLConnection) miUrl.openConnection();
            respuesta = conexion.getResponseCode();

            if (respuesta == HttpURLConnection.HTTP_OK){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String obtenerJsonEnString(){
        StringBuilder resultado = new StringBuilder();
        String linea;

        try {
            InputStream in = new BufferedInputStream(conexion.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((linea = reader.readLine()) != null){
                resultado.append(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado.toString();
    }

    public ArrayList<Equipo> obtenerListaEquipos(){
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/listaEquipos.php");
        ArrayList<Equipo> lista = new ArrayList<>();

        if (cnxExitosa){
            String jsonString = obtenerJsonEnString();
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject j = (JSONObject) jsonArray.get(i);

                    JugadorEquipo p = new JugadorEquipo();
                    p.setId(j.getInt("IdEncargado"));
                    p.setNombre(j.getString("NombreEncargado"));
                    p.setApellido1(j.getString("Apellido1Encargado"));
                    p.setApellido2(j.getString("Apellido2Encargado"));
                    p.setCorreo(j.getString("CorreoEncargado"));
                    p.setContrasena(j.getString("ContrasenaEquipo"));
                    p.setTelefono(j.getString("TelefonoEncargado"));

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formato.parse(j.getString("FechaNacimientoEncargado"));
                    p.setFechaNacimiento(fecha);

                    Equipo e = new Equipo();
                    e.setId(j.getInt("IdEquipo"));
                    e.setNombre(j.getString("NombreEquipo"));
                    e.setContrsaena(j.getString("ContrasenaEquipo"));
                    e.setEncargado(p);

                    lista.add(e);
                }
                return lista;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lista;
        }else{
            return lista;
        }
    }

    public Boolean insertarEquipo(Equipo equipo){
        Boolean ingreso = false;
        String res = "false";
        /*
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/crearEquipo.php?nombre="+ jugador.getNombre() +
                "&apellido1=" + jugador.getApellido1() + "&apellido2=" + jugador.getApellido2() + "&fechaNacimiento=" + fechaCon + "&correo=" +
                jugador.getCorreo() + "&pass=" + jugador.getContrasena() + "&telefono=" + jugador.getTelefono());

        if (cnxExitosa) {
            String jsonString = obtenerJsonEnString();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                res = jsonObject.getString("respuesta");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cnxExitosa && res.equals("true")) {
            ingreso = true;
        }
        */
        return ingreso;
    }
}
