package com.secuencia.saz.sazmobileventas.conexion;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionSqlServer {

    public Connection conexionBD()
    {

        Connection conn=null;

        try
        {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://Saz9.dnsalias.com;databaseName=hosting;user=sa;password=nyc2011@;");


        }catch(Exception e)
        {
           // Toast.makeText(Principal.this, "error en la conexion", Toast.LENGTH_SHORT).show();

        }



        return conn;
    }
}
