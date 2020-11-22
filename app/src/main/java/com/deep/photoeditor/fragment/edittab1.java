package com.deep.photoeditor.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.RecyclerViewAdapter;
import com.deep.photoeditor.api;
import com.deep.photoeditor.hotTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class edittab1 extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<hotTemplate> lsthotTemplate;
    //api
    private static api callApi = new api();


    public edittab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recyclerview_adapter, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lsthotTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
//            callApi.post("http://140.131.115.99/api/template/show","category_id=1&time=1");
//            callApi.post("http://140.131.115.99/api/template/show","category_id=1");
            callApi.get("http://140.131.115.99/api/template/show/1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/template/show/1").trim();
        temp = temp.substring(13,(temp.length()-1));
        Log.d("runrun","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lsthotTemplate = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String filelink = jsonObject.getString("filelink");
                String name = jsonObject.getString("name");
                String author = jsonObject.getString("author");
                int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("runrun", "template_id:" + id + ", filelink:" + filelink + ", name:" + name + ", count:" + count);
                //產生cardView
                lsthotTemplate.add(new hotTemplate(name,filelink,author,count,id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
