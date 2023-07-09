package com.example.servicedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class nicheng_edit_frag extends AppCompatActivity implements View.OnClickListener {

    private EditText frag_name_et;
    private Button frag_name_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicheng_edit_frag);

        frag_name_et = findViewById(R.id.frag_name_edit_et);
        frag_name_btn = findViewById(R.id.frag_name_edit_btn);
        frag_name_btn.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("修改昵称");

        Intent intent = getIntent();
        if(intent.hasExtra("name1"))
        {
            Bundle bundle = intent.getExtras();
            String name_s = bundle.getString("name1");
            frag_name_et.setText(name_s);
        }


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.frag_name_edit_btn)
        {
            String name_s= frag_name_et.getText().toString();
            SharedPreferences nicheng_save = getSharedPreferences("nicheng_save",MODE_PRIVATE);
            SharedPreferences.Editor nicheng_editer = nicheng_save.edit();
            nicheng_editer.putString("nicheng",name_s);
            nicheng_editer.commit();

            Intent intent = new Intent();
           Bundle bundle = new Bundle();
           bundle.putString("name_s",frag_name_et.getText().toString());
           intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }
    }
}