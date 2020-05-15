package com.deep.photoeditor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
//
//import me.majiajie.pagerbottomtabstrip.NavigationController;
//import me.majiajie.pagerbottomtabstrip.PageNavigationView;
//import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
//import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

import com.github.clans.fab.FloatingActionButton;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //死按鈕宣告
    FloatingActionButton fabMeme, fabElder, fabgif;
    //button onClick to next page
    public Button btnAddMeme;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    int tabIndex;
    private static api callApi = new api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            callApi.post("http://140.131.115.99/api/details",":D");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test",callApi.returnString());
        for(int i=0;i<callApi.cutString().size();i++){
            Log.d("test",callApi.cutString().get(i).toString());
        }
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
                intent.setClass(MainActivity.this,EditMain.class);
                startActivity(intent);
            }
        });

        fabElder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You Selected fabElder!!!!", Toast.LENGTH_LONG).show();

            }
        });

        fabgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You Selected fabgif!!!!", Toast.LENGTH_LONG).show();
            }
        });

//
//        //fragment cardview
//        tabLayout = (TabLayout)findViewById(R.id.mainTablayout);
//        viewPager = (ViewPager)findViewById(R.id.mainViewPager);
//        pagerAdapter = new PageAdapter(getSupportFragmentManager());
//
//        //Add Fragment here
//        pagerAdapter.AddFragment(new maintab1(),"梗圖模板");
//        pagerAdapter.AddFragment(new maintab2(),"長輩圖模板");
//
//        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
}