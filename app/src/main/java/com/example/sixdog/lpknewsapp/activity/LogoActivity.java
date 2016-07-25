package com.example.sixdog.lpknewsapp.activity;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sixdog.lpknewsapp.R;
import com.example.sixdog.lpknewsapp.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SIXDOG on 2016/7/10.
 */
public class LogoActivity extends BaseActivity {
    ImageView imageView;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_logo);
    }

    @Override
    public void getView() {
        imageView= (ImageView) findViewById(R.id.logo_img);
        imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.logo_img));
    }

    @Override
    public void setView() {
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(MainActivity.class,0,0);
                finish();
            }
        };
        timer.schedule(task,1000);
    }
}
