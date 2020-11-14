package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.deep.photoeditor.activity.MainActivity;

import java.io.FileNotFoundException;

public class editShare extends AppCompatActivity {
    public Button btnNext;
    public ImageView mImageView;
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
