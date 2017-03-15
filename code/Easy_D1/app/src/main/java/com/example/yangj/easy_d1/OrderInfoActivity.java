package com.example.yangj.easy_d1;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInfoActivity extends ListActivity {

    private SimpleAdapter adapter;
    private List<Map<String,Object>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        mData=new ArrayList<Map<String, Object>>();
        Bundle extras = getIntent().getExtras();
        try {
            JSONObject order = new JSONObject(extras.getString("orderInfo"));
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("orderInfo","订单号： "+order.getString("id"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("orderInfo","用户ID： "+order.getString("client"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("orderInfo","店铺ID： "+order.getString("shop"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("orderInfo","配送员ID： "+order.getString("user"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("orderInfo","经纬度： "+order.getString("latitude")+","+order.getString("longitude"));
            mData.add(map);
            map=new HashMap<String, Object>();
            map.put("orderInfo","下单时间： "+order.getString("time"));
            mData.add(map);
            String status=order.getString("status");
            String s=status=="1"?"已完成":"未完成";
            map=new HashMap<String, Object>();
            map.put("orderInfo","订单状态："+s);
            mData.add(map);
        }
        catch (JSONException e) {

        }
        adapter = new SimpleAdapter(getApplicationContext(),
                mData,R.layout.order_info_listview,
                new String[]{"orderInfo"},
                new int[]{R.id.orderInfo});
        setListAdapter(adapter);
    }
}
