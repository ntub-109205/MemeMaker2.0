package com.deep.photoeditor.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.deep.photoeditor.R;
import com.deep.photoeditor.api;
import com.deep.photoeditor.fragment.PublicFragment;
import com.deep.photoeditor.variable;

public class SearchTagInfoActivity extends AppCompatActivity {
    private static com.deep.photoeditor.variable variable = new variable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tag_info);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("#"+variable.tagNameGetter());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PublicFragment()).commit();

    }
}