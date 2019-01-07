package template.bean;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import base.annotation.Template;

@Template(tag = "select")
public class SelectTemplate extends RadioTemplate{

    public boolean[] getSelectItems(String value){
        boolean[] selectItems = new boolean[codes.size()];
        if(TextUtils.isEmpty(value))
            return selectItems;
        String[] values = value.split(",");
        for(String  v : values){
            int index = codes.indexOf(v);
            selectItems[index] = true;
        }
        return selectItems;
    }

    @Override
    public String getShowName(Object object, Context context) {
        if(object == null || "".equals(object.toString()))
            return "";

        String[] codes = object.toString().split(",");
        String name = "";
        for(int i=0; i<codes.length; i++){
            if(i != codes.length -1)
                name += getNameByCode(codes[i]) + ",";
            else name += getNameByCode(codes[i]);
        }
        return name;
    }
}
