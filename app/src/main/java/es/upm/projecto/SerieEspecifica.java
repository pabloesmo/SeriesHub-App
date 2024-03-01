package es.upm.projecto;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;

public class SerieEspecifica extends AppCompatActivity{

    NotificationHandler handler;
    int notificationCounter = 1; // Contador de notificaciones


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_serie);
        handler = new NotificationHandler(SerieEspecifica.this);
        TextView dondeVer = findViewById(R.id.donde_ver);

        Intent intent = getIntent();
        String serieTitle = intent.getStringExtra("serie_title");
        double serieAverage = intent.getDoubleExtra("serie_average", 1);
        String serieImage = intent.getStringExtra("serie_image");
        String serieWeb = intent.getStringExtra("serie_sitioOficial");
        String serieResumen = intent.getStringExtra("serie_resumen");
        String serieIdioma = intent.getStringExtra("serie_idioma");
        String imagen = intent.getStringExtra("serie_image");

        TextView tituloSerie = findViewById(R.id.serie_title);
        TextView averageSerie = findViewById(R.id.serie_average);
        ImageView imagenSerie = findViewById(R.id.serie_foto);
        TextView resumenSerie = findViewById(R.id.serie_resumen);
        String resumenLimpio = Jsoup.parse(serieResumen).text();
        TextView idiomaSerie = findViewById(R.id.serie_idioma);
        ImageButton fav = findViewById(R.id.me_gusta_boton);


        if(serieIdioma.contains("English")){
            idiomaSerie.setCompoundDrawablesWithIntrinsicBounds(R.drawable.usa, 0, 0, 0);
        }
        else if(serieIdioma.contains("Chinese")){
            idiomaSerie.setCompoundDrawablesWithIntrinsicBounds(R.drawable.china, 0, 0, 0);
        }
        else if(serieIdioma.contains("Spanish")){
            idiomaSerie.setCompoundDrawablesWithIntrinsicBounds(R.drawable.espagna, 0, 0, 0);
        }
        else if(serieIdioma.contains("Hungarian")){
            idiomaSerie.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hungria, 0, 0, 0);
        }
        else if(serieIdioma.contains("French")){
            idiomaSerie.setCompoundDrawablesWithIntrinsicBounds(R.drawable.francia, 0, 0, 0);
        }
        else if(serieIdioma.contains("Portuguese")){
            idiomaSerie.setCompoundDrawablesWithIntrinsicBounds(R.drawable.portugal, 0, 0, 0);
        }


        tituloSerie.setText(serieTitle);
        if(serieAverage != 0) {
            averageSerie.setText(String.format("%.1f", serieAverage));
        } else
            averageSerie.setText("No disponible");
        resumenSerie.setText(resumenLimpio);;
        Picasso.get().load(serieImage).into(imagenSerie);

        String officialSiteUrl = serieWeb;
        ImageView imagenWeb = findViewById(R.id.imagen_web);

        ImageButton comp = findViewById(R.id.boton_compartir);
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(SerieEspecifica.this, R.animator.scale_anim);
                animatorSet.setTarget(comp);
                animatorSet.start();
                Intent in = new Intent(SerieEspecifica.this, EscribirOpinion.class);
                in.putExtra("titulo", serieTitle);
                in.putExtra("serie-img", imagen);
                startActivity(in);
            }
        });


        if (officialSiteUrl == null){
            imagenWeb.setVisibility(View.GONE);
            dondeVer.setVisibility(View.GONE);
            // Ajusta las restricciones del botón de compartir cuando no hay imagen_web
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) comp.getLayoutParams();
            params.horizontalBias = 0.75f;
            comp.setLayoutParams(params);

            ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) fav.getLayoutParams();
            params2.horizontalBias = 0.25f;
            fav.setLayoutParams(params2);
        }

        else if (officialSiteUrl != null && officialSiteUrl.contains("netflix")) {
            imagenWeb.setImageResource(R.drawable.netflix);
        } else if (officialSiteUrl != null && officialSiteUrl.contains("hbo")) {
            imagenWeb.setImageResource(R.drawable.hbo);
        }
        else if (officialSiteUrl != null && officialSiteUrl.contains("prime") || officialSiteUrl.contains("amazon")) {
            imagenWeb.setImageResource(R.drawable.prime_video);
        } else {
            imagenWeb.setImageResource(R.drawable.google);
        }

        imagenWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = serieWeb;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(SerieEspecifica.this, R.animator.scale_anim);
                animatorSet.setTarget(fav);
                animatorSet.start();

                Image serieImagen = new Image(serieImage);
                Show showCompleto = new Show(serieTitle,serieResumen,serieImagen,serieWeb,serieIdioma);
                Series serieSelecionada = new Series(serieAverage,showCompleto);

                // Agrega la serie actual al ArrayList de series favoritas
                if(!FavoritosSingleton.getInstance().serieYaEnFavoritos(serieTitle)) {
                    FavoritosSingleton.getInstance().agregarSerieFavorita(serieSelecionada);

                    Notification.Builder nBuilder = handler.createNotification("Añadida", "¡Se ha añadido a favoritos!", true);
                    handler.getManager().notify(notificationCounter,nBuilder.build());
                    notificationCounter++;
                    //Toast.makeText(SerieEspecifica.this, "¡Serie añadida a favoritos!", Toast.LENGTH_SHORT).show();

                }
                //ya existe en la lista
                else{
                    Notification.Builder nBuilder = handler.createNotification("Aviso", "Ya se añadió", true);
                    nBuilder.setSmallIcon(R.drawable.baseline_error_outline_24);
                    handler.getManager().notify(notificationCounter,nBuilder.build());
                    notificationCounter++;
                }
            }
        });
    }
}
