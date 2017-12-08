package microprocessados.ufc.epc;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainScreenActivity extends AppCompatActivity {

    private FlowController flowController;
    private BluetoothController bluetoothController;
    private FrameLayout card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        flowController = new FlowController();


//        flowController.setAquaConsume(10);

        card = (FrameLayout) findViewById(R.id.card);
        card.setBackgroundColor(Color.parseColor("red"));
        try {
            bluetoothController = new BluetoothController();
        }catch (BluetoothController.NoBluetoothFoundException e1){
            Toast.makeText(this,"O dispositivo não possui Bluetooth. Seu Pobre!",Toast.LENGTH_SHORT).show();
        }catch (BluetoothController.BluetoothDisabledException e2){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeCardColor(){

    }
}
