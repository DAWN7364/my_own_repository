package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    //建立菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    //点击菜单事项
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.help_item:
                Toast.makeText(this,"你点击了帮助！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_item:
               Intent settingact = new Intent();
               settingact.setClass(LogActivity.this,SettingActivity.class);
               startActivity(settingact);
                break;
            case R.id.back_item:
                AlertDialog.Builder back_Alert = new AlertDialog.Builder(LogActivity.this);
                back_Alert.setTitle("是否要退出？");
                back_Alert.setMessage("要是确定了就真的退出喽");
                back_Alert.setCancelable(false);
                back_Alert.setPositiveButton("退出",new DialogInterface.OnClickListener(){
                         public void onClick(DialogInterface back_Alert, int which){
                                finish();
                        }
                });
                back_Alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface back_Alert, int which) {
                        Toast.makeText(LogActivity.this, "请继续登录操作", Toast.LENGTH_SHORT).show();
                    }
                });
                back_Alert.show();
                break;
            default:
                break;
            }

        return true;
        }

//    设置两个全局变量：edittetx_pass edittext_name
    private EditText editText_pass;
    private EditText editText_name;
    private Button loginbutton;
    private Button show_button;

    private ImageButton im_button;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.logactivity);

        loginbutton = findViewById(R.id.login);
         show_button = findViewById(R.id.show_button);

         editText_pass = (EditText) findViewById(R.id.mima);
         editText_name = (EditText) findViewById(R.id.xuehao);
         im_button = (ImageButton) findViewById(R.id.ib_setting);
        //点击按钮事项
        show_button.setOnClickListener(this);
        loginbutton.setOnClickListener(this);
        im_button.setOnClickListener(this);




    }
    public void onClick(View v)
    {
            switch(v.getId())
            {
                case R.id.show_button:
                    String passText = editText_pass.getText().toString();
                    String passsize = editText_pass.getText().toString().trim();
                    String namesize = editText_name.getText().toString().trim();
                    if(passsize.length() == 0)
                    {
                        Toast.makeText(this,"您还没有输入",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else if(passsize.length() != 0 && namesize.length()!= 0) {
                        loginbutton.setEnabled(true);
                        Toast.makeText(this, passText, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else break;

                case R.id.login:
                    String size_pass = editText_pass.getText().toString().trim();
                    String size_name = editText_name.getText().toString().trim();
                    if(size_pass.length()==0 || size_name.length()==0)
                    {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(LogActivity.this);
                        dialog.setTitle("你还没有输入学号或密码");
                        dialog.setMessage("请返回输入学号或密码再继续");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("好的",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                            }
                        });
                        dialog.setNegativeButton("退出",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                                finish();
                            }
                        });
                        dialog.show();
                        break;
                    }
                    else{
                        Intent intent = new Intent(LogActivity.this,MainActivity2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                         }
                case R.id.ib_setting:
                    Intent intent2 = new Intent();
                    intent2.setClass(LogActivity.this,SettingActivity.class);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }

    }

}
