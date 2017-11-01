package microprocessados.ufc.epc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int splash_time_sec = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread threadCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(splash_time_sec * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                endSplashScreen();
            }
        });
        threadCounter.start();
    }

    private void endSplashScreen() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

}
