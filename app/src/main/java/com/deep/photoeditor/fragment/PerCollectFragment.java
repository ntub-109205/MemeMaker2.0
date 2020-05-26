package com.deep.photoeditor.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.ColEldActivity;
import com.deep.photoeditor.activity.ColEldTmpActivity;
import com.deep.photoeditor.activity.ColGifActivity;
import com.deep.photoeditor.activity.ColMemeActivity;
import com.deep.photoeditor.activity.ColMemeTmpActivity;

import java.io.InputStream;
import java.net.URL;

public class PerCollectFragment extends Fragment implements View.OnClickListener {
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

        btnColGif.setImageDrawable(loadImageFromURL("https://www.urad.com.tw/wp-content/uploads/2015/08/giphy.gif"));

        btnColGif.setOnClickListener(this);
        btnColMemTmp.setOnClickListener(this);
        btnColMem.setOnClickListener(this);
        btnColEldTmp.setOnClickListener(this);
        btnColEld.setOnClickListener(this);
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
                startActivity(intent);
                break;
            case R.id.btnColMem:
                Intent intent1 = new Intent(getActivity(), ColMemeActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnColGif:
                Intent intent2 = new Intent(getActivity(), ColGifActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnColEld:
                Intent intent3 = new Intent(getActivity(), ColEldActivity.class);
                startActivity(intent3);

               break;
            case R.id.btnColEldTmp:
                Intent intent4 = new Intent(getActivity(), ColEldTmpActivity.class);
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
