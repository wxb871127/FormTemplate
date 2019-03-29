package template.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

import template.bean.InputTemplate;

public class InputTemplateView extends BaseTemplateView<InputTemplate> {
    private static final Map<String, Integer> INPUT_TYPE = new HashMap<String, Integer>();
    static {
        INPUT_TYPE.put("none", 0x0);
        INPUT_TYPE.put("text", 0x1);
        INPUT_TYPE.put("textCapCharacters", 0x1001);
        INPUT_TYPE.put("textCapWords", 0x2001);
        INPUT_TYPE.put("textCapSentences", 0x4001);
        INPUT_TYPE.put("textAutoCorrect", 0x8001);
        INPUT_TYPE.put("textAutoComplete", 0x10001);
        INPUT_TYPE.put("textMultiLine", 0x20001);
        INPUT_TYPE.put("textImeMultiLine", 0x40001);
        INPUT_TYPE.put("textNoSuggestions", 0x80001);
        INPUT_TYPE.put("textUri", 0x11);
        INPUT_TYPE.put("textEmailAddress", 0x21);
        INPUT_TYPE.put("textEmailSubject", 0x31);
        INPUT_TYPE.put("textShortMessage", 0x41);
        INPUT_TYPE.put("textLongMessage", 0x51);
        INPUT_TYPE.put("textPersonName", 0x61);
        INPUT_TYPE.put("textPostalAddress", 0x71);
        INPUT_TYPE.put("textPassword", 0x81);
        INPUT_TYPE.put("textVisiblePassword", 0x91);
        INPUT_TYPE.put("textWebEditText", 0xa1);
        INPUT_TYPE.put("textFilter", 0xb1);
        INPUT_TYPE.put("textPhonetic", 0xc1);
        INPUT_TYPE.put("textWebEmailAddress", 0xd1);
        INPUT_TYPE.put("textWebPassword", 0xe1);
        INPUT_TYPE.put("number", 0x2);
        INPUT_TYPE.put("numberSigned", 0x1002);
        INPUT_TYPE.put("numberDecimal", 0x2002);
        INPUT_TYPE.put("numberPassword", 0x12);
        INPUT_TYPE.put("phone", 0x3);
        INPUT_TYPE.put("datetime", 0x4);
        INPUT_TYPE.put("date", 0x14);
        INPUT_TYPE.put("time", 0x24);
    }

    public InputTemplateView(Context context) {
        super(context);
    }


    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void initView(BaseViewHolder holder, final InputTemplate template, String value, final boolean editable) {
        super.initView(holder, template, value, editable);
        holder.getConvertView().setClickable(false);

        if(editable){
            text.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setHint("");
            if(!TextUtils.isEmpty(template.hint))
                editText.setHint("请输入" + template.hint);
            else
                editText.setHint("请输入" + template.getShowName(template.label, null));


            if (TextUtils.isEmpty(template.inputType)) {
                editText.setInputType(0x20001);
            }else
                editText.setInputType(INPUT_TYPE.get(template.inputType));

           if(editText.getTag() instanceof TextWatcher){//防止recyclerView刷新 触发TextWatcher事件
                editText.removeTextChangedListener((TextWatcher)editText.getTag());
           }

            editText.setText(value);
            if (template.maxLength > 0) {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(template.maxLength)});
            }
            editText.setSelection(editText.getText().length());
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(templateListener != null)
                        templateListener.onDataChange(template.name, s.toString());
                }
            };
            editText.setTag(textWatcher);
            editText.addTextChangedListener(textWatcher);
        }else {
            text.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            text.setText(value);
        }
    }
}
