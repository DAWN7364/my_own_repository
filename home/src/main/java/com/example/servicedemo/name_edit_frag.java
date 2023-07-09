package com.example.servicedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class name_edit_frag extends AppCompatActivity implements View.OnClickListener {

    private EditText frag_name_edit_et;
    private Button frag_name_edit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_edit_frag);

        frag_name_edit_et = findViewById(R.id.frag_name2_edit_et);
        frag_name_edit_btn = findViewById(R.id.frag_name2_edit_btn);
        frag_name_edit_btn.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("修改姓名");

        Intent intent = getIntent();
        if(intent.hasExtra("name2"))
        {
            Bundle bundle = intent.getExtras();
            String name_s = bundle.getString("name2");
            frag_name_edit_et.setText(name_s);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.frag_name2_edit_btn)
        {
            String name_s= frag_name_edit_et.getText().toString();
            SharedPreferences name_save = getSharedPreferences("name_save",MODE_PRIVATE);
            SharedPreferences.Editor name_editer = name_save.edit();
            name_editer.putString("name",name_s);
            name_editer.commit();

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("name_s_2",frag_name_edit_et.getText().toString());
            intent.putExtras(bundle);
            setResult(2,intent);
            finish();
        }
    }
}