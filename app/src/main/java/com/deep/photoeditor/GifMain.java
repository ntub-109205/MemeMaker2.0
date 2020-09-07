package com.deep.photoeditor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.gifmake.VideoToGifActivity;


public class GifMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_main);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void createGif(View view) {
        startActivity(new Intent(this, GifMakeActivity.class));
    }
    public void createVideoGif(View view) {
        startActivity(new Intent(this, VideoToGifActivity.class));
    }
}
