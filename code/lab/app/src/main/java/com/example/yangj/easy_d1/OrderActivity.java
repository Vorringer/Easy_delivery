package com.example.yangj.easy_d1;

import android.os.Bundle;
import android.view.View;

import android.app.Activity;
import android.graphics.Color;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.httpconn.HttpConnImpl;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import com.bean.Order;
public class OrderActivity extends Activity implements OnClickListener {
    boolean mFlag = false;
    private Order order;
    private TableLayout table;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_order);

        Button mButton = (Button) findViewById(R.id.allOrderButton);
        Button undeButton = (Button) findViewById(R.id.UndeliveredOrderButton);
        mButton.setOnClickListener(this);
        undeButton.setOnClickListener(this);
    }

    public void addWegit(int mode) {
        table = (TableLayout) findViewById(R.id.tablelayout);
        table.setStretchAllColumns(true);
        table.removeAllViews();
        String urlString="";
        //String urlString = "http://59.78.45.3:8080/getAllOrder";
        switch (mode)
        {
            case 0:urlString = "http://192.168.16.70:8080/getAllOrder";break;
            case 1:urlString = "http://192.168.16.70:8080/getAllNewOrder";break;
        }
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                String fill[]=new String[]{"id","longitude","latitude","status","shopId","userId","clientId"};
                try {
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    order=new Order();
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        order.setId(orderObject.getInt("id"));
                        order.setClient(orderObject.getInt("client"));
                        order.setShop(orderObject.getInt("shop"));
                        order.setUser(orderObject.getInt("user"));
                        order.setLongitude(orderObject.getDouble("longitude"));
                        order.setLatitude(orderObject.getDouble("latitude"));
                        order.setStatus((short)orderObject.getInt("status"));
                        TableRow tablerow = new TableRow(OrderActivity.this);
                        tablerow.setBackgroundColor(Color.rgb(222, 220, 210));

                        TextView testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getId()));
                        tablerow.addView(testview);
                        testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getLongitude()));
                        tablerow.addView(testview);
                        testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getLatitude()));
                        tablerow.addView(testview);
                        testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getStatus()));
                        tablerow.addView(testview);
                        testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getShop()));
                        tablerow.addView(testview);
                        testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getUser()));
                        tablerow.addView(testview);
                        testview = new TextView(OrderActivity.this);
                        testview.setText(String.valueOf(order.getClietnt()));
                        tablerow.addView(testview);

                        table.addView(tablerow, new TableLayout.LayoutParams(
                                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    }
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

    @Override
    public void onClick(View v) {

        int vv = v.getId();
        switch (vv) {
            case R.id.allOrderButton:
                /*if (!mFlag) {
                    addWegit();
                    mFlag = !mFlag;
                }*/
                addWegit(0);
                break;
            case R.id.UndeliveredOrderButton:
                addWegit(1);
                break;
        }

    }
}
