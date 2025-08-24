package com.example.poo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;

public class PublicarComunicados extends AppCompatActivity {
    private RadioGroup rgTipo;
    private RadioButton rbEvento, rbAnuncio;
    private Spinner spArea, spUrgencia;
    private CheckBox cbEstudiantes, cbProfesores, cbAdministrativo;
    private EditText etTitulo, etDescripcion, etLugar, etArte;
    private DatePicker datePickerEvento;
    private LinearLayout layoutEvento, layoutAnuncio;
    private Button btnPublicar, btnVolver, btnSeleccionarImagen;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagenUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicarcomunicados);

        rgTipo = findViewById(R.id.rgTipo);
        rbEvento = findViewById(R.id.rbEvento);
        rbAnuncio = findViewById(R.id.rbAnuncio);
        spArea = findViewById(R.id.spArea);
        spUrgencia = findViewById(R.id.spUrgencia);
        cbEstudiantes = findViewById(R.id.checkbox_estudiantes);
        cbProfesores = findViewById(R.id.checkbox_profesores);
        cbAdministrativo = findViewById(R.id.checkbox_administrativo);
        etTitulo = findViewById(R.id.edit_text_titulo);
        etDescripcion = findViewById(R.id.edit_text_descripcion);
        etLugar = findViewById(R.id.etLugarEvento);
        etArte = findViewById(R.id.edit_text_arte);
        datePickerEvento = findViewById(R.id.datePickerEvento);
        layoutEvento = findViewById(R.id.layoutEvento);
        layoutAnuncio = findViewById(R.id.layoutAnuncio);
        btnPublicar = findViewById(R.id.btnPublicar);
        btnVolver = findViewById(R.id.btnVolver);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        rgTipo.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbEvento) {
                layoutEvento.setVisibility(View.VISIBLE);
                layoutAnuncio.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbAnuncio) {
                layoutEvento.setVisibility(View.GONE);
                layoutAnuncio.setVisibility(View.VISIBLE);
            }
        });
        btnPublicar.setOnClickListener(v -> {
            try {
                guardarComunicado();
                Toast.makeText(this, "Comunicado publicado", Toast.LENGTH_SHORT).show();
            } catch (DatosIncompletosException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error guardando comunicado", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                imagenUri = data.getData();
                if (imagenUri != null) {
                    etArte.setText(imagenUri.toString()); // 👈 guardamos la URI en el EditText
                }
            }
        }

    private void guardarComunicado() throws DatosIncompletosException, IOException {
        String tipo = rbEvento.isChecked() ? "evento" : rbAnuncio.isChecked() ? "anuncio" : "";
        String area = spArea.getSelectedItem() != null ? spArea.getSelectedItem().toString() : "";
        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String imagen = etArte.getText().toString().trim();
        String audiencia = obtenerAudiencia();
        if (tipo.isEmpty() || titulo.isEmpty() || descripcion.isEmpty() || imagen.isEmpty() || audiencia.isEmpty()) {
            throw new DatosIncompletosException("No están todos los datos completos");
        }

        String linea;
        if (tipo.equals("evento")) {
            String lugar = etLugar.getText().toString().trim();
            int dia = datePickerEvento.getDayOfMonth();
            int mes = datePickerEvento.getMonth() + 1;
            int anio = datePickerEvento.getYear();
            String fecha = String.format("%02d/%02d/%04d", dia, mes, anio);
            if (lugar.isEmpty()) throw new DatosIncompletosException("Falta el lugar del evento");
            linea = String.format("%d, %s, %s, %s, %s, %s, %s, %s, %s\n",
                    generarId(), tipo, area, titulo, audiencia, descripcion, imagen, lugar, fecha);
        } else {
            String urgencia = spUrgencia.getSelectedItem() != null ? spUrgencia.getSelectedItem().toString() : "";
            if (urgencia.isEmpty()) throw new DatosIncompletosException("Falta seleccionar urgencia");
            linea = String.format("%d, %s, %s, %s, %s, %s, %s, %s\n",
                    generarId(), tipo, area, titulo, audiencia, descripcion, imagen, urgencia);
        }
        FileOutputStream fos = openFileOutput("comunicados.txt", MODE_APPEND);
        fos.write(linea.getBytes());
        fos.close();
    }

    private String obtenerAudiencia() {
        StringBuilder sb = new StringBuilder();
        if (cbEstudiantes.isChecked()) sb.append("Estudiantes;");
        if (cbProfesores.isChecked()) sb.append("Profesores;");
        if (cbAdministrativo.isChecked()) sb.append("Administrativo;");
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
    private int generarId() {
        return (int) (Math.random() * 1000) + 1;
    }
}
