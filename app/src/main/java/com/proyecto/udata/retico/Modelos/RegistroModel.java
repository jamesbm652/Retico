package com.proyecto.udata.retico.Modelos;

import com.proyecto.udata.retico.Objetos.Jugador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by oscal on 23/5/2017.
 */

public class RegistroModel {
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

    public Boolean insertarJugador(Jugador jugador){
        Boolean ingreso = false;
        String res = "false";

        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/registro.php?nombre="+ jugador.getNombre() +
                "&apellido1=" + jugador.getApellido1() + "&apellido2=" + jugador.getApellido2() + "&fechaNacimiento=" +jugador.getFechaNacimiento()+ "&correo=" +
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
        return ingreso;
    }
}
