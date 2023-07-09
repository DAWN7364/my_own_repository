package Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.servicedemo.R;

public class PhotoPopupWindow extends PopupWindow {
    private View mView;
    // PopupWindow 菜单布局
    private Context mContext;
    // 上下文参数
    private View.OnClickListener mSelectListener;
    // 相册选取的点击监听器
    private View.OnClickListener mCaptureListener;

    // 拍照的点击监听器
    public PhotoPopupWindow(Activity context, View.OnClickListener selectListener, View.OnClickListener captureListener)
    {super(context);this.mContext = context;this.mSelectListener = selectListener;this.mCaptureListener = captureListener;Init();}
    private void Init()
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.photo_select,null);
        Button btn_camera = (Button) mView.findViewById(R.id.icon_btn_camera);
        Button btn_select = (Button) mView.findViewById(R.id.icon_btn_select);
        Button btn_cancel = (Button) mView.findViewById(R.id.icon_btn_cancel);
        btn_select.setOnClickListener(mSelectListener);
        btn_camera.setOnClickListener(mCaptureListener);
        btn_cancel.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {dismiss();}});
        this.setContentView(mView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        mView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                int height = mView.findViewById(R.id.ll_pop).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {if (y < height) {dismiss();}}
                return true;
            }});


    }



}
