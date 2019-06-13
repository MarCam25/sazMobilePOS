package com.secuencia.saz.sazmobileventas;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.content.ClipData;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.os.RemoteException;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.secuencia.saz.sazmobileventas.utilidades.TemplatePDF;

import java.io.File;
import java.util.ArrayList;

public class ViewPDFActivity extends AppCompatActivity {

    PDFView pdfView;
    private File file;
    FragmentManager fm = getSupportFragmentManager();
    private TemplatePDF pdf;
    Button btnWhats;
    EditText edtNumero,edtMail,edtNombre;
    Button btnFacebook, btnCorreo;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        StrictMode.setVmPolicy(builder.build());
        btnWhats = (Button) findViewById(R.id.btnWhastapp);
        edtNumero = (EditText) findViewById(R.id.edtNumero);
        edtMail=(EditText)findViewById(R.id.edtMail);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                sendPdfByEmail();


            }
        });
        btnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             /*  Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=52"+edtNumero.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                */



                 addCont();

            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            file = new File(bundle.getString("path", ""));

        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }


    @Override
    public void onBackPressed() {
        Principal.menu = true;
        Intent intent = new Intent(getApplicationContext(), menu.class);
        startActivity(intent);

    }


    public void sendPdfByEmail() {
        builder.detectFileUriExposure();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SazMobile POS Ticket");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nota de venta");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{""});
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String sdCardRoot = Environment.getExternalStorageDirectory().getPath();
        String fullFileName = sdCardRoot + File.separator + "SazTicket" + File.separator + "TicketVenta.pdf";
        // Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + "com.whatsapp", new File(fullFileName));
        Uri uri = Uri.fromFile(new File(fullFileName));
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.setType("application/pdf");
        startActivity(Intent.createChooser(emailIntent, "SazMobile POS. Enviar por:"));


    }



    public void addCont(){
        if(!edtNombre.getText().toString().isEmpty() && !edtNumero.getText().toString().isEmpty()){
            Intent  contacto=new Intent(ContactsContract.Intents.Insert.ACTION);
            contacto.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contacto
                    .putExtra(ContactsContract.Intents.Insert.EMAIL,edtMail.getText().toString())
                    .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .putExtra(ContactsContract.Intents.Insert.PHONE,edtNumero.getText().toString())
                    .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .putExtra(ContactsContract.Intents.Insert.NAME,edtNombre.getText().toString());


            startActivity(contacto);
        }else{
            Toast.makeText(this, "Tienes que ingresar un nombre y telefono del cliente", Toast.LENGTH_SHORT).show();
        }

    }


}