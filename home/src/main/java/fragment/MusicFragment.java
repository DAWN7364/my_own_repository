package fragment;

import static android.content.Context.BIND_AUTO_CREATE;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.servicedemo.MusicService;
import com.example.servicedemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Util.MusicUtil;
import adapter.MyAdapter;
import bean.MusicBean;


public class MusicFragment extends Fragment {

    private Handler handler;//交互页面处理机制
    private static final String TAG="MainActivity";
    private ConstraintLayout cl_main;
    private TextView tv_leftTime,tv_rightTime,tv_song;
    private SeekBar seekBar;
    private ImageButton ib_precious,ib_state,ib_next;
    private MusicService.MyBinder myBinder;
    private ListView lv_music;
    private List<MusicBean> list;//数据集
    private MusicUtil utils;
    private MyAdapter adapter;
    private SimpleDateFormat time=new SimpleDateFormat("mm:ss");//时间显示格式
    private Intent MediaServiceIntent;
    private boolean isPlaying=false;//判断是否在播放
    private int seek_flag=0;//进度条标志量
    private int music_index=0;//音乐索引
    private String song;//歌曲名字
    public ButtonBroadcastReceiver buttonBroadcastReceiver;//广播接收器
    public final static String BUTTON_PREV_ID="BUTTON_PREV_ID";//对应Action
    public final static String BUTTON_PLAY_ID="BUTTON_PLAY_ID";
    public final static String BUTTON_NEXT_ID="BUTTON_NEXT_ID";
    public final static String BUTTON_CLOSE_ID="BUTTON_CLOSE_ID";



    private View musicfragment;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;







    public MusicFragment() {

    }


    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        musicfragment = inflater.inflate(R.layout.fragment_music, container, false);


        MediaServiceIntent =new Intent(getContext(),MusicService.class);//MediaServiceIntent为一个Intent
        getContext().bindService(MediaServiceIntent,connection,BIND_AUTO_CREATE);

        check();

        return musicfragment;
    }

    //初始化各个控件，初始状态，以及监听
    private void InitView(){
        cl_main=musicfragment.findViewById(R.id.cl_main);
        tv_leftTime=musicfragment.findViewById(R.id.tv_leftTime);
        tv_rightTime=musicfragment.findViewById(R.id.tv_rightTime);
        tv_song=musicfragment.findViewById(R.id.tv_song);
        seekBar=musicfragment.findViewById(R.id.seekBar);
        ib_precious=musicfragment.findViewById(R.id.ib_precious);
        ib_state=musicfragment.findViewById(R.id.ib_state);
        ib_next=musicfragment.findViewById(R.id.ib_next);
        lv_music=musicfragment.findViewById(R.id.lv_music);

        utils=new MusicUtil();

        ib_state.setImageResource(R.drawable.ib_play_music);

        list=new ArrayList<>();
        list= MusicUtil.getMp3InfoList(getContext());
        adapter=new MyAdapter(getContext(),list);
        lv_music.setAdapter(adapter);
        adapter.setCurrentItem(0);

        tv_song.setText("音乐名称");
        tv_leftTime.setText("00:00");
        tv_rightTime.setText("00:00");

        //歌曲列表点击某个Item
        lv_music.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获得点击音乐序号
                music_index=position;
                //设置状态为播放中
                isPlaying=true;

                adapter.setCurrentItem(music_index);
                adapter.notifyDataSetInvalidated();
                //设置seekbar的最大值
                seekBar.setMax(myBinder.getProgress(music_index));
                //设置正在播放标题
                song=list.get(music_index).getTitle();
                tv_song.setText(song);
                //设置播放图标
                ib_state.setImageResource(R.drawable.music_stop);
                //加载新线程
                handler.post(runnable);
                //通过后台播放音乐
                myBinder.playMusic(music_index);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                    myBinder.seekToPosition(seekBar.getProgress());
                    //标记当前播放进度条的位置
                    seek_flag = seekBar.getProgress();

            }
        });

        //为按钮设置监听
        ib_precious.setOnClickListener(l);
        ib_state.setOnClickListener(l);
        ib_next.setOnClickListener(l);
        handler=new Handler();

        initButtonReceiver();


    }

    //按钮点击事件监听
    public View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_state:
                    if(!isPlaying){
                        isPlaying=true;
                        song=list.get(music_index).getTitle();
                        tv_song.setText(song);
                        ib_state.setImageResource(R.drawable.music_stop);
                        if(seek_flag==0) {//是0说明刚开始播放
                            myBinder.playMusic(music_index);
                        }else{//暂停过后的情况
                            myBinder.playMusic(music_index);
                            myBinder.seekToPosition(seek_flag);
                        }
                    }else{
                        isPlaying=false;
                        ib_state.setImageResource(R.drawable.ib_play_music);
                        seek_flag=myBinder.getPlayPosition();//获取当前位置暂停
                        myBinder.pauseMusic();
                    }

                    break;
                case R.id.ib_precious://上一首
                    isPlaying=true;
                    ib_state.setImageResource(R.drawable.music_stop);
                    seek_flag=0;
                    if(music_index<=0){
                        music_index=list.size()-1;
                    }else{
                        music_index-=1;
                    }

                    seekBar.setMax(myBinder.getProgress(music_index));
                    song=list.get(music_index).getTitle();
                    tv_song.setText(song);
                    myBinder.preciousMusic();
                    break;
                case R.id.ib_next://下一首
                    isPlaying=true;
                    ib_state.setImageResource(R.drawable.music_stop);
                    seek_flag=0;
                    if(music_index>=list.size()-1){
                        music_index=0;
                    }else{
                        music_index+=1;
                    }

                    seekBar.setMax(myBinder.getProgress(music_index));
                    song=list.get(music_index).getTitle();
                    tv_song.setText(song);
                    myBinder.nextMusic();
                    break;
            }
        }
    };

    //Service连接初始化
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder= (MusicService.MyBinder) service;
            seekBar.setMax(myBinder.getProgress());

            Log.d(TAG, "Service与Activity已连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //界面刷新
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(myBinder.getPlayPosition());
            tv_leftTime.setText(time.format(myBinder.getPlayPosition())+"");
            tv_rightTime.setText(time.format(myBinder.getProgress()-myBinder.getPlayPosition())+"");
            if(myBinder.getProgress()-myBinder.getPlayPosition()<1000){//时间不够了自动触发下一首
                getActivity().runOnUiThread(new Runnable() {//使用uiThread触发点击事件
                    @Override
                    public void run() {
                        ib_next.performClick();
                    }
                });
            }
            handler.postDelayed(runnable,1000);
        }
    };

    //检查权限
    private void check(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Log.d(TAG,"---------------------写权限不够-----------------");
            }
            else InitView();
            if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                Log.d(TAG,"---------------------读权限不够-----------------");
            }
            else InitView();
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    InitView();
                    Log.d(TAG, "---------------------写权限够了-----------------------------");
                }
                else
                {
                    Toast.makeText(getActivity(), "你的权限不够", Toast.LENGTH_LONG).show();

                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    InitView();
                    Log.d(TAG, "---------------------读权限够了-----------------------------");
                }
                else {
                    Toast.makeText(getActivity(), "你的权限不够", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }



    //注册广播(通知编程~未完成)
    private void initButtonReceiver(){
        buttonBroadcastReceiver=new ButtonBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(BUTTON_PREV_ID);
        intentFilter.addAction(BUTTON_PLAY_ID);
        intentFilter.addAction(BUTTON_NEXT_ID);
        intentFilter.addAction(BUTTON_CLOSE_ID);
        getActivity().registerReceiver(buttonBroadcastReceiver,intentFilter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        myBinder.closeMusic();
        getActivity().unregisterReceiver(buttonBroadcastReceiver);
    }


    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Log.d(TAG,"--------------------收到action:"+action+"--------------------------");
            if(action.equals(BUTTON_PREV_ID)){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ib_precious.performClick();
                        return;
                    }
                });
            }
            if(action.equals(BUTTON_PLAY_ID)){
               getActivity(). runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ib_state.performClick();
                        return;
                    }
                });
            }
            if(action.equals(BUTTON_NEXT_ID)){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ib_next.performClick();
                        return;
                    }
                });
            }
            if(action.equals(BUTTON_CLOSE_ID)){
                handler.removeCallbacks(runnable);
                myBinder.closeMusic();
                getActivity().unbindService(connection);

                getActivity().unregisterReceiver(buttonBroadcastReceiver);
                getActivity().finish();
            }
        }
    }


}