package es.upm.projecto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationHandler extends ContextWrapper {
    private NotificationManager manager;
    public static final String CHANNEL_HIGH_ID = "1";
    public static final String CHANNEL_LOW_ID = "2";
    public static final String CHANNEL_HIGH_NAME = "HIGH_CHANNEL";
    public static final String CHANNEL_LOW_NAME = "LOW_CHANNEL";

    public NotificationHandler(Context base) {
        super(base);
        createChannels();
    }

    public NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public void createChannels(){
        //Creo el canal
        NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_NAME,NotificationManager.IMPORTANCE_HIGH);

        NotificationChannel lowChannel = new NotificationChannel(CHANNEL_LOW_ID,CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);

        //configuracion canal high
        highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        highChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(highChannel);
        getManager().createNotificationChannel(lowChannel);
    }

    public Notification.Builder createNotification(String titulo, String msg, boolean prioridad){
        if(Build.VERSION.SDK_INT >= 26){
            if(prioridad){
                return createNotificationChannels(titulo,msg,CHANNEL_HIGH_ID);
            }
            return createNotificationChannels(titulo,msg,CHANNEL_LOW_ID);
        }
        return createNotificationNoChannels(titulo,msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder createNotificationChannels (String titulo, String msg, String channel){
        return new Notification.Builder(getApplicationContext(), channel)
                .setContentTitle(titulo)
                .setContentText(msg)
                .setSmallIcon(R.drawable.baseline_favorite_24)
                .setAutoCancel(true);
    }

    private Notification.Builder createNotificationNoChannels (String titulo, String msg){
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(titulo)
                .setContentText(msg)
                .setSmallIcon(R.drawable.baseline_favorite_border_24)
                .setAutoCancel(true);
    }
}
