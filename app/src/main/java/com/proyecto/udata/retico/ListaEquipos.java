package com.proyecto.udata.retico;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.JugadorEquipo;
import com.proyecto.udata.retico.Objetos.ManejadorEquipo;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;
import java.util.Calendar;

public class ListaEquipos extends AppCompatActivity implements ListadoEquiposFragment.OnItemSelectedListener{
    ArrayList<Equipo> listaDeEquipos = null;
    android.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipos);

        ObtenerListaDeWS();
    }

    private void ObtenerListaDeWS(){
        Thread tr = new Thread(){
            @Override
            public void run() {
                ManejadorEquipo manejadorEquipo = new ManejadorEquipo();
                manejadorEquipo.cargarListaEquipos();
                listaDeEquipos = manejadorEquipo.getListaEquipos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!listaDeEquipos.isEmpty()){
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("lista", convertirListaDeObjectAString());
                            ListadoEquiposFragment listadoEquiposFrag = new ListadoEquiposFragment();
                            listadoEquiposFrag.setArguments(bundle);

                            fragmentManager = getFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_Content, listadoEquiposFrag);
                            fragmentTransaction.commit();

                        }
                    }
                });
            }
        };
        tr.start();
    }

    private ArrayList<String> convertirListaDeObjectAString(){
        ArrayList<String> lista = new ArrayList<>();
        for (Equipo e: listaDeEquipos) {
            lista.add(e.getNombre());
        }
        return lista;
    }

    private ArrayList<String> convertirListaDeJugadoresAString(Equipo e){
        ArrayList<String> jugadores = new ArrayList<>();
        for (JugadorEquipo j: e.getListaJugadores()) {
            jugadores.add(j.getNombre() +" "+ j.getApellido1() +" "+ j.getApellido2());
        }
        return jugadores;
    }

    @Override
    public void itemSelected(int item) {
        Bundle bundle2 = new Bundle();
        Equipo equipoSeleccionado = listaDeEquipos.get(item);
        JugadorEquipo encargado = equipoSeleccionado.getEncargado();

        bundle2.putString("nombreEquipo", equipoSeleccionado.getNombre());
        bundle2.putInt("idEquipo",equipoSeleccionado.getId());
        bundle2.putString("nombreEncargado", encargado.getNombre()+" "+encargado.getApellido1()+" "+encargado.getApellido2());
        bundle2.putString("localizacion", equipoSeleccionado.getLocalizacion());
        bundle2.putString("contacto", encargado.getTelefono());
        bundle2.putString("contrasena",equipoSeleccionado.getContrsaena());
        bundle2.putInt("edadPromedio", calcularPromedioEdad(equipoSeleccionado));
        bundle2.putStringArrayList("listaJugadores", convertirListaDeJugadoresAString(equipoSeleccionado));
        InfoEquiposFragment infoEquiposFrag = new InfoEquiposFragment();
        infoEquiposFrag.setArguments(bundle2);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_Content, infoEquiposFrag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private int calcularPromedioEdad(Equipo e){
        Calendar cal = Calendar.getInstance();
        LocalDate hoy = LocalDate.now();
        Period p = null;
        int edadTotal = 0;
        for (JugadorEquipo j: e.getListaJugadores()) {
            cal.setTime(j.getFechaNacimiento());
            LocalDate fechaNacimiento = new LocalDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
            p = new Period(fechaNacimiento, hoy, PeriodType.years());
            edadTotal += p.getYears();
        }
        return edadTotal / e.getListaJugadores().size();
    }
}
