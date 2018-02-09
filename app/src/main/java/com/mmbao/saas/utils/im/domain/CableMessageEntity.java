package com.mmbao.saas.utils.im.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by BaJie on 2017/1/13.
 */
public class CableMessageEntity implements Serializable {

    private String msg;

    public CableMessageEntity(String message) {
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", this.msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static CableMessageEntity getEntityFromJSONObject(JSONObject jsonMsgType) {
        try {
            String message = jsonMsgType.getString("title");
            CableMessageEntity entity = new CableMessageEntity(message);
            return entity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
