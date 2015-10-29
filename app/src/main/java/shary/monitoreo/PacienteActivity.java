package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import Configuraciones.Geolocalizacion;
import Configuraciones.ObtenerNumero;

public class PacienteActivity extends AppCompatActivity {
    private Geolocalizacion geolocalizacion;
    private ObtenerNumero obtenerNumero;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private String pais = "";
    private String ciudad = "";
    private String estado = "";
    private String direccion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        obtenerNumero = new ObtenerNumero(PacienteActivity.this);
        System.out.println("NUMERO" + obtenerNumero.getPhoneNumber());
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
            MapsActivity mapsActivity = new MapsActivity();
            Toast.makeText(PacienteActivity.this, obtenerNumero.getPhoneNumber(), Toast.LENGTH_LONG).show();
            //maps();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void maps() {
        Toast.makeText(PacienteActivity.this, "ENTRO", Toast.LENGTH_LONG).show();
        MapsActivity mapsActivity = new MapsActivity();
        sharedPreferences = this.getSharedPreferences("Ubicacion", Context.MODE_PRIVATE);
        pais = sharedPreferences.getString("pais", "");
        estado = sharedPreferences.getString("estado", "");
        ciudad = sharedPreferences.getString("ciudad", "");
        direccion = sharedPreferences.getString("direccion", "");
        Toast.makeText(this, pais + estado + ciudad + direccion, Toast.LENGTH_LONG).show();
    }

}
