package com.example.a3_29;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity  {


    private MY_BroadcastReceiver receiver;
    private IntentFilter filter;

    private LocalBroadcastManager localmng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_nothing);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcast.MY_BROADCAST");
                localmng.sendBroadcast(intent);
            }
        });

        localmng = LocalBroadcastManager.getInstance(this);

        filter = new IntentFilter();
        filter.addAction("com.example.broadcast.MY_BROADCAST");

        receiver = new MY_BroadcastReceiver();
        localmng.registerReceiver(receiver,filter);

    }


    class MY_BroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(context, "接受到啦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

    }
}