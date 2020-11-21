package com.deep.photoeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.deep.photoeditor.activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class editShare extends AppCompatActivity {
    private static final String TAG = "editShare";
    public Button btnNext;
    public ImageView mImageView;
    private ProgressDialog mProgressDialog;
    private static variable variable = new variable();

    public void init() throws FileNotFoundException {
        btnNext = (Button)findViewById(R.id.btnNext);
        mImageView = (ImageView)findViewById(R.id.imageView);
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(variable.memeUriGetter()));
        mImageView.setImageBitmap(bitmap);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent edit = new Intent(editShare.this, MainActivity.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_share);
        try {
            init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
