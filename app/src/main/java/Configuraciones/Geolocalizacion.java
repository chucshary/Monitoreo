package Configuraciones;

import android.content.Context;
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

    public Geolocalizacion(Context rootView) {
        this.rootView = rootView;
    }

    public String gps() {
        try {
            locationManager = (LocationManager) rootView.getSystemService(Context.LOCATION_SERVICE);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            System.out.println("LATLONG" + latitude + longitude);
            List<Address> addresses;
            Geocoder geocoder = new Geocoder(rootView, Locale.getDefault());
            LatLng latLng = new LatLng(21.1619, -86.8515);
            addresses = geocoder.getFromLocation(21.1619, -86.8515, 1);
            country = addresses.get(0).getCountryName();
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            direccion = addresses.get(0).getSubLocality() + " " + addresses.get(0).getThoroughfare();
        } catch (Exception e) {
        }
        return "DIR" + country + city + state + direccion;
    }
}
