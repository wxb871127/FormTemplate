package com.convert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *  体检表单输入数据格式转换  适用于个性化的格式
 */
public class TjInputConverter implements Converter<Object, Map>{
    @Override
    public Map convert(Object value) {
        Map<String, Object> map = new HashMap<>();
////        JSONObject jsonObject = value;
//        JSONArray jsonArray = jsonObject.optJSONArray("jbxx");
//        for(int i=0; i<jsonArray.length(); i++) {
//            JSONObject json = jsonArray.optJSONObject(i);
//            Object object = json.optDouble("value");
//            if(object.equals(Double.NaN))
//                object = json.optString("value");
//            map.put(json.optString("code"), object);
//        }
//        jsonArray = jsonObject.optJSONArray("yyjl");
//        map.put("yyjl", jsonArray);
        return map;
    }
}
