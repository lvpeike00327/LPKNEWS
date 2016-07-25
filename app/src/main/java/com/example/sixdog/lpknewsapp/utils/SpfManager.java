package com.example.sixdog.lpknewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by SIXDOG on 2016/7/10.
 */
public class SpfManager {
    Context mC;
    public static SpfManager spfManager;
    private  SpfManager(Context mC) {
        this.mC = mC;
    }
    public static SpfManager getSpf(Context context){
        if(spfManager==null){
            spfManager=new SpfManager(context);
        }
        return spfManager;
    }
    public void saveIsFrist(){
        SharedPreferences sharedPreferences=mC.getSharedPreferences("frist",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=sharedPreferences.edit();
        ed.putString("isFrist","yes");
        ed.commit();
    }
    public String getIsFrist(){
        SharedPreferences sharedPreferences=mC.getSharedPreferences("frist",Context.MODE_PRIVATE);
        return  sharedPreferences.getString("isFrist","no");
    }

    /**
     * 保存新闻标签
     * @param set
     */
    public void saveNewsTag(Set<String> set){
        SharedPreferences sharedPreferences=mC.getSharedPreferences("newskey",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=sharedPreferences.edit();
        ed.putStringSet("newstag",set);
//        Log.i("Tag","贡献参数添加"+set.toString());
        ed.commit();
    }
    /**
     * 取出新闻标签
     */
    public ArrayList<String> loadNewsTag(){
        Set<String> set=null;
        SharedPreferences sharedPreferences=mC.getSharedPreferences("newskey",Context.MODE_PRIVATE);

        set=sharedPreferences.getStringSet("newstag",null);

        ArrayList<String> list=new ArrayList<>();
        if(set==null){
            list.add("互联网");
            list.add("安卓");
            list.add("热点");
            list.add("四川");
            list.add("星座");
            list.add("军事");
        }else{
            for(String s:set){
                list.add(s);
            }
        }
        return list;
    }
}
