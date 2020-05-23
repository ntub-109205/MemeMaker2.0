package com.deep.photoeditor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.ColMemeActivity;
import com.deep.photoeditor.activity.ColMemeTmpActivity;

public class PerCollectFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_per_collect, container, false);
        ImageButton btnColMemTmp = (ImageButton) rootView.findViewById(R.id.btnColMemTmp);
        ImageButton btnColMem = (ImageButton) rootView.findViewById(R.id.btnColMem);
        btnColMemTmp.setOnClickListener(this);
        btnColMem.setOnClickListener(this);
        return rootView;
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
        }
    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.personrelativeLayout, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
