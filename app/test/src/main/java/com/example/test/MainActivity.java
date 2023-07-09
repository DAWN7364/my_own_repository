package com.example.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<NoteBean> list;

    SQLiteHelper mSQLiteHelper;

    RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_layout);

        ImageButton mB = findViewById(R.id.add_btn);
        mB.setOnClickListener(this);

        StaggeredGridLayoutManager smg = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(smg);

        mSQLiteHelper = new SQLiteHelper(this); //创建数据库



        if (list != null) {
            list.clear();
        }
        //从数据库中查询数据(保存的标签)
        list = mSQLiteHelper.query();
        adapter = new Adapter(MainActivity.this,list);
        recyclerView.setAdapter(adapter);





        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NoteBean note = list.get(position);

                Intent intent = new Intent(MainActivity.this,Record_note.class);
                intent.putExtra("posi",position);
                intent.putExtra("id",note.getId());
                intent.putExtra("content",note.getNotepadContent());
                intent.putExtra("time",note.getNotepadTime());

                startActivityForResult(intent,1);
            }
        });



        adapter.setOnItemLongClickListener(new Adapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("是否删除此笔记？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NoteBean notepadBean = list.get(position);
                                if(mSQLiteHelper.deleteData(notepadBean.getId())){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(),"删除成功",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog =  builder.create();
                dialog.show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_btn)
        {
            Intent intent = new Intent(MainActivity.this,Record_note.class);
            startActivityForResult(intent,1);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1&&resultCode==2){

            if (list!=null){
                list.clear();
            }

            StaggeredGridLayoutManager smg = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(smg);
            //从数据库中查询数据(保存的标签)
            list = mSQLiteHelper.query();
            Adapter adapter = new Adapter(MainActivity.this,list);

            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    NoteBean note = list.get(position);

                    Intent intent = new Intent(MainActivity.this,Record_note.class);
                    intent.putExtra("posi",position);
                    intent.putExtra("id",note.getId());
                    intent.putExtra("content",note.getNotepadContent());
                    intent.putExtra("time",note.getNotepadTime());

                    startActivityForResult(intent,1);
                }
            });



            adapter.setOnItemLongClickListener(new Adapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(int position) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setMessage("是否删除此笔记？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NoteBean notepadBean = list.get(position);
                                    if(mSQLiteHelper.deleteData(notepadBean.getId())){
                                        list.remove(position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(),"删除成功",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialog =  builder.create();
                    dialog.show();
                }
            });

        }

    }


}

