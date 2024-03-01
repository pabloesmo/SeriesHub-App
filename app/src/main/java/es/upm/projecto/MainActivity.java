package es.upm.projecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Timer;

/*
-------------------------------------------------------------------
----INFORMACION IMPORTANTE PARA LA EJECUCION CORRECTA DE LA APP----
-------------------------------------------------------------------
Usuario: admin, Contraseña: admin
   Para gradle:
   dependencies {
        implementation ("org.jsoup:jsoup:1.14.3")
        implementation ("com.squareup.picasso:picasso:2.71828")
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
   }

    compileSdk = 34
    minSdk = 33
    targetSdk = 34

    Necesario tener activado el permiso de recibir notificaciones en el teléfono del
    emulador para poder recibirlas al añadir una seria a favoritos.d
 */

public class MainActivity extends AppCompatActivity {

    TextView username;
    TextView password;
    Button botonLogin;
    LinearLayout linearLayout;
    ImageView imagenGoogle;
    ImageView imagenFacebook;
    ImageView imagenTwitter;
    CheckBox checkBoxMantenerSesion;

    SharedPreferences sP;
    private HashMap<String, String> usuariosCredenciales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        botonLogin = findViewById(R.id.botonLogin);
        linearLayout = findViewById(R.id.linea_imagenes);
        imagenGoogle = findViewById(R.id.img_google);
        imagenFacebook = findViewById(R.id.img_facebook);
        imagenTwitter = findViewById(R.id.img_twitter);
        checkBoxMantenerSesion = findViewById(R.id.checkBoxMantenerSesion);

        usuariosCredenciales = new HashMap<>();
        usuariosCredenciales.put("admin", "admin");

        sP = getSharedPreferences("cuenta", MODE_PRIVATE);

        String usuario = sP.getString("usuario", null);
        if (usuario != null) {
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(i);
        }

        //BOTON DE LOGIN PARA ACCEDER A LA APP
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                if (usuariosCredenciales.containsKey(enteredUsername) && usuariosCredenciales.get(enteredUsername).equals(enteredPassword)) {
                    // Credenciales válidas
                    boolean mantenerSesion = checkBoxMantenerSesion.isChecked();
                    if (mantenerSesion) {
                        SharedPreferences.Editor ed = sP.edit();
                        ed.putString("usuario", enteredUsername);
                        ed.putString("contrasena", enteredPassword);
                        ed.apply();
                    }
                    Intent in = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(in);
                    in.putExtra("usuario", enteredUsername); // Pasa el nombre de usuario
                } else {
                    // Credenciales incorrectas
                    Toast.makeText(MainActivity.this, "¡Usuario o contraseña incorrectos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON IR A LA WEB 1
        imagenGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre un navegador web con el sitio deseado cuando se hace clic en la imagen 1
                String url = "https://www.fi.upm.es/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        //BOTON IR A LA WEB 2
        imagenFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre un navegador web con el sitio deseado cuando se hace clic en la imagen 2
                String url = "https://www.facebook.com/ETSIINF/?locale=es_ES";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        //BOTON IR A LA WEB 3
        imagenTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre un navegador web con el sitio deseado cuando se hace clic en la imagen 3
                String url = "https://twitter.com/La_UPM?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

}