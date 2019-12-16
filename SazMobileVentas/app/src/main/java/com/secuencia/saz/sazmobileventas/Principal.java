package com.secuencia.saz.sazmobileventas;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Build;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.secuencia.saz.sazmobileventas.Modelo.ModeloEmpresa;
import com.secuencia.saz.sazmobileventas.Modelo.ModeloUsuario;
import com.secuencia.saz.sazmobileventas.conexion.ConexionBDCliente;
import com.secuencia.saz.sazmobileventas.conexion.ConexionSQLiteHelper;
import com.secuencia.saz.sazmobileventas.conexion.ConexionSqlServer;
import com.secuencia.saz.sazmobileventas.utilidades.Utilidades;

import org.json.JSONException;
import org.json.JSONObject;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Principal extends AppCompatActivity {


    Button button;




    EditText des, en;
    public static Boolean similarPass=false;
    public static boolean passConsulta=false;
    public static boolean busqueda2=false;
    public static String estilo="";
    public String res;

    CheckBox check;
    public static boolean menu=false;
    public RequestQueue queue;
    public String usuario = "";
    Boolean mensaje=false;
    String asistencia="";


    public static String punto="";
    public static String idCorrida=null, idAcabado=null,idColor=null;


    String fechaContrato;
    String pass = " ";
    public static  boolean scannPass=false;
   public static boolean banco=false;
    int verificador;
    public String empresa ="";
    String Password;
    String idUsuario;
    String fechaHoy;
    int filas=0;
    Boolean membrecia=false;



    String nombreUsuario;
    ModeloEmpresa me;
    ModeloUsuario mu;

    ConexionSqlServer conex = new ConexionSqlServer();
    ConexionBDCliente bdc = new ConexionBDCliente();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //this.deleteDatabase("db tienda");
        PermisosCamara();

        Toolbar toolbaris = (Toolbar) findViewById(R.id.toolbarPrincipal);
        check = (CheckBox) findViewById(R.id.check);
        des = (EditText) findViewById(R.id.des);
        en = (EditText) findViewById(R.id.en);



        button = (Button) findViewById(R.id.button);
        toolbaris.setTitleTextColor(Color.WHITE);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());

        Date date=new Date();
        fechaHoy=dateFormat.format(date);
        mostrarDatos();
        queue = Volley.newRequestQueue(this);
        consultarBuscador();


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inicializarDatos();
                iniciarSesion();

            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

        }

        return true;

    }



    public void consultarBuscador(){
        int buscador=0;
        try {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "db tienda", null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();

            String sql="SELECT "+ Utilidades.CAMPO_BUSCADOR2+" FROM "+ Utilidades.TABLA_CHECKB;
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                buscador= Integer.parseInt(cursor.getString(0));
            }

            if(buscador==1){
                busqueda2=true;
            }
        }catch (Exception e){

            Toast toast = Toast.makeText(getApplicationContext(), "La versión nueva de SazMobile LITE se ha instalado", Toast.LENGTH_LONG);
            TextView x = (TextView) toast.getView().findViewById(android.R.id.message);
            x.setTextColor(Color.BLACK); toast.show();
            Intent intent = new Intent(getApplicationContext(), Principal.class);
            getApplicationContext().deleteDatabase("db tienda");
            startActivity(intent);

        } finally {

        }

    }

    public void iniciarSesion(){
       try {
           final ProgressDialog progressDialog = new ProgressDialog(Principal.this);
           progressDialog.setIcon(R.mipmap.ic_launcher);
           progressDialog.setMessage("Cargando...");
           progressDialog.show();


        final String url = "http://secuenciaonline.com/svcCadEncrypt/cadEncrypt.svc/encjson/" + pass;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Password = response.get("EncriptaJSONResult").toString();

                    consultarEmpresa();

                    if (!empresa.equals("")) {

                        verificar();
                        verificarRenta();

                        if (fechaContrato != null) {
                            compararFechaReta();
                        }
                        if (verificador == 1 && membrecia == true) {
                            mismoDispositivo();

                            if (check.getLinksClickable() == true) {
                                recordarDatos();
                            } else if (check.getLinksClickable() == false) {
                                noRecordar.start();
                            }
                            insertDatosDispositivo.start();

                            if(asistencia.equals("SALIDA")){

                                if (mensaje == true ) {

                                    accesoPrincipalAviso();

                                } else if(mensaje==false) {

                                    accesoPrincipal();
                                }

                            }else if(asistencia.equals("ENTRADA")) {

                                    if (mensaje == true) {
                                     accesoPrincipalAviso();
                                    } else if(mensaje==false) {
                                     accesoMenu();
                                    }
                            }

                            if(asistencia.equals("")){

                                     accesoPrincipal();

                            }

                        }else if (filas == 0) {

                            iniciarPrueba.start();
                            iniciarSesion();

                        } else {
                            AlertDialog.Builder dialogo = new AlertDialog.Builder(Principal.this);
                            dialogo.setTitle("Aviso");
                            dialogo.setMessage("Tu periodo de prueba ha expirado o ha sido suspendido por falta de pago , Comunicate con saz para ReConectar");

                            dialogo.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            });

                            dialogo.show();
                        }

                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(Principal.this, "Error verifica tus datos....!!!", Toast.LENGTH_LONG).show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override//Obtiene el error al no encontrar el resultado que se pide
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);
       }catch (Exception e){
           e.getMessage();


       }

    }


    public void inicializarDatos() {
        pass = String.valueOf(des.getText());
        usuario = en.getText().toString();
    }

    public void obtenerLineaConexion() {


        me = new ModeloEmpresa();


        {
            try {
                Statement st = conex.conexionBD().createStatement();
                String query = "select  Server, Usuariosvr,PassSvr, basededatos, empresa from logins where idEmpresa= " + empresa + " and status=1 and isnull(borrado,0)=0  ";
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {


                    String server = verificarServer(rs.getString(1));
                    String usuario = rs.getString(2);
                    String pass = rs.getString(3);
                    String base = rs.getString(4);
                    String empresa = rs.getString(5);

                    me.setServer(server);
                    me.setUsuario(usuario);
                    me.setPass(pass);
                    me.setBase(base);
                    me.setEmpresa(empresa);


                }
                st.close();



            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error en la linea de conexion", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String verificarServer(String server) {
        String contenedor = "";
        String[] contServer = server.split("");
        try {
            for (int x = 0; x < contServer.length; x++) {
                if (!contServer[x].isEmpty()) {

                    String predeterminada = ",";


                    String entrada = contServer[x];


                    String aux = "";


                    if (predeterminada.length() == entrada.length()) {

                        for (int i = 0; i < predeterminada.length(); i++) {


                            if (predeterminada.charAt(i) == entrada.charAt(i)) {

                                aux += predeterminada.charAt(i);
                            }
                        }


                        if (aux.equals(predeterminada)) {
                            contServer[x] = ":";
                        } else {


                        }


                    } else {

                    }


                }

            }

            for (int i = 0; i < contServer.length; i++) {

                contenedor += contServer[i];

            }
        } catch (Exception e) {
            e.getMessage();
        }
        String verificado = "";
        verificado = contenedor;
        return verificado;
    }



    public void getIdUsuario() {

        try {
            Statement st = bdc.conexionBD(me.getServer(), me.getBase(), me.getUsuario(), me.getPass()).createStatement();
            String query = "select numero, nombre from empleado where [user]='" + en.getText() + "'";
            ResultSet rs = st.executeQuery(query);


            while (rs.next()) {


                idUsuario = rs.getString(1);
                nombreUsuario = rs.getString(2);


                mu.setCorreo(en.getText().toString());
                mu.setNombre(nombreUsuario);
            }
            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al traer id usuario", Toast.LENGTH_SHORT).show();
        }


    }

    public void mostrarDatos() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "db tienda", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String sql = "SELECT usuario, contraseña FROM login  ORDER BY id ASC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            String usu = cursor.getString(0);
            String contra = cursor.getString(1);

            if (!usu.isEmpty()) {
                en.setText(usu);
                des.setText(contra);
                check.setChecked(true);
            }


        }
    }

    public void asistencia() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();

        String fecha = dateFormat.format(date);


        try {
            Statement st = bdc.conexionBD(me.getServer(), me.getBase(), me.getUsuario(), me.getPass()).createStatement();
            String sql = "select top 1 tipo from logdia where idEmpleado=" + idUsuario + " and CONVERT(nCHAR(8), fecha , 112)='" + fecha + "' order by fecha desc";
            ResultSet rs = st.executeQuery(sql);


            while (rs.next()) {


                asistencia = rs.getString(1);

            }
            st.close();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al verificar asistencia", Toast.LENGTH_SHORT).show();
        }

    }


    public void PermisosCamara() {
        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)) {

        }

        if (shouldShowRequestPermissionRationale(CAMERA) || shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) ) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA,READ_EXTERNAL_STORAGE}, 100);
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE,CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(Principal.this);
        dialogo.setTitle("Permisos desactivados ");
        dialogo.setMessage("Debe aceptar los permisos para el corecto funcionamiento de SazMobile app");

        dialogo.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA}, 100);
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE,CAMERA}, 100);
            }
        });

        dialogo.show();
    }


    public void verificarRenta() {


        try {
            Statement st =  conex.conexionBD().createStatement();
            String sql = " SELECT isnull(smapp,0), fechaSmApp  FROM rlogin WHERE usuario='" + usuario + "' ";
            ResultSet rs = st.executeQuery(sql);


            while (rs.next()) {

                verificador = rs.getInt(1);
                fechaContrato=rs.getString(2);

            }

            st.close();

        } catch (SQLException e) {
            e.getMessage();
            Toast.makeText(getApplicationContext(), "No sé puede verificar la renta", Toast.LENGTH_SHORT).show();
        }

    }

    public void compararFechaReta() throws ParseException {


        String[] fechaCAux=fechaContrato.split("");
        String[] fechaHAux=fechaHoy.split("");


        int añoC=Integer.parseInt(fechaCAux[1]+fechaCAux[2]+fechaCAux[3]+fechaCAux[4]);
        int mesC=Integer.parseInt(fechaCAux[6]+fechaCAux[7]);
        int diaC=Integer.parseInt(fechaCAux[9]+fechaCAux[10]);

        String hoy=fechaHAux[1]+fechaHAux[2]+fechaHAux[3]+fechaHAux[4]+"-"+fechaHAux[5]+fechaHAux[6]+"-"+fechaHAux[7]+fechaHAux[8];




        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateContrato = sdf.parse(fechaContrato);
        Date dateActual = sdf.parse(hoy);

        if(dateContrato.compareTo(dateActual)>0){

            membrecia=true;
        }else{
            membrecia=false;
        }





    }

    public void verificar(){
        try{
            Statement st= conex.conexionBD().createStatement();
            String sql="select top 1 1 from rlogin where Empresa="+empresa+" and smApp=2;";
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                filas=rs.getInt(1);
            }
            st.close();

        }catch (Exception e ){
            e.getMessage();
            Toast.makeText(getApplicationContext(), "No sé puede obtener datos del dispositivo", Toast.LENGTH_SHORT).show();
        }
    }

    public void mismoDispositivo(){

        String dispositivo="";
        int app=0;

        String modelo = Build.MODEL;
        String serie = Build.MANUFACTURER;
        String marca = Build.ID;
        try {
            Statement st= conex.conexionBD().createStatement();
            String sql=" SELECT idDisp, app FROM smAppAccesos where mail='"+usuario+"' and app=2 ";
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                dispositivo=rs.getString(1);
                app=rs.getInt(2);
            }
            st.close();

        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(getApplicationContext(), "No sé puede actualizar el dispositivo", Toast.LENGTH_SHORT).show();
        }


        String predeterminada = dispositivo;

        String entrada = marca + "-" + serie + "-" + modelo;


        if(predeterminada.equals(entrada) && app==2){
            mensaje=false;
        }else{
            mensaje=true;
        }



    }


    public void consultarEmpresa() {
        try{
        Statement st = conex.conexionBD().createStatement();
        String query = "select  Empresa from Rlogin where Usuario= '" + usuario + "' and Clave= '" + Password + "' and Activo=1 and suspendido=0";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            empresa = (rs.getString(1));
            obtenerLineaConexion();
            getIdUsuario();
            asistencia();
        }
        st.close();


    }catch (SQLException e){
            e.getMessage();

        }
    }

    public void accesoPrincipal(){
        Intent intent = new Intent(getApplicationContext(), ActPrincipal.class);
        intent.putExtra("Empresa", empresa);
        intent.putExtra("Usuario", usuario);
        startActivity(intent);
    }
public void accesoMenu(){

    Intent intent = new Intent(getApplicationContext(), menu.class);
    intent.putExtra("Empresa", empresa);
    intent.putExtra("Usuario", usuario);
    startActivity(intent);
}

    public void accesoPrincipalAviso(){
        android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(Principal.this);
        alerta.setMessage("Este usuario estaba siendo usado en otro dispositivo por lo que se cerrará la sesión anterior.")
                .setCancelable(false).setIcon(R.drawable.aviso)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Principal.this, "Bienvenido", Toast.LENGTH_LONG).show();
                        // Toast.makeText(Principal.this,"Bienvenido", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ActPrincipal.class);
                        intent.putExtra("Empresa", empresa);
                        intent.putExtra("Usuario", usuario);
                        startActivity(intent);

                    }
                });

        android.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("Aviso");
        titulo.show();
    }



    Thread insertDatosDispositivo = new Thread(new Runnable() {
        @Override
        public void run() {

            String modelo = Build.MODEL;
            String serie = Build.MANUFACTURER;
            String marca = Build.ID;
            try{
                Statement st= conex.conexionBD().createStatement();
                String sql="if exists (select 1 from smAppAccesos where mail = '"+usuario+"' and idEmpresa = '"+empresa+"' and app=2) begin update smAppAccesos \n" +
                        "set idDisp ='"+marca+"-"+serie+"-"+modelo+"', nombreDisp='"+serie+" "+modelo+"',ultimoAcceso=GETDATE() where mail = '"+usuario+"' and idEmpresa = '"+empresa+"' and app=2\n" +
                        " end else begin insert into smAppAccesos (mail,idEmpresa,ultimoAcceso,idDisp,nombreDisp,activo,app,llave)values('"+usuario+"',"+empresa+",GETDATE(),'"+marca+"-"+serie+"-"+modelo+"','"+serie+" "+ modelo+"',1,2,NEWID()); end";
                st.executeUpdate(sql);
                st.close();
            }catch (Exception e ){
                e.getMessage();
                Toast.makeText(getApplicationContext(), "No sé puede obtener datos del dispositivo", Toast.LENGTH_SHORT).show();
            }
        }
    });



        public void recordarDatos () {

            try {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "db tienda", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();


                db.execSQL("INSERT INTO  login (usuario,contraseña) VALUES('" + usuario + "', '" + pass + "')");
                db.close();
                conn.close();

            }catch (Exception E){
                E.getMessage();
                Toast.makeText(Principal.this, "Error al capturar tus datos ", Toast.LENGTH_LONG).show();
            }
        }




    Thread iniciarPrueba = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Statement st =  conex.conexionBD().createStatement();
                String sql = "update Rlogin set smApp=1 , fechaSmApp=DATEADD(day,30,GETDATE()) where Empresa="+empresa+"";
                st.executeUpdate(sql);
                st.close();



            } catch (SQLException e) {
                e.getMessage();
                Toast.makeText(getApplicationContext(), "No sé puede iniciar la prueba", Toast.LENGTH_SHORT).show();
            }

        }
    });



    Thread noRecordar = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "db tienda", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
                db.execSQL("DELETE FROM LOGIN");
                db.close();
                conn.close();

            } catch (Exception e) {
                e.getMessage();
                Toast.makeText(getApplicationContext(), "No sé puede iniciar la prueba", Toast.LENGTH_SHORT).show();
            }

        }
    });





}
