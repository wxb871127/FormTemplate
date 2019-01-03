package template.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import template.bean.InputTemplate;

public class InputTemplateView extends BaseTemplateView<InputTemplate> {


    public InputTemplateView(Context context) {
        super(context);
    }


    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void initView(BaseViewHolder holder, final InputTemplate template, String value, boolean editable) {
        super.initView(holder, template, value, editable);
        if(editable){
            text.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setHint("");
            if(!TextUtils.isEmpty(template.hint))
                editText.setHint("请输入" + template.hint);
            if (TextUtils.isEmpty(template.inputType)) {
                editText.setInputType(0x20001);
            }

           if(editText.getTag() instanceof TextWatcher){//防止recyclerView刷新 触发TextWatcher事件
                editText.removeTextChangedListener((TextWatcher)editText.getTag());
           }

            editText.setText(value);
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
            text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }



}
