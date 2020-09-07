package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_combine_picture);

        lstPicture = new ArrayList<>();
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
