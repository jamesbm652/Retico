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
import java.text.DateFormat;
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

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formato.parse(j.getString("FechaNacimiento"));

                    jugador = new Jugador();
                    jugador.setId(Integer.parseInt(j.getString("Id")));
                    jugador.setNombre(j.getString("Nombre"));
                    jugador.setApellido1(j.getString("Apellido1"));
                    jugador.setApellido2(j.getString("Apellido2"));
                    jugador.setFechaNacimiento(fecha);
                    jugador.setCorreo(j.getString("Correo"));
                    jugador.setContrasena(j.getString("Contraseña"));
                    jugador.setTelefono(j.getString("Telefono"));

                    return jugador;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jugador;
        }else{
            return jugador;
        }
    }



    public Boolean modificarJugador(int id, String nombre,String apellido1, String apellido2, Date fechaNac, String correo, String contrasena,String telefono){
        Boolean ingreso = false;
        String res = "false";

        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaCon = formato.format(fechaNac);

        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/modificarJugador.php?id="+id+"&nombre="+nombre+
                "&apellido1="+apellido1+"&apellido2="+apellido2+"&fechaNacimiento="+fechaCon+
                "&correo="+correo+"&pass="+contrasena+"&telefono="+telefono);

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
