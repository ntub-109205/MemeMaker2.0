package com.deep.photoeditor.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.ColEldActivity;
import com.deep.photoeditor.activity.ColEldTmpActivity;
import com.deep.photoeditor.activity.ColGifActivity;
import com.deep.photoeditor.activity.ColMemeActivity;
import com.deep.photoeditor.activity.ColMemeTmpActivity;
import com.deep.photoeditor.api;
import com.deep.photoeditor.memeTemplate;
import com.deep.photoeditor.variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class PerCollectFragment extends Fragment implements View.OnClickListener {
    private static api callApi = new api();
    private static com.deep.photoeditor.variable variable = new variable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_per_collect, container, false);
        ImageButton btnColMemTmp = (ImageButton) rootView.findViewById(R.id.btnColMemTmp);
        ImageButton btnColMem = (ImageButton) rootView.findViewById(R.id.btnColMem);
        ImageButton btnColGif = (ImageButton) rootView.findViewById(R.id.btnColGif);
        ImageButton btnColEldTmp = (ImageButton) rootView.findViewById(R.id.btnColEldTmp);
        ImageButton btnColEld = (ImageButton) rootView.findViewById(R.id.btnColEld);
//        TextView item_template = (TextView)rootView.findViewById(R.id.item_template);

        btnColGif.setOnClickListener(this);
        btnColMemTmp.setOnClickListener(this);
        btnColMem.setOnClickListener(this);
        btnColEldTmp.setOnClickListener(this);
        btnColEld.setOnClickListener(this);
        try {
            callApi.get("http://140.131.115.99/api/profile/show/saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("wormetemp",callApi.returnString());
        Log.d("collectMem",callApi.get("http://140.131.115.99/api/profile/show/saved"));
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/profile/show/saved").trim();
        //temp = temp.substring(16,(temp.length()-1));
        Log.d("collectMem","cut allready :"+ temp);
        try {
            JSONArray array = new JSONArray(temp);
            Log.d("collectMemfrag", "" + array.length());
        }catch(JSONException e) {
            e.printStackTrace();
        }
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            Log.d("collectMemfrag",""+array.length());
            //lstMemeMemeTemplate = new ArrayList<memeTemplate>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String count = jsonObject.getString("count");
                String id = jsonObject.getString("id");
                String filelink = jsonObject.getString("filelink");
//                JSONArray meme = jsonObject.getJSONArray("meme");
//                Log.d("collectMemfrag", ""+ meme );
               // String author = jsonObject.getString("author");
                //int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("collectMemfrag", "count:" + count + ", id:" + id + ", filelink:" + filelink );
                //產生cardView
                //lstMemeMemeTemplate.add(new memeTemplate(id,name,filelink,author,count));
                if (id=="1"){
                    btnColMem.setImageURI(Uri.parse(filelink));
//                    item_template.setText("("+count+")");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rootView;
    }
    private Drawable loadImageFromURL(String url){
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        }catch (Exception e) {
            //TODO handle error
            Log.i("loadingImg", e.toString());
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.btnColMemTmp:
                Intent intent = new Intent(getActivity(), ColMemeTmpActivity.class);
                variable.category_idSetter("1");
                startActivity(intent);
                break;
            case R.id.btnColMem:
                Intent intent1 = new Intent(getActivity(), ColMemeActivity.class);
                variable.category_idSetter("1");
                startActivity(intent1);
                break;
            case R.id.btnColGif:
                Intent intent2 = new Intent(getActivity(), ColGifActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnColEld:
                Intent intent3 = new Intent(getActivity(), ColEldActivity.class);
                variable.category_idSetter("2");
                startActivity(intent3);

               break;
            case R.id.btnColEldTmp:
                Intent intent4 = new Intent(getActivity(), ColEldTmpActivity.class);
                variable.category_idSetter("2");
                startActivity(intent4);
                break;
        }
    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.personrelativeLayout, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
