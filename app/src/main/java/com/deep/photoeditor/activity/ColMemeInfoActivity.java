package com.deep.photoeditor.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.R;
import com.wx.goodview.GoodView;

public class ColMemeInfoActivity extends AppCompatActivity {
    private static final String TAG = "ColMemeInfoActivity";
    //switch button
    Switch switchTemp;
    //button onClick to next page
    public Button btnEdit;
    Uri uri;
    //goodview
    GoodView mGoodView;
    public void init(){
//        btnEdit = (Button)findViewById(R.id.btnEditWorkMemTmpShare);
//
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                //new一個intent物件，並指定Activity切換的class
//                Intent edit = new Intent(WorkMemeTempInfoActivity.this,editShare.class);
//                edit.setData(uri);
//                //切換Activity
//                startActivity(edit);
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_col_meme_info);

        //goodview
        mGoodView = new GoodView(this);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //switch
        switchTemp = findViewById(R.id.tempSwitch);
      //  switchMeme = findViewById(R.id.memeSwitch);

        //將switch的狀態儲存到shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        //---switchTemp---
        switchTemp.setChecked(sharedPreferences.getBoolean("value",true));

        switchTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTemp.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchTemp.setChecked(true);
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchTemp.setChecked(false);
                }
            }
        });


        init();
        getIncomingIntent();
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("temp_url") && getIntent().hasExtra("temp_name") && getIntent().hasExtra("user_name") && getIntent().hasExtra("used_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String tempUrl = getIntent().getStringExtra("temp_url");
            String tempName = getIntent().getStringExtra("temp_name");
            String userName = getIntent().getStringExtra("user_name");
            int usedSum = getIntent().getIntExtra("used_sum", 0);

            setInfo(tempUrl, tempName, userName, usedSum);
        }
    }
    private void setInfo(String tempUrl, String tempName, String userName, int usedSum){
        Log.d("YY", "setInfo: set tempUrl tempName userName usedSum");

        //設置模板名
        TextView name = findViewById(R.id.workMemTmpSelName);
        name.setText(tempName);

        //設置模板圖片
        ImageView image = findViewById(R.id.imageView);
        Glide.with(this)
                .asBitmap()
                .load(tempUrl)
                .into(image);
//        //設置製作者名
//        TextView user = findViewById(R.id.madeByUser);
//        user.setText(userName);
        //設置熱門程度(被使用次數)
        TextView fireNum = findViewById(R.id.fireNum);
        fireNum.setText(String.valueOf(usedSum));


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    //onclick判斷在xml
    public void bookmark(View view) {
        ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
        mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
}
