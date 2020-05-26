package com.deep.photoeditor.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.TemplateInfoActivity;
import com.deep.photoeditor.adpater.RecyclerViewAdapter__meme;
import com.deep.photoeditor.api;
import com.deep.photoeditor.memeTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempInfoFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeInfo;
    //api
    private static api callApi = new api();
    //temp id
    private String tempId;


    public TempInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tempinfo, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.tempInfo_recyclerView);
        RecyclerViewAdapter__meme recyclerViewAdapter = new RecyclerViewAdapter__meme(getContext(),lstMemeInfo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //取得TemplateInfoActivity的tempId
        tempId = ((TemplateInfoActivity) context).returnTempIdString();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callApi.post("http://140.131.115.99/api/template/meme","template_id="+tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("temp",callApi.returnString());
        //留下array[]，其他切掉
        String temp = callApi.returnString().trim();
        temp = temp.substring(8,(temp.length()-1));
        Log.d("temp","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstMemeInfo = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String filelink = jsonObject.getString("filelink");
//                String name = jsonObject.getString("name");
//                String author = jsonObject.getString("author");
//                int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("temp", "template_id:" + id + ", filelink:" + filelink );
                //產生cardView
                lstMemeInfo.add(new PublicMeme("#",filelink,"製作者",0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        lstMemeInfo = new ArrayList<>();
//        lstMemeInfo.add(new PublicMeme("#居家隔離","https://www.urad.com.tw/wp-content/uploads/2015/08/giphy.gif","oUO",30));
//        lstMemeInfo.add(new PublicMeme("#牽手","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1584282936597.jpg","天才",20));
//        lstMemeInfo.add(new PublicMeme("#期末考","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1578233253954.jpg","龍澳天本添",9));
//        lstMemeInfo.add(new PublicMeme("#快去睡","https://memeprod.s3.ap-northeast-1.amazonaws.com/user-wtf/1577726914062.jpg","七彩瑪莉蘇",6));
//        lstTempInfo.add(new TempInfo("morning flower",R.drawable.meme5));
//        lstTempInfo.add(new TempInfo("my idol",R.drawable.meme6));

    }
}
