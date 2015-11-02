package shary.monitoreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneNumberActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText telefono;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        telefono = (EditText) findViewById(R.id.phoneNumber);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("Telefono", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("numero", telefono.getText().toString());
                editor.commit();

                Toast.makeText(PhoneNumberActivity.this, telefono.getText().toString(), Toast.LENGTH_SHORT).show();
                intent = new Intent().setClass(
                        PhoneNumberActivity.this, PacienteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
