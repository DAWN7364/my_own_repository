package fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.servicedemo.R;
import com.example.servicedemo.name_edit_frag;
import com.example.servicedemo.nicheng_edit_frag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private View homefragment;
    private TextView frag_home_tv_nicheng;
    private TextView frag_home_tv_name;
    private CircleImageView profile;

    private static final int TAKE_PHOTO = 10;
    private static final int CHOOSE_PHOTO = 20;

    private Uri imageUri;

    private PopupWindow popupWindow;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        homefragment = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView iv_into_name = homefragment.findViewById(R.id.home_frag_iv_name_right);
        ImageView iv_into_nicheng = homefragment.findViewById(R.id.home_frag_iv_nicheng_right);
        ImageView iv_back = homefragment.findViewById(R.id.iv_backward);
        Button frag_home_logout = homefragment.findViewById(R.id.home_frag_btn_logout);
        frag_home_tv_nicheng = homefragment.findViewById(R.id.frag_home_tv_nicheng);
        frag_home_tv_name = homefragment.findViewById(R.id.frag_home_tv_name);
        profile = homefragment.findViewById(R.id.profile_image);

        iv_back.setOnClickListener(this);
        iv_into_name.setOnClickListener(this);
        iv_into_nicheng.setOnClickListener(this);
        frag_home_logout.setOnClickListener(this);
        profile.setOnClickListener(this);

        SharedPreferences name_save  = getContext().getSharedPreferences("name_save", Context.MODE_PRIVATE);
        String name_s = name_save.getString("name",null);
        frag_home_tv_name.setText(name_s);

        SharedPreferences nicheng_save = getContext().getSharedPreferences("nicheng_save",Context.MODE_PRIVATE);
        String nicheng_s = nicheng_save.getString("nicheng",null);
        frag_home_tv_nicheng.setText(nicheng_s);
        return homefragment;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_backward) {
            ((Activity) getContext()).finish();
        }
        if (view.getId() == R.id.home_frag_iv_nicheng_right) {
            String name_s = frag_home_tv_nicheng.getText().toString();
            Intent intent = new Intent(getContext(), nicheng_edit_frag.class);
            Bundle bundle = new Bundle();
            bundle.putString("name1", name_s);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        }
        if (view.getId() == R.id.home_frag_iv_name_right) {
            String name_s = frag_home_tv_name.getText().toString();
            Intent intent = new Intent(getContext(), name_edit_frag.class);
            Bundle bundle = new Bundle();
            bundle.putString("name2", name_s);
            intent.putExtras(bundle);
            startActivityForResult(intent, 2);
        }
        if (view.getId() == R.id.home_frag_btn_logout) {
            Intent intent = new Intent("cancel login");
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            ((Activity) getContext()).finish();
        }
        if(view.getId() == R.id.profile_image)
        {
            initPopup(view);

        }

    }

    private void initPopup(View v) {

        View mview = LayoutInflater.from(getContext()).inflate(R.layout.photo_select,null,false);
        Button icon_camera = mview.findViewById(R.id.icon_btn_camera);
        Button icon_photo = mview.findViewById(R.id.icon_btn_select);
        Button icon_cancel = mview.findViewById(R.id.icon_btn_cancel);
        final PopupWindow popupwindow = new PopupWindow(mview,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        //构造popupwindow，mview就是pop出的view
        popupwindow.setAnimationStyle(R.anim.anim_pop);

        popupwindow.setTouchable(true);

        popupwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        popupwindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        popupwindow.setOutsideTouchable(true);

        popupwindow.showAsDropDown(mview,0,0);


        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupwindow.setAnimationStyle(R.anim.anim_cancel);
                popupwindow.dismiss();
            }
        });

        icon_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(((Activity)getContext()).getExternalCacheDir(),"output_image.jpg");
                try
                {
                    if(outputImage.exists())
                    {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                if(Build.VERSION.SDK_INT >= 24)
                {
                    imageUri = FileProvider.getUriForFile(getContext(),"com.example.cameraalbumtest.fileprovider",outputImage);
                }
                else
                {
                    imageUri = Uri.fromFile(outputImage);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                if(popupwindow.isShowing())
                {
                    popupwindow.dismiss();
                }
            }
        });


        icon_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions((Activity)getContext(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else
                {
                    openAlbum();
                }

                if(popupwindow.isShowing())
                {
                    popupwindow.dismiss();
                }
            }
        });

    }

    private void openAlbum() {

        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == 1)
            {
                String name_s = data.getStringExtra("name_s");
                frag_home_tv_nicheng.setText(name_s);
            }
        }

        if(requestCode == 2)
        {
            if(resultCode == 2)
            {
                String name_s = data.getStringExtra("name_s_2");
                frag_home_tv_name.setText(name_s);
            }
        }

        if(requestCode == TAKE_PHOTO && resultCode == RESULT_OK)
        {
            try
            {
                Bitmap bitmap = BitmapFactory.decodeStream(((Activity)getContext()).getContentResolver().openInputStream(imageUri));
                profile.setImageBitmap(bitmap);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        if(requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK)
        {
            if(Build.VERSION.SDK_INT >= 19){
                handleImageOnKitKat(data);
            }
            else{
                handleImageBeforeKitKat(data);
            }
        }


    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);

    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(getContext(),uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection =MediaStore.Images.Media._ID + "=" +id;
                 imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);

    }

    private void displayImage(String imagePath) {
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            profile.setImageBitmap(bitmap);
        }else{
            Toast.makeText(getContext(), "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = ((Activity)getContext()).getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst())
            {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }
}