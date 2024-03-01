package es.upm.projecto;

import android.graphics.Bitmap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import es.upm.projecto.util.NetUtils;

public class SearchThread implements Runnable {

    private MainActivity2 ma;
    private String serie = "";

    public SearchThread(MainActivity2 mainActivity2, String serie) {
        ma = mainActivity2;
        this.serie = serie;
    }

    @Override
    public void run() {
        ma.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ma.startDownload(); // Mover aquí para mostrar la barra de progreso al inicio de la búsqueda
            }
        });

        List<Series> pls = null;
        try {
            Thread.sleep(2000);
            //url de la API base
            String baseUrl = "https://api.tvmaze.com/search/shows?q=";
            //añadido de la busqueda que el usuario haga
            String jsonSeriesList = NetUtils.getURLText(baseUrl + serie);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            pls = Arrays.asList(gson.fromJson(jsonSeriesList, Series[].class));

            for (Series serie : pls) {
                String imagenADescargar = serie.getShow().getImage().getImage();
                Bitmap imageSerie = NetUtils.getURLImage(imagenADescargar);
                serie.getShow().getImage().setImageBmp(imageSerie);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final List<Series> res = pls;

        ma.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ma.finishDownload(res); // Mover aquí para ocultar la barra de progreso y mostrar los datos
            }
        });
    }
}
