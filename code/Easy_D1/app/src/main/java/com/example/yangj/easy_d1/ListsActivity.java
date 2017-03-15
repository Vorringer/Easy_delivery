package com.example.yangj.easy_d1;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
        import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
        import com.util.HttpUtil;

        import org.apache.http.Header;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.UnsupportedEncodingException;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class ListsActivity extends AppCompatActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper {

    RecyclerView mRecyclerView;
    MainAdapter mAdapter;
    String[] dialogItems;
    List<Integer> unclickableRows, unswipeableRows;
    private RecyclerTouchListener onTouchListener;
    private int openOptionsPosition;
    private OnActivityTouchListener touchListener;
    private List<RowModel> mData;
    private List<Map<String,Object>>mmData;
    private JSONArray orderResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);

        Bundle extras = getIntent().getExtras();
        if (extras==null) return;
        String orderJson = extras.getString("data");
        String beginTime=extras.getString("beginTime");
        String endTime=extras.getString("endTime");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        mData=new ArrayList<>();
        mmData=new ArrayList<>();
        try {
            orderResult = new JSONArray(orderJson);
            for (int i = 0; i < orderResult.length(); i++) {
                JSONObject orderObject = (JSONObject) orderResult.get(i);
                String ttime = orderObject.getString("time");
                Date begin = sdf2.parse(beginTime);
                Date end = sdf2.parse(endTime);
                Date curr=sdf1.parse(ttime);
                if (curr.after(begin) && curr.before(end)) {
                    String oid="订单号: "+orderObject.getString("id");
                    String sStatus=orderObject.getLong("status")==1?"已完成":"未完成";
                    String secRow="用户ID："+orderObject.getString("user")+"  店铺ID: "+
                        orderObject.getString("shop")+"  状态："+sStatus;
                    RowModel rmodel=new RowModel(oid,secRow);
                    mData.add(rmodel);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("orderID", orderObject.getLong("id"));
                    map.put("userID", orderObject.getLong("user"));
                    map.put("shopID", orderObject.getLong("shop"));
                    map.put("clientID",orderObject.getLong("client"));
                    map.put("latitude",orderObject.getLong("latitude"));
                    map.put("longitude",orderObject.getLong("longitude"));
                    map.put("time", ttime);
                    map.put("status", orderObject.getLong("status"));
                    mmData.add(map);

                }
            }
        }
        catch (JSONException e)
        {

        }
        catch (ParseException e)
        {}
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("订单列表");

        unclickableRows = new ArrayList<>();
        unswipeableRows = new ArrayList<>();
        dialogItems = new String[mData.size()];
        for (int i = 0; i < mData.size(); i++) {
            dialogItems[i] = String.valueOf(i + 1);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new MainAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {

                        try {
                            Intent intent = new Intent(getApplicationContext(), OrderInfoActivity.class);
                            intent.putExtra("orderInfo", orderResult.get(position).toString());
                            startActivity(intent);
                        }
                        catch (JSONException e)
                        {

                        }
                        //ToastUtil.makeToast(getApplicationContext(), "Row " + (position + 1) + " clicked!");
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        String shopS=mmData.get(position).get("shopID").toString();
                        Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                        intent.putExtra("shopID", shopS);
                        startActivity(intent);
                        //ToastUtil.makeToast(getApplicationContext(), "Button in row " + (position + 1) + " clicked!");
                    }
                })
//                .setLongClickable(true, new RecyclerTouchListener.OnRowLongClickListener() {
//                    @Override
//                    public void onRowLongClicked(int position) {
//                        ToastUtil.makeToast(getApplicationContext(), "Row " + (position + 1) + " long clicked!");
//                    }
//                })
                .setSwipeOptionViews(R.id.add)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        String message = "";
                        if (viewID == R.id.add) {
                            message += "Add";
                        }
//                         else if (viewID == R.id.edit) {
//                            message += "Edit";
//                        } else if (viewID == R.id.change) {
//                            message += "Change";
//                        }
                        String oid=mmData.get(position).get("orderID").toString();
                        String urlString = getApplicationContext().getString(R.string.serverUrl)+"setOrderStatus?id="+oid+"&status=-1";
                        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
                            public void onSuccess(int state, Header[] header, byte[] response) {

                            };
                            public void onFailure(int state, Header[] header, byte[] response, Throwable arg0) { // 失败，调
                            };
                            public void onFinish() { // 完成后调用，失败，成功，都要掉

                            };
                        });
                        message += " clicked for row " + (position + 1);
                        //ToastUtil.makeToast(getApplicationContext(), message);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener); }

    @Override
    protected void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }

    private List<RowModel> getData() {
        List<RowModel> list = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            list.add(new RowModel("Row " + (i + 1), "Some Text... "));
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean currentState = false;
        if (item.isCheckable()) {
            currentState = item.isChecked();
            item.setChecked(!currentState);
        }
        switch (item.getItemId()) {
            case R.id.menu_swipeable:
                onTouchListener.setSwipeable(!currentState);
                return true;
            case R.id.menu_clickable:
                onTouchListener.setClickable(!currentState);
                return true;
            case R.id.menu_unclickableRows:
                showMultiSelectDialog(unclickableRows, item.getItemId());
                return true;
            case R.id.menu_unswipeableRows:
                showMultiSelectDialog(unswipeableRows, item.getItemId());
                return true;
            case R.id.menu_openOptions:
                showSingleSelectDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMultiSelectDialog(final List<Integer> list, final int menuId) {
        boolean[] checkedItems = new boolean[25];
        for (int i = 0; i < list.size(); i++) {
            checkedItems[list.get(i)] = true;
        }

        String title = "Select {} Rows";
        if (menuId == R.id.menu_unclickableRows) title = title.replace("{}", "Unclickable");
        else title = title.replace("{}", "Unswipeable");

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMultiChoiceItems(dialogItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked)
                            list.add(which);
                        else
                            list.remove(which);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer[] tempArray = new Integer[list.size()];
                        if (menuId == R.id.menu_unclickableRows)
                            onTouchListener.setUnClickableRows(list.toArray(tempArray));
                        else
                            onTouchListener.setUnSwipeableRows(list.toArray(tempArray));
                    }
                });
        builder.create().show();
    }

    private void showSingleSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Open Swipe Options for row: ")
                .setSingleChoiceItems(dialogItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openOptionsPosition = which;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onTouchListener.openSwipeOptions(openOptionsPosition);
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) touchListener.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
        LayoutInflater inflater;
        List<RowModel> modelList;

        public MainAdapter(Context context, List<RowModel> list) {
            inflater = LayoutInflater.from(context);
            modelList = new ArrayList<>(list);
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.recycler_row, parent, false);
            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            holder.bindData(modelList.get(position));
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView mainText, subText;

            public MainViewHolder(View itemView) {
                super(itemView);
                mainText = (TextView) itemView.findViewById(R.id.mainText);
                subText = (TextView) itemView.findViewById(R.id.subText);
            }

            public void bindData(RowModel rowModel) {
                mainText.setText(rowModel.getMainText());
                subText.setText(rowModel.getSubText());
            }
        }
    }
}