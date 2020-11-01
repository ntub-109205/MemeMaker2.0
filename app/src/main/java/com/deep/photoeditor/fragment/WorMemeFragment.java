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

import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_worMem;
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
public class WorMemeFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeMeme;
    private static api callApi = new api();

    public WorMemeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_wor_meme, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.wor_mem_recyclerView);
        RecyclerViewAdapter_worMem recyclerViewAdapter = new RecyclerViewAdapter_worMem(getContext(),lstMemeMeme);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            callApi.post("http://140.131.115.99/api/meme/info","category_id=1");
            callApi.get("http://140.131.115.99/api/meme/show/1?user=1&time=1");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("memeinfo",callApi.get("http://140.131.115.99/api/meme/show/1"));
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/meme/show/1?user=1&time=1").trim();
        temp = temp.substring(8,(temp.length()-1));
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
//        lstMemeMeme.add(new PublicMeme("1","1","#Dog #隔離","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1585196981888.jpg","潔西卡",35,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#反叛的魯路修","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589009676654.jpg","Geo",20,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#三國殺","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589044855818.jpg","跑跑跑的人",19,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#鼠定了你","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589086291871.jpg","好累",18,1));
//        lstMemeMeme.add(new PublicMeme("1","1","#某系列主機遊戲","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589041392638.jpg","Anc1233",17,0));
//        lstMemeMeme.add(new PublicMeme("1","1","#PM日常","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589041053097.jpg","牙醫09",16,0));
//        lstMemeMeme.add(new PublicMeme("1","1","#歷史","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589034990886.jpg","江戶川先生",14,0));
//

    }
}
