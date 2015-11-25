package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 30;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private boolean mVisible;
    private Intent mainIntent;
    private SharedPreferences sharedPreferences;
    private String tipoUusario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_splash_screen);
            mVisible = true;
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                            }
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sharedPreferences = getSharedPreferences("TIPO_USUARIO", Context.MODE_PRIVATE);
                    tipoUusario = sharedPreferences.getString("usuario", "0");
                    if (tipoUusario.equals("0")) {
                        mainIntent = new Intent().setClass(
                                SplashScreenActivity.this, ChooseActivity.class);
                        startActivity(mainIntent);
                    } else if (tipoUusario.equals("1")) {
                        mainIntent = new Intent().setClass(
                                SplashScreenActivity.this, LoginActivity.class);
                        startActivity(mainIntent);
                    } else {
                        mainIntent = new Intent().setClass(
                                SplashScreenActivity.this, PacienteActivity.class);
                        startActivity(mainIntent);
                    }
                    finish();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
