package com.example.servicedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Util.DBUtils;
import bean.NoteBean;
import database.SQLiteHelper;


public class Record_note extends AppCompatActivity implements View.OnClickListener {

    ImageView note_back;
    TextView note_time;
    EditText content;
    ImageView delete;
    ImageView note_save;
    SQLiteHelper mSQLiteHelper;
    TextView noteName;
    String id;

    List<NoteBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_note);

            note_back = (ImageView) findViewById(R.id.note_back);
            note_time = (TextView)findViewById(R.id.tv_time);
            content = (EditText) findViewById(R.id.note_content);
            delete = (ImageView) findViewById(R.id.delete);
            note_save = (ImageView) findViewById(R.id.note_save);
            noteName = (TextView) findViewById(R.id.note_name);
            note_back.setOnClickListener(this);
            delete.setOnClickListener(this);
            note_save.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
            initData();

        }
        protected void initData() {
            mSQLiteHelper = new SQLiteHelper(this);
            noteName.setText("添加笔记");
            Intent intent = getIntent();
            if(intent!= null){
                id = intent.getStringExtra("id");
                if (id != null){
                    noteName.setText("修改笔记");
                    content.setText(intent.getStringExtra("content"));
                    note_time.setText(intent.getStringExtra("time"));
                    note_time.setVisibility(View.VISIBLE);
                }
            }
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.note_back:
                    finish();
                    break;
                case R.id.delete:
                    content.setText("");
                    break;
                case R.id.note_save:
                    String noteContent=content.getText().toString().trim();
                    if (id != null){//修改操作
                        if (noteContent.length()>0){
                            if (mSQLiteHelper.updateData(id, noteContent, DBUtils.getTime())){
                                showToast("修改成功");
                                setResult(2);
                                finish();
                            }else {
                                showToast("修改失败");
                            }
                        }else {
                            showToast("修改内容不能为空!");
                        }
                    }else {
                        //向数据库中添加数据
                        if (noteContent.length()>0){
                            if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                                showToast("保存成功");
                                setResult(2);
                                finish();
                            }else {
                                showToast("保存失败");
                            }
                        }else {
                            showToast("修改内容不能为空!");
                        }
                    }
                    break;
            }
        }
        public void showToast(String message){
            Toast.makeText(Record_note.this,message,Toast.LENGTH_SHORT).show();
        }

    }
