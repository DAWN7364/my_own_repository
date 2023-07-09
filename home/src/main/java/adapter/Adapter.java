package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servicedemo.R;

import java.util.List;

import bean.NoteBean;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>  {

    //声明接口对象
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;


    public interface OnItemClickListener{
        //点击事件的接口
        void onItemClick(int position);//抽象方法
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

    List<NoteBean> list;
    Context context;



    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    public Adapter(Context context, List<NoteBean> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout,parent,false);
        MyViewHolder myViewholder = new MyViewHolder(mview);



        return myViewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


                NoteBean noteitem = list.get(position);
                holder.content.setText(noteitem.getNotepadContent());
                holder.time.setText(noteitem.getNotepadTime());

                if(mOnItemClickListener != null) {//item被点击了
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onItemClick(position);
                        }
                    });
                }

                if(mOnItemLongClickListener !=null)
                {
                    holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            mOnItemLongClickListener.onItemLongClick(position);
                            return true;
                        }
                    });

                }
    }




    @Override
    public int getItemCount() {
        return list.size();
    }


   static class MyViewHolder extends RecyclerView.ViewHolder {
        View mview;
        TextView time;
        TextView content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            time = itemView.findViewById(R.id.tv_time);
            content = itemView.findViewById(R.id.tv_content);
        }
    }



}
