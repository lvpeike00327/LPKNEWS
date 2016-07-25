package com.example.sixdog.lpknewsapp.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sixdog.lpknewsapp.R;
import com.example.sixdog.lpknewsapp.base.BaseActivity;
import com.example.sixdog.lpknewsapp.utils.SpfManager;
import com.example.sixdog.lpknewsapp.utils.Tools;
import com.example.sixdog.lpknewsapp.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class AddTagActivity extends BaseActivity {
    private Toolbar toolbar;
    private FlowLayout flowlayout;
    private Set<String> set;
    private SpfManager spfManager;
    private ListView listview;
    private FlowLayout.FlowRemove flowRemove;
    private String[] sz={"互联网","安卓","热点","四川","星座","军事"};
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_add);
        spfManager=SpfManager.getSpf(this);
        flowRemove=new FlowLayout.FlowRemove() {
            @Override
            public void changeSpf(Set<String> set) {
                Tools.isRefresh=true;
                if(set.size()>0){
                    spfManager.saveNewsTag(set);
                }else{
                    spfManager.saveNewsTag(null);
                }

            }
        };
    }

    @Override
    public void getView() {
        set= new TreeSet<String>();
        List<String> list=spfManager.loadNewsTag();
        for (int i =0;i<list.size();i++){
            set.add(list.get(i));
        }
        flowlayout = (FlowLayout) findViewById(R.id.flowlayout);
        flowlayout.setFlowRemove(flowRemove);
        listview= (ListView) findViewById(R.id.add_listiew);
        toolbar= (Toolbar) findViewById(R.id.web_toolbar);
        toolbar.setNavigationIcon(R.mipmap.btn_return);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setView() {

        flowlayout.getSetData(set);

        ArrayAdapter<String> adapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,sz);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                set.add(sz[position]);
                Tools.isRefresh=true;
                spfManager.saveNewsTag(set);
                flowlayout.getSetData(set);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        spfManager.saveNewsTag(set);
        Log.i("Tag","增加页面存储");
    }
}
