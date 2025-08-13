package com.example.poo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private Button btnVerComunicados;
    private Button btnPublicar;
    private Button btnTablero;

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
