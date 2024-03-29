package com.okjunkstore.beta.dashboard.buy.BuyFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ElectronicsFragment();
            case 1: return new BookFragment();
            case 2: return new OtherItemsFragment();

            default:return new ElectronicsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0){
            title = "Electronics";
        }else if (position==1){
            title = "Books";
        }else if (position==2){
            title = "Other Items";
        }
        return title;
    }
}
