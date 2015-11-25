package Configuraciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Preferencias._SharedPreferences;
import rest.UbicacionAux;
import rest.UbicacionService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import shary.monitoreo.R;

/**
 * Created by Shary on 31/10/2015.
 */
public class Ubicacion {
    private Context rootView;

    private Location location;
    private LocationManager locationManager;
    private double longitude = 0;
    private double latitude = 0;
    private LatLng latLng;
    private Localizacion localizacion;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String strDate;
    private boolean dato = false;
    private String endpoint;
    private UbicacionAux ubicacion;
    private String[] datosLoalizacion;
    private SharedPreferences sharedPreferences;
    private int idPaciente;
    private RestAdapter adapter;
    private UbicacionService ubicacionService;
    private String latlon = "";
    private _SharedPreferences classShared;
    private String datos = "";

    public Ubicacion(Context rootView) {
        this.rootView = rootView;
        ubicacion = new UbicacionAux();
        adapter = null;
        ubicacionService = null;
    }

    public void datosLocalizacion() {
        try {
            locationManager = (LocationManager) rootView.getSystemService(Context.LOCATION_SERVICE);
            dato = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (dato) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                latLng = new LatLng(latitude, longitude);
                localizacion = new Localizacion(rootView, latLng);
                calendar = Calendar.getInstance();
                simpleDateFormat =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm");
                strDate = simpleDateFormat.format(calendar.getTime());
                postUbicacion(latitude, longitude, localizacion.gps(), strDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postUbicacion(double latitude, double longitude, String datos, String strDate) {
        System.out.print("\nSUCCESS DATE " + strDate);
        datosLoalizacion = datos.split("/");
        endpoint = rootView.getString(R.string.api_endpoint);
        adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ubicacionService = adapter.create(UbicacionService.class);

        ubicacion.setPais(datosLoalizacion[0]);
        ubicacion.setEstado(datosLoalizacion[1]);
        ubicacion.setCiudad(datosLoalizacion[2]);
        ubicacion.setDireccion(datosLoalizacion[3]);
        ubicacion.setLatitud(String.valueOf(latitude));
        ubicacion.setLongitud(String.valueOf(longitude));
        ubicacion.setFecha(strDate);
        sharedPreferences = rootView.getSharedPreferences("Telefono", Context.MODE_PRIVATE);
        idPaciente = sharedPreferences.getInt("idPaciente", 0);
        ubicacion.setPacienteId(idPaciente);

        ubicacionService.addLocation(ubicacion, new Callback<rest.Ubicacion>() {
            @Override
            public void success(rest.Ubicacion ubicacion, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.print("\nERROR UBICACION" + error + "\n");
                datosLocalizacion();
            }
        });
    }

    public void getUbicacion(int id) {

        endpoint = rootView.getString(R.string.api_endpoint);
        adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint).setLogLevel(RestAdapter.LogLevel.FULL).build();
        ubicacionService = adapter.create(UbicacionService.class);
        ubicacionService.getLocation(id, new Callback<rest.Ubicacion>() {
            @Override
            public void success(rest.Ubicacion ubicacion, Response response) {
                latlon = String.valueOf(ubicacion.getLatitud() + "/" + ubicacion.getLongitud());
                datos = ubicacion.getPais() + ", " + ubicacion.getEstado() + " " + ubicacion.getCiudad() +
                        ", " + ubicacion.getDireccion();
                classShared = new _SharedPreferences(rootView, "Latitud/Longitud/Datos", latlon + "/" + datos, "LATILONG");
                classShared.guardarPreferencias();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Ubicacion", error.getMessage());
                System.out.print("\nERROR GET UBICACION" + error + "\n");
            }
        });
    }
}
