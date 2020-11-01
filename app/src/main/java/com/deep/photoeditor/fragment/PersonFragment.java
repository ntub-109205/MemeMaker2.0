package com.deep.photoeditor.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;
import com.deep.photoeditor.api;

public class PersonFragment extends Fragment implements View.OnClickListener {
    //api
    private static api callApi = new api();
    View rootView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person, container, false);

        Button btnShowCollect = (Button) rootView.findViewById(R.id.btnCollect);
        Button btnShowWorks = (Button) rootView.findViewById(R.id.btnWorks);
        TextView txtPerson = rootView.findViewById(R.id.txtPerson);
        try {
            callApi.get("http://140.131.115.99/api/profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("temp",callApi.get("http://140.131.115.99/api/profile"));
        //留下array[]，其他切掉
        String userName = callApi.get("http://140.131.115.99/api/profile").trim();
        userName = userName.substring(8,(userName.length()-1));
        Log.d("temp","cut allready :"+ userName);
        txtPerson.setText("Hello, " + userName.replaceAll("\"",""));
       // String person = callApi.get("http://140.131.115.99/api/profile");
        btnShowCollect.setOnClickListener(this);
        btnShowWorks.setOnClickListener(this);

        Fragment fragment = new PerCollectFragment();
        replaceFragment(fragment);
        btnShowCollect.setBackground(getResources().getDrawable(R.drawable.button_press));
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        Button btnShowCollect = (Button) rootView.findViewById(R.id.btnCollect);
        Button btnShowWorks = (Button) rootView.findViewById(R.id.btnWorks);
        switch (view.getId()) {
            case R.id.btnCollect:
                fragment = new PerCollectFragment();
                replaceFragment(fragment);
                btnShowCollect.setBackground(getResources().getDrawable(R.drawable.button_press));
                btnShowCollect.setTextColor(Color.parseColor("#FFFFFF"));
                btnShowWorks.setBackground(getResources().getDrawable(R.drawable.bgrectangle));
                btnShowWorks.setTextColor(Color.parseColor("#000000"));
                break;

            case R.id.btnWorks:
                fragment = new PerWorksFragment();
                replaceFragment(fragment);
                btnShowCollect.setBackground(getResources().getDrawable(R.drawable.bgrectangle));
                btnShowCollect.setTextColor(Color.parseColor("#000000"));
                btnShowWorks.setBackground(getResources().getDrawable(R.drawable.button_press));
                btnShowWorks.setTextColor(Color.parseColor("#FFFFFF"));
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
