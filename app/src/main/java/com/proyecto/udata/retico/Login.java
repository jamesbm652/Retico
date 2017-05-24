package com.proyecto.udata.retico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Jugador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button btnRegistrar,btnIngresar;
    TextView txtCorreo, txtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        btnRegistrar.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);
    }

    protected void inicializarComponentes(){
        btnRegistrar = (Button)findViewById(R.id.btnRegistro);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);
        txtContrasena = (TextView)findViewById(R.id.txtContrasena);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnRegistro:
                Intent intent = new Intent(getApplicationContext(),Registro.class);
                startActivity(intent);
                break;

            case R.id.btnIngresar:
                if(!txtCorreo.getText().toString().equals("") && !txtContrasena.getText().toString().equals("")){
                    if(validarCorreo(txtCorreo.getText().toString())) {
                        Thread tr = new Thread() {
                            @Override
                            public void run() {
                                final Jugador jugador = new Jugador().obtenerJugador(txtCorreo.getText().toString(), txtContrasena.getText().toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (jugador != null) {
                                            //Intent i= new Intent(getApplicationContext(), registroNotas.class);
                                            //i.putExtra("cod", txtCorreo.getText().toString());
                                            //startActivity(i);
                                            Toast.makeText(getApplicationContext(), "Usuario valido", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Usuario invalido", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        };
                        tr.start();
                    }else{
                        Toast.makeText(getApplicationContext(),"Formato del correo incorrecto",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Debe completar los campos",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public static boolean validarCorreo(String correo) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = correo;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
