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
        btnIngresar = (Button)findViewById(R.id.btnRegistrar);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);
        txtContrasena = (TextView)findViewById(R.id.txtContrasena);
    }



    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()){
            case R.id.btnRegistrar:
                Intent intent = new Intent();
                break;

            case R.id.btnIngresar:

                break;
        }
        */

        if(txtCorreo.getText().toString() !="" && txtContrasena.getText().toString() != ""){

            Thread tr = new Thread(){
                @Override
                public void run() {
                    final Jugador jugador = new Jugador().obtenerJugador(txtCorreo.getText().toString(),txtContrasena.getText().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (jugador != null){
                                //Intent i= new Intent(getApplicationContext(), registroNotas.class);
                                //i.putExtra("cod", txtCorreo.getText().toString());
                                //startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(), "Usuario invalido", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };
            tr.start();
        }

        /*
        Thread tr = new Thread(){
            @Override
            public void run() {
                final String resultado = enviarDatosGET(txtCorreo.getText().toString(), txtContrasena.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtenerDatosJSON(resultado);
                        if (r > 0){
                            //Intent i= new Intent(getApplicationContext(), registroNotas.class);
                            //i.putExtra("cod", txtCorreo.getText().toString());
                            //startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(), "Usuario invalido", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        tr.start();*/
    }
/*
    //Metodo para enviar datos y recibir el JSON
    public String enviarDatosGET(String correo, String pass){
        URL url =  null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;

        try {
            //url = new URL("http://192.168.0.105/WebService/login.php?usu="+usu+"&pass="+pass);
            url = new URL("https://ws-android-gestion-multim.c9users.io/login.php?correo="+correo+"&pass="+pass);

            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linea = reader.readLine()) != null){
                    resul.append(linea);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resul.toString();
    }

    //Metodo para validar si el JSON tiene datos o no
    public int obtenerDatosJSON(String response){
        int res = 0;

        try {
            JSONArray json = new JSONArray(response);
            if (json.length()>0){
                return 1;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
    */
}
