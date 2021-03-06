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
import com.deep.photoeditor.adpater.RecyclerViewAdapter_colMemTmp;
import com.deep.photoeditor.api;
import com.deep.photoeditor.colMemTmp;
import com.deep.photoeditor.memeTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColMemeTmpFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<memeTemplate> lstTempInfo;
    private static api callApi = new api();
    private String st;
    public ImageView imgNomeme;
    public int isNomeme=1;
    public ColMemeTmpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recyclerview_with_image, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter_colMemTmp recyclerViewAdapter = new RecyclerViewAdapter_colMemTmp(getContext(),lstTempInfo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        imgNomeme = (ImageView) v.findViewById(R.id.noResultImageView);
        if (isNomeme == 0) imgNomeme.setImageResource(R.drawable.no_saved);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
//            callApi.post("http://140.131.115.99/api/template/show","category_id=1&time=1");
//            callApi.post("http://140.131.115.99/api/template/show","category_id=1");
            st =callApi.get("http://140.131.115.99/api/template/show/1?time=1&profile=saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("wormetemp",callApi.returnString());
//        Log.d("posttoget",callApi.get("http://140.131.115.99/api/template/show/1?time=1&profile=saved"));
        //留下array[]，其他切掉
        String temp = st.trim();
        temp = temp.substring(13,(temp.length()-1));
        if (temp.length()<10) isNomeme=0;
        Log.d("posttoget","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstTempInfo = new ArrayList<memeTemplate>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String filelink = jsonObject.getString("filelink");
                String name = jsonObject.getString("name");
                String author = jsonObject.getString("author");
                int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("wormemtemp", "template_id:" + id + ", filelink:" + filelink + ", name:" + name + ", count:" + count);
                //產生cardView
                lstTempInfo.add(new memeTemplate(id,name,filelink,author,count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
