package com.deep.photoeditor.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.R;
import com.deep.photoeditor.variable;
import com.felipecsl.gifimageview.library.GifImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class GifShareActivity extends AppCompatActivity {
    public Button btnNext;
    public GifImageView gifView;
    private static com.deep.photoeditor.variable variable = new variable();

    public void init() throws FileNotFoundException {
        btnNext = (Button)findViewById(R.id.btnNext);
//        mImageView = (ImageView)findViewById(R.id.imageView);
//        ContentResolver cr = this.getContentResolver();
//        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(variable.memeUriGetter()));
//        mImageView.setImageBitmap(bitmap);
        gifView = findViewById(R.id.imageView);
        //顯示gif
        if(variable.getGifByteArray()==null){
            gifView.setImageURI(Uri.parse(variable.getGifPath()));
        }else{
            byte[] fileBytes=variable.getGifByteArray();
            gifView.setBytes(fileBytes);
            gifView.startAnimation();
        }



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent edit = new Intent(GifShareActivity.this, MainActivity.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_share);
        try {
            init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
