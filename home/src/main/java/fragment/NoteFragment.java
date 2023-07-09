package fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.servicedemo.R;
import com.example.servicedemo.Record_note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import adapter.Adapter;
import bean.NoteBean;
import database.SQLiteHelper;


public class NoteFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private View notefragment;

    RecyclerView recyclerView;
    List<NoteBean> list;
    SQLiteHelper mSQLiteHelper;
    Adapter adapter;
    private FloatingActionButton add;


    public NoteFragment() {

    }

    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1&&resultCode==2){
            if (list!=null){
                list.clear();
            }
            //从数据库中查询数据(保存的标签)
            list = mSQLiteHelper.query();
            adapter = new Adapter(getContext(), list);
            recyclerView.setAdapter(adapter);

            StaggeredGridLayoutManager smg = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(smg);


            adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    NoteBean note = list.get(position);

                    Intent intent = new Intent(getContext(),Record_note.class);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setMessage("是否删除此笔记？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NoteBean notepadBean = list.get(position);
                                    if(mSQLiteHelper.deleteData(notepadBean.getId())){
                                        list.remove(position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(),"删除成功",
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        notefragment = inflater.inflate(R.layout.fragment_note, container, false);



        recyclerView = (notefragment).findViewById(R.id.recycler_layout);
        add = (FloatingActionButton) notefragment.findViewById(R.id.frag_note_fbtn_add);

        add.setOnClickListener(this);
        mSQLiteHelper= new SQLiteHelper(getContext()); //创建数据库

        if (list!=null){
            list.clear();
        }
        //从数据库中查询数据(保存的标签)
        list = mSQLiteHelper.query();
        adapter = new Adapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager smg = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(smg);



        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NoteBean note = list.get(position);

                Intent intent = new Intent(getContext(),Record_note.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setMessage("是否删除此笔记？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NoteBean notepadBean = list.get(position);
                                if(mSQLiteHelper.deleteData(notepadBean.getId())){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(),"删除成功",
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



        return notefragment;
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.frag_note_fbtn_add)
        {
            Intent intent = new Intent(getContext(),Record_note.class);
            startActivityForResult(intent,1);
        }
    }



}