package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import Configuraciones.Connectivity;
import Configuraciones.Localizacion;
import Configuraciones.ObtenerNumero;
import Configuraciones.Ubicacion;
import rest.Paciente;
import rest.PacienteService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PacienteActivity extends AppCompatActivity {
    private Localizacion localizacion;
    private ObtenerNumero obtenerNumero;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    String numero = "";
    private Ubicacion ubicacion;
    private String endpoint;
    int ii = 0;
    private Timer timer;
    Toolbar toolbar;
    private Connectivity connectivity;
    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("Telefono", Context.MODE_PRIVATE);
        numero = sharedPreferences.getString("numero", "");

        if ((numero.equals(""))) {
            obtenerNumero = new ObtenerNumero(PacienteActivity.this);
            if (obtenerNumero.getPhoneNumber().equals("")) {
                intent = new Intent().setClass(
                        PacienteActivity.this, PhoneNumberActivity.class);
                startActivity(intent);
                finish();
            } else {
                sharedPreferences = this.getSharedPreferences("Telefono", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("numero", obtenerNumero.getPhoneNumber());
                editor.commit();
                intent = new Intent().setClass(
                        this, PacienteActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            connectivity = new Connectivity(viewGroup);
            if (connectivity.checkConnectivity()) {
                getPaciente(numero);
            } else {
                Snackbar.make(viewGroup, "No hay conexion a Internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivity = new Connectivity(viewGroup);
                if (connectivity.checkConnectivity()) {
                    getPaciente(numero);
                } else {
                    Snackbar.make(viewGroup, "No hay conexion a Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    class LocationTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.print("TIMER " + ii++ + "\n");
            ubicacion = new Ubicacion(PacienteActivity.this);
            ubicacion.datosLocalizacion();
        }
    }

    public void getPaciente(String numero) {
        endpoint = getString(R.string.api_endpoint);
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(endpoint);
        RestAdapter restAdapter = builder.build();
        PacienteService service = restAdapter.create(PacienteService.class);
        service.getPatientId(numero, new Callback<Paciente>() {
            @Override
            public void success(Paciente paciente, Response response) {
                toolbar.setTitle(paciente.getNombre());
                sharedPreferences = getSharedPreferences("Telefono", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("idPaciente", paciente.getPacienteId());
                editor.putInt("notificacion", paciente.getNotificacion());
                editor.commit();
                TimerTask timerTask = new LocationTimerTask();
                timer = new Timer();
                timer.schedule(timerTask, 100, paciente.getNotificacion() * 60 * 1000);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Paciente", error.getMessage());
                Snackbar.make(viewGroup, "El usuario no esta registrado.\nRegistrarse en la Aplicacion Web.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


}
