package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.Switch;

import java.io.FileNotFoundException;

public class editPublicsetting extends AppCompatActivity {
    Switch switchMeme;
    public Button btnNext;
    private static variable variable = new variable();
    public String memeShare = "1";
    Uri memeUri;

    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);
        ContentResolver cr = this.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(memeUri));
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }
        //switch
        switchMeme = findViewById(R.id.memeSwitch);
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

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
                    memeShare = "1";
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchMeme.setChecked(false);
                    memeShare = "0";
                }
                variable.memeShareSetter(memeShare);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent edit = new Intent(editPublicsetting.this,editShare.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_publicsetting);
        memeUri = variable.memeUriGetter();
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
