package es.upm.projecto;

import java.util.ArrayList;

public class FavoritosSingleton {
    private static FavoritosSingleton instance;
    private ArrayList<Series> seriesFavoritas;

    private FavoritosSingleton() {
        seriesFavoritas = new ArrayList<>();
    }

    public static FavoritosSingleton getInstance() {
        if (instance == null) {
            instance = new FavoritosSingleton();
        }
        return instance;
    }

    public ArrayList<Series> getSeriesFavoritas() {
        return seriesFavoritas;
    }

    public void agregarSerieFavorita(Series serie) {
        seriesFavoritas.add(serie);
    }

    public boolean serieYaEnFavoritos(String serieTitle) {
        for (Series serie : seriesFavoritas) {
            if (serie.getShow().getName().equals(serieTitle)) {
                return true; // La serie ya está en favoritos
            }
        }
        return false; // La serie no está en favoritos
    }
}
