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
import com.deep.photoeditor.activity.PublicMemeInfoActivity;
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
public class MemeInfoFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeInfo;
    public ImageView imgNomeme;
    public int isNomeme=1;
    //api
    private static api callApi = new api();
    private static com.deep.photoeditor.variable variable = new variable();
    //temp id
    private String tempId;
    private String memeId;
    private String st;


    public MemeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recyclerview_with_image, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter__meme recyclerViewAdapter = new RecyclerViewAdapter__meme(getContext(),lstMemeInfo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        imgNomeme = (ImageView) v.findViewById(R.id.noResultImageView);
        if (isNomeme == 0) imgNomeme.setImageResource(R.drawable.no_meme);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //取得PublicMemeInfoActivity的tempId
        tempId = ((PublicMemeInfoActivity) context).returnTempIdString();
        memeId = ((PublicMemeInfoActivity) context).returnMemeIdString();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (memeUrl.substring(-3,3).equals("gif")) {
//            try {
//                st = callApi.get("http://140.131.115.99/api/meme/show/3?tag_name=宮崎駿");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else {
//            try {
//                st = callApi.get("http://140.131.115.99/api/template/meme/" +tempId+"?exclude="+memeId);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        try {
            st = callApi.get("http://140.131.115.99/api/template/meme/" +tempId+"?exclude="+memeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //留下array[]，其他切掉
        String temp = st.trim();
        temp = temp.substring(8,(temp.length()-1));
        if (temp.length()<10) isNomeme=0;
        Log.d("tempQQQQ","cut allready :"+ temp);
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
                //---把tag們分出來---//
                String tags = jsonObject.getString("tags");
                String[] items = tags.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
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
                Log.d("tempGoGo", "template_id:" + memeId + ", memefilelink:" + memeFilelink +"tempFilelink"+tempFilelink );
                //產生cardView
                lstMemeInfo.add(new PublicMeme(tempId,memeId,newtag,tempFilelink,memeFilelink,author,count,thumb));
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
