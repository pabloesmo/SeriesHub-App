package es.upm.projecto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

import es.upm.projecto.util.NetUtils;

public class EscribirOpinion extends AppCompatActivity {

    Uri imagenUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir_opinion);

        Intent in = getIntent();
        ImageButton b = findViewById(R.id.escribir_op_compartir);
        EditText resena = findViewById(R.id.escribir_op_res);
        String imagen = in.getStringExtra("serie-img");
        String titulo = in.getStringExtra("titulo");


        CatchImagen catchImagen = new CatchImagen(this, imagen);
        Thread thread = new Thread(catchImagen);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String res = resena.getText().toString();
                if (res.trim().length() != 0){

                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_TEXT, "El usuario" + " compartió su opinión sobre : " + titulo + "\n" + "\nComentario: " + res);
                    i.putExtra(Intent.EXTRA_STREAM, imagenUri);
                    i.setType("*/*");
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Se debe escribir algo...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void onImageDownloaded(Bitmap bitmap) {
        // Este método será llamado cuando la imagen se haya descargado
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Hacer algo con el Bitmap en el hilo principal
                if (bitmap != null) {
                    imagenUri = getImageUri(getApplicationContext(),bitmap);
                }
            }
        });
    }
}