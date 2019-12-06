package com.convert;

import org.json.JSONObject;
import java.util.Map;

/**
 *  表单数据转换器
 */
public interface Converter<F, T> {
    T convert(F value);//将类型F转换成类型T
    abstract class Factory{
        public Converter<Object, ?> inputConverter(){//表单输入的数据：将JSON转换成map
            return null;
        }

        public Converter<Map, Object> outputConverter(){//表单输出的数据：将Map转换成JSON
            return null;
        }
    }
}
