package com.proyecto.udata.retico.Modelos;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.JugadorEquipo;
import com.proyecto.udata.retico.Objetos.Reto;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public ArrayList<Reto> obtenerRetosARCEquipos(int idEstado, int idEquipo){
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/listaRetos.php?idEquipo=" + idEquipo +
        " &idEstado=" + idEstado);
        ArrayList<Reto> lista = new ArrayList<>();

        if (cnxExitosa){
            String jsonString = obtenerJsonEnString();
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject r = (JSONObject) jsonArray.get(i);

                    Reto reto = new Reto();
                    reto.setId(r.getInt("IdReto"));
                    reto.setDescripcion(r.getString("Mensaje"));
                    reto.setHora(r.getString("Hora"));

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formato.parse(r.getString("FechaReto"));
                    reto.setFechaReto(fecha);

                    Equipo eRetador = new Equipo();
                    eRetador.setId(r.getInt("IdEquipoRetador"));
                    eRetador.setNombre(r.getString("EquipoRetador"));

                    Equipo eRetado = new Equipo();
                    eRetado.setId(r.getInt("IdEquipoRetado"));
                    eRetado.setNombre(r.getString("EquipoRetado"));

                    reto.setEquipoRetador(eRetador);
                    reto.setEquipoRetado(eRetado);

                    lista.add(reto);
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


    public ArrayList<Reto> obtenerRetosPendientesEquipos(int idEstado, int idEquipo){
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/listaRetosPendientes.php?idEquipo=" + idEquipo +
                " &idEstado=" + idEstado);
        ArrayList<Reto> lista = new ArrayList<>();

        if (cnxExitosa){
            String jsonString = obtenerJsonEnString();
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject r = (JSONObject) jsonArray.get(i);

                    Reto reto = new Reto();
                    reto.setId(r.getInt("IdReto"));
                    reto.setDescripcion(r.getString("Mensaje"));
                    reto.setHora(r.getString("Hora"));

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formato.parse(r.getString("FechaReto"));
                    reto.setFechaReto(fecha);

                    Equipo eRetador = new Equipo();
                    eRetador.setId(r.getInt("IdEquipoRetador"));
                    eRetador.setNombre(r.getString("EquipoRetador"));

                    Equipo eRetado = new Equipo();
                    eRetado.setId(r.getInt("IdEquipoRetado"));
                    eRetado.setNombre(r.getString("EquipoRetado"));

                    reto.setEquipoRetador(eRetador);
                    reto.setEquipoRetado(eRetado);

                    lista.add(reto);
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


    public ArrayList<Reto> obtenerRetosEnviadosEquipos(int idEstado, int idEquipo){
        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/listaRetosEnviados.php?idEquipo=" + idEquipo +
                " &idEstado=" + idEstado);
        ArrayList<Reto> lista = new ArrayList<>();

        if (cnxExitosa){
            String jsonString = obtenerJsonEnString();
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject r = (JSONObject) jsonArray.get(i);

                    Reto reto = new Reto();
                    reto.setId(r.getInt("IdReto"));
                    reto.setDescripcion(r.getString("Mensaje"));
                    reto.setHora(r.getString("Hora"));

                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = formato.parse(r.getString("FechaReto"));
                    reto.setFechaReto(fecha);

                    Equipo eRetador = new Equipo();
                    eRetador.setId(r.getInt("IdEquipoRetador"));
                    eRetador.setNombre(r.getString("EquipoRetador"));

                    Equipo eRetado = new Equipo();
                    eRetado.setId(r.getInt("IdEquipoRetado"));
                    eRetado.setNombre(r.getString("EquipoRetado"));

                    reto.setEquipoRetador(eRetador);
                    reto.setEquipoRetado(eRetado);

                    lista.add(reto);
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

}
