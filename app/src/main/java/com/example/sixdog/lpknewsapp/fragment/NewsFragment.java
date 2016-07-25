package com.example.sixdog.lpknewsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.sixdog.lpknewsapp.R;
import com.example.sixdog.lpknewsapp.activity.AddTagActivity;
import com.example.sixdog.lpknewsapp.adapter.NewsViewPagerAdapter;
import com.example.sixdog.lpknewsapp.utils.SpfManager;
import com.example.sixdog.lpknewsapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class NewsFragment extends Fragment {
    TabLayout tab;
    ViewPager viewPager;
    View view;
    List<Fragment> list;
    List<String> title;
    SpfManager spf;
    RelativeLayout add_rl;
    NewsViewPagerAdapter newsViewPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_news,null);
        spf=SpfManager.getSpf(getContext());
        setview();
        return view;

    }
    void setview(){
        tab= (TabLayout) view.findViewById(R.id.fragment_tab);
        viewPager= (ViewPager) view.findViewById(R.id.fragment_news_viewpager);
        add_rl= (RelativeLayout) view.findViewById(R.id.change_newstag_rl);
        add_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddTagActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(   Tools.isRefresh){
            title=  spf.loadNewsTag();
            Log.i("Tag","news碎片"+title.toString());
            if(title.size()>0){
                list=new ArrayList<Fragment>();
                for (int i=0;i<title.size();i++){
                    Fragment news_fragment=new ShowNewsFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("tag",title.get(i));
                    news_fragment.setArguments(bundle);//传递标签
                    list.add(news_fragment);
                }
                newsViewPagerAdapter=new NewsViewPagerAdapter(getActivity().getSupportFragmentManager(),list,title);
//            newsViewPagerAdapter.setList(list);
//            newsViewPagerAdapter.setTitlelist(title);
//            newsViewPagerAdapter.notifyDataSetChanged();
                viewPager.setOffscreenPageLimit(list.size());
                viewPager.setAdapter(newsViewPagerAdapter);
                tab.setupWithViewPager(viewPager);
        }

        }
}
}
