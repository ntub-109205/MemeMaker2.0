package com.deep.photoeditor.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.EditImageActivity;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.api;
import com.deep.photoeditor.editSetname;
import com.deep.photoeditor.fragment.MemeInfoFragment;
import com.deep.photoeditor.fragment.TempInfoFragment;
import com.deep.photoeditor.fragment.maintab2;
import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.variable;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.material.snackbar.Snackbar;
import com.wx.goodview.GoodView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.SaveSettings;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PublicMemeInfoActivity extends AppCompatActivity {
    private static final String TAG = "PublicMemeInfoActivity";
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    private static variable variable = new variable();
    private ProgressDialog mProgressDialog;
    //api
    private static api callApi = new api();
    public Button btnDomeme;

    Dialog mDialog;
    //goodview
    GoodView mGoodView;
    //給相關梗圖用的tempid
    private static String tempId;
    private static int thumb; //thumb值 判斷有沒有點過愛心
    private static int likeSum;
    private static String memeId;
    private static int saved; //是否被收藏

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

        String temp="";
        try {
            temp = callApi.get("http://140.131.115.99/api/meme/saved/"+memeId).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //{"saved":"0"}
        saved =Integer.parseInt(temp.substring(10,11));
        //設置收藏顏色
        ImageView bookMark = findViewById(R.id.bookmark);
        if (saved==0) {
            bookMark.setImageResource(R.drawable.bookmark);
        }else {
            bookMark.setImageResource(R.drawable.bookmark_checked);
        }

    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("meme_url") && getIntent().hasExtra("hashTag") && getIntent().hasExtra("user_name") && getIntent().hasExtra("like_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            //模板id給相關梗圖fragment用
            tempId = getIntent().getStringExtra("temp_id");
            memeId = getIntent().getStringExtra("memeId");
            variable.templateIDSetter(tempId);

            //下面這些放到cardView
            String memeUrl = getIntent().getStringExtra("meme_url");

            String tempUrl = getIntent().getStringExtra("temp_url");
            Log.d("NotUseMyImage","memeUrl="+tempUrl);
            //Log.d("NotUseMyImage","getBitmap(tempUrl)="+getBitmap(tempUrl).toString());
            //variable.memeImageSetter(getBitmap(memeUrl));

            variable.templateImageSetter(getBitmap(tempUrl));
            String hashTag = getIntent().getStringExtra("hashTag");
            String userName = getIntent().getStringExtra("user_name");
            likeSum = getIntent().getIntExtra("like_sum", 0);
            thumb = getIntent().getIntExtra("thumb", 0);
            memeId = getIntent().getStringExtra("memeId");

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
        Log.d(TAG, "set meme url: "+memeUrl);
        //設置製作者名
        TextView user = findViewById(R.id.madeByUser);
        user.setText(userName);
        //設置點讚次數
        TextView likeNum = findViewById(R.id.likeNum);
        likeNum.setText(String.valueOf(likeSum));
        //設置愛心顏色
        ImageView like = findViewById(R.id.like);
        if (thumb==0) {
            like.setImageResource(R.drawable.like_gray);
        }else {
            like.setImageResource(R.drawable.like_checked);
        }

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
    public static String returnMemeIdString(){
        return memeId;
    }

    //onclick判斷在xml
    public void bookmark(View view) {
        if (saved == 0) {
            ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
            mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
            mGoodView.show(view);
            saved=1;
        } else {
            ((ImageView) view).setImageResource(R.drawable.bookmark);
            mGoodView. setTextInfo("取消收藏", Color.parseColor("#999da4"), 12);
            mGoodView.show(view);
            saved=0;
        }
        try {
            callApi.post("http://140.131.115.99/api/meme/saved","meme_id=" + memeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void like(View view) {
        TextView likeNum = findViewById(R.id.likeNum);
        if(thumb == 0){
            ((ImageView) view).setImageResource(R.drawable.like_checked);
            mGoodView. setTextInfo("+1", Color.parseColor("#f66467"), 12);
            mGoodView.show(view);
            //改旁邊的顯示
            likeNum.setText(String.valueOf(likeSum +1));
            likeSum += 1;
            thumb = 1;
        } else {
            ((ImageView) view).setImageResource(R.drawable.like_gray);
            mGoodView. setTextInfo("-1", Color.parseColor("#999da4"), 12);
            mGoodView.show(view);
            //改旁邊的顯示
            likeNum.setText(String.valueOf(likeSum- 1));
            likeSum -= 1;
            thumb = 0;
        }

        try {
            callApi.post("http://140.131.115.99/api/meme/thumb","meme_id=" + memeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("thumb", callApi.returnString());
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

    public void shareLine(View view) {
        saveImageToGallery(this,variable.memeImageGetter());
        String scheme ="line://msg/image"+variable.memePathGetter();
        this.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(scheme)));
    }

    public void saveImage(View view) {
        showLoading("儲存中...");
        saveImageToGallery(this,variable.memeImageGetter());

//        String path = "MeMe Maker";
//        File dirFile = new File(Environment.getExternalStorageDirectory(),path);
//        if(!dirFile.exists()){//如果資料夾不存在
//            dirFile.mkdir();//建立資料夾
//        }
    }
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先儲存圖片
        String path = "MeMe Maker";
        File appDir = new File(Environment.getExternalStorageDirectory(), path);
        Log.d(TAG, "appDir: "+appDir);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        variable.memePathSetter(file.toString());
        Log.d(TAG, "路徑在哪: "+file);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            hideLoading();
            showSnackbar("儲存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showSnackbar("儲存失敗");
            hideLoading();
        } catch (IOException e) {
            e.printStackTrace();
            showSnackbar("儲存失敗");
            hideLoading();
        }

        // 其次把檔案插入到系統圖庫
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最後通知相簿更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    protected void showLoading(@NonNull String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showSnackbar(@NonNull String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

}
