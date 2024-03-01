package es.upm.projecto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterSeries extends BaseAdapter {

    private Activity context;
    private List<Series> data;

    public AdapterSeries(Activity context, List<Series> data){
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
        if (view == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.serie_layout, null);
        }
        ((ImageView)view.findViewById(R.id.serie_image)).setImageBitmap(data.get(position).getShow().getImage().getImageBmp());
        ((TextView)view.findViewById(R.id.serie_name)).setText(data.get(position).getShow().getName());
        String score = String.format("Coincidencia = %.2f", data.get(position).getScore()*100);
        ((TextView)view.findViewById(R.id.serie_score)).setText(score);

        return view;
    }
}
