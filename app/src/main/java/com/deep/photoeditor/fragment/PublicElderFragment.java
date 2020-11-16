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

import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
//import com.deep.photoeditor.adpater.RecyclerViewAdapter__gif;
import com.deep.photoeditor.adpater.RecyclerViewAdapter__meme;
import com.deep.photoeditor.api;
import com.deep.photoeditor.variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicElderFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeMeme;
    public ImageView imgNomeme;
    public int isNomeme=1;
    //api
    private static api callApi = new api();

    public PublicElderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_public_tab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.publicMeme_recyclerView);
        RecyclerViewAdapter__meme recyclerViewAdapter = new RecyclerViewAdapter__meme(getContext(), lstMemeMeme);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        imgNomeme = (ImageView) v.findViewById(R.id.nomeme);
        if (isNomeme == 0) imgNomeme.setImageResource(R.drawable.nomeme);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tagName="";
        String temp ="";

        tagName = variable.tagNameGetter();
        if (tagName != null) {
            try {
                temp = callApi.get("http://140.131.115.99/api/meme/show/2?tag_name="+tagName).trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                temp = callApi.get("http://140.131.115.99/api/meme/show/2").trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        Log.d("memeinfo",callApi.get("http://140.131.115.99/api/meme/show/2"));
        //留下array[]，其他切掉
        temp = temp.substring(8,(temp.length()-1));
        if (temp.length()<10) isNomeme=0;
        Log.d("memeinfo","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstMemeMeme = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String memeId = jsonObject.getString("meme_id");
                String filelink = jsonObject.getString("filelink");
                String author = jsonObject.getString("author");
                String tempId = jsonObject.getString("template_id");
                int count = Integer.parseInt(jsonObject.getString("count"));
                int thumb = Integer.parseInt(jsonObject.getString("thumb"));
                Log.d("memeinfo", "template_id:" + tempId + ", filelink:" + filelink + ", author:" + author);

                //---把tag們分出來---//
                String tags = jsonObject.getString("tags");
                String[] items = tags.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                Log.d("tags", "tags:" + items);
                // items.length 是所有項目的個數
                String[] results = new String[items.length];
                // 將結果放入 results
                for (int j = 0; j < items.length; j++) {
                    results[j] = items[j].trim();
                }
                String newtag = "";
                for (String tag : results) {
                    tag = tag.replaceAll("\"", "");
                    Log.d("tags", "tags:" + tag + ", ");
                    newtag = newtag + "#" + tag;
                }
                //---tag們分完了---//

                //產生cardView
                lstMemeMeme.add(new PublicMeme(tempId,memeId,newtag,filelink,author,count,thumb));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        lstMemeMeme = new ArrayList<>();
//        lstMemeMeme.add(new PublicMeme("1","1","#早安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ30pqBnXi_TeFAw6HRq5Fuj7Gn87UDy5HlMyaor_hbrJwU6c4W&usqp=CAU","jessie",30,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#午安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdurKHXqRX6Sf7jZ5wA95BtGmF67r4RMrfcyHyyhwUTn9rdnSx&usqp=CAU","Geo",20,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#沒錢 #努力","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR7pZhRXjwG6fRsLIMKOuvpCVTQ1lkG_ppoEgUTf5PEUCaUokVy&usqp=CAU","跑跑跑的人",19,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#周末愉快","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ9u3tpzS-PsSYdZ9qJdi_qsnvOCdfzKTy5YfgpgYpkqAT4vOLp&usqp=CAU","好累",18,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#早安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ31J8IdHmgv-A5K7R1HS6DsRu8juT6uHdPcqLDbgn_NAd58Afa&usqp=CAU","Anc1233",17,0));
//        lstMemeMeme.add(new PublicMeme("1","1","#晚安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQgTnBFHVPfe260CJsG7UQ6uTopSzsQ47qOmMsVESUmMwPy8Og2&usqp=CAU","牙醫09",16,0));
//        lstMemeMeme.add(new PublicMeme("1","1","#認同","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTqJWtzg9D-9omdBRjwYEXHifBGMjcraz7ZwW_AQa29H7eF8pfH&usqp=CAU","江戶川先生",14,0));



    }
}
