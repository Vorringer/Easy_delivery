package com.example.yangj.easy_d1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends ListActivity {
    private List<Map<String,Object>> mData;
    private EditText messageText;
    private EditText receiverText;
    private SimpleAdapter adapter;
    private String sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        printMessage();
    }

    public void sendMessage(View v)
    {
        messageText=(EditText)findViewById(R.id.messageEditText);
        final String message=messageText.getText().toString();
        if (message==null || "".equals(message.trim())) return;
        receiverText=(EditText)findViewById(R.id.receiverText);
        String receiverName=receiverText.getText().toString();
        if (receiverName==null || "".equals(receiverName.trim())) return;
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        sender=sharedPreferences.getString("email","");
        if (sender==null || "".equals(sender.trim())) return;
        String urlString = this.getString(R.string.serverUrl)+"addMessageByName?status=0&info="+message
                +"&sender="+sender+"&receiver="+receiverName;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                onCreate(null);
            };
            public void onFailure(int state, Header[] header, byte[] response, Throwable arg0) { // 失败，调

            };
            public void onFinish() { // 完成后调用，失败，成功，都要掉
            };
        });

    }

    public void printMessage()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        Long userID=sharedPreferences.getLong("userID",-1);
        sender=sharedPreferences.getString("email","");
        mData=new ArrayList<Map<String, Object> >();
        //String urlString = this.getString(R.string.serverUrl)+"getAllOrder";
        String urlString = this.getString(R.string.serverUrl)+"getMessageByReceiverId?receiverId="+userID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("message",orderObject.getString("info"));
                        map.put("sender","发送人："+orderObject.getString("senderName")+"      ");
                        map.put("time",orderObject.getString("time"));
                        if (orderObject.getString("senderName").equals(sender))
                            map.put("img",R.drawable.blue_left);
                        else
                            map.put("img",R.drawable.blue_right);
                        map.put("time",orderObject.getString("time"));
                        mData.add(map);
                    }
                    adapter = new SimpleAdapter(getApplicationContext(),
                            mData,R.layout.message_listview,
                            new String[]{"img","message","sender","time",},
                            new int[]{R.id.blueRow,R.id.messageText,R.id.senderText,R.id.timeText});
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
