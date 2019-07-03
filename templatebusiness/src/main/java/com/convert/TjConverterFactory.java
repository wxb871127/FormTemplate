package com.convert;

import org.json.JSONObject;
import java.util.Map;

/**
 *   适用于北京体检系统的数据类型
 */
public class TjConverterFactory extends Converter.Factory{
    @Override
    public Converter<JSONObject, ?> inputConverter() {
        return new TjInputConverter();
    }

    @Override
    public Converter<Map, JSONObject> outputConverter() {
        return new TjOutputConverter();
    }

    @Override
    public Converter<JSONObject, ?> attrInputConverter() {
        return new TjAttrInputConverter();
    }
}
