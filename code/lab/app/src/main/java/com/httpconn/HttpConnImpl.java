package com.httpconn;

import android.content.Intent;
import android.widget.Toast;

import com.bean.Order;
import com.example.yangj.easy_d1.LoginActivity;
import com.example.yangj.easy_d1.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Vorringer on 2016/7/8.
 */
public class HttpConnImpl implements HttpConn
{
    private JSONArray orderResult;
    public JSONArray getAllOrder()
    {
        String urlString = "http://59.78.45.3:8080/getAllOrder";
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
    public JSONArray getOrderByUser(int ID)
    {
        String urlString = "http://59.78.45.3:8080/getOrderByUser?id="+ID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
    public JSONArray getOrderByShop(int ID)
    {
        String urlString = "http://59.78.45.3:8080/getOrderByShop?id="+ID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
    public JSONArray getOrderByClient(int ID)
    {
        String urlString = "http://59.78.45.3:8080/getOrderByClient?id="+ID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
    public JSONArray getNewOrderByUser(int ID)
    {
        String urlString = "http://59.78.45.3:8080/getNewOrderByUser?id="+ID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
    public JSONArray getNewOrderByShop(int ID)
    {
        String urlString = "http://59.78.45.3:8080/getNewOrderByShop?id="+ID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
    public JSONArray getNewOrderByClient(int ID)
    {
        String urlString = "http://59.78.45.3:8080/getNewOrderByClient?id="+ID;
        HttpUtil.get(urlString, new AsyncHttpResponseHandler() {
            public void onSuccess(int state, Header[] header, byte[] response) { // 获取数据成功会调用这里
                try {
                    orderResult = new JSONArray(new String(response,"utf-8"));
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
        return orderResult;
    }
}
