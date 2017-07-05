package com.proyecto.udata.retico;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.proyecto.udata.retico.Objetos.Equipo;
import com.proyecto.udata.retico.Objetos.Jugador;
import com.proyecto.udata.retico.Objetos.ManejadorEquipo;
import com.proyecto.udata.retico.Objetos.Reto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RetarEquipos extends AppCompatActivity implements View.OnClickListener{
    ArrayList<Equipo> listaMisEquipos;
    EditText txtFechaReto, txtHoraReto, txtMensajeReto;
    Button btnRetar;
    ImageButton btnAtrasRetarEquipos;
    Spinner cbxMisEquipos;
    int mes, ano, dia, min, hora;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retar_equipos);
        inicializarComponentes();
        cargarListaMisEquipos();
        btnRetar.setOnClickListener(this);
        btnAtrasRetarEquipos.setOnClickListener(this);

        //DatePicker para solicitar la fecha
        txtFechaReto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                dia = cal.get(Calendar.DAY_OF_MONTH);
                mes = cal.get(Calendar.MONTH);
                ano = cal.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(RetarEquipos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFechaReto.setText(year + "-"
                                + (month + 1) + "-" + dayOfMonth);
                    }
                }, ano, mes, dia);
                dpd.show();
            }
        });

        //DatePicker para solicitar la hora
        txtHoraReto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                min = cal.get(Calendar.MINUTE);
                hora = cal.get(Calendar.HOUR);

                TimePickerDialog tdp = new TimePickerDialog(RetarEquipos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtHoraReto.setText(hourOfDay+":"+minute);
                    }
                }, hora, min, true);
                tdp.show();
            }
        });
    }

    private void inicializarComponentes(){
        txtFechaReto = (EditText)findViewById(R.id.txtFechaReto);
        txtHoraReto = (EditText)findViewById(R.id.txtHoraReto);
        txtMensajeReto = (EditText)findViewById(R.id.txtMensajeReto);
        btnRetar = (Button)findViewById(R.id.btnRetar);
        btnAtrasRetarEquipos = (ImageButton)findViewById(R.id.btnBackSpace);
        cbxMisEquipos = (Spinner)findViewById(R.id.cbxMisEquipos);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    startActivity(new Intent(getApplicationContext(),ListaEquipos.class));
                    return true;
            }

        }
        return true;
    }

    private void cargarListaMisEquipos(){
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> listaNombresMisEquipos = bundle.getStringArrayList("listaNombresMisEquipos");
        ArrayList<Integer> listaIdMisEquipos = bundle.getIntegerArrayList("listaIdMisEquipos");

        for(int i = 0; i<listaIdMisEquipos.size(); i++){
            Equipo equipo = new Equipo();
            equipo.setId(listaIdMisEquipos.get(i));
            equipo.setNombre(listaNombresMisEquipos.get(i));
            listaMisEquipos.add(equipo);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaNombresMisEquipos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbxMisEquipos.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRetar:
                Date fecha = null;
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    fecha = format.parse(txtFechaReto.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final Date fechaReto = fecha;
                final String horaReto = txtHoraReto.getText().toString();
                final String mensaje = txtMensajeReto.getText().toString();
                final Equipo eRetador = new Equipo();
                final Equipo eRetado = new Equipo();
                eRetador.setId(listaMisEquipos.get(cbxMisEquipos.getSelectedItemPosition()).getId());
                Bundle extra = getIntent().getExtras();
                eRetado.setId(extra.getInt("idEquipoSeleccionado"));

                if(fechaReto == null || horaReto.equals("") || mensaje.equals("")){
                    Toast.makeText(getApplicationContext(), "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    if(eRetado.getId() == eRetador.getId()){
                        Toast.makeText(getApplicationContext(), "No puedes retar a tu mismo equipo", Toast.LENGTH_SHORT).show();
                    }else{
                        Thread tr = new Thread() {
                            @Override
                            public void run() {
                                final Boolean retoCreado = new Reto(fechaReto, horaReto, eRetador, eRetado, mensaje).insertarReto();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (retoCreado) {
                                            Toast.makeText(getApplicationContext(), "Retor creado correctamente", Toast.LENGTH_SHORT).show();
                                            txtFechaReto.setText("");
                                            txtHoraReto.setText("");
                                            txtMensajeReto.setText("");
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error al crear equipo", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        };
                        tr.start();
                    }
                }
                break;

            case R.id.btnBackSpace:
                startActivity(new Intent(v.getContext(),ListaEquipos.class));
                break;
        }
    }
}
