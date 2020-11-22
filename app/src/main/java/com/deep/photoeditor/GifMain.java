package com.deep.photoeditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.adpater.RecyclerViewAdapter__gif;
import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.gifmake.VideoToGifActivity;
import com.felipecsl.gifimageview.library.GifImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GifMain extends AppCompatActivity {
    private static final String TAG = "GifMain";

    private RecyclerView recyclerView;
    private List<PublicMeme> lstMineGif;
    RecyclerViewAdapter__gif recyclerViewAdapter_myGif;
    private View.OnClickListener mMyGifListener;
    private View.OnClickListener mPublicGifListener;
    private List<WorPublicMeme> lstMemeMeme;
    private static api callApi = new api();
    private String st;
    public ImageView imgNomeme;
    public int isNomeme=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_main);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initListener();

        try {
//            callApi.post("http://140.131.115.99/api/meme/info","category_id=1");
            st = callApi.get("http://140.131.115.99/api/meme/show/3?profile=myWork&time=1");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("memeinfo",callApi.get("http://140.131.115.99/api/meme/show/1"));
        //留下array[]，其他切掉
        String temp = st.trim();
        temp = temp.substring(8,(temp.length()-1));
        if (temp.length()<10) isNomeme=0;
        Log.d("memeinfo","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            lstMineGif = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String memeId = jsonObject.getString("meme_id");
                String memeFilelink = jsonObject.getString("meme_filelink");
                String tempFilelink = jsonObject.getString("template_filelink");
                String author = jsonObject.getString("author");
                String tempId = jsonObject.getString("template_id");
                int count = Integer.parseInt(jsonObject.getString("count"));
                int thumb = Integer.parseInt(jsonObject.getString("thumb"));
                int shared = Integer.parseInt(jsonObject.getString("meme_share"));
                Log.d("memeinfo", "template_id:" + tempId  + ", author:" + author);

                //---把tag們分出來---//
                String tags = jsonObject.getString("tags");
                String[] items = tags.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

//                Log.d("tags", "tags:" + items);
                // items.length 是所有項目的個數
                String[] results = new String[items.length];
                // 將結果放入 results
                for (int j = 0; j < items.length; j++) {
                    results[j] = items[j].trim();
                }
                String newtag = "";
                for (String tag : results) {
                    tag = tag.replaceAll("\"", "");
                    Log.d("tags", "tags:" + tag);
                    newtag = "#" + tag;
                }
                //---tag們分完了---//

                //產生cardView
                lstMineGif.add(new PublicMeme(tempId, memeId, newtag, tempFilelink,memeFilelink, author, count, thumb));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.myGifRecyclerview);
        recyclerViewAdapter_myGif = new RecyclerViewAdapter__gif(this, lstMineGif);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter_myGif);


    }

    //由圖片做Gif
    public void createGif(View view) {
        startActivity(new Intent(this, GifMakeActivity.class));
    }
    //由影片做Gif
    public void createVideoGif(View view) { startActivity(new Intent(this,VideoToGifActivity.class)); }

    private void initListener() {
        mMyGifListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + mData.get(position));
            }
        };

        mPublicGifListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + mData.get(position));
            }
        };
    }


}
