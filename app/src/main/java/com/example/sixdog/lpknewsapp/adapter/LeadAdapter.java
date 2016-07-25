package com.example.sixdog.lpknewsapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by SIXDOG on 2016/7/10.
 */
public class LeadAdapter extends PagerAdapter {
    List<View> list;
    Context mC;

    public LeadAdapter(List<View> list, Context mC) {
        this.list = list;
        this.mC = mC;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=list.get(position);
        container.addView(view);
        Log.i("msg","执行");
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=list.get(position);
        container.removeView(view);
    }
}
