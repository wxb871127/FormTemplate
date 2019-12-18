package template.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import template.bean.BaseTemplate;
import template.bean.InputTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.widget.tree.Node;

public class InputTemplateView extends BaseTemplateView<InputTemplate> {
    private static final Map<String, Integer> TYPE = new HashMap<String, Integer>();

    static {
        TYPE.put("none", 0x0);
        TYPE.put("text", 0x1);
        TYPE.put("textCapCharacters", 0x1001);
        TYPE.put("textCapWords", 0x2001);
        TYPE.put("textCapSentences", 0x4001);
        TYPE.put("textAutoCorrect", 0x8001);
        TYPE.put("textAutoComplete", 0x10001);
        TYPE.put("textMultiLine", 0x20001);
        TYPE.put("textImeMultiLine", 0x40001);
        TYPE.put("textNoSuggestions", 0x80001);
        TYPE.put("textUri", 0x11);
        TYPE.put("textEmailAddress", 0x21);
        TYPE.put("textEmailSubject", 0x31);
        TYPE.put("textShortMessage", 0x41);
        TYPE.put("textLongMessage", 0x51);
        TYPE.put("textPersonName", 0x61);
        TYPE.put("textPostalAddress", 0x71);
        TYPE.put("textPassword", 0x81);
        TYPE.put("textVisiblePassword", 0x91);
        TYPE.put("textWebEditText", 0xa1);
        TYPE.put("textFilter", 0xb1);
        TYPE.put("textPhonetic", 0xc1);
        TYPE.put("textWebEmailAddress", 0xd1);
        TYPE.put("textWebPassword", 0xe1);
        TYPE.put("number", 0x2);
        TYPE.put("numberSigned", 0x1002);
        TYPE.put("numberDecimal", 0x2002);
        TYPE.put("numberPassword", 0x12);
        TYPE.put("phone", 0x3);
        TYPE.put("datetime", 0x4);
        TYPE.put("date", 0x14);
        TYPE.put("time", 0x24);
    }
    private TextView text;
    private EditText editText;
    private TextView unit;
    private ImageView quote;
    private OnInputTemplateViewListener listener;
    public interface OnInputTemplateViewListener{
        void onClickQuote(BaseTemplate template);
    }

    public InputTemplateView(Context context) {
        super(context);
    }

    public void setListener(OnInputTemplateViewListener listener){
        this.listener = listener;
    }

    @Override
    public int getType() {
        return INPUT_TYPE;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.template_input_content;
    }

    @Override
    protected void initContentView() {
        text = (TextView) holder.getViewById(R.id.template_text);
        editText = (EditText) holder.getViewById(R.id.template_edit);
        unit = (TextView) holder.getViewById(R.id.template_input_unit);
        quote = (ImageView)holder.getViewById(R.id.common_template_quote);
    }

    @Override
    public void initView(BaseViewHolder holder, Node node, final InputTemplate template, TemplateValue value) {
        super.initView(holder, node, template, value);
        unit.setText(template.unit);
        if ("true".equals(template.quote)) {
            quote.setVisibility(VISIBLE);
        }else
            quote.setVisibility(GONE);

        quote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClickQuote(template);
            }
        });
    }

    @Override
    protected void setValueEdit(boolean editable) {
        super.setValueEdit(editable);
        if (editable) {
            text.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setHint("");
            if (!TextUtils.isEmpty(template.hint))
                editText.setHint(template.hint);
            else
                editText.setHint("请输入" + template.getShowName(template.label, null));

            if (TextUtils.isEmpty(template.inputType)) {
                editText.setInputType(0x20001);
            } else
                editText.setInputType(TYPE.get(template.inputType));
            editText();
            editText.setText(value.showValue);
            if (template.maxLength > 0) {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(template.maxLength)});
            }
            editText.setSelection(editText.getText().length());
            text.setTextColor(getResources().getColor(R.color.black));
            holder.getConvertView().setClickable(true);
            quote.setClickable(true);
        } else {
            text.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            text.setText(value.showValue);
            text.setTextColor(getResources().getColor(R.color.B0));
            holder.getConvertView().setClickable(false);
            quote.setClickable(false);
        }
    }

    private void editText(){
        if (editText.getTag() instanceof TextWatcher) {//防止recyclerView刷新 触发TextWatcher事件
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("number".equals(template.inputType) || "numberDecimal".equals(template.inputType)) {
                    try {
                        notifyItemViewData(new BigDecimal(s.toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else
                    notifyItemViewData(s.toString());
            }
        };
        editText.setTag(textWatcher);
        editText.addTextChangedListener(textWatcher);
    }

    @Override
    public void onFouces() {
        editText.requestFocus();
    }

    public int getSelectionStart(){
        return editText.getSelectionStart();
    }

    public String getText(){
        return editText.getText().toString();
    }
}
