package com.example.poo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * {@code LoginActivity} gestiona el inicio de sesión de los usuarios en la aplicación.
 *
 * <p>Lee las credenciales desde un archivo de texto ubicado en la carpeta {@code assets}
 * (archivo {@code usuarios.txt}), y compara los datos ingresados con los almacenados.</p>
 *
 * <p>En caso de que las credenciales sean correctas, redirige al usuario a {@code MenuActivity}.
 * Si no lo son, se lanza una {@link CredencialesInvalidasException} mostrando un mensaje
 * en pantalla.</p>
 *
 * @author Daniela Bozza
 * @version 1.0
 * @since 2025-09-04
 */
public class LoginActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button btnLogin;

    /**
     * Método llamado al crear la actividad. Inicializa la interfaz y define
     * el comportamiento del botón de inicio de sesión.
     *
     * @param savedInstanceState Estado guardado de la actividad (si existe)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Username= findViewById(R.id.editTextUser);
        Password= findViewById(R.id.editTextPassword);
        btnLogin= findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();

                try {
                    if (checkCredenciales(usuario, password)) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (CredencialesInvalidasException e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(LoginActivity.this, "Error leyendo archivo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Verifica las credenciales del usuario comparándolas con las guardadas en
     * el archivo {@code usuarios.txt} dentro de la carpeta {@code assets}.
     *
     * @param usuario  Nombre de usuario ingresado
     * @param password Contraseña ingresada
     * @return {@code true} si las credenciales son correctas, {@code false} en caso contrario
     * @throws IOException Si ocurre un error al leer el archivo
     * @throws CredencialesInvalidasException Si las credenciales no coinciden con ninguna en el archivo
     */
    private boolean checkCredenciales(String usuario, String password)
            throws IOException, CredencialesInvalidasException {

        try (InputStream is = getAssets().open("usuarios.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String userFile = partes[0].trim();
                    String passFile = partes[1].trim();
                    if (usuario.equals(userFile) && password.equals(passFile)) {
                        return true;
                    }
                }
            }
        }
        throw new CredencialesInvalidasException("Usuario o contraseña incorrectos");
    }
}