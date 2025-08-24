package com.example.poo;

import android.content.Intent;
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
            comunicados.sort(Comparator.comparing(c -> c[3])); // ordena por título
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
        for (String[] c : comunicados) {
            TableRow fila = new TableRow(this);
            for (String dato : c) {
                TextView tv = new TextView(this);
                tv.setText(dato.trim());
                tv.setPadding(8, 8, 8, 8);
                fila.addView(tv);
            }
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