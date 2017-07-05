package com.proyecto.udata.retico;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.Jugador;
import com.proyecto.udata.retico.Objetos.ManejadorEquipo;

import java.util.ArrayList;

/**
 * Created by James on 4/6/2017.
 */

public class InfoEquiposFragment extends Fragment implements View.OnClickListener{

    ArrayList<String> elementosLista;
    EditText txtNombreEquipo, txtEncargado, txtPromedioEdad, txtLocalizacion, txtContacto;
    ListView listaJugadores;
    ImageButton btnBackSpaceMostrarEquipos;
    Button btnUnirse, btnRetar;
    View view;
    ArrayAdapter adaptador;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_info_equipos, container, false);
        btnBackSpaceMostrarEquipos = (ImageButton)view.findViewById(R.id.btnBackSpaceMostrarEquipos);
        btnUnirse = (Button)view.findViewById(R.id.btnUnirse);
        btnRetar = (Button)view.findViewById(R.id.btnRetar);
        /*if(new Jugador().esEncargado()){
            btnUnirse.setClickable(false);
        }*/
        btnBackSpaceMostrarEquipos.setOnClickListener(this);
        btnUnirse.setOnClickListener(this);
        btnRetar.setOnClickListener(this);

        inicializarComponentes(view);

        txtNombreEquipo.setText(getArguments().getString("nombreEquipo"));
        txtEncargado.setText(getArguments().getString("nombreEncargado"));
        txtPromedioEdad.setText(""+getArguments().getInt("edadPromedio"));
        txtLocalizacion.setText(getArguments().getString("localizacion"));
        txtContacto.setText(getArguments().getString("contacto"));

        elementosLista = getArguments().getStringArrayList("listaJugadores");
        adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, elementosLista);
        listaJugadores.setAdapter(adaptador);

        return view;
    }

    private void inicializarComponentes(View v){
        txtNombreEquipo = (EditText)v.findViewById(R.id.txtNombreEquipo);
        txtEncargado = (EditText)v.findViewById(R.id.txtEncargado);
        txtPromedioEdad = (EditText)v.findViewById(R.id.txtPromedioEdad);
        txtLocalizacion = (EditText)v.findViewById(R.id.txtLocalizacion);
        txtContacto = (EditText)v.findViewById(R.id.txtContacto);
        listaJugadores = (ListView)v.findViewById(R.id.listaJugadores);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackSpaceMostrarEquipos:
                startActivity(new Intent(v.getContext(),ListaEquipos.class));
                break;
            case R.id.btnUnirse:
                if(!validarUnionAlEquipo(new Jugador().getNombreCompleto())){
                    Toast.makeText(getActivity().getApplicationContext(),"Ya eres miembro de " + getArguments().getString("nombreEquipo"),Toast.LENGTH_SHORT).show();
                }else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View view2 = (LayoutInflater.from(getActivity()).inflate(R.layout.campos_popup, null));
                    Button btnUnirseAlEquipo = (Button) view2.findViewById(R.id.btnUnirseAlEquipo);
                    TextView titulo = (TextView) view2.findViewById(R.id.tituloPopup);
                    final EditText pass = (EditText) view2.findViewById(R.id.txtPass);

                    titulo.setText(titulo.getText().toString() + getArguments().getString("nombreEquipo"));
                    btnUnirseAlEquipo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pass.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar la contraseña", Toast.LENGTH_SHORT).show();
                            } else {
                                if (getArguments().getString("contrasena").equals(pass.getText().toString())) {
                                    Thread tr = new Thread(){
                                        @Override
                                        public void run() {
                                            if (new Equipo().unirJugador(new Jugador().getId(), getArguments().getInt("idEquipo"))) {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        elementosLista.add(new Jugador().getNombreCompleto());
                                                        adaptador.notifyDataSetChanged();
                                                        //ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, elementosLista);
                                                        listaJugadores.setAdapter(adaptador);
                                                        pass.setText("");
                                                        Toast.makeText(getActivity().getApplicationContext(), "Ahora eres un nuevo jugador de " + getArguments().getString("nombreEquipo"), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }else{
                                                Toast.makeText(getActivity().getApplicationContext(),"Ya eres miembro de " + getArguments().getString("nombreEquipo"),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    };
                                    tr.start();
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    });
                    builder.setView(view2);
                    builder.show();
                }
                break;
            case R.id.btnRetar:
                Thread th = new Thread(){
                    @Override
                    public void run() {
                        final ManejadorEquipo manejadorMisEquipos = new ManejadorEquipo();
                        manejadorMisEquipos.cargarMisEquipos(new Jugador().getId());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!manejadorMisEquipos.getListaEquipos().isEmpty()){
                                    Intent ventRetarEquipo = new Intent(getActivity(), RetarEquipos.class);
                                    ventRetarEquipo.putExtra("idEquipoSeleccionado", getArguments().getInt("idEquipo"));
                                    ventRetarEquipo.putExtra("listaNombresMisEquipos",convertirNombresEquiposAString(manejadorMisEquipos.getListaEquipos()));
                                    ventRetarEquipo.putExtra("listaIdMisEquipos",convertirIdEquiposAString(manejadorMisEquipos.getListaEquipos()));
                                    startActivity(ventRetarEquipo);
                                }else{
                                    Toast.makeText(getActivity().getApplicationContext(),"Usted no se encuentra en ningún equipo",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                };

                break;
        }
    }

    private ArrayList<String> convertirNombresEquiposAString(ArrayList<Equipo> listaEquipo){
        ArrayList<String> listaNombresEquipos = new ArrayList<>();
        for (Equipo e: listaEquipo) {
            listaNombresEquipos.add(e.getNombre());
        }
        return listaNombresEquipos;
    }

    private ArrayList<String> convertirIdEquiposAString(ArrayList<Equipo> listaEquipo){
        ArrayList<String> listaIdEquipos = new ArrayList<>();
        for (Equipo e: listaEquipo) {
            listaIdEquipos.add(e.getId() + "");
        }
        return listaIdEquipos;

    }

    public Boolean validarUnionAlEquipo(String nombre){
        for (String n: elementosLista) {
            if(n.equals(nombre)){
                return false;
            }
        }
        return true;
    }



}
