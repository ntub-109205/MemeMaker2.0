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

import java.io.InputStream;
import java.net.URL;

public class PerWorksFragment extends Fragment implements View.OnClickListener {
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

        btnWorGif.setImageDrawable(loadImageFromURL("https://5b0988e595225.cdn.sohucs.com/images/20191020/0e9badc14cf342de81a4b61e61e85a26.gif"));

        btnWorGif.setOnClickListener(this);
        btnWorMemTmp.setOnClickListener(this);
        btnWorMem.setOnClickListener(this);
        btnWorEldTmp.setOnClickListener(this);
        btnWorEld.setOnClickListener(this);
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
