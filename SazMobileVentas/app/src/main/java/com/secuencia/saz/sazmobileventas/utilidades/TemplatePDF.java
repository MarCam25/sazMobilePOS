package com.secuencia.saz.sazmobileventas.utilidades;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.secuencia.saz.sazmobileventas.Principal;
import com.secuencia.saz.sazmobileventas.R;
import com.secuencia.saz.sazmobileventas.ViewPDFActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class TemplatePDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Image image;
    private Paragraph paragraph;
    private Font fTitle= new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD,new BaseColor(0,67,115));
    private Font fSubTitle= new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD,BaseColor.WHITE);
    private Font textoTabla= new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD,BaseColor.BLACK);
    private Font fText= new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    private Font fHighText= new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD,BaseColor.BLACK);
    File folder=new File(Environment.getExternalStorageDirectory().toString(),"SazTicket");
    public TemplatePDF(Context context) {
        this.context=context;
    }
    public void openDocument(){

        createFile();
        try{
            document=new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            document.open();

        }catch (Exception e){
            Log.e("OpenDocument", e.toString() );
        }
    }

    private void createFile(){

        if(!folder.exists())
            folder.mkdir();

            pdfFile= new File(folder, "TicketVenta.pdf");

    }


    public void closeDocument(){
        document.close();

    }


    public void addMetaData(String title, String subjet, String autor){
        document.addTitle(title);
        document.addSubject(subjet);
        document.addAuthor(autor);


    }

    public void addTitles(String title, String subTitle, String date ){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubTitle));
            addChildP(new Paragraph("Generado " + date, fHighText));
            paragraph.setSpacingAfter(2);
            paragraph.setSpacingBefore(2);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles", e.toString());
        }
    }

    public void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(text, fText));
            paragraph.setSpacingAfter(2);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }




    public void tablaProductos(String[] header, ArrayList<String []>clientes){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setSpacingBefore(5);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor( new BaseColor(0,67,115));
                pdfPTable.addCell(pdfPCell);

            }

            for (int indexR = 0; indexR < clientes.size(); indexR++) {
                String[] row = clientes.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC],textoTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);


                }

            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }



    public void tablaPagos(String[] header, ArrayList<String []>clientes){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor( new BaseColor(0,67,115));
                pdfPTable.addCell(pdfPCell);

            }

            for (int indexR = 0; indexR < clientes.size(); indexR++) {
                String[] row = clientes.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC],textoTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);


                }

            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }

    public void tablaTotal(String[] header, ArrayList<String []>clientes){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor( new BaseColor(0,67,115));
                pdfPTable.addCell(pdfPCell);

            }

            for (int indexR = 0; indexR < clientes.size(); indexR++) {
                String[] row = clientes.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC],textoTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);


                }

            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }



    public void viewPDF(){
        Intent intent=new Intent(context, ViewPDFActivity.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    public void appViewPDFApp(Activity activity){
        if(pdfFile.exists()){
            Uri uri=Uri.fromFile(pdfFile);
            Intent intent=new Intent (Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            try{
                activity.startActivity(intent);

            }catch (ActivityNotFoundException e){

                activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getApplicationContext(),"No cuentas con una aplicacion para visualizar PDF", Toast.LENGTH_LONG).show();

            }

        }else{
            Toast.makeText(activity.getApplicationContext(),"No se encontro un archivo pdf", Toast.LENGTH_LONG).show();
        }
    }


    public void enviarPDF(){
        String[] mailto = {""};
        Uri uri=Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/CALC/REPORTS/","TicketVenta.pdf"));
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Calc PDF Report");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Hi PDF is attached in this mail. ");
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(emailIntent, "Send email using:"));

    }



    public void addImgName () {

        try{
            Image image = Image.getInstance(folder.getAbsolutePath() + R.drawable.logotipo +".png");
            image.setSpacingBefore(5);
            image.setSpacingAfter(5);
            image.scaleToFit(400,400);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        }catch (Exception e){
            Log.e("addImgName ", e.toString());
        }
    }


    //Procedimiento para enviar por email el documento PDF generado


}

