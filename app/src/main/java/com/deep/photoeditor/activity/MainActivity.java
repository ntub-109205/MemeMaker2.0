package com.deep.photoeditor.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.deep.photoeditor.EditMain;
import com.deep.photoeditor.GifMain;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.api;
import com.deep.photoeditor.editCombinePicture;
import com.deep.photoeditor.fragment.HomeFragment;
import com.deep.photoeditor.fragment.PersonFragment;
import com.deep.photoeditor.fragment.PublicFragment;
import com.deep.photoeditor.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
//
//import me.majiajie.pagerbottomtabstrip.NavigationController;
//import me.majiajie.pagerbottomtabstrip.PageNavigationView;
//import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
//import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    //死按鈕宣告
    FloatingActionButton fabMeme, fabElder, fabgif;
    private static api callApi = new api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            callApi.post("http://140.131.115.99/api/test","str=中文");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.d("test",callApi.returnString());
//        for(int i=0;i<callApi.cutString().size();i++){
//            Log.d("test",callApi.cutString().get(i).toString());
//        }
        // init();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
        //死按鈕設定
        fabMeme = findViewById(R.id.fabMeme);
        fabElder = findViewById(R.id.fabElder);
        fabgif = findViewById(R.id.fabgif);

        fabMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "You Selected FabExplore!!!!", Toast.LENGTH_LONG).show();
                /**從MAIN 跳到 SECOND 頁面*/
                Intent intent = new Intent(MainActivity.this, EditMain.class);
                /** 啟動intent */
                intent.setClass(MainActivity.this, EditMain.class);
                startActivity(intent);
            }
        });

        fabElder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "You Selected fabElder!!!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, editCombinePicture.class);
                /** 啟動intent */
                intent.setClass(MainActivity.this, editCombinePicture.class);
                startActivity(intent);

            }
        });

        fabgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "You Selected FabExplore!!!!", Toast.LENGTH_LONG).show();
                /**從MAIN 跳到 SECOND 頁面*/
                Intent intent = new Intent(MainActivity.this, GifMain.class);
                /** 啟動intent */
                intent.setClass(MainActivity.this, GifMain.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_public:
                            selectedFragment = new PublicFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_person:
                            selectedFragment = new PersonFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    //退出確認提示
    private long firstPressedTime;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // System.currentTimeMillis() 當前系統時間
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }
}