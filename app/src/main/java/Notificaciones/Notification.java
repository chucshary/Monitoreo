package Notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import shary.monitoreo.Auxiliar;
import shary.monitoreo.R;

/**
 * Created by Shary on 05/11/2015.
 */
public class Notification {
    private Context rootView;
    private NotificationCompat.Builder mBuilder;
    private Intent resultIntent;
    private TaskStackBuilder stackBuilder;
    private PendingIntent resultPendingIntent;
    private NotificationManager notificationManager;

    public Notification(Context rootView) {
        this.rootView = rootView;
    }

    public void push(List<String> listadoPaciente) {
        mBuilder =
                new NotificationCompat.Builder(rootView)
                        .setSmallIcon(R.drawable.bpnotification)
                        .setContentTitle("Ubicación pacientes")
                        .setContentText("Mapa");
        resultIntent = new Intent(rootView, Auxiliar.class);
        resultIntent.putStringArrayListExtra("datos", (ArrayList<String>) listadoPaciente);
        stackBuilder = TaskStackBuilder.create(rootView);
        stackBuilder.addParentStack(Auxiliar.class);
        stackBuilder.addNextIntent(resultIntent);
        resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager =
                (NotificationManager) rootView.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }
}
