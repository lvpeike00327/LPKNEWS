package com.example.sixdog.lpknewsapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
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
import com.example.sixdog.lpknewsapp.adapter.BaseLoadAdapter;
import com.example.sixdog.lpknewsapp.adapter.NewsAdapter;
import com.example.sixdog.lpknewsapp.base.MyApplication;
import com.example.sixdog.lpknewsapp.entity.BaiDuiInfo;
import com.example.sixdog.lpknewsapp.utils.HttpUitils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class ShowNewsFragment extends Fragment {
    String titlename;//新闻标签
    RecyclerView nes_recycle;//新闻显示列表

    NewsAdapter newsAdapter;
    private MyApplication myapp;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View view;
    private final int ADD_DATA_FLAG=1;
    private final int UPDATE_DATA_FLAG=2;
    private NewsAdapter adapter;
    private int nowpage=1;//当前数据的页数
    private int all_page;
    private Gson gson;
    private List<BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list;
    private String strkey;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
//                    String str= (String) msg.obj;
//                    try {
//                    BaiDuiInfo info = gson.fromJson(str,  BaiDuiInfo.class);
//                    all_page = info.getShowapi_res_body().getPagebean().getAllPages();
//                    getDataSuccess(info.getShowapi_res_body().getPagebean().getContentlist(), ADD_DATA_FLAG);
//                }catch (Exception e){
//                    getDataDefult(ADD_DATA_FLAG);
//                }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shownews,container,false);
        strkey= getArguments().getString("tag");//获得标签
        myapp=MyApplication.getApplication(getContext());
//        Log.i("msg","数据加载");
        list=new ArrayList<BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        inview();
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }
//setOnRefreshListener(OnRefreshListener listener)  设置下拉监听，当用户下拉的时候会去执行回调
//    2、setColorSchemeColors(int... colors) 设置 进度条的颜色变化，最多可以设置4种颜色
//    3、setProgressViewOffset(boolean scale, int start, int end) 调整进度条距离屏幕顶部的距离
//    4、setRefreshing(boolean refreshing) 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，、
// 在数据被加载到View中后，设置为false。

    void inview(){
        Log.i("msg","执行加载数据");
        recyclerView = (RecyclerView)view.findViewById(R.id.news_recycleview);
        //设置布局管理器 决定线性还是网格
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newinfo_swiperefresh);
        //第一次加载显示进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //设置下拉刷新的进度条颜色（最多四种）
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.orange,
                R.color.green, R.color.text_color_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp(UPDATE_DATA_FLAG, 1);
            }
        });

        adapter=new NewsAdapter(recyclerView,list,getActivity());
        adapter.setOnLoadingListener(new BaseLoadAdapter.OnLoadingListener() {
            @Override
            public void loading() {
                getDataFromHttp(ADD_DATA_FLAG,nowpage);
            }
        });
        //设置adapter
        recyclerView.setAdapter(adapter);
        getDataFromHttp(ADD_DATA_FLAG,nowpage);
    }
    public void getDataFromHttp(int type,int page) {
        if(all_page!=0&&nowpage>all_page){
            getDataNoMore();
        }
        final int nowtype=type;
        Log.i("msg",HttpUitils.getNewsUrl(strkey,page));
        StringRequest stringRequest = new StringRequest(HttpUitils.getNewsUrl(strkey,page),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.i("msg",response);
                        try {
                            BaiDuiInfo info =new Gson().fromJson(response,  BaiDuiInfo.class);
//                            Log.i("msg",info.toString());
                            Log.i("msg","数据呢");

                            all_page = info.getShowapi_res_body().getPagebean().getAllPages();
                            getDataSuccess(info.getShowapi_res_body().getPagebean().getContentlist(), nowtype);
                        }catch (Exception e){
                            getDataDefult(nowtype);
                        }
        }
    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                getDataDefult(nowtype);
                Log.i("msg","错误");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", HttpUitils.NEWS_KEY);
//                Log.i("msg","拼接");
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
        myapp.addToRequestQueue(stringRequest);

    }
    private void getDataSuccess(List<BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist, int type) {
        Log.i("msg","meiyoushuju");
        if(contentlist==null||contentlist.size()<=0){
            Log.i("msg","meiyoushuju");
            return;
        }
        for(int i=0;i<contentlist.size();i++){
            if(contentlist.get(i).getImageurls() == null||
                    contentlist.get(i).getImageurls().size()<=0||
                    contentlist.get(i).getImageurls().get(0).getUrl()==null){
                contentlist.remove(i);
                i--;
            }
        }
        switch (type){
            case ADD_DATA_FLAG:
                Log.i("msg","加载更多");
                adapter.setLoadingComplete();
                int position=list.size();
                if(contentlist != null){
                    list.addAll(contentlist);
                    Log.i("msg","添加数据");
//                    adapter.notifyItemInserted(position);
                    adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
                }
                nowpage++;
                break;
            case UPDATE_DATA_FLAG:
                Log.e("msg", contentlist.size() + " ");
                if(contentlist!=null){
                    list.clear();
                    list.addAll(contentlist);
                    Log.i("msg","添加刷新");
                    adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
                }
                swipeRefreshLayout.setRefreshing(false);
                nowpage=1;
                break;
        }
    }
    private void getDataDefult(int type) {
        Log.i("msg","错误");
        switch (type){
            case ADD_DATA_FLAG:
                adapter.setLoadingError();
                break;
            case UPDATE_DATA_FLAG:
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }
    private void getDataNoMore() {
        adapter.setLoadingNoMore();
        swipeRefreshLayout.setRefreshing(false);
    }

}
