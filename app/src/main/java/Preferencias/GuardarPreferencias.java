package Preferencias;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shary on 18/10/2015.
 */
public class GuardarPreferencias {
    private String _Token;
    private int _TutorId;
    private Context rootView;
    SharedPreferences sharedPreferences;

    public GuardarPreferencias(Context view, String token, int tutorId) {
        this.rootView = view;
        this._Token = token;
        this._TutorId = tutorId;
    }

    public void guardarPreferencias() {
        sharedPreferences = rootView.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", _Token);
        editor.putInt("tutorId", _TutorId);
        editor.commit();
    }
}
