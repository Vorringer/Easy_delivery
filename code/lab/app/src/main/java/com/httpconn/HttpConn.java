package com.httpconn;

import com.bean.Order;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;

/**
 * Created by Vorringer on 2016/7/8.
 */
public interface HttpConn {
    public JSONArray getAllOrder();
    public JSONArray getOrderByUser(int ID);
    public JSONArray getOrderByShop(int ID);
    public JSONArray getOrderByClient(int ID);
    public JSONArray getNewOrderByUser(int ID);
    public JSONArray getNewOrderByShop(int ID);
    public JSONArray getNewOrderByClient(int ID);

}
