package com.proyecto.udata.retico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.Jugador;
import com.proyecto.udata.retico.Objetos.JugadorEquipo;

import java.util.ArrayList;

public class CreacionEquipos extends AppCompatActivity implements View.OnClickListener{
    EditText txtNombre, txtLocalizacion, txtContrasena, txtEncargado;
    Button btnCrearEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_equipos);

        inicializarComponentes();

        txtEncargado.setText(new Jugador().getNombre()+" "+new Jugador().getApellido1());
        btnCrearEquipo.setOnClickListener(this);

    }

    private void inicializarComponentes(){
        txtNombre = (EditText)findViewById(R.id.txtNombreEquipo);
        txtLocalizacion = (EditText)findViewById(R.id.txtLocalizacionEquipo);
        txtContrasena = (EditText)findViewById(R.id.txtContrasena);
        txtEncargado = (EditText)findViewById(R.id.txtEcargadoEquipo);
        btnCrearEquipo = (Button)findViewById(R.id.btnCrearEquipo);
    }

    @Override
    public void onClick(View v) {
        final String nombreEquipo = txtNombre.getText().toString().trim();
        final String localizacion = txtLocalizacion.getText().toString().trim();
        final String contrasena = txtContrasena.getText().toString().trim();

        if(!nombreEquipo.equals("") && !localizacion.equals("") && !contrasena.equals("")){
            Thread tr = new Thread() {
                @Override
                public void run() {
                    JugadorEquipo encargado = new JugadorEquipo();
                    encargado.setId(new Jugador().getId());
                    ArrayList<JugadorEquipo> listaJugadores = new ArrayList<>();
                    listaJugadores.add(encargado);

                    final Boolean equipoCreado = new Equipo(nombreEquipo, contrasena, localizacion, encargado, listaJugadores).insertarEquipo();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (equipoCreado) {
                                Toast.makeText(getApplicationContext(), "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                                //Intent i = new Intent(getApplicationContext(), Login.class);
                                //i.putExtra("cod", txtCorreo.getText().toString());
                                //startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error al crear equipo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            };
            tr.start();
        }else{
            Toast.makeText(getApplicationContext(), "Debe completar todos los espacios", Toast.LENGTH_SHORT).show();
        }
    }
}
