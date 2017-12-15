package microprocessados.ufc.epc.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import microprocessados.ufc.epc.CustomAdapter;
import microprocessados.ufc.epc.R;
import microprocessados.ufc.epc.controllers.BluetoothController;

public class PairedDevicesActivity extends AppCompatActivity {

    private BluetoothController bluetoothController;
    private ListView devicesList;
    private Handler connectHandler;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ConfigActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setBluetooth() {
        try {
            devicesList = (ListView) findViewById(R.id.devicesLists);
            if(bluetoothController==null)
                bluetoothController = new BluetoothController(connectHandler);
            final CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, bluetoothController.getPairedDevices());
            devicesList.setAdapter(adapter);
            devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),MainScreenActivity.class);
                    intent.putExtra("Device", adapter.getItem(position));
                    startActivity(intent);
                }
            });
        }catch (BluetoothController.NoBluetoothFoundException e1){
            Toast.makeText(this,"O dispositivo não possui Bluetooth. Seu Pobre!",Toast.LENGTH_SHORT).show();
        }catch (BluetoothController.BluetoothDisabledException e2){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("RESTART","RESTARTANDO");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBluetooth();
        Log.d("RESUMO","RESUMINDO");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PAUSE","PAUSANDO");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            bluetoothController.disconnect();
        }catch(Exception e){

        }
    }

}

