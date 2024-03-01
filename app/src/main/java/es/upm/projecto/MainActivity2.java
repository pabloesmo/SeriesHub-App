package es.upm.projecto;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity {

    SharedPreferences sP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText busqueda = findViewById(R.id.sec_busqueda);
        ImageButton b = findViewById(R.id.lupa);
        ProgressBar barraP = findViewById(R.id.barra_progreso);
        barraP.setVisibility((View.GONE));
        ImageButton logout = findViewById(R.id.btn_logout);
        ImageButton bListaFavs = findViewById(R.id.btn_favoritos);
        sP = getSharedPreferences("cuenta", MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = sP.edit();
                ed.clear();
                ed.commit();
                Toast.makeText(getApplicationContext(), "Has cerrado sesión", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Boton busqueda
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barraP.setVisibility((View.VISIBLE));
                //AQUI OCULTO EL TECLADO CUANDO LE DOY AL BOTON DE BUSQUEDA
                ocultarTeclado();
                final String nombre = busqueda.getText().toString();
                Thread th = new Thread(new SearchThread(MainActivity2.this, busqueda.getText().toString()));
                th.start();
            }
        });

        bListaFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agrega la animación al hacer clic en el botón
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity2.this, R.animator.scale_anim);
                animatorSet.setTarget(bListaFavs);
                animatorSet.start();
                Intent intent = new Intent(MainActivity2.this, ListaFavoritas.class);
                startActivity(intent);
            }
        });
    }

    private void ocultarTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void startDownload() {
        findViewById(R.id.lupa).setEnabled(false);
    }

    public void finishDownload(List<Series> results) {
        AdapterSeries adapter = new AdapterSeries(this, results);
        ListView listView = findViewById(R.id.lista);
        listView.setAdapter(adapter);

        // Configura el OnItemClickListener para el ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene la serie seleccionada
                Series selectedSeries = results.get(position);

                // Crea un Intent para iniciar la nueva Activity
                Intent intent = new Intent(MainActivity2.this, SerieEspecifica.class);
                intent.putExtra("serie_title", selectedSeries.getShow().getName());
                intent.putExtra("serie_average", selectedSeries.getShow().getRating().getAverage());
                intent.putExtra("serie_image", selectedSeries.getShow().getImage().getImage());
                intent.putExtra("serie_resumen", selectedSeries.getShow().getResumen());
                intent.putExtra("serie_sitioOficial", selectedSeries.getShow().getSitioOficial());
                intent.putExtra("serie_idioma", selectedSeries.getShow().getIdioma());
                startActivity(intent);
            }
        });
        findViewById(R.id.lupa).setEnabled(true);
        findViewById(R.id.barra_progreso).setVisibility((View.GONE));
    }
}