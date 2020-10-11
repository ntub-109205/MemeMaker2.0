package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.deep.photoeditor.adpater.RecyclerViewAdapter;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_layoutImage;

import java.util.ArrayList;
import java.util.List;

public class editCombinePicture extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter_layoutImage recyclerViewAdapter;
    List<layoutImage> lstPicture;
    private static variable variable = new variable();
    private static ArrayList<Bitmap> mArrayBitmap = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_combine_picture);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mArrayBitmap = variable.templatesBitmapGetter();
        int size = mArrayBitmap.size();
        lstPicture = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lstPicture.add(new layoutImage(R.drawable.meme9));
        }
        lstPicture.add(new layoutImage(R.drawable.meme9));
        lstPicture.add(new layoutImage(R.drawable.meme11));
        lstPicture.add(new layoutImage(R.drawable.meme6));
        lstPicture.add(new layoutImage(R.drawable.meme10));
        lstPicture.add(new layoutImage(R.drawable.meme12));
        lstPicture.add(new layoutImage(R.drawable.meme3));

        recyclerView = findViewById(R.id.layoutRecyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter_layoutImage(this,lstPicture);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}
