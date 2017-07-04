package com.proyecto.udata.retico;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnMenu,btnLogout;
    Button btnModificar, btnListarEquipos, btnCrearEquipo, btnRetosPendientes,btnRetosAceptados,btnRetosRechazados, btnRetosEnviados,btnRetosCancelados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        inicializarComponentes();
        setOnClickListener();

    }

    private void inicializarComponentes(){
        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnListarEquipos = (Button)findViewById(R.id.btnListarEquipos);
        btnCrearEquipo = (Button)findViewById(R.id.btnCreacionEquipos);
        btnRetosPendientes = (Button)findViewById(R.id.btnRetosPendientes);
        btnRetosEnviados = (Button)findViewById(R.id.btnRetosEnviados);
        btnRetosAceptados = (Button)findViewById(R.id.btnRetosAceptados);
        btnRetosRechazados = (Button)findViewById(R.id.btnRetosRechazados);
        btnRetosCancelados = (Button)findViewById(R.id.btnRetosCancelados);
        btnMenu = (ImageButton)findViewById(R.id.btnMenu);
        btnLogout = (ImageButton)findViewById(R.id.btnLogout);
    }

    private void setOnClickListener(){
        btnModificar.setOnClickListener(this);
        btnListarEquipos.setOnClickListener(this);
        btnCrearEquipo.setOnClickListener(this);
        btnRetosPendientes.setOnClickListener(this);
        btnRetosEnviados.setOnClickListener(this);
        btnRetosAceptados.setOnClickListener(this);
        btnRetosRechazados.setOnClickListener(this);
        btnRetosCancelados.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }

        }
        return true;
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
            case R.id.btnCreacionEquipos:
                Intent ventCrearEquipos = new Intent(getApplicationContext(), CreacionEquipos.class);
                ventCrearEquipos.putExtra("origen","1");
                startActivity(ventCrearEquipos);
                break;
            case R.id.btnRetosPendientes:
                Intent retosPendientes = new Intent(getApplicationContext(),SeguimientoRetos.class);
                retosPendientes.putExtra("tipoReto","Pendiente");
                startActivity(retosPendientes);
                break;
            case R.id.btnRetosEnviados:
                Intent retosEnviados = new Intent(getApplicationContext(),SeguimientoRetos.class);
                retosEnviados.putExtra("tipoReto","Enviado");
                startActivity(retosEnviados);
                break;
            case R.id.btnRetosAceptados:
                Intent retosAceptados = new Intent(getApplicationContext(),SeguimientoRetos.class);
                retosAceptados.putExtra("tipoReto","Aceptado");
                startActivity(retosAceptados);
                break;
            case R.id.btnRetosRechazados:
                Intent retosRechazados = new Intent(getApplicationContext(),SeguimientoRetos.class);
                retosRechazados.putExtra("tipoReto","Rechazado");
                startActivity(retosRechazados);
                break;
            case R.id.btnRetosCancelados:
                Intent retosCancelados = new Intent(getApplicationContext(),SeguimientoRetos.class);
                retosCancelados.putExtra("tipoReto","Cancelado");
                startActivity(retosCancelados);
                break;
            case R.id.btnMenu:
                DrawerLayout dl = (DrawerLayout)findViewById(R.id.drawer_Layout);
                dl.openDrawer(Gravity.START);
                break;
            case R.id.btnLogout:
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                break;
        }

    }
}
