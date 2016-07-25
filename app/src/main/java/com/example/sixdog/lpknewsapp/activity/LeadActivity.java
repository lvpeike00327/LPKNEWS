package com.example.sixdog.lpknewsapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sixdog.lpknewsapp.R;
import com.example.sixdog.lpknewsapp.adapter.LeadAdapter;
import com.example.sixdog.lpknewsapp.base.BaseActivity;
import com.example.sixdog.lpknewsapp.utils.SpfManager;
import com.example.sixdog.lpknewsapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SIXDOG on 2016/7/10.
 */
public class LeadActivity extends Activity {
    Button skip_btn;
    ViewPager viewPager;
    LeadAdapter leadAdapter;
    List<View> list;
    LayoutInflater layoutInflater;
    SpfManager spfManager;
    Animation animation_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.isRefresh=true;
        setLayout();
    }


    public void setLayout() {
        setContentView(R.layout.activity_lead);
        layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        spfManager=SpfManager.getSpf(this);
        animation_in=AnimationUtils.loadAnimation(this,R.anim.lead_btn_in);
        if(spfManager.getIsFrist().equals("yes")){
            Intent intent=new Intent(this, LogoActivity.class);
            startActivity(intent);
            finish();
        }else{
            spfManager.saveIsFrist();
            getView();
            setView();
        }
    }


    public void getView() {
        skip_btn= (Button) findViewById(R.id.lead_skip);
        viewPager= (ViewPager) findViewById(R.id.lead_viewpager);
        list=new ArrayList<View>();
        ImageView ivone= (ImageView) layoutInflater.inflate(R.layout.lead_img,null);
        ivone.setBackgroundResource(R.mipmap.lead_1);
        list.add(ivone);
        ImageView ivtwo= (ImageView) layoutInflater.inflate(R.layout.lead_img,null);
        ivtwo.setBackgroundResource(R.mipmap.lead_2);
        list.add(ivtwo);
        ImageView ivthree= (ImageView) layoutInflater.inflate(R.layout.lead_img,null);
        ivthree.setBackgroundResource(R.mipmap.lead_3);
        list.add(ivthree);
    }


    public void setView() {
        leadAdapter=new LeadAdapter(list,this);
        viewPager.setAdapter(leadAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    skip_btn.setVisibility(View.VISIBLE);

                   skip_btn.startAnimation(animation_in);

                    skip_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(LeadActivity.this,LogoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }else{
                    Log.i("msg","zhixing");
                    skip_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
