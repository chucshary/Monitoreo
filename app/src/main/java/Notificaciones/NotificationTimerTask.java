package Notificaciones;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Timer;
import java.util.TimerTask;

import rest.*;
import rest.Ubicacion;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
    private int idAux = 0;
    private RestAdapter adapter;
    private String endpoint;
    private UbicacionService ubicacionService;


    public NotificationTimerTask(Context rootView) {
        this.rootView = rootView;
        getNotification();
    }

    public class TimerTaskNotification extends TimerTask {
        int id;

        public TimerTaskNotification(int id) {
            this.id = id;
        }

        public void run() {
            endpoint = rootView.getString(R.string.api_endpoint);
            adapter = new RestAdapter.Builder()
                    .setEndpoint(endpoint).setLogLevel(RestAdapter.LogLevel.FULL).build();
            ubicacionService = adapter.create(UbicacionService.class);
            ubicacionService.getLocation(id, new Callback<rest.Ubicacion>() {
                @Override
                public void success(Ubicacion ubicacion, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
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
            timer = new Timer();
            timer.schedule(new TimerTaskNotification(arrayIds[i]), 100, arrayNotificacion[i] * 60 * 1000);
        }
    }
}

