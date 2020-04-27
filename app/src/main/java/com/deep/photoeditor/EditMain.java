package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;


import android.net.Uri;
public class EditMain extends AppCompatActivity {
    public Button btnAddMeme;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    Uri uri;
    //button onClick to next page
    public void init(){
        btnAddMeme = (Button)findViewById(R.id.btnAddmeme);
        btnAddMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action                                            //會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片後返回本畫面

                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout)findViewById(R.id.editTablayout);
        viewPager = (ViewPager)findViewById(R.id.editViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new edittab1(),"熱門模板");
        pagerAdapter.AddFragment(new edittab2(),"我的模板");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        init();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            //取得圖檔的路徑位置
            uri = data.getData();
            //寫log
            Intent edit = new Intent();
            edit.setData(uri);
            edit.setClass(EditMain.this, editSetname.class);
            startActivity(edit);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
