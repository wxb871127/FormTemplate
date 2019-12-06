package com.convert;

import com.google.gson.Gson;

import org.json.JSONObject;
import java.util.Map;

/**
 *  标准表单输入格式转换  用于标准随访、专项、评估
 */

public class InputConverter implements Converter<Object, Map>{

    @Override
    public Map convert(Object value) {
        return new Gson().fromJson(value.toString(), Map.class);
    }
}
