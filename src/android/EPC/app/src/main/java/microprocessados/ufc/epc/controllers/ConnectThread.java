package microprocessados.ufc.epc.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Simao on 09/12/2017.
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectedThread mConnectedThread;
    private ConnectionListener connectionListener;


    public ConnectThread(BluetoothDevice device,BluetoothAdapter mBluetoothAdapter) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
        this.mBluetoothAdapter = mBluetoothAdapter;

        connectionListener = new ConnectionListener() {
            @Override
            public void connectionSucess() {

            }

            @Override
            public void connectionFailed() {

            }
        };

    }

    public void setConnectionListener(ConnectionListener connectionListener){
        this.connectionListener = connectionListener;
    }

    public void run(){
        mBluetoothAdapter.cancelDiscovery();
        try {
            mmSocket.connect();
            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
            connectionListener.connectionSucess();;
        } catch (Exception connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {

            }
            connectionListener.connectionFailed();
            return;
        }

    }
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    public class ConnectionFailedException extends Throwable {
    }

    public interface ConnectionListener {
        public void connectionSucess();
        public void connectionFailed();
    }
}