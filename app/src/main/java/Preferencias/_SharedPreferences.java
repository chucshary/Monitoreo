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

    public _SharedPreferences(Context rootView, String fields, String values) {
        this.rootView = rootView;
        this.fields = fields;
        this.values = values;
    }
}
