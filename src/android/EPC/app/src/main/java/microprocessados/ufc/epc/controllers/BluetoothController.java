package microprocessados.ufc.epc.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Icriarli on 08/12/2017.
 */

public class BluetoothController {

    private final ConnectThread mConnectThread;
    private BluetoothDevice mDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectedThread mConnectedThread;


    public BluetoothController(ConnectThread.ConnectionListener connectionListener) throws NoBluetoothFoundException, BluetoothDisabledException {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
            throw new NoBluetoothFoundException();
        if (!mBluetoothAdapter.isEnabled()) {
            throw new BluetoothDisabledException();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mDevice = device;
            }
        }


        mConnectThread = new ConnectThread(mDevice,mBluetoothAdapter);
        mConnectThread.setConnectionListener(connectionListener);
        mConnectThread.start();

    }

    public Set<BluetoothDevice> getPairedDevice(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        return new HashSet<BluetoothDevice>(pairedDevices);
    }

    public class NoBluetoothFoundException extends Throwable {
    }

    public class BluetoothDisabledException extends Throwable {
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
