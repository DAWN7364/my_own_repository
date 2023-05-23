package com.example.homework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_xuehao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myspace);
        Button bt_set = findViewById(R.id.bt_setting);
        Button bt_escape = findViewById(R.id.bt_escape);
        Button bt_update = findViewById(R.id.bt_update);
        Button bt_calcu = findViewById(R.id.bt_calcu);
        tv_xuehao = findViewById(R.id.tv_xuehao);
        tv_name = findViewById(R.id.tv_name);
        //设置按钮监听
        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
        //退出登录按钮监听
        bt_escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity.this);
                dialog.setTitle("确定要退出登录？");
               dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int which) {
                       finish();
                   }
               });
               dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int which) {

                   }
               });
               dialog.show();
            }

        });
        //检查更新按钮监听器
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyActivity.this, "已经是最新版本啦！", Toast.LENGTH_SHORT).show();
            }
        });

        bt_calcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.mycalcu");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
            }
        });

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity.this,PersonSetting.class);
                startActivityForResult(intent,1);
            }
        });
    }


    protected void onActivityResult(int requestCode ,int resultCode ,Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && intent != null)
        {
            Bundle bundle = intent.getExtras();
            String name = bundle.getString("result_name");
            String xuhao = bundle.getString("result_xuehao");
            tv_name.setText(name);
            tv_xuehao.setText(xuhao);
        }

    }

}