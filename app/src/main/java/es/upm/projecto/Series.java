package es.upm.projecto;

import android.graphics.Bitmap;
import android.text.Html;
import com.google.gson.annotations.SerializedName;

public class Series {
    @SerializedName("score")
    private double score;

    @SerializedName("show")
    private Show show;
    public Series(double score, Show show){
        this.score = score;
        this.show = show;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String toString(){
        return "Serie {" +
                "score=" + score + '\'' +
                ", " + show.toString() + '\'' +
                '}';
    }
}

class Show{
    public static String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }
    private String name;
    @SerializedName("summary")
    private String summary;
    private Image image;
    @SerializedName("officialSite")
    private String sitioOficial;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("language")
    private String idioma;

    public  Show (String name, String summary, Image image,String sitioOficial, String idioma){
        this.name = name;
        this.summary = summary;
        this.image = image;
        this.sitioOficial = sitioOficial;
        this.idioma = idioma;
    }

    public String getName() {
        return name;
    }

    public String getSitioOficial(){
        return sitioOficial;
    }

    public Rating getRating(){
        return rating;
    }

    public String getIdioma(){
        return idioma;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String toString() {
        return "name= "+ name + '\'' +
                ", summary= " + stripHtml(summary) + '\'';
    }

    public String getResumen() {
        return summary;
    }

}

class Rating {
    @SerializedName("average")
    private double average;

    public double getAverage() {
        return average;
    }
}


class Image {
    @SerializedName("medium")
    private String image;
    private Bitmap imagen;

    public Image(String image){
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getImageBmp() {
        return imagen;
    }

    public void setImageBmp(Bitmap imagen) {
        this.imagen = imagen;
    }

}