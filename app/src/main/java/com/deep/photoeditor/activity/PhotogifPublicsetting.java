package com.deep.photoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.deep.photoeditor.R;
import com.felipecsl.gifimageview.library.GifImageView;

import java.util.Arrays;

public class PhotogifPublicsetting extends AppCompatActivity {

    private static final String TAG = "PhotoToGif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photogif_publicsetting);
        GifImageView gifView = (GifImageView) findViewById(R.id.imageView);

        Intent intent=getIntent();
        if(intent !=null){
            byte [] fileBytes=intent.getByteArrayExtra("GifBytes");
            Log.d(TAG, "fileBytes："+ Arrays.toString(fileBytes));
        }

//        gifView.setBytes(fileBytes);
//        gifView.startAnimation();
    }
}
