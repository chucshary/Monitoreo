package shary.monitoreo;


/**
 * Created by Shary on 21/10/2015.
 */


import android.app.Fragment;
import android.os.Bundle;
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
    public List<String> listado1;
    public List<String> listado2;
    public List<String> listado3;
    private String endpoint;
    public List<String> listadoPacienteFoto;
    public List<String> listadoPacienteNombre;
    public List<String> listadoPacienteEtapa;
    private CargarPreferencias cargarPreferencias;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_recicler, container, false);
        super.onCreate(savedInstanceState);

        cargarPreferencias = new CargarPreferencias(rootView.getContext());
        cargarPreferencias.cargarPreferencias();
        int id = cargarPreferencias.getPreferenceTutorId;
        System.out.println("TUTOR " + id);

        List items = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String url = getString(R.string.api_endpoint).concat("/images/foto.jpg".toLowerCase());
            System.out.println("URL " + url);
            items.add(new DescripcionPaciente(url, "Shary Chuc", "Avanzado"));
        }
/*
        listado(4);

        List items = new ArrayList();
        for (int i = 0; i < this.listado1.size(); i++) {
            String url = getString(R.string.api_endpoint).concat(this.listadoPacienteFoto.get(i).toString().toLowerCase());
            System.out.println("URL " + url);
            items.add(new DescripcionPaciente(url, this.listadoPacienteNombre.get(i).toString(), this.listado3.get(i).toString()));
        }*/
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RVAdapter(items, rootView);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void listado(int id) {
        endpoint = getString(R.string.api_endpoint);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(endpoint).build();
        PacienteService service = adapter.create(PacienteService.class);
        listadoPacienteFoto = new ArrayList<>();
        listadoPacienteNombre = new ArrayList<>();
        listadoPacienteEtapa = new ArrayList<>();
        service.getPatientByTutor(id, new Callback<List<Paciente>>() {
            @Override
            public void success(List<Paciente> pacientes, Response response) {
                for (int i = 0; i < pacientes.size(); i++) {
                    listadoPacienteFoto.add(pacientes.get(i).getUrlFoto());
                    listadoPacienteNombre.add(pacientes.get(i).getNombre());
                    listadoPacienteEtapa.add(pacientes.get(i).getEtapa());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
