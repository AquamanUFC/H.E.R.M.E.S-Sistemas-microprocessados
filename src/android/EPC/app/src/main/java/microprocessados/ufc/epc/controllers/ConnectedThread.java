package microprocessados.ufc.epc.controllers;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by Simao on 09/12/2017.
 */

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private Handler receiveHandler;

    private static final String CONTROL_CHAR = "#";

    private static final String INIT_CONTROL_CHAR = "&";

    public ConnectedThread(BluetoothSocket socket,Handler receiveHandler) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
        this.receiveHandler = receiveHandler;
    }
    public void run() {
        Log.d("BLUETOOTH","Rodando...");
        byte[] buffer = new byte[1024];
        int begin = 0;
        int bytes = 0;
        boolean is_reading = false;
        while (true) {
            try {
                bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                String s = new String(buffer);
                Log.d("BLUETOOTH", "RECEBEU + " + s);
                for (int i = begin; i < bytes; i++) {
                if(buffer[i]==INIT_CONTROL_CHAR.getBytes()[0]) {
                    Log.d("BLUETOOTH","Lendo...");
                    is_reading = true;
                    buffer = new byte[1024];
                }
                }
                if(is_reading == false)
                    buffer = new byte[1024];


                    for (int i = begin; i < bytes; i++) {
                        if (buffer[i] == CONTROL_CHAR.getBytes()[0]) {
                            Message message = new Message();
                            byte[] buffer2 = Arrays.copyOfRange(buffer, 0, i - 1);
                            message.obj = buffer2;
                            message.what = 2;
                            receiveHandler.sendMessage(message);
//                        receiveHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            begin = i + 1;
                            if (i == bytes - 1) {
                                bytes = 0;
                                begin = 0;
                            }
                        }
                    }


            } catch (IOException e) {
                break;
            }
        }
    }
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}