package com.deep.photoeditor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PersonFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person, container, false);

        Button btnShowCollect = (Button) rootView.findViewById(R.id.btnCollect);
        Button btnShowWorks = (Button) rootView.findViewById(R.id.btnWorks);

        btnShowCollect.setOnClickListener(this);
        btnShowWorks.setOnClickListener(this);

        Fragment fragment = new PerCollectFragment();
        replaceFragment(fragment);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.btnCollect:
                fragment = new PerCollectFragment();
                replaceFragment(fragment);
                break;

            case R.id.btnWorks:
                fragment = new PerWorksFragment();
                replaceFragment(fragment);
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
