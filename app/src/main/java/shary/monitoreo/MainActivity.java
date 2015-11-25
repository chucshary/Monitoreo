package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Configuraciones.Connectivity;
import Notificaciones.Notification;
import rest.DtoUbicacionPaciente;
import rest.UbicacionService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Recycler recycler;
    private LocationManager locationManager;
    private int id = 0;
    private String token = "";
    private String endpoint = "";
    private List<String> listadoUbicacionPaciente;
    RestAdapter.Builder builder;
    RestAdapter restAdapter;
    UbicacionService service;
    private Connectivity connectivity;
    private ViewGroup viewGroup;
    private Notification notification;
    int ii = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connectivity = new Connectivity(viewGroup);
        if (connectivity.checkConnectivity()) {
            recycler = new Recycler();
            notifications();
        } else {
            Snackbar.make(viewGroup, "No hay conexion a Internet", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                MainActivity.this.finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    class LocationTimerTask extends TimerTask {
        @Override
        public void run() {
            SystemClock.sleep(300000);
            System.out.print("TIMER " + ii++ + "\n");
            notifications();
        }
    }

    public void notifications() {
        try {
            sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
            id = sharedPreferences.getInt("tutorId", -1);
            token = sharedPreferences.getString("token", "");
            endpoint = getString(R.string.api_endpoint);
            listadoUbicacionPaciente = new ArrayList<String>();
            builder = new RestAdapter.Builder();
            builder.setEndpoint(endpoint);
            restAdapter = builder.build();
            service = restAdapter.create(UbicacionService.class);
            service.getLocationPatients(id, token, new Callback<List<DtoUbicacionPaciente>>() {
                @Override
                public void success(List<DtoUbicacionPaciente> dtoUbicacionPacientes, Response response) {
                    for (int i = 0; i < dtoUbicacionPacientes.size(); i++) {
                        listadoUbicacionPaciente.add(dtoUbicacionPacientes.get(i).getLatitud() + "/" +
                                        dtoUbicacionPacientes.get(i).getLongitud() + "/" +
                                        dtoUbicacionPacientes.get(i).getPais() + "/" +
                                        dtoUbicacionPacientes.get(i).getEstado() + "/" + dtoUbicacionPacientes.get(i).getCiudad() + "/" +
                                        dtoUbicacionPacientes.get(i).getDireccion() + "/" +
                                        dtoUbicacionPacientes.get(i).getFecha() + "/" + dtoUbicacionPacientes.get(i).getNombre()
                        );
                    }
                    System.out.println("PacienteUbicacion " + listadoUbicacionPaciente.toString());
                    notification = new Notification(viewGroup.getContext());
                    notification.push(listadoUbicacionPaciente);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Paciente Ubicacion ", error.getMessage());
                    Snackbar.make(viewGroup, "Error al conectar con el Servidor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            TimerTask timerTask = new LocationTimerTask();
            Timer timer = new Timer();
            timer.schedule(timerTask, 1000, 300000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
