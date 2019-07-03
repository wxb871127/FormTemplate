package com.convert;

import com.google.gson.Gson;

import org.json.JSONObject;
import java.util.Map;

/**
 *  标准表单输入格式转换  用于标准随访、专项、评估
 */

public class InputConverter implements Converter<JSONObject, Map>{

    @Override
    public Map convert(JSONObject... value) {
        return new Gson().fromJson(value[0].toString(), Map.class);
    }
}
