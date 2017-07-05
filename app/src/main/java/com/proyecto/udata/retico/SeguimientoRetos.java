package com.proyecto.udata.retico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    Spinner cbxMisEquipos;
    ArrayList<Reto> listaRetos = new ArrayList<>();
    ArrayList<Equipo> listaMisEquipos = new ArrayList<>();
    ArrayList<String> listaStringRetos = new ArrayList<>();
    int tipoReto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_retos);

        titulo = (TextView)findViewById(R.id.tituloReto);
        listViewRetos = (ListView)findViewById(R.id.listaRetos);
        btnAtras = (ImageButton)findViewById(R.id.btnBackSpaceListaRetos);
        btnAtras.setOnClickListener(this);
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

        final ArrayAdapter adaptador;

        for (Reto e: listaRetos) {
            listaStringRetos.add(e.getEquipoRetador().getNombre() + " vs " + e.getEquipoRetado().getNombre());
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
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    View view2 = (LayoutInflater.from(view.getContext()).inflate(R.layout.info_retos_popup, null));

                    TextView txtEquipoRetador = (TextView)view2.findViewById(R.id.txtEquipoRetador);
                    TextView txtEquipoRetado = (TextView)view2.findViewById(R.id.txtEquipoRetado);
                    TextView txtFechaInfoReto = (TextView)view2.findViewById(R.id.txtFechaInfoReto);
                    TextView txtHoraInfoReto = (TextView)view2.findViewById(R.id.txtHoraInfoReto);
                    TextView txtMensajeInfoReto = (TextView)view2.findViewById(R.id.txtMensajeInfoReto);
                    Button btnAceptarReto = (Button)view2.findViewById(R.id.btnAceptarReto);
                    Button btnRechazarReto = (Button)view2.findViewById(R.id.btnRechazarReto);
                    Button btnCancelarReto = (Button)view2.findViewById(R.id.btnCancelarReto);

                    txtEquipoRetador.setText(listaRetos.get(position).getEquipoRetador().getNombre());
                    txtEquipoRetado.setText(listaRetos.get(position).getEquipoRetado().getNombre());
                    txtFechaInfoReto.setText(listaRetos.get(position).getFechaReto().toString());
                    txtHoraInfoReto.setText(listaRetos.get(position).getHora());
                    txtMensajeInfoReto.setText(listaRetos.get(position).getDescripcion());

                    if(getIntent().getExtras().getString("tipoReto").equals("Pendiente")) {
                        btnAceptarReto.setVisibility(View.VISIBLE);
                        btnRechazarReto.setVisibility(View.VISIBLE);
                        btnCancelarReto.setVisibility(View.INVISIBLE);

                        btnAceptarReto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cambiarEstadoReto(listaRetos.get(position).getId(),3);
                                listaRetos.remove(listaRetos.get(position));

                                adaptador.remove(listaStringRetos.remove(position));
                                adaptador.notifyDataSetChanged();
                                listViewRetos.setAdapter(adaptador);
                            }
                        });

                        btnRechazarReto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cambiarEstadoReto(listaRetos.get(position).getId(),2);
                                listaRetos.remove(listaRetos.get(position));

                                adaptador.remove(listaStringRetos.remove(position));
                                adaptador.notifyDataSetChanged();
                                listViewRetos.setAdapter(adaptador);
                            }
                        });
                    }else if(getIntent().getExtras().getString("tipoReto").equals("Aceptado") || getIntent().getExtras().getString("tipoReto").equals("Enviado")){
                        btnAceptarReto.setVisibility(View.INVISIBLE);
                        btnRechazarReto.setVisibility(View.INVISIBLE);
                        btnCancelarReto.setVisibility(View.VISIBLE);

                        btnCancelarReto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cambiarEstadoReto(listaRetos.get(position).getId(),4);
                                listaRetos.remove(listaRetos.get(position));

                                adaptador.remove(listaStringRetos.remove(position));
                                adaptador.notifyDataSetChanged();
                                listViewRetos.setAdapter(adaptador);
                            }
                        });

                    }else{
                        btnAceptarReto.setVisibility(View.INVISIBLE);
                        btnRechazarReto.setVisibility(View.INVISIBLE);
                        btnCancelarReto.setVisibility(View.INVISIBLE);
                    }
                    builder.setView(view2);
                    builder.show();

                }
            });
        }
    }

    private void cambiarEstadoReto(final int id, final int idEstado){
        boolean retorno;
        Thread tr = new Thread(){
            @Override
            public void run() {
                Reto reto = new Reto();
                reto.setId(id);
                reto.cambiarEstadoReto(idEstado);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(idEstado == 3) Toast.makeText(getApplicationContext(),"Has aceptado el reto",Toast.LENGTH_SHORT).show();
                        if(idEstado == 2) Toast.makeText(getApplicationContext(),"Has rechazado el reto",Toast.LENGTH_SHORT).show();
                        if(idEstado == 4) Toast.makeText(getApplicationContext(),"Has cancelado el reto",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        tr.start();
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
