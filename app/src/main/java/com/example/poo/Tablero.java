package com.example.poo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tablero extends AppCompatActivity {

    private TableLayout tabla;
    private Button btnOrdenar, btnGuardar, btnVolver;
    private List<Comunicado> comunicados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        tabla = findViewById(R.id.tablaComunicados);
        btnOrdenar = findViewById(R.id.btnOrdenar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);

        cargarComunicados();
        mostrarComunicados();

        btnOrdenar.setOnClickListener(v -> {
            Collections.sort(comunicados);
            mostrarComunicados();
        });

        btnGuardar.setOnClickListener(v -> guardarSerializado());

        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        });
    }

    private void cargarComunicados() {
        comunicados.clear();
        try (FileInputStream fis = openFileInput("comunicados.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");

                Comunicado comunicado = null;
                // Evento
                if (partes[1].trim().equalsIgnoreCase("evento") && partes.length >= 9) {
                    comunicado = new Comunicado(Integer.parseInt(partes[0].trim()), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                            partes[4].trim(), partes[5].trim(), partes[6].trim(), partes[7].trim(), partes[8].trim(), null);
                }
                // Anuncio
                else if (partes[1].trim().equalsIgnoreCase("anuncio") && partes.length >= 8) {
                    comunicado = new Comunicado(Integer.parseInt(partes[0].trim()), partes[1].trim(), partes[2].trim(), partes[3].trim(), partes[4].trim(),
                            partes[5].trim(), partes[6].trim(), null, null, partes[7].trim());
                }

                if (comunicado != null) {
                    comunicados.add(comunicado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarComunicados() {
        tabla.removeAllViews();

        // Cabecera
        TableRow header = new TableRow(this);

        TextView thTitulo = new TextView(this);
        thTitulo.setText("Título");
        thTitulo.setTypeface(null, Typeface.BOLD);
        thTitulo.setPadding(16, 8, 16, 8);
        header.addView(thTitulo);

        TextView thFecha = new TextView(this);
        thFecha.setText("Fecha");
        thFecha.setTypeface(null, Typeface.BOLD);
        thFecha.setPadding(16, 8, 16, 8);
        header.addView(thFecha);

        tabla.addView(header);

        // Filas con datos
        for (Comunicado c : comunicados) {
            TableRow fila = new TableRow(this);

            TextView tvTitulo = new TextView(this);
            tvTitulo.setText(c.getTitulo());
            tvTitulo.setPadding(16, 8, 16, 8);
            fila.addView(tvTitulo);

            TextView tvFecha = new TextView(this);
            String fecha = "--";
            if ("evento".equalsIgnoreCase(c.getTipo()) && c.getFecha() != null) {
                fecha = c.getFecha().trim();
            }
            tvFecha.setText(fecha);
            tvFecha.setPadding(16, 8, 16, 8);
            fila.addView(tvFecha);

            tabla.addView(fila);
        }
    }

    private void guardarSerializado() {
        try {
            String fecha = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
            String nombreArchivo = "comunicados_" + fecha + ".dat";
            FileOutputStream fos = openFileOutput(nombreArchivo, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<>(comunicados));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}