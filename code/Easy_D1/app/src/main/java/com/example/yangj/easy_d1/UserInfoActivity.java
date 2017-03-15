package com.example.yangj.easy_d1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends ListActivity {
    private SimpleAdapter adapter;
    private List<Map<String,Object>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mData=new ArrayList<Map<String, Object>>();
        Bundle extras = getIntent().getExtras();
        try {
            JSONObject order = new JSONObject(extras.getString("userInfo"));
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("userInfo","用户ID： "+order.getString("id"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("userInfo","店铺ID： "+order.getString("shopId"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("userInfo","用户名： "+order.getString("name"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("userInfo","手机号： "+order.getString("phone"));
            mData.add(map);
        }
        catch (JSONException e) {

        }
        adapter = new SimpleAdapter(getApplicationContext(),
                mData,R.layout.user_info_listview,
                new String[]{"userInfo"},
                new int[]{R.id.userInfo});
        setListAdapter(adapter);
    }
    public void logout(View v)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent =new Intent();
        intent.setClass(UserInfoActivity.this,MainActivity.class);
        intent.putExtra("mode", "no");
        startActivity(intent);
    }
}
