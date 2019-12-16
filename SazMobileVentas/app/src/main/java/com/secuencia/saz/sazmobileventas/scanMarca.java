package com.secuencia.saz.sazmobileventas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scanMarca extends AppCompatActivity  implements ZXingScannerView.ResultHandler {


    TextView codigo;
    ZXingScannerView scannerView;
    Button scann;
    ZXingScannerView vistaescaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_marca);

        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        Ventas.cargarDatos(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
