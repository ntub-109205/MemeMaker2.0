package com.deep.photoeditor.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deep.photoeditor.R;

public class PersonFragment extends Fragment implements View.OnClickListener {
    View rootView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person, container, false);

        Button btnShowCollect = (Button) rootView.findViewById(R.id.btnCollect);
        Button btnShowWorks = (Button) rootView.findViewById(R.id.btnWorks);

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
                btnShowWorks.setBackground(getResources().getDrawable(R.drawable.button_default));
                break;

            case R.id.btnWorks:
                fragment = new PerWorksFragment();
                replaceFragment(fragment);
                btnShowCollect.setBackground(getResources().getDrawable(R.drawable.button_default));
                btnShowWorks.setBackground(getResources().getDrawable(R.drawable.button_press));
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
