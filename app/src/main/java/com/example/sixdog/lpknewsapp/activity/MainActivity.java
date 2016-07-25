package com.example.sixdog.lpknewsapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.sixdog.lpknewsapp.R;
import com.example.sixdog.lpknewsapp.base.BaseActivity;
import com.example.sixdog.lpknewsapp.base.MyApplication;
import com.example.sixdog.lpknewsapp.fragment.HotFragment;
import com.example.sixdog.lpknewsapp.fragment.NewsFragment;
import com.example.sixdog.lpknewsapp.fragment.SerachFragment;
import com.example.sixdog.lpknewsapp.utils.Tools;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sixdog.lpknewsapp.R.id.main_bottom_hotimg;

/**
 * Created by SIXDOG on 2016/7/10.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    int[] mainBottomImageNormal = new int[]{R.mipmap.new_unselected, R.mipmap.hot_unselected, R.mipmap.find_defult};//底部图片默认ID
    int[] mainBottomImageSelect = new int[]{R.mipmap.new_selected, R.mipmap.hot_selected, R.mipmap.find_selected};//底部图片默认ID
    TextView[] textBottom = new TextView[3];
    ImageView[] ImageBottom = new ImageView[3];
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;//联动
    DrawerLayout drawerLayout;//抽屉
    ImageView news_img, hot_img, search_img;
    RelativeLayout news_rl, hot_rl, search_rl;
    TextView news_text, hot_text, search_text;
    int position = -1;
    FragmentManager fm;
    FragmentTransaction ft;
    NewsFragment news;
    HotFragment hot;
    SerachFragment serach;
    private OnekeyShare oks;
    CircleImageView head_circle;
    RelativeLayout to_login_rl;
    TextView show_login_tv;
    boolean Login_Statce;

    @Override
    public void setLayout() {
        Tools.isRefresh = true;
        setContentView(R.layout.activity_main);
        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("每天都有新资讯");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("下载我！每天都离万事通更近一点！");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
        position = 0;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        news = new NewsFragment();
        hot = new HotFragment();
        serach = new SerachFragment();
        ft.add(R.id.main_fragment, news).add(R.id.main_fragment, hot).add(R.id.main_fragment, serach);
        ft.hide(news).hide(hot).hide(serach).show(news);
        ft.commit();
    }

    @Override
    public void getView() {
        setToolbar();
        Tools.isRefresh = true;
        news_rl = (RelativeLayout) findViewById(R.id.main_bottom_news_rl);
        hot_rl = (RelativeLayout) findViewById(R.id.main_bottom_hot_rl);
        search_rl = (RelativeLayout) findViewById(R.id.main_bottom_search_rl);
        news_text = (TextView) findViewById(R.id.main_bottom_newstext);
        hot_text = (TextView) findViewById(R.id.main_bottom_hottext);
        search_text = (TextView) findViewById(R.id.main_bottom_searchtext);
        news_img = (ImageView) findViewById(R.id.main_bottom_newsimg);
        hot_img = (ImageView) findViewById(main_bottom_hotimg);
        search_img = (ImageView) findViewById(R.id.main_bottom_searchimg);
        head_circle = (CircleImageView) findViewById(R.id.head_img);
        to_login_rl = (RelativeLayout) findViewById(R.id.tologin_rl);
        show_login_tv = (TextView) findViewById(R.id.login_tv_msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Tag", "Main页面执行");
//        news=new NewsFragment();
//        hot=new HotFragment();
//        serach=new SerachFragment();
//        news=new NewsFragment();
//        hot=new HotFragment();
//        serach=new SerachFragment();
//        ft=fm.beginTransaction();
//        ft.add(R.id.main_fragment,news).add(R.id.main_fragment,hot).add(R.id.main_fragment,serach);
//        ft.hide(news).hide(hot).hide(serach).show(news);
//        ft.commit();
    }

    @Override
    public void setView() {
        setBottom();
        changeBottom(position);
        news_rl.setOnClickListener(this);
        search_rl.setOnClickListener(this);
        hot_rl.setOnClickListener(this);
        to_login_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
//                startActivity(LoginActivity.class,0,0);
            }
        });
    }

    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("NewsDday");
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_share:
                        Tools.isRefresh = false;
                        oks.show(MainActivity.this);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.main_bottom_news_rl:
                position = 0;
                ft.hide(news).hide(hot).hide(serach).show(news);
                break;
            case R.id.main_bottom_hot_rl:
                ft.hide(news).hide(hot).hide(serach).show(hot);
                position = 1;

                break;
            case R.id.main_bottom_search_rl:
                ft.hide(news).hide(hot).hide(serach).show(serach);
                position = 2;
                break;

        }
        ft.commit();
        changeBottom(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void changeBottom(int position) {
        for (int i = 0; i < textBottom.length; i++) {
            if (i == position) {
                textBottom[i].setTextColor(android.graphics.Color.parseColor("#3047ba"));
                ImageBottom[i].setImageResource(mainBottomImageSelect[i]);
            } else {
                textBottom[i].setTextColor(android.graphics.Color.parseColor("#000000"));
                ImageBottom[i].setImageResource(mainBottomImageNormal[i]);
            }
        }
    }

    public void setBottom() {
        textBottom[0] = news_text;
        textBottom[1] = hot_text;
        textBottom[2] = search_text;
        ImageBottom[0] = news_img;
        ImageBottom[1] = hot_img;
        ImageBottom[2] = search_img;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            Login_Statce = true;
            show_login_tv.setText("退出登录");
        }
        if (resultCode == 3) {
            Login_Statce = true;
            show_login_tv.setText("退出登录");
            String icon_path = data.getStringExtra("usericon");
            ImageRequest imageRequest = new ImageRequest(
                    icon_path, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    head_circle.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    head_circle.setImageResource(R.mipmap.login_defult_img);
                }
            });
            MyApplication.getApplication(this).addToRequestQueue(imageRequest);

        }
    }
}
