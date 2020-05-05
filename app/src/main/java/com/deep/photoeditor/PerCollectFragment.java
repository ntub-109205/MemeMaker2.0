package com.deep.photoeditor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PerCollectFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_per_collect, container, false);
        Button btnColMemTmp = (Button) rootView.findViewById(R.id.btnColMemTmp);
        btnColMemTmp.setOnClickListener(this);
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
        }
    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.personrelativeLayout, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
