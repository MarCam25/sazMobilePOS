package com.secuencia.saz.sazmobileventas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.secuencia.saz.sazmobileventas.Modelo.ModeloDatos;
import com.secuencia.saz.sazmobileventas.Modelo.ModeloEmpresa;
import com.secuencia.saz.sazmobileventas.Modelo.ModeloResumen;
import com.secuencia.saz.sazmobileventas.Modelo.ModeloUsuario;
import com.secuencia.saz.sazmobileventas.conexion.ConexionBDCliente;
import com.secuencia.saz.sazmobileventas.conexion.ConexionSQLiteHelper;
import com.secuencia.saz.sazmobileventas.utilidades.AdapterResumen;
import com.secuencia.saz.sazmobileventas.utilidades.Utilidades;

import java.util.ArrayList;

public class Resumen extends AppCompatActivity {

    public static ModeloEmpresa me=new ModeloEmpresa();


    RecyclerView recyclerView;
    ArrayList<ModeloResumen> listaResumen;
    int verificador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        listaResumen=new ArrayList<>();


        recyclerView=(RecyclerView)findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterResumen adapter=new AdapterResumen(listaResumen);
        recyclerView.setAdapter(adapter);

        verificar();

        if(verificador==0){
            Toast.makeText(this, "No tienes productos agregados", Toast.LENGTH_LONG).show();

        }

        mostrar();

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            Principal.menu=true;
            Principal.banco=true;
            Intent intent = new Intent(getApplicationContext(), menu.class);

            startActivity(intent);
        }
        return true;
    }

    public void mostrar(){

        ModeloResumen mr=null;

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "db tienda", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONTENEDOR , null);
        while (cursor.moveToNext()) {

            mr=new ModeloResumen();

            mr.setId(cursor.getString(0));
            mr.setEstilo(cursor.getString(1));
            mr.setImagen(cursor.getString(2));
            mr.setPunto(cursor.getString(3));
            mr.setCantidad(cursor.getString(4));
            mr.setMarca(cursor.getString(5));
            String color=cursor.getString(6);
            mr.setColor(color);
            mr.setSubtotal(cursor.getString(7));
            mr.setTotal(cursor.getString(8));
            mr.setBarcode(cursor.getString(9));
            mr.setAcabado(cursor.getString(10));
            listaResumen.add(mr);

        }
    }



    public void verificar(){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "db tienda", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT id FROM " + Utilidades.TABLA_CONTENEDOR , null);
        while (cursor.moveToNext()) {

            String var=cursor.getString(0);
            verificador=1;

        }
    }

}
