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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tablero extends AppCompatActivity {private TableLayout tabla;
    private Button btnOrdenar, btnGuardar, btnVolver;
    private List<String[]> comunicados = new ArrayList<>();

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
            comunicados.sort(Comparator.comparing(c -> c[3]));
            mostrarComunicados();
        });

        btnGuardar.setOnClickListener(v -> guardarSerializado());

        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        });
    }

    private void cargarComunicados() {
        try (FileInputStream fis = openFileInput("comunicados.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                comunicados.add(linea.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarComunicados() {
        tabla.removeAllViews();
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

        for (String[] c : comunicados) {
            TableRow fila = new TableRow(this);

            TextView tvTitulo = new TextView(this);
            tvTitulo.setText(c[3].trim());
            tvTitulo.setPadding(16, 8, 16, 8);
            fila.addView(tvTitulo);

            TextView tvFecha = new TextView(this);
            String fecha = "";
            if (c[1].equalsIgnoreCase("evento") && c.length >= 9) {
                fecha = c[8].trim();
            } else {
                fecha = "--";
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
            oos.writeObject(comunicados);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}