package com.convert;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *  标准表单输出格式转换  用于标准随访、专项、评估
 */
public class OutputConverter implements Converter<Map, JSONObject>{
    @Override
    public JSONObject convert(Map... value) {
        try {
            return new JSONObject(new Gson().toJson(value[0]));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
