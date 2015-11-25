package shary.monitoreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import Preferencias._SharedPreferences;

public class ChooseActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Intent intent;
    private _SharedPreferences _shaSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrups);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                try {
                    if (radioButton.getText().equals("Tutor")) {
                        _shaSharedPreferences = new _SharedPreferences(view.getContext(), "usuario", "1", "TIPO_USUARIO");
                        _shaSharedPreferences.guardarPreferencias();
                        intent = new Intent().setClass(
                                ChooseActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (radioButton.getText().equals("Paciente")) {
                        _shaSharedPreferences = new _SharedPreferences(view.getContext(), "usuario", "2", "TIPO_USUARIO");
                        _shaSharedPreferences.guardarPreferencias();
                        intent = new Intent().setClass(
                                ChooseActivity.this, PacienteActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
