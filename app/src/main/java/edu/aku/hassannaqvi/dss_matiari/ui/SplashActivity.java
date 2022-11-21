package edu.aku.hassannaqvi.dss_matiari.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.aku.hassannaqvi.dss_matiari.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (Exception e) {

                } finally {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        th.start();
    }
}