package shary.monitoreo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Auxiliar extends FragmentActivity {
    private GoogleMap mMap;
    private double longitude = 0;
    private double latitude = 0;
    private LatLng latLng;
    private CameraUpdate mcamera;
    private GoogleMap mapAux;
    private String[] listadoUbicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_auxiliar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNotification);
        mapAux = mapFragment.getMap();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        setUpMap(mapAux);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    private void setUpMap(GoogleMap googleMap) {
        try {
            this.mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            Intent intent = getIntent();
            ArrayList<String> listadoPaciente = intent.getStringArrayListExtra("datos");
            for (int i = 0; i < listadoPaciente.size(); i++) {
                listadoUbicaciones = listadoPaciente.get(i).toString().split("/");
                latitude = Double.parseDouble(listadoUbicaciones[0]);
                longitude = Double.parseDouble(listadoUbicaciones[1]);
                latLng = new LatLng(latitude, longitude);
                setMarker(latLng, listadoUbicaciones[7] + " - " + listadoUbicaciones[6], listadoUbicaciones[2] + " " +
                        listadoUbicaciones[3] + " " + listadoUbicaciones[4] + " " +
                        listadoUbicaciones[5]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setMarker(LatLng latLng, String titulo, String info) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(titulo)
                .snippet(info)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mcamera = CameraUpdateFactory.newLatLngZoom((latLng), 10);
    }
}
