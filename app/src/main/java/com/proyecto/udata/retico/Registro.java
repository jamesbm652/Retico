package com.proyecto.udata.retico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.udata.retico.Modelos.RegistroModel;
import com.proyecto.udata.retico.Objetos.Jugador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    TextView txtNombre,txtApellido1,txtApellido2, txtFechaNacimiento,txtTelefono, txtCorreo, txtContrasena;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();
        btnRegistrar.setOnClickListener(this);
    }

    private void inicializarComponentes(){
        txtNombre = (TextView)findViewById(R.id.txtNombre);
        txtApellido1 = (TextView)findViewById(R.id.txtApellido1);
        txtApellido2 = (TextView)findViewById(R.id.txtApellido2);
        txtFechaNacimiento = (TextView)findViewById(R.id.txtFechaNacimiento);
        txtTelefono = (TextView)findViewById(R.id.txtTelefono);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);
        txtContrasena = (TextView)findViewById(R.id.txtContrasena);
        btnRegistrar = (Button)findViewById(R.id.btnRegistro);
    }




    @Override
    public void onClick(View v) {

        Date fecha = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            fecha = format.parse(txtFechaNacimiento.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String nombre = txtNombre.getText().toString();
        final String apellido1 = txtApellido1.getText().toString();
        final String apellido2 = txtApellido2.getText().toString();
        final Date fechaNac = fecha;
        final String correo = txtCorreo.getText().toString();
        final String pass = txtContrasena.getText().toString();
        final String tel = txtTelefono.getText().toString();

        if(nombre != "" && apellido1 != "" &&  apellido2 != "" &&  correo != "" &&  pass != "" &&  tel != ""){

            //validar q el correo no es de otra persona ya existente

            Thread tr = new Thread(){
                @Override
                public void run() {
                    final Boolean ingreso = new Jugador(nombre,apellido1,apellido2,fechaNac,correo,pass,tel).insertarJugador();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (ingreso){
                                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                                Intent i= new Intent(getApplicationContext(), Login.class);
                                //i.putExtra("cod", txtCorreo.getText().toString());
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };
            tr.start();
        }

    }

}
