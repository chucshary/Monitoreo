package rest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Shary on 18/10/2015.
 */
public interface UbicacionService {
    @POST("/ubicacion")
    public void addLocation(@Body UbicacionAux ubicacionAux, Callback<Ubicacion> cb);
}
