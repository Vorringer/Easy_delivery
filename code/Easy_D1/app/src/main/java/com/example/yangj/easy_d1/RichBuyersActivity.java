package com.example.yangj.easy_d1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bean.Shop;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RichBuyersActivity extends AppCompatActivity {
    private ColumnChartView mColumnChartView;
    private ColumnChartData mColumnChartData;
    private static final int DEFAULT_DATA = 0;
    private static final int SUBCOLUMNS_DATA = 1;
    private static final int STACKED_DATA = 2;
    private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
    private static final int NEGATIVE_STACKED_DATA = 4;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = true;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;
    List<Map.Entry<Long, Integer>> infoIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_buyers);
        mColumnChartView = (ColumnChartView) findViewById(R.id.column);
        printChart();
    }
    public void printChart()
    {
        String urlString = this.getString(R.string.serverUrl)+"getAllOrder";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    Map<Long,Integer> shopPop=new HashMap<Long,Integer>();
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        if (shopPop.containsKey(orderObject.getLong("user")))
                            shopPop.put(orderObject.getLong("user"),shopPop.get(orderObject.getLong("user"))+1);
                        else
                            shopPop.put(orderObject.getLong("user"),1);
                    }
                    infoIds = new ArrayList<Map.Entry<Long, Integer> >(shopPop.entrySet());
                    Collections.sort(infoIds, new Comparator<Map.Entry<Long, Integer>>() {
                        public int compare(Map.Entry<Long, Integer> o1,
                                           Map.Entry<Long, Integer> o2) {
                            return ( o2.getValue()-o1.getValue());
                        }
                    });
                    int numSubcolumns = 1;//设置每个柱状图显示的颜色数量(每个柱状图显示多少块)
                    int size=infoIds.size();
                    int numColumns = size > 8 ? 8 : size;//柱状图的数量
                    // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
                    List<Column> columns = new ArrayList<Column>();
                    List<SubcolumnValue> values;
                    for (int i = 0; i < numColumns; ++i) {
                        values = new ArrayList<SubcolumnValue>();
                        for (int j = 0; j < numSubcolumns; ++j) {
                            SubcolumnValue value = new SubcolumnValue(infoIds.get(i).getValue(), ChartUtils.pickColor());//第一个值是数值(值>0 方向朝上，值<0，方向朝下)，第二个值是颜色
                            //    SubcolumnValue value = new SubcolumnValue((float) Math.random() * 50f + 5, Color.parseColor("#00000000"));//第一个值是数值，第二个值是颜色
                            //    values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
                            values.add(value);
                        }
                        Column column = new Column(values);//一个柱状图的实例
                        column.setHasLabels(hasLabels);//设置是否显示每个柱状图的高度，
                        column.setHasLabelsOnlyForSelected(hasLabelForSelected);//点击的时候是否显示柱状图的高度，和setHasLabels()和用的时候，setHasLabels()失效
                        columns.add(column);
                    }
                    mColumnChartData = new ColumnChartData(columns);//表格的数据实例
                    if (hasAxes) {
                        Axis axisX = new Axis();
                        List<AxisValue> axisValues = new ArrayList<AxisValue>();
                        //   axisX.setInside(true);//是否显示在里面，默认为false
                        for (int i=0;i<numColumns;i++)
                        {
                            AxisValue value=new AxisValue(i);
                            value.setLabel(infoIds.get(i).getKey().toString());
                            axisValues.add(value);
                        }
                        axisX.setValues(axisValues);
                        Axis axisY = new Axis().setHasLines(true);
                        if (hasAxesNames) {
                            axisX.setName("用户ID");//设置X轴的注释
                            axisY.setName("点单总量");//设置Y轴的注释
                        }
                        mColumnChartData.setAxisXBottom(axisX);//设置X轴显示的位置
                        mColumnChartData.setAxisYLeft(axisY);//设置Y轴显示的位置
                    } else {
                        mColumnChartData.setAxisXBottom(null);
                        mColumnChartData.setAxisYLeft(null);
                    }
                    mColumnChartView.setColumnChartData(mColumnChartData);//为View设置数据

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
