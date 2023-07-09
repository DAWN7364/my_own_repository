package com.example.servicedemo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragment.HomeFragment;
import fragment.MusicFragment;
import fragment.NoteFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;// 视图翻页工具
    private BottomNavigationView navigationView;// 底部导航栏

    private HomeFragment homeFragment = new HomeFragment();
    private MusicFragment musicFragment = new MusicFragment();
    private NoteFragment noteFragment = new NoteFragment();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();


    }

    public void initView()
    {
        viewPager = findViewById(R.id.viewPager);

        // 监听viewPager页面变化事件
        viewPager.addOnPageChangeListener(viewPagerListener);

        // 获取Fragment管理对象
        FragmentManager fragmentManager = getSupportFragmentManager();

        // FragmentPagerAdapter 来处理多个 Fragment 页面
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return musicFragment;
                    case 1:
                        return noteFragment;
                    case 2:
                        return homeFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        // viewPager 设置 adapter
        viewPager.setAdapter(fragmentPagerAdapter);

        // 底部导航栏
        navigationView = findViewById(R.id.nav_view);
        // navigationView点击事件
        navigationView.setOnNavigationItemSelectedListener(navigationViewListener);

    }

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            navigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    BottomNavigationView.OnNavigationItemSelectedListener navigationViewListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            viewPager.setCurrentItem(item.getOrder());
            return false;
        }


    };




}