package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.servicedemo.R;

import java.util.List;

import Util.MusicUtil;
import bean.MusicBean;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<MusicBean> list;
    private ViewHolder holder=null;
    private int currentItem=0;

    //获得位置
    public  void setCurrentItem(int currentItem){
        this.currentItem=currentItem;
    }
    public MyAdapter(Context context,List<MusicBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.music_item_layout,null);
            holder.tv_title=convertView.findViewById(R.id.tv_title);
            holder.tv_artist=convertView.findViewById(R.id.tv_artist);
            holder.tv_duration=convertView.findViewById(R.id.tv_duration);
            holder.tv_position=convertView.findViewById(R.id.tv_position);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        long duration = list.get(position).getDuration();
        String time= MusicUtil.formatTime(duration);

        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_artist.setText(list.get(position).getArtist());
        holder.tv_duration.setText(time);
        holder.tv_position.setText(position+1+"");

        return convertView;
    }
    class ViewHolder{
        TextView tv_title;//歌曲名
        TextView tv_artist;//歌手
        TextView tv_duration;//时长
        TextView tv_position;//序号
    }
}
