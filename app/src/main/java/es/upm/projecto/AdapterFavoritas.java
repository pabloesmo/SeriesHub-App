package es.upm.projecto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterFavoritas extends BaseAdapter {

    private Activity context;
    private ArrayList<Series> data;

    public AdapterFavoritas(Activity context, ArrayList<Series> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.serie_favorita_layout, null);
        }

        ImageView serieImage = view.findViewById(R.id.serie_image);
        TextView serieName = view.findViewById(R.id.serie_name);

        // Aquí obtén la serie de la posición actual
        Series currentSeries = data.get(position);

        // Configura la imagen, nombre y puntuación de la serie en la vista
        Picasso.get().load(currentSeries.getShow().getImage().getImage()).into(serieImage);
        serieName.setText(currentSeries.getShow().getName());

        return view;
    }
}
