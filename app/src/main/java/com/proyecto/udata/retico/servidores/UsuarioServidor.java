package com.proyecto.udata.retico.servidores;

import com.proyecto.udata.retico.modelos.UsuarioModelo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by James on 22/5/2017.
 */

public class UsuarioServidor {
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

    public UsuarioModelo obtenerUsuario(String correo, String contrasena){
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/login.php?correo="
                +correo+"&pass="+contrasena);
        if (cnxExitosa){
            String jsonString = obtenerJsonEnString();
            //Falta codigo para procesar
            return new UsuarioModelo();
        }else{
            return null;
        }
    }


}
