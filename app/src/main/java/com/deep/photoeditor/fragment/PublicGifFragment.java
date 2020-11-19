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
import com.deep.photoeditor.adpater.RecyclerViewAdapter__gif;
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
public class PublicGifFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeMeme;
    public ImageView imgNomeme;
    public int isNomeme=1;
    //api
    private static api callApi = new api();

    public PublicGifFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_public_tab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.publicMeme_recyclerView);
        RecyclerViewAdapter__gif recyclerViewAdapter = new RecyclerViewAdapter__gif(getContext(), lstMemeMeme);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        imgNomeme = (ImageView) v.findViewById(R.id.nomeme);
        if (isNomeme == 0) imgNomeme.setImageResource(R.drawable.no_gif);

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
                temp = callApi.get("http://140.131.115.99/api/meme/show/3?tag_name="+tagName).trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                temp = callApi.get("http://140.131.115.99/api/meme/show/3").trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Log.d("memeinfo",callApi.get("http://140.131.115.99/api/meme/show/3"));
        //留下array[]，其他切掉
//        String temp = callApi.get("http://140.131.115.99/api/meme/show/3").trim();
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
                String memeFilelink = jsonObject.getString("meme_filelink");
                String tempFilelink = jsonObject.getString("template_filelink");
                String author = jsonObject.getString("author");
                String tempId = jsonObject.getString("template_id");
                int count = Integer.parseInt(jsonObject.getString("count"));
                int thumb = Integer.parseInt(jsonObject.getString("thumb"));
                Log.d("memeinfo", "template_id:" + tempId + ", author:" + author);

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
                lstMemeMeme.add(new PublicMeme(tempId,memeId,newtag,tempFilelink,memeFilelink,author,count,thumb));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
