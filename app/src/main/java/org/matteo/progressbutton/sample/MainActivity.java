package org.matteo.progressbutton.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.matteo.progressbutton.ProgressButton;
import org.matteo.progressbutton.sample.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressButton progressButton = (ProgressButton) findViewById(R.id.progress_button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressButton.setProgress(i % 100);
                }
            }
        }).start();
    }
}
