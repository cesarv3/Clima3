package com.example.clima;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PronosticoAdapter extends RecyclerView.Adapter<PronosticoAdapter.PronosticoHolder> {
    private List<PronosticoTiempo> items;

    public class PronosticoHolder extends RecyclerView.ViewHolder{
        public ImageView imagen;
        public TextView nombreCiudad;
        public TextView minima;
        public TextView maxima;
        public TextView descripcion;

        public PronosticoHolder(@NonNull View v){
            super(v);
            imagen = v.findViewById(R.id.imagen_mirenglon);
            nombreCiudad = v.findViewById(R.id.nombreCiudad_mirenglon);
            minima = v.findViewById(R.id.minima_mirenglon);
            maxima = v.findViewById(R.id.maxima_mirenglon);
            descripcion = v.findViewById(R.id.descripcion_mirenglon);
        }
    }
    public PronosticoAdapter(List<PronosticoTiempo> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public PronosticoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_renglon,parent,false);
        return new PronosticoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PronosticoHolder holder, final int position) {
        holder.imagen.setImageResource(items.get(position).getImagen());
        holder.nombreCiudad.setText(items.get(position).getLugar());
        holder.minima.setText(String.valueOf(items.get(position).getMinima()));
        holder.maxima.setText(String.valueOf(items.get(position).getMaxima()));
        holder.descripcion.setText(items.get(position).getDescripcion());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
