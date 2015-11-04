package Configuraciones;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Timer;
import java.util.TimerTask;

import rest.UbicacionService;
import retrofit.RestAdapter;
import shary.monitoreo.R;

/**
 * Created by Shary on 04/11/2015.
 */
public class NotificationTimerTask {

    private Timer timer;
    private TimerTask notificationTimerTask;
    private Context rootView;
    private SharedPreferences sharedPreferences;
    private int[] arrayIds;
    private int[] arrayNotificacion;
    private String[] _arrayIds;
    private String[] _arrayNotificacion;
    private String ids;
    private String notificaciones;


    public NotificationTimerTask(Context rootView) {
        this.rootView = rootView;
        TimerTask timerTask = new TimerTaskNotification();
        timer = new Timer();
        timer.schedule(timerTask, 100, 60 * 1000);
    }

    public class TimerTaskNotification extends TimerTask {
        public void run() {

        }
    }

    public void getNotification() {
        sharedPreferences = rootView.getSharedPreferences("NOTIFICACION", Context.MODE_PRIVATE);
        ids = sharedPreferences.getString("ids", "");
        notificaciones = sharedPreferences.getString("notificaciones", "");
        _arrayIds = ids.split("/");
        _arrayNotificacion = notificaciones.split("/");
        arrayIds = new int[_arrayIds.length];
        arrayNotificacion = new int[_arrayNotificacion.length];
        for (int i = 0; i < _arrayIds.length; i++) {
            arrayIds[i] = Integer.parseInt(_arrayIds[i].toString());
            arrayNotificacion[i] = Integer.parseInt(_arrayNotificacion[i].toString());
        }
    }

}

