package es.upm.projecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import es.upm.projecto.util.NetUtils;

public class CatchImagen implements Runnable {

    private EscribirOpinion eo;
    private String urlImagen = "";
    private Bitmap bmp = null;

    public CatchImagen(EscribirOpinion escribirOpinion, String urlImagen) {
        eo = escribirOpinion;
        this.urlImagen = urlImagen;
    }

    @Override
    public void run() {
        try {
            bmp = NetUtils.getURLImage(urlImagen);
            eo.onImageDownloaded(bmp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Bitmap getBitmap (){
        return bmp;
    }
}
