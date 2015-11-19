package rest;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Shary on 18/10/2015.
 */
public interface UbicacionService {
    @POST("/ubicacion")
    public void addLocation(@Body UbicacionAux ubicacionAux, Callback<Ubicacion> cb);

    @GET("/ubicacion/{pacienteId}/")
    public void getLocation(@Path("pacienteId") int id, Callback<Ubicacion> cb);

    @GET("/ubicacion/ultimas/{tutorId}/")
    public void getLocationPatients(@Path("tutorId") int id, @Header("ACCESS_TOKEN") String token, Callback<List<DtoUbicacionPaciente>> cb);
}
