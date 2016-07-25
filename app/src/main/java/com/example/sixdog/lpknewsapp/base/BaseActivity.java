package com.example.sixdog.lpknewsapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by SIXDOG on 2016/7/10.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        getView();
        setView();

    }
    public abstract  void setLayout();
    public abstract  void getView();
    public  abstract  void setView();
    public void startActivity(Class c,int inId,int exitId){
        Intent intent=new Intent(this,c);
        startActivity(intent);
        if(inId!=0&&exitId!=0){
            overridePendingTransition(inId,exitId);
        }

    }
    public void startActivity(Bundle bundle,Class c, int inId, int exitId){
        Intent intent=new Intent(this,c);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(inId,exitId);
    }
}
