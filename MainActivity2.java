package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button my = findViewById(R.id.bt_my);
        Button class_my = findViewById(R.id.bt_class);
        Button dial = findViewById(R.id.bt_dial);

        my.setOnClickListener(new MyOnClickListener());

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dialnum = "12345";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:"+dialnum);
                intent.setData(uri);
                startActivity(intent);

            }
        });
        class_my.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ProgressDialog dialog = new ProgressDialog(MainActivity2.this);
                dialog.setTitle("正在进入");
                dialog.setMessage("请稍后....");
                dialog.setCancelable(true);
                dialog.show();
                return true;
            }
        });
    }

    class MyOnClickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity2.this,MyActivity.class);
            startActivity(intent);
        }
    }



}