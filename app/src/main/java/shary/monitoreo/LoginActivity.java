package shary.monitoreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import Configuraciones.Connectivity;
import Preferencias.CargarPreferencias;
import Preferencias.GuardarPreferencias;
import rest.AccesoToken;
import rest.AccesoTokenService;
import rest.Credenciales;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private String endpoint;
    private Credenciales credentials;
    private String tokenObtenido;
    private int idObtenido = 0;
    private boolean cancel = false;
    private View rootView;
    private static final String TAG = "Login";
    private GuardarPreferencias guardarPreferencias;
    private CargarPreferencias cargarPreferencias;
    private Connectivity connectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        cargarPreferencias = new CargarPreferencias(LoginActivity.this);
        cargarPreferencias.cargarPreferencias();
        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);
        setSupportActionBar(toolbar);

        email.selectAll();
        password.selectAll();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootView = view;
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError(getString(R.string.error_field_required));
                    cancel = true;
                } else if (!isEmailValid(email.getText().toString())) {
                    email.setError(getString(R.string.error_invalid_email));
                    cancel = true;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError(getString(R.string.error_field_required));
                    cancel = true;
                } else {
                    connectivity = new Connectivity(view);
                    if (connectivity.checkConnectivity()) {
                        credentials = new Credenciales();
                        credentials.setEmail(email.getText().toString());
                        credentials.setPassword(password.getText().toString());
                        endpoint = getString(R.string.api_endpoint);
                        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(endpoint).build();
                        AccesoTokenService service = adapter.create(AccesoTokenService.class);
                        service.createAccessToken(credentials, new Callback<AccesoToken>() {
                            @Override
                            public void success(AccesoToken accesoToken, Response response) {
                                if (accesoToken != null) {
                                    tokenObtenido = accesoToken.getToken();
                                    idObtenido = accesoToken.getTutorId();
                                    guardarPreferencias = new GuardarPreferencias(LoginActivity.this, tokenObtenido, idObtenido);
                                    guardarPreferencias.guardarPreferencias();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    LoginActivity.this.finish();
                                } else {
                                    Snackbar.make(rootView, "Credenciales Incorrectas ", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    email.requestFocus();
                                    email.setText("");
                                    password.setText("");
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e(TAG, error.getMessage());
                            }
                        });
                    } else {
                        Snackbar.make(rootView, "No hay conexion a Internet", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
