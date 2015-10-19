package Configuraciones;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Shary on 18/10/2015.
 */
public class ObtenerNumero {
    private Context rootView;

    public ObtenerNumero(Context context) {
        this.rootView = context;
    }

    public String getPhoneNumber() {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) rootView.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }
}
