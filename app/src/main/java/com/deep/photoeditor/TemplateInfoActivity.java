package com.deep.photoeditor;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

public class TemplateInfoActivity extends AppCompatActivity {
    private static final String TAG = "TemplateInfoActivity";
//    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_info);
        Log.d(TAG, "onCreate: started.");

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        tabLayout = (TabLayout)findViewById(R.id.editTablayout);
        viewPager = (ViewPager)findViewById(R.id.tempInfoViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new TempInfoFragment(),"相關梗圖");
//        pagerAdapter.AddFragment(new edittab2(),"我的模板");

        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        getIncomingIntent();
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("temp_url") && getIntent().hasExtra("temp_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String tempUrl = getIntent().getStringExtra("temp_url");
            String tempName = getIntent().getStringExtra("temp_name");

            setImage(tempUrl, tempName);
        }
    }
    private void setImage(String tempUrl, String tempName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.tempName);
        name.setText(tempName);

        ImageView image = findViewById(R.id.tempImage);
        Glide.with(this)
                .asBitmap()
                .load(tempUrl)
                .into(image);
    }
}
