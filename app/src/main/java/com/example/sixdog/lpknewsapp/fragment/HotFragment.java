package com.example.sixdog.lpknewsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.sixdog.lpknewsapp.R;
import com.example.sixdog.lpknewsapp.activity.WebActivity;
import com.example.sixdog.lpknewsapp.adapter.HotAdapter;
import com.example.sixdog.lpknewsapp.base.MyApplication;
import com.example.sixdog.lpknewsapp.entity.HotInfo;
import com.example.sixdog.lpknewsapp.utils.HttpUitils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class HotFragment extends Fragment {
    private View view;
    private MyApplication myapp;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Gson gson;
    private RecyclerView recyclerView;
    private HotAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_hot, container, false);

        myapp=MyApplication.getApplication(getActivity());

        initView();
        return view;
    }
    private void initView() {

        recyclerView = (RecyclerView)view.findViewById(R.id.newinfo_recyclerview);
        //设置布局管理器 决定线性还是网格
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newinfo_swiperefresh);
        //设置下拉刷新的进度条颜色（最多四种）
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.orange,
                R.color.green, R.color.text_color_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp();
            }
        });

        adapter=new HotAdapter();
        adapter.setOnItemClickListener(new HotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", (String)v.getTag());
                getContext().startActivity(intent);

            }
        });
        //设置adapter
        recyclerView.setAdapter(adapter);
        getDataFromHttp();
    }

    public void getDataFromHttp() {

        StringRequest stringRequest = new StringRequest(HttpUitils.getHotNewsUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("hot","数据获取"+response);
                        try {
                            HotInfo info = new Gson().fromJson(response,HotInfo.class);
                            getDataSuccess(info.getRetData());
                        }catch (Exception e){
                            getDataDefult();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDataDefult();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", HttpUitils.NEWS_KEY);
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = null;

                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        myapp.getRequestQueue().add(stringRequest);

    }
    private void getDataSuccess(List<HotInfo.RetDataEntity> contentlist) {
        Log.i("hot","执行");
        if(contentlist==null||contentlist.size()<=0){
            return;
        }
        adapter.updateData(contentlist);
        swipeRefreshLayout.setRefreshing(false);
    }
    private void getDataDefult() {
        Log.i("hot","重置");
        swipeRefreshLayout.setRefreshing(false);
    }

}
