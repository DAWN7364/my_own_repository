package Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import bean.MusicBean;

public class MusicUtil {

    //获取专辑封面的UI
    private static final String TAG="MusicUtil";
    private static final Uri albumArtUri=Uri.parse("content://media/external/audio/albumart");
    //生成歌曲列表
    @SuppressLint("Range")
    public static List<MusicBean> getMp3InfoList(Context context){
        Cursor cursor=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
        List<MusicBean> musicBeanList =new ArrayList<>();
        while(cursor.moveToNext()){
            MusicBean musicBean =new MusicBean();
            musicBean.setUrl(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));//path
            musicBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicBean.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicBean.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            musicBean.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicBean.setAlbum(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicBeanList.add(musicBean);
        }
        return musicBeanList;
    }
    //格式化时间，转换为分/秒
    public static String formatTime(long time){
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }


}
