package com.convert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

import template.bean.TemplateValue;

public class TjOutputConverter implements Converter<Map, Object>{
    @Override
    public Object convert(Map valueMap) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray();
            for(Object key : valueMap.keySet()){
                JSONObject jsonObject = new JSONObject();
                TemplateValue  templateValue = (TemplateValue) valueMap.get(key);
                if(templateValue.value == null)
                    jsonObject.put(key.toString(), "");
                else
                    jsonObject.put(key.toString(), templateValue.value);
                jsonObject.put("exception", templateValue.exception);
                jsonObject.put("refuse", templateValue.refuse);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
