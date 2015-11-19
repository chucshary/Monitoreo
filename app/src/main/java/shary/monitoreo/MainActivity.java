package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rest.DtoUbicacionPaciente;
import rest.UbicacionService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Recycler recycler;
    private Location location;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;
    private int id = 0;
    private String token = "";
    private String endpoint = "";
    private List<String> listadoUbicacionPaciente;
    RestAdapter.Builder builder;
    RestAdapter restAdapter;
    UbicacionService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recycler = new Recycler();

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

    public void notifications() {
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
                    System.out.println("PacienteUbicacion " + dtoUbicacionPacientes.get(i).getNombre());
                    listadoUbicacionPaciente.add(dtoUbicacionPacientes.get(i).getLatitud() + "/" +
                                    dtoUbicacionPacientes.get(i).getLongitud() + "/" +
                                    dtoUbicacionPacientes.get(i).getPais() + "/" +
                                    dtoUbicacionPacientes.get(i).getEstado() + "/" + dtoUbicacionPacientes.get(i).getCiudad() + "/" +
                                    dtoUbicacionPacientes.get(i).getDireccion() + "/" +
                                    dtoUbicacionPacientes.get(i).getFecha() + "/" + dtoUbicacionPacientes.get(i).getNombre()
                    );
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
