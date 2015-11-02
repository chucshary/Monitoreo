package Configuraciones;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Shary on 31/10/2015.
 */
public class Ubicacion {
    private String country = "";
    private String city = "";
    private String state = "";
    private String direccion = "";
    private Context rootView;

    private Location location;
    private LocationManager locationManager;
    private double longitude = 0;
    private double latitude = 0;
    private LatLng latLng;
    private Localizacion localizacion;
    private GoogleMap mapAux;

    public Ubicacion(Context rootView) {
        this.rootView = rootView;
    }

    public void gps() {
        try {
            LocationManager locationManager = (LocationManager) rootView.getSystemService(Context.LOCATION_SERVICE);
            boolean dato = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();


            System.out.println("DATO " + dato + " " + latitude + "--> " + longitude + "   " + location);
        } catch (Exception e) {
        }
    }
}
