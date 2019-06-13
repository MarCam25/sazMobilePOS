package com.secuencia.saz.sazmobileventas.utilidades;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.secuencia.saz.sazmobileventas.Modelo.Pago;
import com.secuencia.saz.sazmobileventas.R;

import java.util.ArrayList;

public class AdaptadorPago extends RecyclerView.Adapter<AdaptadorPago.ViewHolderPago> {
    ArrayList<Pago> listaPago;

    public AdaptadorPago(ArrayList<Pago> listaPago) {
        this.listaPago = listaPago;
    }


    @Override
    public ViewHolderPago onCreateViewHolder( ViewGroup parent, int i) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago,null,false);
        return new ViewHolderPago(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderPago holder, int position) {
        holder.banco.setText(listaPago.get(position).getFormaPago());
        holder.efectivo.setText(listaPago.get(position).getEfectivo());

        //holder

    }

    @Override
    public int getItemCount() {
        return listaPago.size();
    }

    public class ViewHolderPago extends RecyclerView.ViewHolder {
        TextView banco,efectivo;
        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);

            banco=(TextView)itemView.findViewById(R.id.bancoTXT);
            efectivo=(TextView)itemView.findViewById(R.id.efectivoTXT);
        }

    }
}
