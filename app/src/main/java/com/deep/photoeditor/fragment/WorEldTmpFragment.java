package com.deep.photoeditor.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_worMemTmp;
import com.deep.photoeditor.api;
import com.deep.photoeditor.worMemTmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorEldTmpFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<worMemTmp> lstMemeMemeTemplate;
    private static api callApi = new api();
    private String st;
    public ImageView imgNomeme;
    public int isNomeme=1;
    public WorEldTmpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recyclerview_with_image, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter_worMemTmp recyclerViewAdapter = new RecyclerViewAdapter_worMemTmp(getContext(),lstMemeMemeTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        imgNomeme = (ImageView) v.findViewById(R.id.noResultImageView);
        if (isNomeme == 0) imgNomeme.setImageResource(R.drawable.no_work);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            st =callApi.get("http://140.131.115.99/api/template/show/2?time=1&profile=myWork");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("posttoget",callApi.get("http://140.131.115.99/api/template/show/2?time=1&profile=myWork"));
        //留下array[]，其他切掉
        String temp = st.trim();
        temp = temp.substring(13,(temp.length()-1));
        if (temp.length()<10) isNomeme=0;
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
                int shared = Integer.parseInt(jsonObject.getString("share"));
                Log.d("wormemtemp", "template_id:" + id + ", filelink:" + filelink + ", name:" + name + ", count:" + count);
                //產生cardView
                lstMemeMemeTemplate.add(new worMemTmp(id,name,filelink,author,count,shared));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
