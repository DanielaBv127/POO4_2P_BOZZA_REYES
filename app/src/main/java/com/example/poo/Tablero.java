package com.example.poo;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * La clase {@code Tablero} representa la vista donde se listan los comunicados
 * previamente publicados. Permite al usuario:
 * <ul>
 *     <li>Visualizar en una tabla los comunicados (eventos y anuncios).</li>
 *     <li>Ordenar los comunicados (según el criterio definido en la clase {@link Comunicado}).</li>
 *     <li>Guardar los comunicados en un archivo serializado (.dat).</li>
 *     <li>Regresar al menú principal.</li>
 * </ul>
 *
 * <p>Los comunicados se cargan desde un archivo de texto plano {@code comunicados.txt}
 * generado por {@link PublicarComunicados}, y se muestran con su título y fecha.</p>
 *
 * @author Paola Reyes
 * @version 1.0
 */
public class Tablero extends AppCompatActivity {
    /** Tabla en la que se muestran los comunicados. */
    private TableLayout tabla;
    /** Botón para ordenar, guardar  los comunicados y regresr al menu principal. */
    private Button btnOrdenar, btnGuardar, btnVolver;
    /** Lista de objetos {@link Comunicado} cargados desde archivo. */
    private List<Comunicado> comunicados = new ArrayList<>();
    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa los componentes, carga los comunicados desde archivo
     * y muestra los datos en la tabla.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
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
    /**
     * Carga los comunicados almacenados en el archivo {@code comunicados.txt}
     * y los convierte en objetos {@link Comunicado}, que se agregan a la lista.
     *
     * <p>El formato esperado en cada línea es:
     * <ul>
     *     <li>Evento: id, tipo, área, título, audiencia, descripción, imagen, lugar, fecha</li>
     *     <li>Anuncio: id, tipo, área, título, audiencia, descripción, imagen, urgencia</li>
     * </ul>
     * </p>
     */
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

    /**
     * Muestra en la tabla los comunicados cargados.
     * La tabla tiene una cabecera con títulos de columna y una fila por cada comunicado,
     * mostrando el título y la fecha (si corresponde).
     */
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
    /**
     * Guarda la lista de comunicados en un archivo serializado (.dat),
     * nombrado con la fecha actual en formato {@code comunicados_dd_MM_yyyy.dat}.
     */
    private void guardarSerializado() {
        try {
            String fecha = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
            String nombreArchivo = "comunicados_" + fecha + ".dat";
            FileOutputStream fos = openFileOutput(nombreArchivo, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<>(comunicados));
            oos.close();
            Toast.makeText(this, "Archivo guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}