package com.example.lauga.indoorassetmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button bNFC,bBluetooth;
    private ClickedButton clickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        bBluetooth  = (Button) findViewById(R.id.bBluetooth);
        bNFC        = (Button) findViewById(R.id.bNFC);

        clickedButton = new ClickedButton();

        bBluetooth.setOnClickListener(clickedButton);
        bNFC.setOnClickListener(clickedButton);
    }

    class ClickedButton implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bBluetooth:
                    Intent startBluetooth = new Intent(MainActivity.this,Bluetooth.class);
                    startActivity(startBluetooth);
                    break;
                case R.id.bNFC:
                    Intent startNFC = new Intent(MainActivity.this,NFC.class);
                    startActivity(startNFC);
                    break;
            }
        }
    }
}
