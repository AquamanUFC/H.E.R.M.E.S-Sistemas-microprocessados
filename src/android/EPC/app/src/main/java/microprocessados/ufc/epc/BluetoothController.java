package microprocessados.ufc.epc;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by Icriarli on 08/12/2017.
 */

public class BluetoothController {

    private BluetoothAdapter mBluetoothAdapter;

    public BluetoothController() throws NoBluetoothFoundException, BluetoothDisabledException {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
            throw new NoBluetoothFoundException();
        if (!mBluetoothAdapter.isEnabled()) {
            throw new BluetoothDisabledException();
        }
    }

    public class NoBluetoothFoundException extends Throwable {
    }

    public class BluetoothDisabledException extends Throwable {
    }
}
