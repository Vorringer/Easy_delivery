package com.example.yangj.easy_d1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends ListActivity {
    private Long shopID;
    private List<Map<String,Object>> mData;
    private EditText commentText;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        printComment();
    }
    public void submitComment(View v)
    {
        commentText=(EditText)findViewById(R.id.commentEditText);
        final String comment=commentText.getText().toString();
        if (comment==null || "".equals(comment.trim())) return;
        SharedPreferences sharedPreferences= getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        final Long userID =sharedPreferences.getLong("userID", -1);
        final String email=sharedPreferences.getString("email","");
        int status=1;
        if (userID==-1) return;
        //String urlString = this.getString(R.string.serverUrl)+"getAllOrder";
        String urlString = this.getString(R.string.serverUrl)+
                "addComment?status="+status+"&info=" + comment + "&userId="+userID+"&shopId="+shopID+"&clientId=2";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                /*
                Map<String,Object> map=new HashMap<String, Object>();
                Date date=new Date(System.currentTimeMillis());
                map.put("comment",comment);
                map.put("email",email);
                map.put("time",date.toString());
                mData.add(map);
                */
                onCreate(null);
            };
            public void onFailure(int state, Header[] header, byte[] response, Throwable arg0) { // 失败，调

            };
            public void onFinish() { // 完成后调用，失败，成功，都要掉
            };
        });

    }

    public void printComment()
    {
        Bundle extras = getIntent().getExtras();
        shopID = Long.parseLong(extras.getString("shopID"));
        mData=new ArrayList<Map<String, Object> >();
        //String urlString = this.getString(R.string.serverUrl)+"getAllOrder";
        String urlString = this.getString(R.string.serverUrl)+"getCommentByShopId?shopId="+shopID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("comment",orderObject.getString("info"));
                        map.put("email",orderObject.getString("name")+"      ");
                        map.put("time",orderObject.getString("time"));
                        mData.add(map);
                    }
                    adapter = new SimpleAdapter(getApplicationContext(),
                            mData,R.layout.comment_listview,
                            new String[]{"comment","email","time"},
                            new int[]{R.id.commentText,R.id.userIDText,R.id.timeText});
                    setListAdapter(adapter);
                }
                catch (UnsupportedEncodingException e)
                {

                }
                catch (JSONException e)
                {

                }
            };
            public void onFailure(int state, Header[] header, byte[] response, Throwable arg0) { // 失败，调

            };
            public void onFinish() { // 完成后调用，失败，成功，都要掉
            };
        });
    }

}
