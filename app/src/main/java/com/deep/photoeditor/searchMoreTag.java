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

public class searchMoreTag extends AppCompatActivity {
    private static final String TAG = "searchMoreTag";

    private RecyclerView recyclerView;
    private List<tagSearch> lstMoreTagSearch;
    RecyclerViewAdapter_tagSearch recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_moretag);

        lstMoreTagSearch = new ArrayList<>();
        lstMoreTagSearch.add(new tagSearch("#三倍券"));
        lstMoreTagSearch.add(new tagSearch("#三倍券可以怎麼用"));
        lstMoreTagSearch.add(new tagSearch("#三倍券好少.."));
        lstMoreTagSearch.add(new tagSearch("#三倍券優惠"));
        lstMoreTagSearch.add(new tagSearch("#三倍券期限"));
        lstMoreTagSearch.add(new tagSearch("#三倍券優惠店家"));
        lstMoreTagSearch.add(new tagSearch("#三倍券的限制"));
        lstMoreTagSearch.add(new tagSearch("#三倍券 哀"));
        lstMoreTagSearch.add(new tagSearch("#三倍券..."));
        lstMoreTagSearch.add(new tagSearch("#三倍券花完了"));

        Log.d(TAG,"INTO Recyclerview");
        recyclerView = findViewById(R.id.tagRecyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter_tagSearch(this,lstMoreTagSearch);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
