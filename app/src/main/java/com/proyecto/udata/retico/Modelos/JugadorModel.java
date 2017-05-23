package com.proyecto.udata.retico.Modelos;

import com.proyecto.udata.retico.Objetos.Jugador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by James on 22/5/2017.
 */

public class JugadorModel {
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

    public Jugador obtenerJugador(String correo, String contrasena){
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/login.php?correo="
                +correo+"&pass="+contrasena);
        Jugador jugador = null;
        if (cnxExitosa){
            String jsonString = obtenerJsonEnString();
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                    JSONObject j = (JSONObject) jsonArray.get(0);

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha = formato.parse(j.getString("FechaNacimiento"));

                     jugador = new Jugador(Integer.parseInt(j.getString("Id")), j.getString("Nombre"), j.getString("Apellido1"),
                            j.getString("Apellido2"), fecha, j.getString("Correo"),
                            j.getString("Contrasena"),j.getString("Telefono") );

                    return jugador;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jugador;
        }else{
            return jugador;
        }
    }


}
