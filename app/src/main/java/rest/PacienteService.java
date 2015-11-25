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
    void getAllPatients(Callback<List<Paciente>> cb);

    @GET("/paciente/{pacienteIdTel}/")
    void getPatientId(@Path("pacienteIdTel") String id, Callback<Paciente> cb);

    @GET("/paciente/tutor/{tutorId}/")
    void getPatientByTutor(@Path("tutorId") int id, @Header("ACCESS_TOKEN") String token, Callback<List<Paciente>> cb);
}

