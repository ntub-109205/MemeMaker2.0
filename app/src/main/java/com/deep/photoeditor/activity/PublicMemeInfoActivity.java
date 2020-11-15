package com.deep.photoeditor.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.EditImageActivity;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.editSetname;
import com.deep.photoeditor.fragment.MemeInfoFragment;
import com.deep.photoeditor.fragment.TempInfoFragment;
import com.deep.photoeditor.fragment.maintab2;
import com.deep.photoeditor.variable;
import com.felipecsl.gifimageview.library.GifImageView;
import com.wx.goodview.GoodView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PublicMemeInfoActivity extends AppCompatActivity {
    private static final String TAG = "PublicMemeInfoActivity";
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    private static variable variable = new variable();
    public Button btnDomeme;

    Dialog mDialog;
    //goodview
    GoodView mGoodView;
    //給相關梗圖用的tempid
    private static String tempId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicmeme_info);
        Log.d(TAG, "onCreate: started.");

        //goodview
        mGoodView = new GoodView(this);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("公開梗圖");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.tempInfoViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new MemeInfoFragment(),"相關梗圖");
        viewPager.setAdapter(pagerAdapter);

        //init Dialog
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_gif);

        getIncomingIntent();
        btnDomeme = (Button)findViewById(R.id.domeme);
        btnDomeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                variable.useMyImageSetter(Boolean.FALSE);
                Intent edit = new Intent(PublicMemeInfoActivity.this, EditImageActivity.class);
                startActivity(edit);
            }
        });
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("meme_url") && getIntent().hasExtra("hashTag") && getIntent().hasExtra("user_name") && getIntent().hasExtra("like_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            //模板id給相關梗圖fragment用
            tempId = getIntent().getStringExtra("temp_id");
            variable.templateIDSetter(tempId);

            //下面這些放到cardView
            String memeUrl = getIntent().getStringExtra("meme_url");
            Log.d("NotUseMyImage","memeUrl="+memeUrl);
            Log.d("NotUseMyImage","returnBitMap(memeUrl)="+getBitmap(memeUrl).toString());
            variable.templateImageSetter(getBitmap(memeUrl));
            String hashTag = getIntent().getStringExtra("hashTag");
            String userName = getIntent().getStringExtra("user_name");
            int likeSum = getIntent().getIntExtra("like_sum", 0);

            setInfo(memeUrl, hashTag, userName, likeSum);
            showImageDialog(memeUrl,hashTag);
            Log.d("pubmeme", "tempid="+returnTempIdString());
        }
    }
    private void setInfo(String memeUrl, String hashTag, String userName, int likeSum){
        Log.d(TAG, "setInfo: set memeUrl hashTag userName likeSum");

        //設置模板名
        TextView name = findViewById(R.id.hashTag);
        name.setText(hashTag);

        //設置模板圖片
        ImageView image = findViewById(R.id.memeImage);
        Glide.with(this)
                .asBitmap()
                .load(memeUrl)
                .into(image);
        //設置製作者名
        TextView user = findViewById(R.id.madeByUser);
        user.setText(userName);
        //設置熱門程度(被使用次數)
        TextView fireNum = findViewById(R.id.likeNum);
        fireNum.setText(String.valueOf(likeSum));


    }

    private void showImageDialog(String memeUrl,String hashTag) {
        ImageView image = findViewById(R.id.memeImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GifImageView dialogImage = (GifImageView) mDialog.findViewById(R.id.gif_view);
                TextView dialogTag = (TextView) mDialog.findViewById(R.id.gif_tag);
                ImageView diaglogClose = (ImageView) mDialog.findViewById(R.id.dialog_close);
                //show GIF by using Glide
                Glide.with(view).load(memeUrl).into(dialogImage);
                dialogTag.setText(hashTag);
                diaglogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
    }

    public static String returnTempIdString(){
        return tempId;
    }

    //onclick判斷在xml
    public void bookmark(View view) {
        ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
        mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
    public void like(View view) {
        ((ImageView) view).setImageResource(R.drawable.like_checked);
        mGoodView. setTextInfo("+1", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }


    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

}
