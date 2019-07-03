package com.convert;

import org.json.JSONObject;
import java.util.Map;

/**
 *   标准输入输出数据类型转换 适用于随访、专项、评估
 */

public class TemplateConverterFactory extends Converter.Factory{
    @Override
    public Converter<JSONObject, ?> inputConverter() {
        return new InputConverter();
    }

    @Override
    public Converter<Map, JSONObject> outputConverter() {
        return new OutputConverter();
    }
}
