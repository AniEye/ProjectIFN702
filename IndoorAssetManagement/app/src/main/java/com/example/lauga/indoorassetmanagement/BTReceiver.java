package com.example.lauga.indoorassetmanagement;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Button;

public class BTReceiver extends BroadcastReceiver {
    public IntentFilter intentFilter;
    public Bluetooth theActivity;
    private String DEBUG_STRING = "BTReceiver";

    public BTReceiver(Bluetooth activity) {
        theActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        Log.v(DEBUG_STRING,"Action registered");

        if(BluetoothDevice.ACTION_FOUND.equals(action))
        {
            Log.v(DEBUG_STRING,"Found device");
        }
        else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
        {
            theActivity.setButtonBTSearchText("Searching");
        }
        else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
        {
            theActivity.setButtonBTSearchText("Search");
        }
        else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
        {

        }

        intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        theActivity.registerReceiver(this,intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        theActivity.registerReceiver(this,intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        theActivity.registerReceiver(this,intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        theActivity.registerReceiver(this,intentFilter);



        throw new UnsupportedOperationException("Not yet implemented");
    }
}
