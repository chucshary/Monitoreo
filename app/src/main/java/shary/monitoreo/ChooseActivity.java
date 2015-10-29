package shary.monitoreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChooseActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Intent intent;

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
                        intent = new Intent().setClass(
                                ChooseActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (radioButton.getText().equals("Paciente")) {
                        intent = new Intent().setClass(
                                ChooseActivity.this, PacienteActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {

                }
            }
        });
    }

}
