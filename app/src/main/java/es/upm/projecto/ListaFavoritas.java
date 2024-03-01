package es.upm.projecto;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListaFavoritas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_favoritos);

        ArrayList<Series> listaSeries;
        listaSeries = FavoritosSingleton.getInstance().getSeriesFavoritas();

        ListView listView = findViewById(R.id.lista_favoritos);
        AdapterFavoritas adapter = new AdapterFavoritas(this, listaSeries);
        listView.setAdapter(adapter);
    }
}
