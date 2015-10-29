package Configuraciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;


/**
 * Created by Shary on 19/10/2015.
 */
public class Geolocalizacion {

    private String country = "";
    private String city = "";
    private String state = "";
    private String direccion = "";

    private Location location;
    private double latitude = 0;
    private double longitude = 0;
    private LocationManager locationManager;
    private String[] informacion;
    private Context rootView;
    private LatLng latLng;
    private SharedPreferences sharedPreferences;

    public Geolocalizacion(Context rootView, LatLng latLng) {
        this.rootView = rootView;
        this.latLng = latLng;
    }

    public String gps() {
        try {
            List<Address> addresses;
            Geocoder geocoder = new Geocoder(rootView, Locale.getDefault());
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            country = addresses.get(0).getCountryName();
            state = addresses.get(0).getAdminArea();
            city = addresses.get(0).getLocality();
            direccion = addresses.get(0).getSubLocality() + " " + addresses.get(0).getThoroughfare();
            sharedPreferences = rootView.getSharedPreferences("Ubicacion", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pais", country);
            editor.putString("estado", state);
            editor.putString("ciudad", city);
            editor.putString("direccion", direccion);
            editor.commit();

        } catch (Exception e) {
        }

        return country + " " + state + " " + city + " " + direccion;
    }
}
