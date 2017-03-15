package com.example.yangj.easy_d1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class statisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }
    public void jumpToShopPop(View v)
    {
        Intent intent =new Intent();
        intent.setClass(statisticsActivity.this,shopPopActivity.class);
        startActivity(intent);
    }
    public void jumpToRichBuyers(View v)
    {
        Intent intent =new Intent();
        intent.setClass(statisticsActivity.this,RichBuyersActivity.class);
        startActivity(intent);
    }


}
