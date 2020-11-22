package com.deep.photoeditor.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    public ImageView imgNomeme;
    public int isNomeme=1;
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
        v = inflater.inflate(R.layout.fragment_public_tab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.publicMeme_recyclerView);
        RecyclerViewAdapter__meme recyclerViewAdapter = new RecyclerViewAdapter__meme(getContext(),lstMemeInfo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        imgNomeme = (ImageView) v.findViewById(R.id.nomeme);
        if (isNomeme == 0) imgNomeme.setImageResource(R.drawable.no_meme);
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
//            callApi.post("http://140.131.115.99/api/template/meme","template_id="+tempId);
            callApi.get("http://140.131.115.99/api/template/meme/" +tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("temp",callApi.get("http://140.131.115.99/api/template/meme/" +tempId));
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/template/meme/" +tempId).trim();
        temp = temp.substring(8,(temp.length()-1));
        if (temp.length()<10) isNomeme=0;
        Log.d("temp","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstMemeInfo = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String memeId = jsonObject.getString("id");
                String memeFilelink = jsonObject.getString("meme_filelink");
                String tempFilelink = jsonObject.getString("template_filelink");
                String author = jsonObject.getString("author");
                int count = Integer.parseInt(jsonObject.getString("count"));
                int thumb = Integer.parseInt(jsonObject.getString("thumb"));
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

                Log.d("temp", "template_id:" + memeId + ", memefilelink:" + memeFilelink + "tags："+tags );
                //產生cardView
                lstMemeInfo.add(new PublicMeme(tempId,memeId,newtag,tempFilelink,memeFilelink,author,count,thumb));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
