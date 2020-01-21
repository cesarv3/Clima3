package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class top_ten extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<PronosticoTiempo> items;
    private String apiKey = "&appid=bd4d08ada6b765c131e7a0fc4874da4a";
    private String unidades = "&units=metric";
    private RequestQueue queue;
    private String[] ciudadesTop={"Mexicali","New york","Los Angeles", "Barcelona","Tokyo","Berlin","Paris","London","Mexico city","Montreal"};
    private int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_ten);
        queue = Volley.newRequestQueue(this);
        items = new ArrayList<>();

        for (int i = 0; i < ciudadesTop.length; i++) {
            obtenerClima(ciudadesTop[i]);

        }


        //obtener el recycle
        recycler = (RecyclerView)findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        //Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        //crear un adaptador
        adapter = new PronosticoAdapter(items);
        recycler.setAdapter(adapter);
    }

    private void obtenerClima(String nombreCiudad)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=";
        url = url +nombreCiudad+apiKey+unidades;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject objetoWeather = weather.getJSONObject(0);
                    PronosticoTiempo tiempo = new PronosticoTiempo();
                    tiempo.setImagen(generarIDImg("icon"+objetoWeather.getString("icon")));
                    tiempo.setDescripcion(objetoWeather.getString("description"));
                    tiempo.setLugar(response.getString("name"));
                    tiempo.setMinima(main.getDouble("temp_min"));
                    tiempo.setMaxima(main.getDouble("temp_max"));

                    items.add(tiempo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });
        queue.add(request);
    }

    public int generarIDImg(String codigo)
    {
        int resID = getResources().getIdentifier(codigo , "drawable", getPackageName());
        return resID;
    }
}
