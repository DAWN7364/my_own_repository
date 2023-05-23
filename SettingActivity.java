package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);
        Button personal =  findViewById(R.id.bt_personif);
        Button timesetting = findViewById(R.id.timesetting);

        tv_result = findViewById(R.id.time_receive);

        timesetting.setOnClickListener(this);
        personal.setOnClickListener(this);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.bt_personif:
                Intent na_person = new Intent();
                na_person.setClass(SettingActivity.this,PersonSetting.class);
                startActivity(na_person);
                break;
            case R.id.timesetting:
                Intent na_timeset = new Intent();
                na_timeset.setClass(SettingActivity.this, TimeSetting.class);
                startActivityForResult(na_timeset,0);
                break;
            case R.id.iv_back:
                finish();
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        {
            if(intent != null && requestCode == 0 && resultCode == Activity.RESULT_OK)
            {
                Bundle bundle = intent.getExtras();
                String time = bundle.getString("response");
                tv_result.setText(time);
            }
        }

    }

}