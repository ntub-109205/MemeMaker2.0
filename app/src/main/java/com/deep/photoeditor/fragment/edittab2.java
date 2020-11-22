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
import com.deep.photoeditor.adpater.RecyclerViewAdapter_mineTemp;
import com.deep.photoeditor.api;
import com.deep.photoeditor.mineTemplate;
import com.deep.photoeditor.worMemTmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class edittab2 extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<worMemTmp> lstMemeMemeTemplate;
    private static api callApi = new api();

    public edittab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recyclerview_adapter, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter_mineTemp recyclerViewAdapter_mineTemp = new RecyclerViewAdapter_mineTemp(getContext(),lstMemeMemeTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter_mineTemp);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callApi.get("http://140.131.115.99/api/template/show/1?time=1&user=1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("posttoget",callApi.get("http://140.131.115.99/api/template/show/1?time=1&profile=myWork"));
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/template/show/1?time=1&profile=myWork").trim();
        temp = temp.substring(13,(temp.length()-1));
        Log.d("posttoget","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstMemeMemeTemplate = new ArrayList<worMemTmp>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String filelink = jsonObject.getString("filelink");
                String name = jsonObject.getString("name");
                String author = jsonObject.getString("author");
                int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("wormemtemp", "template_id:" + id + ", filelink:" + filelink + ", name:" + name + ", count:" + count);
                //產生cardView
                lstMemeMemeTemplate.add(new worMemTmp(id,name,filelink,author,count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
