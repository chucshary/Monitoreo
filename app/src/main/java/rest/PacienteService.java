package rest;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by Shary on 18/10/2015.
 */
public interface PacienteService {
    @GET("/paciente")
    public void getAllPatients(Callback<List<Paciente>> cb);

    @GET("/paciente/{pacienteIdTel}/")
    public void getPatientId (@Path("pacienteIdTel")String id, @Header("ACCESS_TOKEN") String token, Callback<List<Paciente>> cb);
}
