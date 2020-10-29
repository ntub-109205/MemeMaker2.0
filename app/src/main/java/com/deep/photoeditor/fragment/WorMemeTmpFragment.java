package com.deep.photoeditor.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_worMemTmp;
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
public class WorMemeTmpFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<memeTemplate> lstMemeMemeTemplate;
    private static api callApi = new api();

    public WorMemeTmpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_wor_meme_tmp, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.wor_mem_tmp_recyclerView);
        RecyclerViewAdapter_worMemTmp recyclerViewAdapter = new RecyclerViewAdapter_worMemTmp(getContext(),lstMemeMemeTemplate);
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
            callApi.get("http://140.131.115.99/api/template/show/1?time=1&user=1");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("runrun",callApi.returnString());
        Log.d("posttoget",callApi.get("http://140.131.115.99/api/template/show/1?time=1&user=1"));
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/template/show/1?time=1&user=1").trim();
        temp = temp.substring(13,(temp.length()-1));
        Log.d("posttoget","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstMemeMemeTemplate = new ArrayList<memeTemplate>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String filelink = jsonObject.getString("filelink");
                String name = jsonObject.getString("name");
                String author = jsonObject.getString("author");
                int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("runrun", "template_id:" + id + ", filelink:" + filelink + ", name:" + name + ", count:" + count);
                //產生cardView
                lstMemeMemeTemplate.add(new memeTemplate(id,name,filelink,author,count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        lstTempInfo = new ArrayList<>();
//        lstTempInfo.add(new worMemTmp("(QQ)","https://cultivatememe.moe/img/article/doge1.jpg","潔西卡",20));
//        lstTempInfo.add(new worMemTmp("我就爛","data:image/jpeg;base64,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","jessie run",15));
//        lstTempInfo.add(new worMemTmp("鸚鵡兄弟互看","https://memeprod.sgp1.digitaloceanspaces.com/user-template-thumbnail/1d27f077c546f31f6a656d7669f4eb2f.jpg", "Angela", 10));
//        lstTempInfo.add(new worMemTmp("黑人抬棺","https://pgw.udn.com.tw/gw/photo.php?u=https://uc.udn.com.tw/photo/2020/04/07/99/7701541.png&x=0&y=0&sw=0&sh=0&sl=W&fw=1050", "安潔拉",5));
////        lstMemeMemeTemplate.add(new memeTemplate("morning flower",R.drawable.meme5));
////        lstTempInfo.add(new TempInfo("morning flower",R.drawable.meme5));
////        lstTempInfo.add(new TempInfo("my idol",R.drawable.meme6));

    }
}
