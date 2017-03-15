package com.example.yangj.easy_d1;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RouteSearch;
import com.bean.Position;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.ACO;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
//import com.amap.map3d.demo.R;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;

import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;

import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;

/**
 * AMapV2地图中介绍定位三种模式的使用，包括定位，追随，旋转
 */
public class ShowMapActivity extends Activity implements LocationSource,
        AMapLocationListener,OnCheckedChangeListener,OnRouteSearchListener{
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private RadioGroup mGPSModeGroup;
    private Button routeButton;
    private TextView mLocationErrText;
    private LatLonPoint currentPoint;
    private RouteSearch routeSearch;
    private DriveRouteResult mDriveRouteResult;
    private List<LatLonPoint>throughPoints=new ArrayList<LatLonPoint>();
    private RouteSearch.FromAndTo fromAndTo;
    private List<Position>passBy;
    private List<LatLng>bestTour;
    private int mflag=0;
    private List<Polyline>lines=new ArrayList<Polyline>();
    private List<Marker>markers=new ArrayList<Marker>();
    private List<Long>orderIDs=new ArrayList<>();
    private List<Long>bestIDs=new ArrayList<>();
    private int nextAddress=1;
    private final double EARTH_RADIUS = 6378.137;
    private long lastRouteTime;
    private LatLonPoint sPrev;
    private int[] result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.content_show_map);
        mapView = (MapView) findViewById(R.id.map);
        routeButton=(Button)findViewById(R.id.route);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        lastRouteTime=System.currentTimeMillis();
        init();
    }

    protected double getDistance(LatLng p1,LatLng p2)
    {
        double radLat1 = Math.toRadians(p1.latitude);
        double radLat2 = Math.toRadians(p2.latitude);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(p1.longitude) - Math.toRadians(p2.longitude);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10;
        return s;
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
        mGPSModeGroup.setOnCheckedChangeListener(this);
        mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
        mLocationErrText.setVisibility(View.GONE);
    }

    private LatLng getLatlng(LatLonPoint p)
    {
        return new LatLng(p.getLatitude(),p.getLongitude());
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gps_locate_button:
                // 设置定位的类型为定位模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                break;
            case R.id.gps_follow_button:
                // 设置定位的类型为 跟随模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                break;
            case R.id.gps_rotate_button:
                // 设置定位的类型为根据地图面向方向旋转
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                break;
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                if (currentPoint==null) {
                    currentPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
                    getRoute();
                }
                else
                {
                    currentPoint.setLatitude(amapLocation.getLatitude());
                    currentPoint.setLongitude(amapLocation.getLongitude());
                }
                if (mflag==1)
                {
                    LatLng curr=new LatLng(currentPoint.getLatitude(),currentPoint.getLongitude());
                    LatLng next=bestTour.get(nextAddress);
                    LatLng prev=bestTour.get(nextAddress-1);
                    if (nextAddress!=1 && nextAddress!=bestTour.size()-1 && getDistance(curr,prev)>100)
                    {
                        markers.get(nextAddress-1).remove();
                        markers.set(nextAddress-1,aMap.addMarker(new MarkerOptions().position(prev)
                                .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier("poi_marker_"+(nextAddress-1), "drawable", getPackageName())))));
                    }
                    if (getDistance(curr,next)<200)
                    {
                        String urlString = this.getString(R.string.serverUrl)+"setOrderStatus?id="+bestIDs.get(nextAddress)+"&status=-1";
                        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
                            public void onSuccess(int state, Header[] header, byte[] response) {

                            };
                            public void onFailure(int state, Header[] header, byte[] response, Throwable arg0) { // 失败，调

                            };
                            public void onFinish() { // 完成后调用，失败，成功，都要掉
                            };
                        });
                        nextAddress++;
                    }
                }

                mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    public void drawMarker(View v){
        String urlString = this.getString(R.string.serverUrl)+"getAllOrder";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        if (orderObject.getInt("status")==0)
                        markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(orderObject.getDouble("latitude"),orderObject.getDouble("longitude")))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_pressed))));
                        else
                           markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(orderObject.getDouble("latitude"),orderObject.getDouble("longitude")))));
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
    public void showRoute() {
        String urlString = this.getString(R.string.serverUrl)+"getAllNewOrder";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    for (int i=0;i<markers.size();i++) markers.get(i).remove();
                    markers.clear();
                    for (int i=0;i<lines.size();i++) lines.get(i).remove();
                    lines.clear();
                    passBy=new ArrayList<Position>();
                    bestTour=new ArrayList<LatLng>();
                    bestIDs=new ArrayList<Long>();

                    passBy.add(new Position(currentPoint.getLatitude(),currentPoint.getLongitude()));
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        orderIDs.add(orderObject.getLong("id"));
                        passBy.add(new Position(orderObject.getDouble("latitude"),orderObject.getDouble("longitude")));
                    }
                    ACO aco = new ACO(passBy, 10, 50, 1.f, 5.f, 0.5f);
                    aco.init();
                    result=aco.solve();
                    Position p;
                    for (int i=0;i<result.length;i++)
                    {
                        p=passBy.get(result[i]);
                        double la=p.getLatitude();
                        double lg=p.getLongitude();
                        if (i==0) sPrev=new LatLonPoint(la,lg);
                        else
                        {
                            searchRouteResult(sPrev,new LatLonPoint(la,lg));
                            sPrev=new LatLonPoint(la,lg);
                        }
                        if (i==0 || i==result.length-1)
                            bestIDs.add(-1L);
                        else
                            bestIDs.add(orderIDs.get(result[i]-1));
                        bestTour.add(new LatLng(la,lg));
                        if (i!=0 && i!=result.length-1)
                        markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(la,lg))
                                .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier("poi_marker_"+i, "drawable", getPackageName())))));
                        else
                            markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(la,lg))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_start))));
                    }
                    //lines.add(aMap.addPolyline((new PolylineOptions()).addAll(bestTour).color(Color.argb(255,61,149,250))));

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
    public void showRouteInCache(View v)
    {
        if (bestTour==null || System.currentTimeMillis()-lastRouteTime>5000) {
            lastRouteTime= System.currentTimeMillis();
            showRoute();
        }
        else
        {
            aMap.clear();
            LatLng p;
            for (int i=0;i<bestTour.size();i++)
            {
                p=bestTour.get(i);
                double la=p.latitude;
                double lg=p.longitude;
                if (i==0) sPrev=new LatLonPoint(la,lg);
                else
                {
                    searchRouteResult(sPrev,new LatLonPoint(la,lg));
                    sPrev=new LatLonPoint(la,lg);
                }
                if (i!=0 && i!=bestTour.size()-1)
                    markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(la,lg))
                            .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier("poi_marker_"+i, "drawable", getPackageName())))));
                else
                    markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(la,lg))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_start))));
            }
            //lines.add(aMap.addPolyline((new PolylineOptions()).addAll(bestTour).color(Color.argb(255,61,149,250))));

        }
    }

    public void getRoute() {
        String urlString = this.getString(R.string.serverUrl)+"getAllNewOrder";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) {
                try {
                    passBy=new ArrayList<Position>();
                    bestTour=new ArrayList<LatLng>();
                    passBy.add(new Position(currentPoint.getLatitude(),currentPoint.getLongitude()));
                    JSONArray orderResult = new JSONArray(new String(response,"utf-8"));
                    for (int i=0;i<orderResult.length();i++)
                    {
                        JSONObject orderObject=(JSONObject)orderResult.get(i);
                        orderIDs.add(orderObject.getLong("id"));
                        passBy.add(new Position(orderObject.getDouble("latitude"),orderObject.getDouble("longitude")));
                    }
                    ACO aco = new ACO(passBy, 50, 50, 1.f, 5.f, 0.5f);
                    aco.init();
                    int[] result=aco.solve();
                    Position p;
                    for (int i=0;i<result.length;i++)
                    {
                        p=passBy.get(result[i]);
                        double la=p.getLatitude();
                        double lg=p.getLongitude();
                        if (i==0 || i==result.length-1)
                            bestIDs.add(-1L);
                        else
                            bestIDs.add(orderIDs.get(result[i]-1));
                        bestTour.add(new LatLng(la,lg));

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
            public void onFinish() {
                mflag=1;
            };
        });

    }
    public void searchRouteResult(LatLonPoint start, LatLonPoint end )
    {
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
// fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
// 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
        fromAndTo=new RouteSearch.FromAndTo(start,end);
        DriveRouteQuery query = new DriveRouteQuery(fromAndTo,RouteSearch.DrivingDefault, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
    }


        @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {}

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {}

        @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
            List<DrivePath> drivePath=result.getPaths();
            List<DriveStep> driveSteps=drivePath.get(0).getSteps();
            List<LatLng>passTemp=new ArrayList<LatLng>();
            List<LatLonPoint>temp=new ArrayList<>();
            for (int i=0;i<driveSteps.size();i++)
            {
                temp=driveSteps.get(i).getPolyline();
                if (i==driveSteps.size()-1)
                {
                    passTemp.add(getLatlng(temp.get(0)));
                    passTemp.add(getLatlng(temp.get(temp.size()-1)));
                }
                else
                    passTemp.add(getLatlng(temp.get(0)));
            }
            lines.add(aMap.addPolyline((new PolylineOptions()).addAll(passTemp).color(Color.argb(255,61,149,250))));

    }

}
