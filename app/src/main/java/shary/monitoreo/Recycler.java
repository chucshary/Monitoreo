package shary.monitoreo;


/**
 * Created by Shary on 21/10/2015.
 */


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Preferencias.CargarPreferencias;
import rest.Paciente;
import rest.PacienteService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Recycler extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String endpoint;
    private List<String> listadoPacienteFoto;
    private List<String> listadoPacienteNombre;
    private List<String> listadoPacienteEtapa;
    private List items;
    private String url = "";
    private CargarPreferencias cargarPreferencias;
    private SharedPreferences sharedPreferences;
    int id = 0;
    private String token = "";
    View rootView;
    private String idsPatients = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_recicler, container, false);
        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = rootView.getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("tutorId", -1);
        token = sharedPreferences.getString("token", "");
        System.out.println("TUTOR " + id + "  " + token);
        listadoPacienteFoto = new ArrayList<String>();
        listadoPacienteNombre = new ArrayList<String>();
        listadoPacienteEtapa = new ArrayList<String>();
        listado(id, token);

        return rootView;
    }

    public void listado(int id, String token) {
        System.out.println("ENTRO LISTADO");
        endpoint = getString(R.string.api_endpoint);
        items = new ArrayList();

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(endpoint);
        RestAdapter restAdapter = builder.build();
        PacienteService service = restAdapter.create(PacienteService.class);
        service.getPatientByTutor(id, token, new Callback<List<Paciente>>() {
            @Override
            public void success(List<Paciente> pacientes, Response response) {
                System.out.println("CALLBACK " + pacientes.size());
                if (pacientes.size() == 1) {
                    System.out.println("OPCION 1 " + pacientes.size());
                    url = getString(R.string.api_endpointImage).concat(pacientes.get(0).getUrlFoto().toString().toLowerCase());
                    items.add(new DescripcionPaciente(url, pacientes.get(0).getNombre().toString(), pacientes.get(0).getEtapa().toString()));
                } else {
                    System.out.println("OPCION 2 " + pacientes.size());
                    for (int i = 0; i < pacientes.size(); i++) {
                        System.out.println("CALLBACK " + pacientes.get(i).getNombre().toString());
                        listadoPacienteFoto.add(pacientes.get(i).getUrlFoto().toString());
                        listadoPacienteNombre.add(pacientes.get(i).getNombre().toString());
                        listadoPacienteEtapa.add(pacientes.get(i).getEtapa().toString());
                        idsPatients += String.valueOf(pacientes.get(i).getPacienteId()) + "/";
                    }
                    shared(idsPatients);
                    for (int j = 0; j < listadoPacienteNombre.size(); j++) {
                        url = getString(R.string.api_endpointImage).concat(listadoPacienteFoto.get(j).toString().toLowerCase());
                        items.add(new DescripcionPaciente(url, listadoPacienteNombre.get(j).toString(), listadoPacienteEtapa.get(j).toString()));
                    }
                }
                adapter = new RVAdapter(items, rootView);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(rootView, "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void shared(String ids) {
        sharedPreferences = rootView.getContext().getSharedPreferences("NOTIFICACION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ids", ids);
        editor.commit();
    }

}
