package com.stan.music.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stan.music.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Stan
 * Date: 2019/11/7 17:00
 * Description: ${DESCRIPTION}
 */
public class MultiFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments = new ArrayList<>();
    public MultiFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        if(fragments != null && i < fragments.size()){
            return fragments.get(i);
        }
        return null;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    public void init(List<BaseFragment> fragmentLists){
        fragments.clear();
        this.fragments = fragmentLists;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(getItem(position) instanceof BaseFragment){
            return ((BaseFragment)getItem(position)).getTitle();
        }
        return super.getPageTitle(position);
    }
}
