package com.deep.photoeditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ColMemTmpFragment extends Fragment {
    View myFragment;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_col_meme_tmp, container, false);
        viewPager = myFragment.findViewById(R.id.col_mem_tmp_viewpager);

      //  pagerAdapter = new PageAdapter(getSupportFragmentManager());

//        //Add Fragment here
//        pagerAdapter.AddFragment(new TempInfoFragment(),"相關梗圖");
//        viewPager.setAdapter(pagerAdapter);
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_col_meme_tmp, container, false);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager) {
        pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new ColMemTmpContent(),"梗圖模板");

        viewPager.setAdapter(pagerAdapter);

    }
}
