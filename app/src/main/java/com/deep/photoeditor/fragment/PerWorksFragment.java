package com.deep.photoeditor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.WorMemeTmpActivity;

public class PerWorksFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_per_works, container, false);
        ImageButton btnWorMemTmp = (ImageButton) rootView.findViewById(R.id.btnWorMemTmp);
        btnWorMemTmp.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.btnWorMemTmp:
                Intent intent = new Intent(getActivity(), WorMemeTmpActivity.class);
                startActivity(intent);
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
