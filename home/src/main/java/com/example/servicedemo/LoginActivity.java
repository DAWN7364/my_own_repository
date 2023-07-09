package com.example.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText zh;
    private EditText mima;
    private CheckBox cb_memkey;
    private CheckBox cb_autologin;
    private TextView tv_toregister;
    private SharedPreferences register;
    private SharedPreferences options;
    private SharedPreferences rem_info;
    private Button btn_login;
    private Boolean ismemkey;
    private Boolean isautologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        zh = findViewById(R.id.et_login_name);
        mima = findViewById(R.id.et_login_mima);

        cb_memkey = findViewById(R.id.cb_memokey);
        cb_autologin = findViewById(R.id.cb_autologin);
        tv_toregister = findViewById(R.id.tv_LoginToRegister);
        btn_login = findViewById(R.id.btn_login);

        //cb_memkey.setOnCheckedChangeListener(this);
        tv_toregister.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //记住密码数据库
        rem_info = getSharedPreferences("rem_info",MODE_PRIVATE);
        //注册信息数据库
        register = getSharedPreferences("register_info",MODE_PRIVATE);

        //从SharedPreference中恢复勾选状态
       options = getSharedPreferences("setting",MODE_PRIVATE);

        ismemkey = options.getBoolean("ismemkey",false);
        isautologin = options.getBoolean("isautologin",false);

        cb_autologin.setChecked(isautologin);
        cb_memkey.setChecked(ismemkey);

        Intent intent =  getIntent();
        Bundle bundle = intent.getExtras();

            if(intent.hasExtra("name") && intent.hasExtra("mima")){
            Log.d("registed","密码已传递");

            String name_b = bundle.getString("name");
            String mima_b = bundle.getString("mima");

            zh.setText(name_b);
            mima.setText(mima_b);
        }




        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(LoginActivity.this);//broadcastManager
        IntentFilter intentFilter = new IntentFilter();//intentFilter
        intentFilter.addAction("cancel login");//添加过滤
        BroadcastReceiver mItemListClickReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {//广播接收器实例
                cb_autologin.setChecked(false);
                SharedPreferences.Editor editor = options.edit();
                editor.putBoolean("isautologin",false);
                editor.commit();
            }
        };

        broadcastManager.registerReceiver(mItemListClickReciver,intentFilter);

        if(isautologin)
        {
            Toast.makeText(LoginActivity.this, "正在登录中", Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            timer.schedule(task,1000);

        }

        if(ismemkey)
        {
            zh.setText(rem_info.getString("name",null));
            mima.setText(rem_info.getString("mima",null));
        }





    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = rem_info.edit();
        editor.putString("name",zh.getText().toString());
        editor.putString("mima",mima.getText().toString());
        editor.commit();
        SharedPreferences.Editor editor1 = options.edit();
        editor1.putBoolean("ismemkey",cb_memkey.isChecked());
        editor1.putBoolean("isautologin",cb_autologin.isChecked());
        editor1.commit();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = rem_info.edit();
        editor.putString("name",zh.getText().toString());
        editor.putString("mima",mima.getText().toString());
        editor.commit();
        SharedPreferences.Editor editor1 = options.edit();
        editor1.putBoolean("ismemkey",cb_memkey.isChecked());
        editor1.putBoolean("isautologin",cb_autologin.isChecked());
        editor1.commit();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.tv_LoginToRegister:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String s_name = zh.getText().toString();
                String s_mima = mima.getText().toString();
                if(s_name.equals(register.getString("name",null)) && s_mima.equals(register.getString("mima",null)))
                {
                        Intent intent_main = new Intent();
                        intent_main.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent_main);
                        if(ismemkey)
                        {
                            SharedPreferences.Editor editor = rem_info.edit();
                            editor.putString("name",zh.getText().toString());
                            editor.putString("mima",mima.getText().toString());
                            editor.commit();
                            SharedPreferences.Editor editor1 = options.edit();
                            editor1.putBoolean("ismemkey",cb_memkey.isChecked());
                            editor1.putBoolean("isautologin",cb_autologin.isChecked());
                            editor1.commit();
                        }
                        finish();
                }
                else if(!s_name.equals("") && !s_mima.equals(""))
                {
                    Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
                }
                else if(s_name.equals("") || s_mima.equals(""))
                {
                    Toast.makeText(this, "请输入完整账号或密码后重试", Toast.LENGTH_SHORT).show();
                }
        }
    }



}