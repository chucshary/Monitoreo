package shary.monitoreo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import Configuraciones.Geolocalizacion;
import Configuraciones.ObtenerNumero;

public class ChooseActivity extends AppCompatActivity {
    private Geolocalizacion geolocalizacion;
    private ObtenerNumero obtenerNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        obtenerNumero = new ObtenerNumero(ChooseActivity.this);
        System.out.println("NUMERO" + obtenerNumero.getPhoneNumber());
        Toast.makeText(ChooseActivity.this, obtenerNumero.getPhoneNumber(), Toast.LENGTH_LONG).show();


        geolocalizacion = new Geolocalizacion(ChooseActivity.this);
        System.out.println("DIRECCION" + geolocalizacion.gps());
        Toast.makeText(ChooseActivity.this, geolocalizacion.gps(), Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
