package com.deep.photoeditor.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.ColMemeActivity;
import com.deep.photoeditor.activity.WorEldActivity;
import com.deep.photoeditor.activity.WorEldTmpActivity;
import com.deep.photoeditor.activity.WorGifActivity;
import com.deep.photoeditor.activity.WorMemeActivity;
import com.deep.photoeditor.activity.WorMemeTmpActivity;
import com.deep.photoeditor.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class PerWorksFragment extends Fragment implements View.OnClickListener {
    private static api callApi = new api();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_per_works, container, false);
        ImageButton btnWorMemTmp = (ImageButton) rootView.findViewById(R.id.btnWorMemTmp);
        ImageButton btnWorMem = (ImageButton) rootView.findViewById(R.id.btnWorMem);
        ImageButton btnWorGif = (ImageButton)rootView.findViewById(R.id.btnWorGif);
        ImageButton btnWorEldTmp = (ImageButton)rootView.findViewById(R.id.btnWorEldTmp);
        ImageButton btnWorEld = (ImageButton)rootView.findViewById(R.id.btnWorEld);

        btnWorGif.setOnClickListener(this);
        btnWorMemTmp.setOnClickListener(this);
        btnWorMem.setOnClickListener(this);
        btnWorEldTmp.setOnClickListener(this);
        btnWorEld.setOnClickListener(this);

        try {
//            callApi.post("http://140.131.115.99/api/template/show","category_id=1&time=1");
//            callApi.post("http://140.131.115.99/api/template/show","category_id=1");
            callApi.get("http://140.131.115.99/api/profile/show/myWork");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("wormetemp",callApi.returnString());
        Log.d("workMem",callApi.get("http://140.131.115.99/api/profile/show/myWork"));
        //留下array[]，其他切掉
        String temp = callApi.get("http://140.131.115.99/api/profile/show/myWork").trim();
        //temp = temp.substring(13,(temp.length()-1));
        Log.d("workMem","cut allready :"+ temp);
        //把jsonArray塞進cardView的arrayList
        try {
            JSONArray array = new JSONArray(temp);
            //lstMemeMemeTemplate = new ArrayList<memeTemplate>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String count = jsonObject.getString("meme");
                String meme_id = jsonObject.getString("meme_id");
                String filelink = jsonObject.getString("filelink");
                String template_id = jsonObject.getString("template_id");
                String created_at = jsonObject.getString("created_at");
                //int count = Integer.parseInt(jsonObject.getString("count"));
                Log.d("workMem", "count:" + count + ", meme_id:" + meme_id + ", filelink:" + filelink + ", template_id:" + template_id+ ", created_at:" + created_at);
                //產生cardView
                //lstMemeMemeTemplate.add(new memeTemplate(id,name,filelink,author,count));
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
            case R.id.btnWorMemTmp:
                Intent intent = new Intent(getActivity(), WorMemeTmpActivity.class);
                startActivity(intent);
                break;
            case R.id.btnWorMem:
                Intent intent1 = new Intent(getActivity(), WorMemeActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnWorGif:
                Intent intent2 = new Intent(getActivity(), WorGifActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnWorEldTmp:
                Intent intent3 = new Intent(getActivity(), WorEldTmpActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnWorEld:
                Intent intent4 = new Intent(getActivity(), WorEldActivity.class);
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
