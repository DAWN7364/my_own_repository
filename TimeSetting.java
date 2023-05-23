package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import util.DateUtil;

public class TimeSetting extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {



    private TextView tv_show;//定义TextView

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timesettingacticity);

        //TextView对象
         tv_show = findViewById(R.id.show_date);
         //设置监听器
         tv_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.show_date://点击TextView
                DatePickerDialog dialog = new DatePickerDialog(this,  this,2022,10,30);
                dialog.show();
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date_show = String.format("现在的时间是%d年%d月%d日",year,month+1,day);
        tv_show.setText(date_show);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("response",DateUtil.getNowTime());
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}