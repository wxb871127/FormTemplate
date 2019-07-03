package com.convert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *  体检属性输入数据格式转换 用来解析体检的拒检、异常等属性
 */
public class TjAttrInputConverter implements Converter<JSONObject, Map>{
    @Override
    public Map convert(JSONObject... value) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = value[0];
        JSONArray jsonArray = jsonObject.optJSONArray("jbxx");
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject json = jsonArray.optJSONObject(i);
            JSONObject attrObject = new JSONObject();
            try {
                attrObject.put("exception", json.optBoolean("exception"));
                attrObject.put("refuse", json.optBoolean("refuse"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(json.optString("code"), attrObject);
        }
        return map;
    }
}
