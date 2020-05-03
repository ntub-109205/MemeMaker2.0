package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.FileNotFoundException;

public class editPublicsetting extends AppCompatActivity {
    //switch button
    Switch switchTemp;
    Switch switchMeme;
    //button onClick to next page
    public Button btnNext;
    Uri uri;

    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(editPublicsetting.this,editShare.class);
                edit.setData(uri);
                //切換Activity
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_publicsetting);
        uri = getIntent().getData();
        Log.e("uri", uri.toString());
        //抽象資料的接口
        ContentResolver cr = this.getContentResolver();
        try {
            //由抽象資料接口轉換圖檔路徑為Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            //取得圖片控制項ImageView
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            // 將Bitmap設定到ImageView
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //switch
        switchTemp = findViewById(R.id.tempSwitch);
        switchMeme = findViewById(R.id.memeSwitch);

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

        //---switchMeme---
        switchMeme.setChecked(sharedPreferences.getBoolean("value",true));

        switchMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchMeme.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchMeme.setChecked(true);
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchMeme.setChecked(false);
                }
            }
        });

        init();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
