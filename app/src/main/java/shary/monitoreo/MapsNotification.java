package shary.monitoreo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import Configuraciones.Localizacion;

public class MapsNotification extends FragmentActivity {

    private GoogleMap mMap;
    private Location location;
    private LocationManager locationManager;
    private double longitude = 0;
    private double latitude = 0;
    private LatLng latLng;
    private CameraUpdate mcamera;
    private Localizacion localizacion;
    private GoogleMap mapAux;
    private String _datos = "";
    private TextView toolbarText;
    private String[] listadoUbicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toolbarText = (TextView) findViewById(R.id.toolbarText);
        mapAux = mapFragment.getMap();
        setUpMap(mapAux);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setUpMap(GoogleMap googleMap) {
        // String latitud, String longitud, String pais,
        //       String estado, String ciudad, String direccion, String fecha, String nombre
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
            setMarker(latLng, listadoUbicaciones[7], latitude + " " + longitude);
        }
    }


    private void setMarker(LatLng latLng, String titulo, String info) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(titulo)
                .snippet(info)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mcamera = CameraUpdateFactory.newLatLngZoom((latLng), 10);
        mMap.animateCamera(mcamera);
    }
}
