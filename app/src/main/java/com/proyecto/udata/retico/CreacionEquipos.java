package com.proyecto.udata.retico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.Jugador;
import com.proyecto.udata.retico.Objetos.JugadorEquipo;

import java.util.ArrayList;

public class CreacionEquipos extends AppCompatActivity implements View.OnClickListener{
    EditText txtNombre, txtLocalizacion, txtContrasena, txtEncargado;
    Button btnCrearEquipo;
    ImageButton btnBackSpaceCrearEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_equipos);

        inicializarComponentes();

        txtEncargado.setText(new Jugador().getNombre()+" "+new Jugador().getApellido1());
        btnCrearEquipo.setOnClickListener(this);
        btnBackSpaceCrearEquipos.setOnClickListener(this);

    }

    private void inicializarComponentes(){
        txtNombre = (EditText)findViewById(R.id.txtNombreEquipo);
        txtLocalizacion = (EditText)findViewById(R.id.txtLocalizacionEquipo);
        txtContrasena = (EditText)findViewById(R.id.txtContrasenaEquipo);
        txtEncargado = (EditText)findViewById(R.id.txtEcargadoEquipo);
        btnCrearEquipo = (Button)findViewById(R.id.btnCrearEquipo);
        btnBackSpaceCrearEquipos = (ImageButton)findViewById(R.id.btnBackSpaceCrearEquipos);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));
                    return true;
            }

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCrearEquipo:
                final String nombreEquipo = txtNombre.getText().toString();
                final String localizacion = txtLocalizacion.getText().toString();
                final String contrasena = txtContrasena.getText().toString();

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
                                        txtNombre.setText("");
                                        txtLocalizacion.setText("");
                                        txtContrasena.setText("");
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
                break;
            case R.id.btnBackSpaceCrearEquipos:
                Bundle extra = getIntent().getExtras();
                if(extra.getString("origen").equals("1")){
                    startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));
                }else{
                    startActivity(new Intent(getApplicationContext(),ListaEquipos.class));
                }

                break;
        }
    }
}
