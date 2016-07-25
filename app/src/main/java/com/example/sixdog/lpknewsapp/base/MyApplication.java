package com.example.sixdog.lpknewsapp.base;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by SIXDOG on 2016/7/17.
 */
public class MyApplication extends Application {
    private RequestQueue mRequestQueue;
    private static MyApplication myApplication;
    private Context mContext;
    private Gson gson;
    public MyApplication(){

    }
    public MyApplication(Context context) {
        this.mContext = context;
        mRequestQueue = getRequestQueue();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        gson=new Gson();
        ShareSDK.initSDK(this);//kaiqi

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                // .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static synchronized MyApplication getApplication(Context context){
        if(myApplication == null){
            myApplication = new MyApplication(context);
        }
        return myApplication;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        mRequestQueue.cancelAll(this);
    }


}
