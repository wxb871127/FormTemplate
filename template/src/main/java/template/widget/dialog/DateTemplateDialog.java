package template.widget.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import java.util.Calendar;
import base.util.DateUtil;
import template.bean.DateTemplate;

public class DateTemplateDialog extends BaseTemplateDialog<DateTemplate>
        implements DatePickerDialog.OnDateSetListener,DialogInterface.OnClickListener {
    public DateTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public void initDialog(DateTemplate template, Object value) {
        super.initDialog(template, value);
        updateDialog(value);
    }

    @Override
    public void updateDialog(Object value) {
        super.updateDialog(value);
        Calendar calendar = DateUtil.getCalendar(
                DateUtil.getDate(template.getShowName(value, mContext), "yyyy-MM-dd"));
        dialog = new DatePickerDialog(mContext, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        String date = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd");
        if(listener != null)
            listener.onDataChange(template, date);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(listener != null)
            listener.onDataChange(template, "");
    }
}
