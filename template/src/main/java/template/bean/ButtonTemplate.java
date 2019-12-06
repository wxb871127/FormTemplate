package template.bean;

import android.content.Context;
import android.text.TextUtils;

import com.wadata.customexpressionlib.ExpressionHelper;

import base.annotation.Template;

@Template(tag = "button")
public class ButtonTemplate extends BaseTemplate{
    @Override
    public String getShowName(Object object, Context context) {
        if(object == null) return "";
        String str = ExpressionHelper.calculateCommand(command, object.toString());
        if(str == null) return "";
        return str;
    }
}
