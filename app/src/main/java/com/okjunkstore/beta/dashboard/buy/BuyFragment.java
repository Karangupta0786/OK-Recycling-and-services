package com.okjunkstore.beta.dashboard.buy;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.okjunkstore.beta.dashboard.buy.BuyFragments.FragmentAdapter;
import com.okjunkstore.beta.R;
import com.google.android.material.tabs.TabLayout;

public class BuyFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager()));

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}