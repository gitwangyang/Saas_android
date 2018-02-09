package com.mmbao.saas.utils.im.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 用户订单消息
 */
public class OrderMessageEntity implements Serializable {

    private String title;
    private String price;
    private String imgUrl;
    private String itemUrl;

    public OrderMessageEntity(String title, String price, String imgUrl, String itemUrl) {
        this.title = title;
        this.price = price;
        this.imgUrl = imgUrl;
        this.itemUrl = itemUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonMsgType = new JSONObject();
        try {
            jsonObject.put("title", this.title);
            jsonObject.put("price", this.price);
            jsonObject.put("img_url", this.imgUrl);
            jsonObject.put("item_url", this.itemUrl);
            jsonMsgType.put("order", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonMsgType;
    }


    public static OrderMessageEntity getEntityFromJSONObject(JSONObject jsonMsgType) {
        try {
            JSONObject jsonOrder = jsonMsgType.getJSONObject("order");
            String title = jsonOrder.getString("title");
            String price = jsonOrder.getString("price");
            String imgUrl = jsonOrder.getString("img_url");
            String itemUrl = jsonOrder.getString("item_url");
            OrderMessageEntity entity = new OrderMessageEntity(title, price, imgUrl, itemUrl);
            return entity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
