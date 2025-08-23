package com.example.poo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class VerComunicados extends AppCompatActivity {

    private Spinner spinnerFechas;
    private LinearLayout layoutComunicados;
    private Button btnVolver;

    private ArrayList<String[]> listaComunicados= new ArrayList<>();
    private ArrayList<String> listaFechas= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercomunicados);

        spinnerFechas= findViewById(R.id.spinnerFechas);
        layoutComunicados = findViewById(R.id.layoutComunicados);
        btnVolver = findViewById(R.id.btnVolver);

        cargarComunicados();

        for (String[] datos : listaComunicados) {
            if (datos.length > 8) {
                if (datos[1].trim().equalsIgnoreCase("evento")) {
                    String fecha = datos[8].trim();
                    if (!listaFechas.contains(fecha)) {
                        listaFechas.add(fecha);
                    }
                }
            }
        }

        if (!listaFechas.isEmpty()){
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaFechas);
            spinnerFechas.setAdapter(adapter);
            spinnerFechas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    String fechaSeleccionada= listaFechas.get(position);
                    mostrarComunicadosPorFecha(fechaSeleccionada);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent){

                }
            });
        }

        btnVolver.setOnClickListener(v -> finish());

        mostrarComunicados(listaComunicados);
    }

    private void cargarComunicados(){
        listaComunicados.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("comunicados.txt")));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(",");
                    for (int i = 0; i < datos.length; i++) {
                        datos[i] = datos[i].trim();
                    }
                    listaComunicados.add(datos);
                }
            }
            br.close();
        } catch (Exception e) {
            Toast.makeText(this, "No hay comunicados aún", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarComunicados(ArrayList<String[]> comunicados) {
        layoutComunicados.removeAllViews();

        for (String[] datos : comunicados) {
            String tipo = datos[1];
            String titulo = datos[3];
            String descripcion = datos[5];
            String imagen = datos[6];

            // Título
            TextView tvTitulo = new TextView(this);
            tvTitulo.setText(titulo + " (" + tipo + ")");
            tvTitulo.setTextSize(20f);
            tvTitulo.setTypeface(null, Typeface.BOLD);
            layoutComunicados.addView(tvTitulo);

            // Imagen
            ImageView img = new ImageView(this);
            int resId = getResources().getIdentifier(imagen, "drawable", getPackageName());
            if (resId != 0) {
                img.setImageResource(resId);
            }
            layoutComunicados.addView(img);

            // Descripción
            TextView tvDesc = new TextView(this);
            tvDesc.setText(descripcion);
            layoutComunicados.addView(tvDesc);
        }
    }

    private void mostrarComunicadosPorFecha(String fecha){
        ArrayList<String[]> filtrados= new ArrayList<>();
        for (String[] datos: listaComunicados){
            if (datos.length>8){
                if (datos[1].equalsIgnoreCase("evento") && datos[8].equals(fecha)){
                    filtrados.add(datos);
                }
            }
        }
        mostrarComunicados(filtrados);
    }
}