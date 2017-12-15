package microprocessados.ufc.epc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import microprocessados.ufc.epc.R;

public class ConfigActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private Button saveButton;
    private EditText maxConsumoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, getApplicationContext().MODE_PRIVATE);
        saveButton = (Button) findViewById(R.id.saveButton);
        maxConsumoEditText = (EditText) findViewById(R.id.maxConsumoEditText);
        maxConsumoEditText.setText(String.valueOf( sharedPreferences.getFloat("max",0)));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float max  = Float.parseFloat(maxConsumoEditText.getText().toString());
                Log.d("CONFIG","MAX: " + max);
//                String ph  = ed2.getText().toString();
//                String e  = ed3.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putFloat("max", max);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Dados Salvos", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}
