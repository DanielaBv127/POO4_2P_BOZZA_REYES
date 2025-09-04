package com.example.poo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/**
 * {@code MenuActivity} representa la pantalla principal del menú en la aplicación.
 *
 * <p>Desde esta actividad el usuario puede acceder a diferentes secciones:</p>
 * <ul>
 *   <li>{@link VerComunicados}: Permite visualizar los comunicados.</li>
 *   <li>{@link PublicarComunicados}: Permite publicar nuevos comunicados.</li>
 *   <li>{@link Tablero}: Muestra el tablero principal con opciones adicionales.</li>
 * </ul>
 *
 * <p>Cada opción se activa a través de un botón en la interfaz.</p>
 *
 * @author Daniela Bozza
 * @version 1.0
 * @since 2025-09-04
 */
public class MenuActivity extends AppCompatActivity {
    private Button btnVerComunicados;
    private Button btnPublicar;
    private Button btnTablero;
    /**
     * Método llamado al crear la actividad.
     * Configura la interfaz y define las acciones de los botones
     * para abrir las actividades correspondientes.
     *
     * @param savedInstanceState Estado guardado de la actividad (si existe)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnVerComunicados= findViewById(R.id.btnVerComunicados);
        btnPublicar= findViewById(R.id.btnPublicar);
        btnTablero= findViewById(R.id.btnTablero);

        btnVerComunicados.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(MenuActivity.this, VerComunicados.class);
                startActivity(intent);
            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(MenuActivity.this, PublicarComunicados.class);
                startActivity(intent);
            }
        });

        btnTablero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(MenuActivity.this, Tablero.class);
                startActivity(intent);
            }
        });
    }
}
