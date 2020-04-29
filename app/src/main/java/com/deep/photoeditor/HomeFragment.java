package com.deep.photoeditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    View myFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;

    public HomeFragment(){

    }

    public static HomeFragment getInstance() {return new HomeFragment(); };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home, container, false);

        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        //fragment cardview
        tabLayout = myFragment.findViewById(R.id.mainTablayout);
        viewPager = myFragment.findViewById(R.id.mainViewPager);

//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return myFragment;
    }

    //call onActivity Create method

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new maintab1(),"梗圖模板");
        pagerAdapter.AddFragment(new maintab2(),"長輩圖模板");

        viewPager.setAdapter(pagerAdapter);

    }
}
