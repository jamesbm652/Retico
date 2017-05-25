package com.proyecto.udata.retico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Jugador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManejoJugadores extends AppCompatActivity implements View.OnClickListener{

    TextView txtNombre,txtApellido1,txtApellido2, txtFechaNacimiento,txtTelefono, txtCorreo, txtContrasena;
    Button btnModificar;
    DatePickerDialog datePickerDialog;

    Jugador jugadorActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejo_jugadores);

        inicializarComponentes();
        btnModificar.setOnClickListener(this);
        setValores();
    }

    private void inicializarComponentes(){
        txtNombre = (TextView)findViewById(R.id.txtNombre);
        txtApellido1 = (TextView)findViewById(R.id.txtApellido1);
        txtApellido2 = (TextView)findViewById(R.id.txtApellido2);
        txtFechaNacimiento = (TextView)findViewById(R.id.txtFechaNacimiento);
        txtTelefono = (TextView)findViewById(R.id.txtTelefono);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);
        txtContrasena = (TextView)findViewById(R.id.txtContrasena);
        btnModificar = (Button)findViewById(R.id.btnModificar);
    }

    public void setValores(){
        jugadorActual = new Jugador();
        txtNombre.setText(jugadorActual.getNombre());
        txtApellido1.setText(jugadorActual.getApellido1());
        txtApellido2.setText(jugadorActual.getApellido2());

        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaCon = formato.format(jugadorActual.getFechaNacimiento());

        txtFechaNacimiento.setText(fechaCon);
        txtCorreo.setText(jugadorActual.getCorreo());
        txtContrasena.setText(jugadorActual.getContrasena());
        txtTelefono.setText(jugadorActual.getTelefono());
    }

    @Override
    public void onClick(View v) {
        Date fecha = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fecha = format.parse(txtFechaNacimiento.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final String nombre = txtNombre.getText().toString().trim();
        final String apellido1 = txtApellido1.getText().toString().trim();
        final String apellido2 = txtApellido2.getText().toString().trim();
        final Date fechaNac = fecha;
        final String correo = txtCorreo.getText().toString().trim();
        final String pass = txtContrasena.getText().toString().trim();
        final String tel = txtTelefono.getText().toString().trim();

        if(!nombre.equals("") && !apellido1.equals("") &&  !apellido2.equals("") && !pass.equals("") && !tel.equals("")){
            Thread tr = new Thread(){
                @Override
                public void run() {

                    final Boolean ingreso = jugadorActual.modificarJugador(jugadorActual.getId(),nombre,apellido1,apellido2,fechaNac,correo,pass,tel);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (ingreso){

                                jugadorActual.setNombre(nombre);
                                jugadorActual.setApellido1(apellido1);
                                jugadorActual.setApellido2(apellido2);
                                jugadorActual.setFechaNacimiento(fechaNac);
                                jugadorActual.setContrasena(pass);
                                jugadorActual.setTelefono(tel);

                                Toast.makeText(getApplicationContext(), "Los datos se han modificado", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };
            tr.start();
        }else{
            Toast.makeText(getApplicationContext(), "No deben quedar espacios vacíos", Toast.LENGTH_LONG).show();
        }
    }

}
