package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonSetting extends AppCompatActivity {


    EditText ed1;
    EditText ed2 ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_setting);

        Button queren = findViewById(R.id.queren);
        ed1= findViewById(R.id.et_name);
        ed2= findViewById(R.id.et_profes);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ed1str = ed1.getText().toString().trim();
                String ed2str = ed2.getText().toString().trim();
                if(ed1str.length() != 0 && ed2str.length() != 0)
                {
                    Toast.makeText(PersonSetting.this, "保存成功", Toast.LENGTH_LONG).show();
                    String name = ed1.getText().toString();
                    String xuehao = ed2.getText().toString();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("result_name",name);
                    bundle.putString("result_xuehao",xuehao);
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                else
                {
                    Toast.makeText(PersonSetting.this, "请完整输入信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}