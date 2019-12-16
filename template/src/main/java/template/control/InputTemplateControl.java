package template.control;

import android.content.Context;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.InputTemplate;
import template.widget.BaseTemplateView;
import template.widget.InputTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.InputTemplateDialog;

@Template(tag = "input")
public class InputTemplateControl extends BaseTemplateControl{

    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return InputTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(final Context context) {
        this.context = context;
        final InputTemplateView templateView = new InputTemplateView(context);
        templateView.setListener(new InputTemplateView.OnInputTemplateViewListener() {
            @Override
            public void onClickQuote(final BaseTemplate template) {
                dialog = getDialog(context, template);
                if(dialog != null){
                    dialog.initDialog(template, null);
                    ((InputTemplateDialog)dialog).setListener(new InputTemplateDialog.OnInputDialogListener() {
                        @Override
                        public void addQuote(String quote) {
                            int index = templateView.getSelectionStart();
                            String value = templateView.getText();
                            StringBuilder builder = new StringBuilder(value);
                            builder.insert(index, quote);
                            if(listener != null)
                                listener.onDataChanged(template, builder.toString(), true);
                        }
                    });
                    dialog.showDialog();
                }
            }
        });
        return templateView;
    }

    @Override
    protected void onClickHolder(BaseTemplate template, Object value) {

    }

    @Override
    protected Object getRealValue(Object value) {
        if(value == null) return "";
        if(TextUtils.isEmpty(((InputTemplate)template).decimalFormat))
            ((InputTemplate) template).decimalFormat = "0.00";
        BigDecimal decimal = new BigDecimal(value.toString());
        return new DecimalFormat(((InputTemplate)template).decimalFormat).format(decimal);
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        InputTemplateDialog templateDialog = new InputTemplateDialog(context);
        return templateDialog;
    }
}
