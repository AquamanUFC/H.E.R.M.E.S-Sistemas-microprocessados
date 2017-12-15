package microprocessados.ufc.epc.activities;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import microprocessados.ufc.epc.R;
import microprocessados.ufc.epc.controllers.BluetoothController;
import microprocessados.ufc.epc.controllers.FlowController;

public class MainScreenActivity extends AppCompatActivity {

    private FlowController flowController;
    private BluetoothController bluetoothController;
    private FrameLayout card;
    private Handler connectHandler;
    private BluetoothDevice device;
    private TextView infoCard;

    private SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        flowController = new FlowController();

        sharedPreferences = getSharedPreferences(MyPREFERENCES, getApplicationContext().MODE_PRIVATE);
//        flowController.setAquaConsume(10);
        device = (BluetoothDevice) getIntent().getParcelableExtra("Device");

        card = (FrameLayout) findViewById(R.id.card);
        infoCard = (TextView) findViewById(R.id.infoCard);
//        card.setBackgroundColor(Color.parseColor("red"));
        try {
            setHandler();
            bluetoothController = new BluetoothController(connectHandler);
            bluetoothController.connect(device);
        }catch (BluetoothController.NoBluetoothFoundException e1){
            Toast.makeText(this,"O dispositivo não possui Bluetooth. Seu Pobre!",Toast.LENGTH_SHORT).show();
        }catch (BluetoothController.BluetoothDisabledException e2){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
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

    public void changeCardColor(int color){
        this.card.setBackgroundColor(color);
    }

    @SuppressLint("HandlerLeak")
    public void setHandler(){
        this.connectHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Houve uma falha ao conectar com o dispositivo", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),
                                "Conectado ao dispositivo com sucesso", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 2:
                        String consumo = new String((byte[]) msg.obj);
                        infoCard.setText(consumo);
                        float max_consumo = sharedPreferences.getFloat("max",0);
                        Log.d("BLUETOOTH","CONSUMO_MAX" + Float.parseFloat(consumo));
                        Log.d("BLUETOOTH","CONSUMO" + Float.parseFloat(consumo));
                        if(Float.parseFloat(consumo)>max_consumo)
                        {
                            changeCardColor(Color.parseColor("#F44336"));

                        }else if(Float.parseFloat(consumo)>= max_consumo * 0.8){
                            changeCardColor(Color.parseColor("#FFB300"));
                        }else{
                            changeCardColor(Color.parseColor("#4CAF50"));
                        }
//                        Toast.makeText(getApplicationContext(),
//                                "RECEBIDO DADO" + msg.obj, Toast.LENGTH_SHORT)
//                                .show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"Desconectado com sucesso",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
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
