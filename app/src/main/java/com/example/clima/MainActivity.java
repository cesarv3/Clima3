package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView temp1, temp1Min,temp1Max,temp2Min,temp2Max,temp3Min,temp3Max, temp4Min,temp4Max, temp5Min,temp5Max,
            ciudadTextView, descripcion, dia1,dia2,dia3,dia4,dia5;
    private EditText nombreCiudad;
    private Button buscarBtn;
    private ImageButton derBtn;
    private ImageView climaImg;
    private String apiKey = "&appid=bd4d08ada6b765c131e7a0fc4874da4a";
    private String unidades = "&units=metric";
    private String gradosCent = "°C";
    private RequestQueue queue;
    private Intent intent;
    private GregorianCalendar gc = new GregorianCalendar();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MMM");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp1 = findViewById(R.id.temp1);
        temp1Min = findViewById(R.id.temp1Min);
        temp1Max = findViewById(R.id.temp1Max);
        temp2Min = findViewById(R.id.temp2Min);
        temp2Max = findViewById(R.id.temp2Max);
        temp3Min = findViewById(R.id.temp3Min);
        temp3Max = findViewById(R.id.temp3Max);
        temp4Min = findViewById(R.id.temp4Min);
        temp4Max = findViewById(R.id.temp4Max);
        temp5Min = findViewById(R.id.temp5Min);
        temp5Max = findViewById(R.id.temp5Max);
        climaImg = findViewById(R.id.climaImg);
        dia1 = findViewById(R.id.dia1);
        dia2 = findViewById(R.id.dia2);
        dia3 = findViewById(R.id.dia3);
        dia4 = findViewById(R.id.dia4);
        dia5 = findViewById(R.id.dia5);
        descripcion = findViewById(R.id.descripcion);
        ciudadTextView = findViewById(R.id.ciudadTextView);
        nombreCiudad = findViewById(R.id.nombreCiudad);
        buscarBtn = findViewById(R.id.buscarBtn); buscarBtn.setOnClickListener(this);
        derBtn = findViewById(R.id.cambiarDer); derBtn.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        peticionPorNombre("Mexicali");
        climaActual("Mexicali");
        ponerFechas();

    }

    public void peticionPorNombre(String nombreCiudad)
    {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=";
        url = url+nombreCiudad+apiKey+unidades;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("list");
                    int x=0;
                    double min[]=new double[8];
                    double max[]=new double[8];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objetos = jsonArray.getJSONObject(i);
                        JSONObject main = objetos.getJSONObject("main");
                        min[x] = main.getDouble("temp_min");
                        max[x] = main.getDouble("temp_max");
                        x++;
                        switch (i)
                        {
                            case 7:{
                                temp1Min.setText(String.valueOf(getMinValue(min)));
                                temp1Max.setText(String.valueOf(getMaxValue(max)));
                                x=0;
                                break;
                            }
                            case 15:{
                                temp2Min.setText(String.valueOf(getMinValue(min)));
                                temp2Max.setText(String.valueOf(getMaxValue(max)));
                                x=0;
                                break;
                            }
                            case 23:{
                                temp3Min.setText(String.valueOf(getMinValue(min)));
                                temp3Max.setText(String.valueOf(getMaxValue(max)));
                                x=0;
                                break;
                            }
                            case 31:{
                                temp4Min.setText(String.valueOf(getMinValue(min)));
                                temp4Max.setText(String.valueOf(getMaxValue(max)));
                                x=0;
                                break;
                            }
                            case 39:{
                                temp5Min.setText(String.valueOf(getMinValue(min)));
                                temp5Max.setText(String.valueOf(getMaxValue(max)));
                                x=0;
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);


    }

    public void climaActual(final String nombreCiudad)
    {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=";
        url = url+nombreCiudad+apiKey+unidades;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject objetoWeather = jsonArray.getJSONObject(0);
                    JSONObject main = response.getJSONObject("main");
                    climaImg.setImageResource(generarIDImg("icon"+objetoWeather.getString("icon")));
                    descripcion.setText(objetoWeather.getString("description"));
                    temp1.setText(String.valueOf(main.getDouble("temp")));
                    ciudadTextView.setText(nombreCiudad);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }


    // getting the maximum value
    public static double getMaxValue(double[] array) {
        double maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    // getting the miniumum value
    public static double getMinValue(double[] array) {
        double minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buscarBtn:{
                if (nombreCiudad.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Ingresa el nombre de una ciudad",Toast.LENGTH_SHORT).show();
                } else {
                    peticionPorNombre(nombreCiudad.getText().toString());
                    climaActual(nombreCiudad.getText().toString());
                }
                break;
            }

            case R.id.cambiarDer:{
                intent = new Intent(this,top_ten.class);
                startActivity(intent);
                break;
            }
        }
    }

    public int generarIDImg(String codigo)
    {
        int resID = getResources().getIdentifier(codigo , "drawable", getPackageName());
        return resID;
    }

    public void ponerFechas()
    {
        gc.setTime(Calendar.getInstance().getTime());
        for (int i = 0; i < 5; i++) {
            switch (i)
            {
                case 0:{
                    dia1.setText(dateFormat.format(gc.getTime())); break;}
                case 1:{
                    gc.add(Calendar.DATE,1);
                    dia2.setText(dateFormat.format(gc.getTime())); break;}
                case 2:{
                    gc.add(Calendar.DATE,1);
                    dia3.setText(dateFormat.format(gc.getTime())); break;}
                case 3:{
                    gc.add(Calendar.DATE,1);
                    dia4.setText(dateFormat.format(gc.getTime())); break;}
                case 4:{
                    gc.add(Calendar.DATE,1);
                    dia5.setText(dateFormat.format(gc.getTime())); break;}
            }

        }
    }
}
