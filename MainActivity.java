package com.example.homework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import util.DateUtil;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button home = findViewById(R.id.home);
        Button click = findViewById(R.id.click_to_enter);

        TextView time = findViewById(R.id.tv_time);
        String timenow = String.format("现在的时间是%s", DateUtil.getNowTime());
        time.setText(timenow);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent();
               intent.setClass(MainActivity.this,LogActivity.class);

               startActivity(intent);
            }
        });


    }
}
