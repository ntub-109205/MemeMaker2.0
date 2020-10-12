package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.deep.photoeditor.adpater.RecyclerViewAdapter;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_layoutImage;

import java.util.ArrayList;
import java.util.List;

public class editCombinePicture extends AppCompatActivity {
    private final static String TAG = "editCombinePicture";
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter_layoutImage recyclerViewAdapter;
    List<Bitmap> mLstPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_combine_picture);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLstPicture = new ArrayList<Bitmap>();
        mLstPicture.add(createBitmap(R.drawable.meme9));
        mLstPicture.add(createBitmap(R.drawable.meme11));
        mLstPicture.add(createBitmap(R.drawable.meme6));
        mLstPicture.add(createBitmap(R.drawable.meme10));
        mLstPicture.add(createBitmap(R.drawable.meme12));
        mLstPicture.add(createBitmap(R.drawable.meme3));


        recyclerView = findViewById(R.id.layoutRecyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter_layoutImage(this, mLstPicture);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private Bitmap createBitmap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        return bitmap;
    }
}
