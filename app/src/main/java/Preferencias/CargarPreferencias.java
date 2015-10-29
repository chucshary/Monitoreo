package Preferencias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import shary.monitoreo.LoginActivity;
import shary.monitoreo.MainActivity;

/**
 * Created by Shary on 18/10/2015.
 */
public class CargarPreferencias {
    private Context rootView;
    SharedPreferences sharedPreferences;
    private String getPreferenceToken;
    public int getPreferenceTutorId;

    public CargarPreferencias(Context rootView) {
        this.rootView = rootView;
    }

    public void cargarPreferencias() {

        sharedPreferences = rootView.getSharedPreferences("Login", Context.MODE_PRIVATE);
        getPreferenceToken = sharedPreferences.getString("token", "");
        getPreferenceTutorId = sharedPreferences.getInt("tutorId", 0);
        try {
            if (getPreferenceToken.isEmpty()) {
                Toast.makeText(rootView, "Es necesario iniciar Sesión", Toast.LENGTH_LONG).show();
            } else {
                rootView.startActivity(new Intent(rootView.getApplicationContext(), MainActivity.class));
                ((LoginActivity) (rootView)).finish();
            }
        } catch (Exception e) {
        }
    }
}
