package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import template.bean.SelectTemplate;

public class SelectTemplateDialog extends BaseTemplateDialog<SelectTemplate>
        implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {

    private String[] items;
    private List<String> selectedItem;
    public SelectTemplateDialog(Context mContext) {
        super(mContext);
        selectedItem = new ArrayList<>();
    }

    @Override
    public <String> void initDialog(SelectTemplate template, String value) {
        super.initDialog(template, value);
        java.lang.String v = "";
        if(value != null)
            v = value.toString();
        boolean[] checkedItem = template.getSelectItems(v);
        items = template.getNames();
        selectedItem.clear();
        if(v.contains(",")) {
            java.lang.String[] vs = v.split(",");
            for (int i = 0; i < vs.length; i++)
                selectedItem.add(vs[i]);
        }

		dialog = new AlertDialog.Builder(mContext)
                .setMultiChoiceItems(items, checkedItem,this)
				.setPositiveButton("确定", this)
				.setNegativeButton("取消", this)
				.setNeutralButton("清空", this).create();
		if (!TextUtils.isEmpty(template.label)) {
			dialog.setTitle(template.label);
		}
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if(isChecked)
            selectedItem.add(template.getCode(items[which]));
        else
            selectedItem.remove(template.getCode(items[which]));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String selected = "";
        if(DialogInterface.BUTTON_NEUTRAL == which){
            selected =  "";
            selectedItem.clear();
            if(listener != null)
                listener.onDataChange(template.name, "");
        }else if(DialogInterface.BUTTON_POSITIVE == which){
            for(int i=0; i<selectedItem.size(); i++){
                if(i != selectedItem.size())
                    selected += selectedItem.get(i) + ",";
                else selected += selectedItem.get(i);
            }
            if(listener != null)
                listener.onDataChange(template.name, selected);
        }
    }
}
