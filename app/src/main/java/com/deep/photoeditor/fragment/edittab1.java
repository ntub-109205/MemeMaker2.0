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
import com.deep.photoeditor.memeTemplate;

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
        v = inflater.inflate(R.layout.fragment_edittab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.hotTemplate_recyclerView);
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
            callApi.post("http://140.131.115.99/api/template/show","category_id=1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("runrun",callApi.returnString());
        //留下array[]，其他切掉
        String temp = callApi.returnString().trim();
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
                lsthotTemplate.add(new hotTemplate(name,filelink,author,count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        lsthotTemplate = new ArrayList<>();
//        lsthotTemplate.add(new hotTemplate("Idot Dog","https://img.ltn.com.tw/Upload/partner/page/2019/03/30/190330-3313-1-phpUCF6ub.jpg","安潔拉",20));
//        lsthotTemplate.add(new hotTemplate("cute dog","https://scontent-yyz1-1.cdninstagram.com/v/t51.2885-15/sh0.08/e35/c0.180.1440.1440a/s640x640/95312078_952632711849290_8581521202673882401_n.jpg?_nc_ht=scontent-yyz1-1.cdninstagram.com&_nc_cat=102&_nc_ohc=grzPhbZX09oAX_DwdUd&oh=2ca5328cbd03458942253918a5843ef6&oe=5ED6ED2E","Vicky",20));
//        lsthotTemplate.add(new hotTemplate("cat","https://imgs.yamedia.tw/yamsales1966/20191009/9ff4fb0d-fa20-4086-9aea-9caf7ff973cb.jpg?h=250","Zark",25));
//        lsthotTemplate.add(new hotTemplate("Idot star","https://attach.setn.com/newsimages/2020/03/11/2450111-XXL.jpg","Taro",33));
//        lsthotTemplate.add(new hotTemplate("morning flower","https://3.bp.blogspot.com/-XWxh1r3GMPg/VwFh6DJfBqI/AAAAAAABV-Y/VGKLtGmC_hkN32eK-KjhpDeFo7jIwq1ZA/s1600/flowering%2BQuince%2B3.jpg","Jessie",25));
//        lsthotTemplate.add(new hotTemplate("my idol","https://www.wepeople.club/new-wepeople-upload/bb3886dc4b408776b51b1ba18ad3fb1e.jpg","Angela",15));

    }
}
