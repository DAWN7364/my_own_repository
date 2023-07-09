package com.example.servicedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_mima;
    private Button btn_register;
    private SharedPreferences register_info;
    private CheckBox ckb_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        et_name = findViewById(R.id.et_name);//账号
        et_mima = findViewById(R.id.et_mima);//密码
        ckb_permission = findViewById(R.id.ckb_permission);
        btn_register = findViewById(R.id.btn_register);//注册按钮
        register_info = getSharedPreferences("register_info",MODE_PRIVATE);//保存注册信息的数据库

        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == btn_register && ckb_permission.isChecked())
        {
            String name_s = et_name.getText().toString();
            String mima_s = et_mima.getText().toString();
            //把注册信息添加到数据库中
            SharedPreferences.Editor editor = register_info.edit();
            editor.putString("name",name_s);
            editor.putString("mima",mima_s);
            editor.apply();

            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",name_s);
            bundle.putString("mima",mima_s);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        else if(view == btn_register && !ckb_permission.isChecked())
        {
            Toast.makeText(this, "请同意协议", Toast.LENGTH_SHORT).show();
        }
    }
}