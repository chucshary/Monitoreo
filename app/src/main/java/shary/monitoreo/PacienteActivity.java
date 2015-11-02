package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("Telefono", Context.MODE_PRIVATE);
        numero = sharedPreferences.getString("numero", "");
        System.out.println("NUM " + numero);
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
            getPaciente(numero);
            ubicacion = new Ubicacion(this);
            ubicacion.gps();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class MyTimerTask extends TimerTask {
        public void run() {
            //12/04/2011 12:00:00 AM
            Calendar c = Calendar.getInstance();
            int startTimeInSeconds = c.get(Calendar.HOUR);
            int startTimeInSeconds2 = c.get(Calendar.MINUTE);
            int startTimeInSeconds3 = c.get(Calendar.SECOND);
            int startTimeInSeconds5 = c.get(Calendar.AM_PM);
            int startTimeInSeconds4 = c.get(Calendar.DATE);

            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
            final String strDate = simpleDateFormat.format(c.getTime());

            System.out.print("HORA " + strDate);
            System.out.print(" IMPOSIBLE");

            System.out.println("");
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
                System.out.println("CALLBACK " + paciente.getNombre() + " " + paciente.getNotificacion());
                sharedPreferences = getSharedPreferences("Telefono", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", paciente.getPacienteId());
                editor.putInt("notificacion", paciente.getNotificacion());
                editor.commit();

                MyTimerTask myTask = new MyTimerTask();
                Timer myTimer = new Timer();
                myTimer.schedule(myTask, 100, paciente.getNotificacion() * 60 * 1000);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Paciente", error.getMessage());
                System.out.print("ERROR " + error);
            }
        });

    }


}
