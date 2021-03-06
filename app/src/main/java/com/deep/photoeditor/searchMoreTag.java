package com.deep.photoeditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_tagSearch;
import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.tagSearch;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class searchMoreTag extends AppCompatActivity {
    private static final String TAG = "searchMoreTag";
    private TextView searchTag;

    private RecyclerView mRecyclerView;
    private List<tagSearch> mLstMoreTagSearch;
    RecyclerViewAdapter_tagSearch mRecyclerViewAdapter;
    private List tag;
    private int tagSize = 0;
    //api
    private static api callApi = new api();
    private static variable variable = new variable();
    private String searchName = "";
    //private static final int MAX = 6;
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
    public void showTag(String searchName) {
        if (searchName.equals("")){
            try {
                Log.d("getTag" ,  decode(callApi.get("http://140.131.115.99/api/tag")));
                //cutString(decode(callApi.get("http://140.131.115.99/api/tag"))).get(4);
                tag = cutString(decode(callApi.get("http://140.131.115.99/api/tag")));
                tagSize = tag.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLstMoreTagSearch = new ArrayList<>();
            for (int i = 3; i < tagSize; i+=3){
                mLstMoreTagSearch.add(new tagSearch("#" + tag.get(i).toString()));
                Log.d("getTag" , tag.get(i).toString());
            }
        }else{
            try {
                Log.d("getTag" ,  decode(callApi.get("http://140.131.115.99/api/tag?name="+searchName)));
                tag = cutString(decode(callApi.get("http://140.131.115.99/api/tag?name="+searchName)));
                tagSize = tag.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLstMoreTagSearch = new ArrayList<>();
            for (int i = 3; i < tagSize; i+=3){
                mLstMoreTagSearch.add(new tagSearch("#" + tag.get(i).toString()));
                Log.d("getTag" , tag.get(i).toString());
            }
        }

        Log.d(TAG,"INTO Recyclerview");
        mRecyclerView = findViewById(R.id.tagRecyclerview);
        mRecyclerViewAdapter = new RecyclerViewAdapter_tagSearch(this,mLstMoreTagSearch);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        mRecyclerView.setLayoutManager(flexboxLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_moretag);
        searchTag =(TextView) findViewById(R.id.search_input);
        searchName = variable.searchNameGetter();
        searchTag.setText(searchName);
        searchTag.setImeOptions(EditorInfo.IME_ACTION_SEND);

        showTag(searchName);
        searchTag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                    searchName = searchTag.getText().toString();
                    showTag(searchName);
                    return true;
                }
                return false;
            }
        });
    }
}
