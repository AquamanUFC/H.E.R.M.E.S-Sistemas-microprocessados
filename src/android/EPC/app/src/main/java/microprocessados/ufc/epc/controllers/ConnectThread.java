package microprocessados.ufc.epc.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

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
    private Handler connectionHandler;

    public ConnectThread(BluetoothDevice device,BluetoothAdapter mBluetoothAdapter, Handler connectionHandler) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
        this.mBluetoothAdapter = mBluetoothAdapter;
        this.connectionHandler = connectionHandler;
    }

    public void run(){
        mBluetoothAdapter.cancelDiscovery();
        try {
            mmSocket.connect();
            mConnectedThread = new ConnectedThread(mmSocket,connectionHandler);
            mConnectedThread.start();
//            connectionListener.connectionSucess();
            connectionHandler.sendEmptyMessage(1);
        } catch (Exception connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {

            }
//            connectionListener.connectionFailed();
            connectionHandler.sendEmptyMessage(0);
            return;
        }

    }
    public void cancel() {
        try {
            mmSocket.close();
            connectionHandler.sendEmptyMessage(3);
        } catch (IOException e) { }
    }

    public class ConnectionFailedException extends Throwable {
    }

    public interface ConnectionListener {
        public void connectionSucess();
        public void connectionFailed();
    }
}