package com.deep.photoeditor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
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
import com.deep.photoeditor.variable;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class SearchFragment extends Fragment {
    private static final String TAG = "searchFragment";
    View v;
    private RecyclerView myrecyclerview,temprecyclerview;
    private List<tagSearch> lstTagSearch;
    private List<tagHotSearch> lstTagHotSearch;
    private TextView mTxtMoreTag;
    private TextView hotTag;
    private TextView hotTemplate;
    private ImageView noTagSearchImage;
    private ImageView noTempSearchImage;
    private static variable variable = new variable();

    private TextView searchTag;

    private List tag;

    private String temp;

    private String searchName = "";
    private int tagSize = 0;
    private int tempSize = 0;
    private int TagSwitch = 0;
    private int TempSwitch = 0;
    private int TempSearchSwitch = 0;


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
        searchTag = (TextView) v.findViewById(R.id.search_input);
        hotTag = (TextView) v.findViewById(R.id.textView8);
        hotTemplate = (TextView) v.findViewById(R.id.textView9);

        noTagSearchImage = (ImageView) v.findViewById(R.id.noTagResultImageView);
        noTempSearchImage = (ImageView) v.findViewById(R.id.noTempResultImageView);

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

        searchTag.setImeOptions(EditorInfo.IME_ACTION_SEND);
        searchTag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                    hotTag.setText("相關標籤搜尋結果");
                    hotTemplate.setText("相關模板搜尋結果");
                    searchName = searchTag.getText().toString();
                    try {
                        Log.d("getTag" ,  decode(callApi.get("http://140.131.115.99/api/tag?limit=10&name="+searchName)));
                        //cutString(decode(callApi.get("http://140.131.115.99/api/tag"))).get(4);
                        tag = cutString(decode(callApi.get("http://140.131.115.99/api/tag?limit=10&name="+searchName)));
                        tagSize = tag.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lstTagSearch = new ArrayList<>();
                    if (tagSize<2){
                        noTagSearchImage.setImageResource(R.drawable.nosearchresult);
                    }else {
                        noTagSearchImage.setImageBitmap(null);
                        for (int i = 3; i < tagSize; i += 3) {
                            lstTagSearch.add(new tagSearch("#" + tag.get(i).toString()));
                            Log.d("getTag", tag.get(i).toString());
                        }
                    }
                    //---------------------------------------------------------------------------
                    //----------------------------顯示搜尋模板模板----------------------------------
                    try {
                        temp = decode(callApi.get("http://140.131.115.99/api/template/show/0?limit=10&name="+searchName)).trim();
                        Log.d("searchTemp" ,temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("NoSearchResult", "temp.length()="+temp.length());

                    if (temp.length()<17){
                        //無相關模板搜尋結果
                        noTempSearchImage.setImageResource(R.drawable.template);
//                        if ( TempSearchSwitch == 1) {
                            int size = lstTagHotSearch.size();
                            for (int i = 0; i < size; i++) {
                                lstTagHotSearch.remove(0);
                            }
//                        }else{
//                            int size = lstTagHotSearch.size();
//                            Log.d("GGGGGGGGGGGGGGG", "temp.length()="+size);
//
//                            for (int i = 0; i < size-2; i++) {
//                                Log.d("GGGGGGGGGGGGGGG", "i="+i);
//
//                                lstTagHotSearch.remove(i);
//                            }
//
//                        }

                    }else {
                        TempSearchSwitch = 1;
                        noTempSearchImage.setImageBitmap(null);
                        temp = temp.substring(13, (temp.length() - 1));
                        try {
                            JSONArray array = new JSONArray(temp);
                            lstTagHotSearch = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String tempId = jsonObject.getString("id");
                                String filelink = jsonObject.getString("filelink");
                                String name = jsonObject.getString("name");
                                String author = jsonObject.getString("author");
                                int count = Integer.parseInt(jsonObject.getString("count"));
                                int a = 0;
                                switch (i){
                                    case 0:
                                        a =  R.drawable.one;
                                        break;
                                    case 1:
                                        a =  R.drawable.two;
                                        break;
                                    case 2:
                                        a =  R.drawable.three;
                                        break;
                                    case 3:
                                        a =  R.drawable.four;
                                        break;
                                    case 4:
                                        a =  R.drawable.five;
                                        break;
                                    case 5:
                                        a =  R.drawable.six;
                                        break;
                                    case 6:
                                        a =  R.drawable.seven;
                                        break;
                                    case 7:
                                        a =  R.drawable.eight;
                                        break;
                                    case 8:
                                        a =  R.drawable.nine;
                                        break;
                                    case 9:
                                        a =  R.drawable.ten;
                                        break;
                                    default:
                                }
                                Log.d("searchTemp", "tempId="+tempId);
                                Log.d("searchTemp", "name="+name);
                                Log.d("searchTemp", "filelink="+filelink);
                                Log.d("searchTemp", "author="+author);
                                Log.d("searchTemp", "count="+count);

                                lstTagHotSearch.add(new tagHotSearch(tempId, name, filelink, a, author, count));
//                                RecyclerViewAdapter_tempHotSearch tempRecyclerViewAdapter = new RecyclerViewAdapter_tempHotSearch(getContext(),lstTagHotSearch);
//                                temprecyclerview.setAdapter(tempRecyclerViewAdapter);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //---------------------------------------------------------------------------------
                    RecyclerViewAdapter_tempHotSearch tempRecyclerViewAdapter = new RecyclerViewAdapter_tempHotSearch(getContext(),lstTagHotSearch);
                    temprecyclerview.setAdapter(tempRecyclerViewAdapter);

                    RecyclerViewAdapter_tagSearch recyclerViewAdapter = new RecyclerViewAdapter_tagSearch(getContext(),lstTagSearch);

                    FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
                    //StaggeredGridLayoutManager tempStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                    flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
                    flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

                    myrecyclerview.setLayoutManager(flexboxLayoutManager);
                    myrecyclerview.setAdapter(recyclerViewAdapter);
                  //  temprecyclerview.setLayoutManager(tempStaggeredGridLayoutManager);

                    return true;
                }
                return false;
            }
        });
        mTxtMoreTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                variable.searchNameSetter(searchName);
                Intent intent = new Intent(getActivity(), searchMoreTag.class);
                startActivity(intent);
            }
        });

        return v;
    }
    public static String decode(String unicodeStr) {//轉換萬國碼
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
        //--------------------------------------顯示top10 Tag----------------------------------------------
        try {
            Log.d("getTag" ,  decode(callApi.get("http://140.131.115.99/api/tag?limit=10")));
            //cutString(decode(callApi.get("http://140.131.115.99/api/tag"))).get(4);
            tag = cutString(decode(callApi.get("http://140.131.115.99/api/tag?limit=10")));
            tagSize = tag.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tagSize<2){
            noTagSearchImage.setImageResource(R.drawable.nosearchresult);
            TagSwitch=1;
        }else {
            if (TagSwitch==1){
                noTagSearchImage.setImageBitmap(null);
            }
            lstTagSearch = new ArrayList<>();
            for (int i = 3; i < tagSize; i += 3) {
                lstTagSearch.add(new tagSearch("#" + tag.get(i).toString()));
                Log.d("getTag", tag.get(i).toString());
            }
        }
        //---------------------------------------------------------------------------
        //----------------------------顯示top10 模板----------------------------------
        try {
           // temp = decode(callApi.get("http://140.131.115.99/api/template/show/0?limit=10")).trim();
            temp = decode(callApi.get("http://140.131.115.99/api/template/show/0?limit=10")).trim();

        } catch (Exception e) {

            e.printStackTrace();
        }
        if (temp.length()<20){
            //無相關模板搜尋結果
            noTempSearchImage.setImageResource(R.drawable.template);
            TempSwitch=1;
            Log.d("NoSearchResult", "進來了");

        }else {
            if (TempSwitch==1){
                noTempSearchImage.setImageBitmap(null);
            }
            temp = temp.substring(13, (temp.length() - 1));
            try {
                JSONArray array = new JSONArray(temp);
                lstTagHotSearch = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String tempId = jsonObject.getString("id");
                    String filelink = jsonObject.getString("filelink");
                    String name = jsonObject.getString("name");
                    String author = jsonObject.getString("author");
                    int count = Integer.parseInt(jsonObject.getString("count"));
                    int a = 0;
                    switch (i){
                        case 0:
                            a =  R.drawable.one;
                            break;
                        case 1:
                            a =  R.drawable.two;
                            break;
                        case 2:
                            a =  R.drawable.three;
                            break;
                        case 3:
                            a =  R.drawable.four;
                            break;
                        case 4:
                            a =  R.drawable.five;
                            break;
                        case 5:
                            a =  R.drawable.six;
                            break;
                        case 6:
                            a =  R.drawable.seven;
                            break;
                        case 7:
                            a =  R.drawable.eight;
                            break;
                        case 8:
                            a =  R.drawable.nine;
                            break;
                        case 9:
                            a =  R.drawable.ten;
                            break;
                        default:
                    }
                    lstTagHotSearch.add(new tagHotSearch(tempId, name, filelink, a, author, count));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            //---------------------------------------------------------------------------------
    }


}
