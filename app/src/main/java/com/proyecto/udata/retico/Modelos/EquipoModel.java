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
                JSONObject jsonObject = new JSONObject(jsonString);
                for (int i = 0; i < jsonObject.length(); i++){
                    JSONObject j = (JSONObject) jsonObject.get("Equipo"+i);

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
                    e.setLocalizacion(j.getString("LocalizacionEquipo"));
                    e.setEncargado(p);

                    ArrayList<JugadorEquipo> listaJugadores = new ArrayList<>();
                    JSONObject listaJug = (JSONObject) j.get("Jugadores");
                    for (int h = 0; h < listaJug.length(); h++){
                        JSONObject jug = (JSONObject) listaJug.get("Jugador"+h);
                        JugadorEquipo jugador = new JugadorEquipo();
                        jugador.setNombre(jug.getString("Nombre"));
                        jugador.setApellido1(jug.getString("Apellido1"));
                        jugador.setApellido2(jug.getString("Apellido2"));

                        Date fechaJugador = formato.parse(jug.getString("FechaNacimiento"));
                        jugador.setFechaNacimiento(fechaJugador);
                        listaJugadores.add(jugador);
                    }

                    e.setListaJugadores(listaJugadores);
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

        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/crearEquipo.php?nombre="+ equipo.getNombre() +
                "&pass=" + equipo.getContrsaena() + "&localizacion=" + equipo.getLocalizacion() + "&idEncargado="+ equipo.getEncargado().getId());
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

    public Boolean unirJugador(int idJugador, int idEquipo){
        Boolean unir = false;
        String res = "false";

        boolean cnxExitosa = conexionConServidor("https://ws-android-gestion-multim.c9users.io/unirJugador.php?idJugador="+idJugador+ "&idEquipo="+idEquipo);
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
            unir = true;
        }

        return unir;
    }

}
