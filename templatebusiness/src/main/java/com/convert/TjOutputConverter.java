package com.convert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TjOutputConverter implements Converter<Map, JSONObject>{
    @Override
    public JSONObject convert(Map... value) {
        try{
            Map<String, Object> valueMap = value[0];
            Map<String, Object> attrMap = value[1];
            JSONArray yyjl = (JSONArray) valueMap.get("yyjl");
            valueMap.remove("yyjl");
            Set<String> set = valueMap.keySet();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(String key: set){
                Object object = valueMap.get(key);
                JSONObject attr = (JSONObject) attrMap.get(key);
                JSONObject json = new JSONObject();
                json.put("code", key);
                if(object != null)
                    json.put("value",object);
                else json.put("value", "");
                if(attr != null) {
                    Iterator<String> it = attr.keys();
                    while (it.hasNext()) {
                        String attrkey = it.next();
                        json.put(attrkey, attr.optBoolean(attrkey));
                    }
                }
//                else {
//                    json.put("refuse", false);
//                    json.put("exception", false);
//                }
                jsonArray.put(json);
            }
            jsonObject.put("jbxx",jsonArray);
            jsonObject.put("yyjl", yyjl);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
