package microprocessados.ufc.epc.activities;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import microprocessados.ufc.epc.R;
import microprocessados.ufc.epc.controllers.BluetoothController;

public class PairedDevicesActivity extends AppCompatActivity {

    private BluetoothController bluetoothController;
    private ListView devicesList;
    private Handler connectHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);
        devicesList = (ListView) findViewById(R.id.devicesLists);
        try {
            bluetoothController = new BluetoothController(connectHandler);
        }catch (BluetoothController.NoBluetoothFoundException e1){
            Toast.makeText(this,"O dispositivo não possui Bluetooth. Seu Pobre!",Toast.LENGTH_SHORT).show();
        }catch (BluetoothController.BluetoothDisabledException e2){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        final ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<BluetoothDevice>(this,
                android.R.layout.simple_list_item_1,
                bluetoothController.getPairedDevices());
        devicesList.setAdapter(adapter);
        devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MainScreenActivity.class);
                intent.putExtra("Device", adapter.getItem(position));
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),
//                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
//                        .show();
            }
        });
    }

}
