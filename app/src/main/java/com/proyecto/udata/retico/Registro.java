package com.proyecto.udata.retico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;


import com.proyecto.udata.retico.Modelos.RegistroModel;
import com.proyecto.udata.retico.Objetos.Jugador;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    TextView txtNombre,txtApellido1,txtApellido2, txtFechaNacimiento,txtTelefono, txtCorreo, txtContrasena;
    Button btnRegistrar;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();
        btnRegistrar.setOnClickListener(this);

        //Mostrar calendario para  seleccionar la fecha
        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Registro.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txtFechaNacimiento.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
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

        if(!nombre.equals("") && !apellido1.equals("") && !apellido2.equals("") && fecha != null && !correo.equals("") && !pass.equals("") && !tel.equals("")){
            if (validarCorreo(correo)) {
                Thread tr = new Thread() {
                    @Override
                    public void run() {
                        final Boolean ingreso = new Jugador(nombre, apellido1, apellido2, fechaNac, correo, pass, tel).insertarJugador();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (ingreso) {
                                    Toast.makeText(getApplicationContext(), "Jugador registrado", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    //i.putExtra("cod", txtCorreo.getText().toString());
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                };
                tr.start();
            }else{
                Toast.makeText(getApplicationContext(), "El formato del correo es incorrecto", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No deben quedar espacios vacíos", Toast.LENGTH_LONG).show();
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
