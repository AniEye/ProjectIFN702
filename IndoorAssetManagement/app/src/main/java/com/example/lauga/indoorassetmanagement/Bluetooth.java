package com.example.lauga.indoorassetmanagement;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Bluetooth extends Activity {
    public Button bBTSearch;
    public ListView lvBTBeacons;

    public BluetoothAdapter btAdapter;
    public IntentFilter intentFilter;
    public BroadcastReceiver btReceiver;
    //public BTReceiver btReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bBTSearch = (Button) findViewById(R.id.bBTSearch);
        lvBTBeacons = (ListView) findViewById(R.id.lvBTBeacons);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        bClicks();
        checkForBluetoothAdapter();
    }

    public void bClicks(){
        bBTSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!btAdapter.isDiscovering()){
                    btAdapter.startDiscovery();
                }else{
                    btAdapter.cancelDiscovery();
                }
            }
        });
    }

    public void checkForBluetoothAdapter(){
        if(btAdapter == null){
            Toast.makeText(getApplicationContext(),"Bluetooth device not present",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if(!btAdapter.isEnabled()){
                Intent turnOnBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOnBluetooth,1);
            }

            initBroadcaster();
            //btReceiver = new BTReceiver(this);

            btAdapter.startDiscovery();
        }
    }

    public void initBroadcaster(){
        btReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                Log.v("Debug","Action registered: " + action);

                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    int RSSI = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.v("Debug", "Device found: " + device.getAddress());
                    //add device here
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
                {
                    bBTSearch.setText("Searching");
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
                {
                    bBTSearch.setText("Search");
                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
                {

                }
            }
        };

        intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Bluetooth.this.registerReceiver(btReceiver,intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        Bluetooth.this.registerReceiver(btReceiver,intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        Bluetooth.this.registerReceiver(btReceiver,intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        Bluetooth.this.registerReceiver(btReceiver,intentFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(),"You did not enable Bluetooth",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void setButtonBTSearchText(String text){
        bBTSearch.setText(text);
    }
}
