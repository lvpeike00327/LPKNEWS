package com.example.sixdog.lpknewsapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class HttpUitils {
    public static final String NEWS_URL="http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static final String NEWS_KEY="83ee0a1b0dc682135ae9575215e0f4bf";
    public static final String NEWS_HOT_URL="http://apis.baidu.com/songshuxiansheng/news/news";
//拼接地址
public static String getNewsUrl(String title,int page){
    String path=NEWS_URL+"?title="+ChangeEncode(title)+"&page="+page;;
    return path;
}
    public static String getHotNewsUrl(){
        return  NEWS_HOT_URL;

    }
        //修改编码
    public static String ChangeEncode(String titlename){
        try {
            titlename= URLEncoder.encode(titlename,"utf-8");
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            titlename="";
        }
        return titlename;

    }
}
