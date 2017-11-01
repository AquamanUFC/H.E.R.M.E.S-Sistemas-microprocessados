package microprocessados.ufc.epc;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Simao on 01/11/2017.
 */

public class BluetoothManager {

    private BluetoothAdapter mBluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 1;
    private Context context;

    public BluetoothManager(Activity activity){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter ==null)
            Toast.makeText(activity.getApplicationContext(),"Sinto muito, esse dispositivo não suporta android.",Toast.LENGTH_SHORT).show();
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
        }
        this.context = activity.getApplicationContext();
    }

    public ArrayList<String> checkPaired(){
        ArrayList<String> devices = new ArrayList<String>();
        Set<BluetoothDevice> pairedDevises = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if(pairedDevises.size()>0){
            for(BluetoothDevice device : pairedDevises)
            {
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device.getName() + "\n" + device.getAddress());
            }
            return devices;
        }
        else
            return null;
    }

}
