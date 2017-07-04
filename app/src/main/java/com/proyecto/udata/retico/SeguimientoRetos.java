package com.proyecto.udata.retico;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.Jugador;
import com.proyecto.udata.retico.Objetos.ManejadorEquipo;
import com.proyecto.udata.retico.Objetos.ManejadorRetos;
import com.proyecto.udata.retico.Objetos.Reto;

import java.util.ArrayList;

public class SeguimientoRetos extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnAtras;
    TextView titulo;
    ListView listViewRetos;
    FloatingActionButton FABRetos;

    Spinner cbxMisEquipos;
    ArrayList<Reto> listaRetos = new ArrayList<>();
    ArrayList<Equipo> listaMisEquipos = new ArrayList<>();
    ArrayList<String> listaStringRetos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_retos);

        titulo = (TextView)findViewById(R.id.tituloReto);
        listViewRetos = (ListView)findViewById(R.id.listaRetos);
        btnAtras = (ImageButton)findViewById(R.id.btnBackSpaceListaRetos);
        btnAtras.setOnClickListener(this);
        FABRetos = (FloatingActionButton)findViewById(R.id.btnFABRetos);
        FABRetos.setOnClickListener(this);
        cbxMisEquipos = (Spinner)findViewById(R.id.cbxMisEquipos);

        cargarListaMisEquipos();
        cbxMisEquipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listaRetos.clear();
                listaStringRetos.clear();
                titulo.setText("Retos ");
                validarTipoReto(position,getIntent().getExtras().getString("tipoReto"));
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }


    private void cargarListaMisEquipos(){
        Thread hiloCargarLista = new Thread(){
            @Override
            public void run() {
                ManejadorEquipo manejadorMisEquipos = new ManejadorEquipo();
                manejadorMisEquipos.cargarMisEquipos(new Jugador().getId());
                listaMisEquipos = manejadorMisEquipos.getListaEquipos();
                final ArrayList<String> listaMisEquiposString = convertirListaMisEquiposAString(listaMisEquipos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaMisEquiposString);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cbxMisEquipos.setAdapter(dataAdapter);
                    }
                });
            }
        };
        hiloCargarLista.start();
    }

    private ArrayList<String> convertirListaMisEquiposAString(ArrayList<Equipo> listaEquipos){
        ArrayList<String> listaStringMisEquipos = new ArrayList<>();
        for (Equipo e: listaEquipos) {
            listaStringMisEquipos.add(e.getNombre());
        }
        return listaStringMisEquipos;
    }

    private void cargarListaRetosAListView(){
        //listaRetos.clear();
        //listaStringRetos.clear();
        ArrayAdapter adaptador;

        for (Reto e: listaRetos) {
            listaStringRetos.add(e.getFechaReto() + "");
        }
        if(listaStringRetos.isEmpty()){
            listaStringRetos.add("No tiene retos");
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaStringRetos);

            listViewRetos.setAdapter(adaptador);
        }else{

            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaStringRetos);

            listViewRetos.setAdapter(adaptador);

            listViewRetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),position,Toast.LENGTH_SHORT).show();
                    //onItemSelectedListener.itemSelected(position);
                }
            });
        }
    }


    private void validarTipoReto(int posicion,String tipo){
        Reto reto = new Reto();
        switch (tipo){
            case "Pendiente":
                titulo.setText(titulo.getText() + "pendientes");
                obtenerRetosPendientes(1,listaMisEquipos.get(posicion).getId());
                break;
            case "Enviado":
                titulo.setText(titulo.getText() + "enviados");
                obtenerRetosEnviados(1,listaMisEquipos.get(posicion).getId());
                break;
            case "Rechazado":
                titulo.setText(titulo.getText() + "rechazados");
                obtenerRetosARC(2,listaMisEquipos.get(posicion).getId());
                break;
            case "Aceptado":
                titulo.setText(titulo.getText() + "aceptados");
                obtenerRetosARC(3,listaMisEquipos.get(posicion).getId());
                break;
            case "Cancelado":
                titulo.setText(titulo.getText() + "cancelados");
                obtenerRetosARC(4,listaMisEquipos.get(posicion).getId());
                break;
        }
    }

    private void obtenerRetosARC(final int idEstado, final int idEquipo){
        Thread tr = new Thread(){
            @Override
            public void run() {
                ManejadorRetos mRetos = new ManejadorRetos();
                mRetos.obtenerRetosARCEquipos(idEstado,idEquipo);
                listaRetos = mRetos.obtenerListaRetos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cargarListaRetosAListView();
                    }
                });
            }
        };
        tr.start();
    }

    private void obtenerRetosEnviados(final int idEstado, final int idEquipo){
        Thread tr = new Thread(){
            @Override
            public void run() {
                ManejadorRetos mRetos = new ManejadorRetos();
                mRetos.obtenerRetosEnviadosEquipos(idEstado,idEquipo);
                listaRetos = mRetos.obtenerListaRetos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cargarListaRetosAListView();
                    }
                });
            }
        };
        tr.start();
    }

    private void obtenerRetosPendientes(final int idEstado, final int idEquipo){
        Thread tr = new Thread(){
            @Override
            public void run() {
                ManejadorRetos mRetos = new ManejadorRetos();
                mRetos.obtenerRetosPendientesEquipos(idEstado,idEquipo);
                listaRetos = mRetos.obtenerListaRetos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cargarListaRetosAListView();
                    }
                });
            }
        };
        tr.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackSpaceListaRetos:
                startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));
                break;
            case R.id.btnFABRetos:
                startActivity(new Intent(getApplicationContext(),RetarEquipos.class));
                break;

        }
    }



}
