package Preferencias;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shary on 05/11/2015.
 */
public class _SharedPreferences {
    private Context rootView;
    private String fields;
    private String values;
    private String[] _fields;
    private String[] _values;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String nameShared;

    public _SharedPreferences(Context rootView, String fields, String values, String nameShared) {
        this.rootView = rootView;
        this.fields = fields;
        this.values = values;
        this.nameShared = nameShared;
    }

    public void guardarPreferencias() {
        _fields = fields.split("/");
        _values = values.split("/");
        sharedPreferences = rootView.getSharedPreferences(nameShared, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        for (int i = 0; i < _fields.length; i++) {
            editor.putString(_fields[i], _values[i]);
        }
        editor.commit();
    }
}
