package com.deep.photoeditor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.MainActivity;
import com.deep.photoeditor.adpater.RecyclerViewAdapter__meme;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_tagSearch;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_tempHotSearch;
import com.deep.photoeditor.api;
import com.deep.photoeditor.editCombinePicture;
import com.deep.photoeditor.searchMoreTag;
import com.deep.photoeditor.tagHotSearch;
import com.deep.photoeditor.tagSearch;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String TAG = "searchFragment";
    View v;
    private RecyclerView myrecyclerview,temprecyclerview;
    private List<tagSearch> lstTagSearch;
    private List<tagHotSearch> lstTagHotSearch;
    private TextView mTxtMoreTag;
    private List tag;
    private int tagSize = 0;
    //api
    private static api callApi = new api();

    public SearchFragment() {
    }
    public static List cutString(String b) {
        int x = 0;
        ArrayList a=new ArrayList();
        a.add(b.indexOf("\""));//*第一個出現的索引位置
        while ((Integer)a.get(x)!= -1) {
            x+=1;
            a.add(b.indexOf("\"", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
        }
        a.remove(a.size()-1);
        ArrayList list=new ArrayList();
        for(int i=0;i<a.size();i = i +2) {
            list.add(b.substring((Integer)a.get(i)+1,(Integer)a.get(i+1)));
        }
        return list;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for tagSearch fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.tagRecyclerview);
        temprecyclerview = (RecyclerView) v.findViewById(R.id.tempRecyclerview);
        mTxtMoreTag = (TextView) v.findViewById(R.id.txtMoreTag);

        RecyclerViewAdapter_tagSearch recyclerViewAdapter = new RecyclerViewAdapter_tagSearch(getContext(),lstTagSearch);
        RecyclerViewAdapter_tempHotSearch tempRecyclerViewAdapter = new RecyclerViewAdapter_tempHotSearch(getContext(),lstTagHotSearch);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        StaggeredGridLayoutManager tempStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        myrecyclerview.setLayoutManager(flexboxLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        temprecyclerview.setLayoutManager(tempStaggeredGridLayoutManager);
        temprecyclerview.setAdapter(tempRecyclerViewAdapter);

        mTxtMoreTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent(getActivity(), searchMoreTag.class);
                startActivity(intent);
            }
        });

        return v;
    }
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Log.d("getTag" ,  decode(callApi.get("http://140.131.115.99/api/tag")));
            //cutString(decode(callApi.get("http://140.131.115.99/api/tag"))).get(4);
            tag = cutString(decode(callApi.get("http://140.131.115.99/api/tag")));
            tagSize = tag.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lstTagSearch = new ArrayList<>();
        for (int i = 3; i < tagSize; i+=3){
            lstTagSearch.add(new tagSearch("#" + tag.get(i).toString()));
            Log.d("getTag" , tag.get(i).toString());
        }


        //tempHotSearch
        lstTagHotSearch = new ArrayList<>();
        lstTagHotSearch.add(new tagHotSearch("曾之喬","https://www.wepeople.club/new-wepeople-upload/bb3886dc4b408776b51b1ba18ad3fb1e.jpg",R.drawable.one,"Angela",99));
        lstTagHotSearch.add(new tagHotSearch("Winnie with Pooh","https://www.trafalgar.com/real-word/wp-content/uploads/sites/3/2020/01/Screenshot-2020-01-16-at-15.15.54.png",R.drawable.two,"ZARK",98));
        lstTagHotSearch.add(new tagHotSearch("しばいぬ","https://assets.juksy.com/files/articles/90814/800x_100_w-5ce41f432bb5d.jpg",R.drawable.three,"Jessie",93));
        lstTagHotSearch.add(new tagHotSearch("蠟筆小新","https://cdn2.ettoday.net/images/4705/4705033.jpg",R.drawable.four,"Angela",88));
        lstTagHotSearch.add(new tagHotSearch("ねずこ","https://i.ytimg.com/vi/wqGSbiLC3aA/maxresdefault.jpg",R.drawable.five,"Vicky",85));
        lstTagHotSearch.add(new tagHotSearch("我妻善逸","https://i.ytimg.com/vi/U72gIwh9jr0/maxresdefault.jpg",R.drawable.six,"Kevin",81));
        lstTagHotSearch.add(new tagHotSearch("實習","https://specials-images.forbesimg.com/imageserve/1184595415/960x0.jpg?fit=scale",R.drawable.seven,"Lisa",76));
        lstTagHotSearch.add(new tagHotSearch("出國","https://aiesecntputw.weebly.com/uploads/5/7/5/8/57583935/6576321.jpg?460",R.drawable.eight,"Ning",71));
        lstTagHotSearch.add(new tagHotSearch("小琉球","https://yas.com.hk/blog/wp-content/uploads/2020/06/%E5%B0%8F%E7%90%89%E7%90%83_Blog.jpg",R.drawable.nine,"Jessie",65));
        lstTagHotSearch.add(new tagHotSearch("社畜","https://read.html5.qq.com/image?imgflag=7&q=100&imageUrl=http://s.cimg.163.com/i/by1.manhuayin.com/wp-content/uploads/2020/06/12/20200612_5ee31b077aab4.jpg_c.0x0.auto.jpg&src=share",R.drawable.ten,"Angela",50));
    }
}
