package com.proyecto.udata.retico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Jugador;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button btnRegistrar, btnIngresar;
    TextView txtCorreo, txtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        btnIngresar.setOnClickListener(this);
    }

    protected void inicializarComponentes(){
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);
        txtContrasena = (TextView)findViewById(R.id.txtContrasena);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRegistrar:
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btnIngresar:
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
                break;
        }

    }
}
