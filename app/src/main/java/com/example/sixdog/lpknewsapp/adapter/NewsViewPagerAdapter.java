package com.example.sixdog.lpknewsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class NewsViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> list;
    List<String> titlelist;
    public NewsViewPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> titlelist) {
        super(fm);
        this. list=list;
        this.titlelist=titlelist;
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public List<Fragment> getList() {
        return list;
    }

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public List<String> getTitlelist() {
        return titlelist;
    }

    public void setTitlelist(List<String> titlelist) {
        this.titlelist = titlelist;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }
}
