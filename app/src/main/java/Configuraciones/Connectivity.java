package Configuraciones;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * Created by Shary on 18/11/2015.
 */
public class Connectivity {
    private View rootView;

    public Connectivity(View rootView) {
        this.rootView = rootView;
    }

    public boolean checkConnectivity() {
        boolean isConnected = false;
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) rootView.getContext().getSystemService(rootView.getContext().CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isConnected;
    }
}
