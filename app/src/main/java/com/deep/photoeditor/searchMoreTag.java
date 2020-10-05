package com.deep.photoeditor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_tagSearch;
import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.tagSearch;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class searchMoreTag extends AppCompatActivity {
    private static final String TAG = "searchMoreTag";

    private RecyclerView mRecyclerView;
    private List<tagSearch> mLstMoreTagSearch;
    RecyclerViewAdapter_tagSearch mRecyclerViewAdapter;
    //private static final int MAX = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_moretag);

        mLstMoreTagSearch = new ArrayList<>();
        mLstMoreTagSearch.add(new tagSearch("#三倍券"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券可以怎麼用"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券好少.."));
        mLstMoreTagSearch.add(new tagSearch("#三倍券優惠"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券期限"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券優惠店家哈哈哈哈哈"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券的限制"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券 哀"));
        mLstMoreTagSearch.add(new tagSearch("#三倍券..."));
        mLstMoreTagSearch.add(new tagSearch("#三倍券花完了"));

        Log.d(TAG,"INTO Recyclerview");
        mRecyclerView = findViewById(R.id.tagRecyclerview);
        mRecyclerViewAdapter = new RecyclerViewAdapter_tagSearch(this,mLstMoreTagSearch);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        mRecyclerView.setLayoutManager(flexboxLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
