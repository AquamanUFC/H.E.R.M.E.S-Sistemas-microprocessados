package microprocessados.ufc.epc.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;


/**
 * Created by Icriarli on 08/12/2017.
 */

public class BluetoothController {

    private ConnectThread mConnectThread;
    private BluetoothDevice mDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectedThread mConnectedThread;
    private Handler connectHandler;

    public BluetoothController(Handler connectHandler) throws NoBluetoothFoundException, BluetoothDisabledException {
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
        this.connectHandler = connectHandler;



    }

    public void connect(BluetoothDevice mdevice){
        mConnectThread = new ConnectThread(mDevice,mBluetoothAdapter,connectHandler);
        mConnectThread.start();
    }

    public ArrayList<BluetoothDevice> getPairedDevices(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        ArrayList<String> list = new ArrayList();
        for(BluetoothDevice bluetoothDevice : pairedDevices)
            list.add(bluetoothDevice.getName());
        Log.d("DEVICE","Paired Devices:" + pairedDevices.size());
        return new ArrayList<>(pairedDevices);
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
