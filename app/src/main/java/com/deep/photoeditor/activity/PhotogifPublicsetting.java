package com.deep.photoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.deep.photoeditor.R;
import com.deep.photoeditor.editShare;
import com.felipecsl.gifimageview.library.GifImageView;

import java.util.Arrays;

public class PhotogifPublicsetting extends AppCompatActivity {

    private static final String TAG = "PhotoToGif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photogif_publicsetting);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GifImageView gifView = findViewById(R.id.imageView);
        Button btnNext = findViewById(R.id.btnNext);

        Intent intent=getIntent();
        if(intent !=null){
            byte [] fileBytes=intent.getByteArrayExtra("GifBytes");
            Log.d(TAG, "fileBytes："+ Arrays.toString(fileBytes));
            gifView.setBytes(fileBytes);
            gifView.startAnimation();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PhotogifPublicsetting.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
