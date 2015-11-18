package shary.monitoreo;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import Configuraciones.Localizacion;

public class MapFragment extends Fragment {
    private GoogleMap mMap;
    private Location location;
    private LocationManager locationManager;
    private double longitude = 0;
    private double latitude = 0;
    private LatLng latLng;
    private CameraUpdate mcamera;
    private Localizacion localizacion;
    private GoogleMap mapAux;
    private String _latitude;
    private String _longitude;
    private String _datos = "";
    private String _paciente = "";
    private TextView toolbarText;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_map, container, false);
        super.onCreate(savedInstanceState);
        return rootView;
    }
}
