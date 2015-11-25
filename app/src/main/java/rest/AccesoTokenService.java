package rest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Shary on 18/10/2015.
 */
public interface AccesoTokenService {
    @POST("/token")
    void createAccessToken(@Body Credenciales credentials, Callback<AccesoToken> cb);
}
