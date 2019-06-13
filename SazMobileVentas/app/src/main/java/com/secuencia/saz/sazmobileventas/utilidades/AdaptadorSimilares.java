package com.secuencia.saz.sazmobileventas.utilidades;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.secuencia.saz.sazmobileventas.menu;
import com.secuencia.saz.sazmobileventas.Modelo.DatosLupita;
import com.secuencia.saz.sazmobileventas.Modelo.ModeloSimilar;
import com.secuencia.saz.sazmobileventas.Principal;
import com.secuencia.saz.sazmobileventas.R;

import java.util.ArrayList;

public class AdaptadorSimilares  extends RecyclerView.Adapter<AdaptadorSimilares.ViewHolderSimilar> {



    ArrayList<ModeloSimilar> listSimilar;
    public AdaptadorSimilares(ArrayList<ModeloSimilar> listSimilar){
        this.listSimilar=listSimilar;
    }

    @NonNull
    @Override
    public AdaptadorSimilares.ViewHolderSimilar onCreateViewHolder( ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar,null,false);
        return new ViewHolderSimilar(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderSimilar holder, int position) {
        holder.estilo.setText(listSimilar.get(position).getEstilo());
        holder.marca.setText(listSimilar.get(position).getMarca());
        holder.color.setText(listSimilar.get(position).getColor());
        holder.corrida.setText(listSimilar.get(position).getCorrida());
        holder.acabado.setText(listSimilar.get(position).getAcabado());
        holder.barcode.setText(listSimilar.get(position).getBarcode());
        holder.punto.setText(listSimilar.get(position).getPunto());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Principal.similarPass=true;
                Principal.passConsulta=true;
                Principal.menu=true;
                Intent intent = new Intent(holder.context, menu.class);
                DatosLupita dl=new DatosLupita();
                dl.setBarcode(holder.barcode.getText().toString());
                holder.context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSimilar.size();
    }

    public class ViewHolderSimilar extends RecyclerView.ViewHolder {
        TextView estilo, color, marca, acabado, corrida,barcode, punto;
        LinearLayout layout;
        Context context;
        public ViewHolderSimilar(@NonNull View itemView) {
            super(itemView);

            estilo=(TextView)itemView.findViewById(R.id.idEstilo);
            marca=(TextView)itemView.findViewById(R.id.idMarca);
            color=(TextView)itemView.findViewById(R.id.idColor);
            acabado=(TextView)itemView.findViewById(R.id.idAcabado);
            corrida=(TextView)itemView.findViewById(R.id.idPunto);
            layout=(LinearLayout)itemView.findViewById(R.id.idLayout);
            barcode=(TextView)itemView.findViewById(R.id.idBar);
            punto=(TextView)itemView.findViewById(R.id.idImagen);
            context=itemView.getContext();

        }
    }
}
