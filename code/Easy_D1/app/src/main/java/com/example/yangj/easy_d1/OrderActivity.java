package com.example.yangj.easy_d1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bean.Order;
import android.widget.DatePicker;

public class OrderActivity extends ListActivity implements OnClickListener {
    boolean mFlag = false;
    private Order order;
    private TableLayout table;
    private Button btn;
    private TextView et;
    private String beginTime="0000.00.00";
    private String endTime="9999.99.99";
    private List<Map<String,Object>>mData;
    private int tFlag=0;
    public final class ViewHolder{
        public TextView orderID;
        public TextView userID;
        public TextView shopID;
        public TextView status;
        public Button comment;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int pos=position;
            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.order_listview, null);
                holder.orderID = (TextView)convertView.findViewById(R.id.orderIDText);
                holder.userID = (TextView)convertView.findViewById(R.id.userIDText);
                holder.shopID=(TextView)convertView.findViewById(R.id.shopIDText);
                holder.status=(TextView)convertView.findViewById(R.id.statusText);
                holder.comment = (Button)convertView.findViewById(R.id.commentButton);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }
            String statusStr="";
            holder.orderID.setText("订单号："+mData.get(position).get("orderID").toString()+" ");
            holder.userID.setText("用户ID："+mData.get(position).get("userID").toString()+"   ");
            holder.shopID.setText("店铺ID："+mData.get(position).get("shopID").toString()+"   ");
            if (mData.get(position).get("status").toString().equals("1"))
                statusStr="已完成   ";
            else
                statusStr="未完成   ";

            holder.status.setText("订单状态："+statusStr);

            holder.comment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                    intent.putExtra("shopID", mData.get(pos).get("shopID").toString());
                    startActivity(intent);
                }
            });


            return convertView;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_order);
        mData=new ArrayList<Map<String,Object>>();
        Button mButton = (Button) findViewById(R.id.myButton);
        mButton.setOnClickListener(this);
        btn = (Button) findViewById(R.id.dateBtn);
        et = (TextView) findViewById(R.id.et);
        btn.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(OrderActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        beginTime=startYear+"."+(startMonthOfYear+1)+"."+startDayOfMonth;
                        endTime=endYear+"."+(endMonthOfYear+1)+"."+endDayOfMonth;
                        String textString = String.format("开始时间：%d-%d-%d\n结束时间：%d-%d-%d\n", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        et.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
            }
        });

    }


    public void addWegit() {
        final Context context=getApplicationContext();
        String urlString="";
        urlString = context.getString(R.string.serverUrl)+"getAllOrder";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    mData.clear();
                    Intent intent =new Intent();
                    intent.setClass(OrderActivity.this,ListsActivity.class);
                    intent.putExtra("data",new String(response,"utf-8"));
                    intent.putExtra("beginTime",beginTime);
                    intent.putExtra("endTime",endTime);
                    startActivity(intent);
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        String ttime=orderObject.getString("time");
                        if (ttime.compareTo(beginTime)==1 && ttime.compareTo(endTime)==-1) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("orderID", orderObject.getLong("id"));
                            map.put("userID", orderObject.getLong("user"));
                            map.put("shopID", orderObject.getLong("shop"));
                            map.put("time", ttime);
                            map.put("status", orderObject.getLong("status"));
                            mData.add(map);
                        }
                    }
                    MyAdapter adapter = new MyAdapter(context);
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
                tFlag=1;
            };
        });
    }

    @Override
    public void onClick(View v) {

        int vv = v.getId();
        switch (vv) {
            case 0x0:
                Toast.makeText(OrderActivity.this, "heh,keyidianjide",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.myButton:
                addWegit();
                break;
        }

    }
}
