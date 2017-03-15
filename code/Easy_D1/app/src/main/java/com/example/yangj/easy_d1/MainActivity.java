package com.example.yangj.easy_d1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.oushangfeng.marqueelayout.MarqueeLayout;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;
import com.oushangfeng.marqueelayout.OnItemClickListener;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bumptech.glide.Glide;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private MarqueeLayout mMarqueeLayout;
    private MarqueeLayout mMarqueeLayout1;

    private List<String> mSrcList;
    private MarqueeLayoutAdapter<String> mSrcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mMarqueeLayout = (MarqueeLayout) findViewById(R.id.marquee_layout);
        mSrcList = new ArrayList<>();
        mSrcList.add("品味经典 麦田风味");
        mSrcList.add("~~~~~~~水果大狂欢~~~~~~");
        mSrcList.add("~~~~谷物飘香~~~~");
        mSrcList.add("炎炎夏日~~~~~~~~第二杯半价");
        mSrcAdapter = new MarqueeLayoutAdapter<String>(mSrcList) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_simple_text;
            }

            @Override
            public void initView(View view, int position, String item) {
                ((TextView) view).setText(item);
            }
        };
        mMarqueeLayout.setAdapter(mSrcAdapter);
        mMarqueeLayout.start();

        mMarqueeLayout1 = (MarqueeLayout) findViewById(R.id.marquee_layout1);
        final List<String> imgs = new ArrayList<>();
        imgs.add("https://s-media-cache-ak0.pinimg.com/564x/0d/3d/8d/0d3d8db7e2868a21af88f12421d95756.jpg");
        imgs.add("https://s-media-cache-ak0.pinimg.com/564x/bb/49/52/bb4952e51ad739f3b868abf793cd7e29.jpg");
        imgs.add("https://s-media-cache-ak0.pinimg.com/564x/bb/1a/5f/bb1a5fcd3aea274814c8dd6fab66f824.jpg");
        imgs.add("https://s-media-cache-ak0.pinimg.com/564x/5e/f9/d9/5ef9d97092f6550f49b3a8686ca3aaa1.jpg");
        MarqueeLayoutAdapter<String> adapter1 = new MarqueeLayoutAdapter<String>(imgs) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_simple_image;
            }

            @Override
            public void initView(View view, int position, String item) {
                Glide.with(view.getContext()).load(item).into((ImageView) view);
            }
        };
        // 设置点击事件，第二个参数为不定长id，为想要点击的view的id，若为空或者不传的话默认为点击最外层的view
        adapter1.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("TAG", "MainActivity-74行-onClick(): " + position);
            }
        }, R.id.iv);

        mMarqueeLayout1.setAdapter(adapter1);
        mMarqueeLayout1.start();

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
// 使用getString方法获得value，注意第2个参数是value的默认值
        final String email = sharedPreferences.getString("email", "Easy D1 User");
        final String password = sharedPreferences.getString("password", "");
        Long uid = sharedPreferences.getLong("userID", -1);
        Bundle extras = getIntent().getExtras();
        String mode = null;
        if (extras != null) mode = extras.getString("mode");
        boolean flag = (mode != null && mode.equals("login"));
        if (email!=null && !email.equals("Easy D1 User") && !flag) {
            String urlString = this.getString(R.string.serverUrl) + "login?name=" + email + "&password=" + password;
            HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
                public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                    Long userID = -1L;
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    String s="";
                    try {
                        s=new String(response,"utf-8");
                        JSONObject object=new JSONObject(s);
                        userID=object.getLong("id");
                    } catch (UnsupportedEncodingException e)
                    {

                    } catch (JSONException e) {
                        editor.putInt("isLogin", -1);
                        editor.commit();//提交修改
                    }


                    editor.putString("userInfo",s);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putLong("userID", userID);
                    editor.putInt("isLogin", 1);
                    editor.commit();//提交修改
                }

                ;

                public void onFailure(int state, Header[] header, byte[] response, Throwable arg0) { // 失败，调

                }

                ;

                public void onFinish() { // 完成后调用，失败，成功，都要掉
                }

                ;
            });

        }


        View v = navigationView.getHeaderView(0);
        TextView editText = (TextView) v.findViewById(R.id.textView);
        editText.setText(email);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle login
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo",
                    Activity.MODE_PRIVATE);
            final int isLogin = sharedPreferences.getInt("isLogin", -1);
            if (isLogin==1)
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("userInfo",sharedPreferences.getString("userInfo",""));
                startActivity(intent);
            }
            else {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }

        } else if (id == R.id.nav_gallery) {
            //显示所有订单

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, OrderActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ShowMapActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, statisticsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_shop) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ShopActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MessageActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.yangj.easy_d1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.yangj.easy_d1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
