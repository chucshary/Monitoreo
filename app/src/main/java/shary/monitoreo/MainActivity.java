package shary.monitoreo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Recycler recycler;
    private Location location;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recycler = new Recycler();

        TelephonyManager tMgr = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        String phoneNumber = "";

        for (Account ac : accounts) {
            String acname = ac.name;
            String actype = ac.type;
            // Take your time to look at all available accounts
            System.out.println("Accounts : " + acname + ", " + actype);

            if (actype.equals("com.whatsapp")) {
                phoneNumber = ac.name;
            }
        }


        System.out.println("NUMERO MAIN " + phoneNumber);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                MainActivity.this.finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }


}
