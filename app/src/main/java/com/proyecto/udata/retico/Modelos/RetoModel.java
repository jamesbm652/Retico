package com.proyecto.udata.retico.Modelos;

import com.proyecto.udata.retico.Objetos.Reto;

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

/**
 * Created by James on 3/7/2017.
 */

public class RetoModel {
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

    public Boolean insertarReto(Reto reto){
        Boolean ingreso = false;
        String res = "false";

        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaCon = formato.format(reto.getFechaReto());

        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/crearReto.php?fecha="+ fechaCon +
                "&idEquipoRetador=" + reto.getEquipoRetador().getId() + "&idEquipoRetado=" + reto.getEquipoRetado().getId() +
                "&hora="+ reto.getHora() + "&mensaje="+ reto.getDescripcion());
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
