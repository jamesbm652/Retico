package com.proyecto.udata.retico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener{

    Button btnModificar, btnListarEquipos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnListarEquipos = (Button)findViewById(R.id.btnListarEquipos);

        btnModificar.setOnClickListener(this);
        btnListarEquipos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnModificar:
                Intent ventModificar = new Intent(getApplicationContext(),ManejoJugadores.class);
                startActivity(ventModificar);
                break;
            case R.id.btnListarEquipos:
                Intent ventListaEquipos = new Intent(getApplicationContext(), ListaEquipos.class);
                startActivity(ventListaEquipos);
                break;
        }

    }
}
