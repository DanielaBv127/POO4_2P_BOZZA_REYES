package com.example.poo;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerComunicados extends AppCompatActivity {
    private LinearLayout layoutComunicados;
    private Button btnVolver;
    private Spinner spinnerFechas;

    private List<String[]> comunicados= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercomunicados);

        layoutComunicados = findViewById(R.id.layoutComunicados);
        btnVolver = findViewById(R.id.btnVolver);
        spinnerFechas= findViewById(R.id.spinnerFechas);

        cargarComunicados();
        cargarFechas();
        mostrarComunicados(comunicados);

        spinnerFechas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fechaSeleccionada= parent.getItemAtPosition(position).toString();
                if (fechaSeleccionada.equals("Todos")){
                    mostrarComunicados(comunicados);
                }else{
                    filtrarPorFecha(fechaSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarComunicados() {
        comunicados.clear();
        try (FileInputStream fis = openFileInput("comunicados.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 7) {
                    comunicados.add(partes);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "No se encontraron comunicados", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarFechas(){
        List<String> opciones= new ArrayList<>();
        opciones.add("Todos");
        for (String[] c: comunicados){
            if (c[1].trim().equalsIgnoreCase("evento") && c.length>=9){
                String fecha= c[8].trim();
                if (!opciones.contains(fecha)){
                    opciones.add(fecha);
                }
            }
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFechas.setAdapter(adapter);
    }

    private void mostrarComunicados(List<String[]> lista){
        layoutComunicados.removeAllViews();
        for (String[] c: lista){
            String titulo= c[3].trim();
            String descripcion= c[5].trim();
            String imagen= c[6].trim();

            agregarCard(titulo,descripcion,imagen);
        }
    }

    private void filtrarPorFecha(String fecha){
        List<String[]> filtrados= new ArrayList<>();
        for (String[] c: comunicados){
            if (c[1].trim().equalsIgnoreCase("evento") && c.length>=9){
                String fechaEvento= c[8].trim();
                if (fechaEvento.equals(fecha)){
                    filtrados.add(c);
                }
            }
        }
        mostrarComunicados(filtrados);
    }

    private void agregarCard(String titulo, String descripcion, String nombreImagen){
        LinearLayout card= new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(20,20,20,20);

        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,30);
        card.setLayoutParams(params);

        //Título
        TextView tvTitulo= new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextSize(18f);
        tvTitulo.setTypeface(null, Typeface.BOLD);
        tvTitulo.setPadding(0,0,0,30);
        tvTitulo.setGravity(Gravity.CENTER_HORIZONTAL);


        //Imagen
        ImageView img= new ImageView(this);
        try{
            Uri uri= Uri.parse(nombreImagen);
            img.setImageURI(uri);
        }catch (Exception e){
            img.setImageResource(android.R.drawable.ic_menu_report_image);
        }
        img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        img.setAdjustViewBounds(true);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //Descripción
        TextView tvDesc= new TextView(this);
        tvDesc.setText(descripcion);
        tvDesc.setPadding(0,30,0,0);
        tvDesc.setLineSpacing(0,1.5f);

        card.addView(tvTitulo);
        card.addView(img);
        card.addView(tvDesc);

        layoutComunicados.addView(card);
    }
}