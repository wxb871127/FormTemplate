package template.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;

import template.bean.Attr;
import template.bean.ListTemplate;
import template.com.form.R;

public class ListTemplateView extends BaseTemplateView<ListTemplate>{
    private RecyclerView recyclerView;
    private JSONArray data;
    private OnListTemplateViewListener templateViewListener;

    public interface OnListTemplateViewListener{
        public void onDataDelete(int index);
        public void onClickAdd();
        public void onItemViewClick(int index);
    }

    public void setTemplateViewListener(OnListTemplateViewListener listener){
        templateViewListener = listener;
    }

    public ListTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public int getlayout() {
        return R.layout.list_template;
    }

    @Override
    public  void initView(BaseViewHolder holder, ListTemplate template, Object value, Attr attr) {
        holder.getConvertView().setClickable(false);
        super.initView(holder, template, value, attr);
        ImageView add = (ImageView) holder.getViewById(R.id.template_list_add);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(templateViewListener != null)
                    templateViewListener.onClickAdd();
            }
        });
//        add.setClickable(editable);
        if(!attr.editable)
            add.setVisibility(View.INVISIBLE);
        else add.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) holder.getViewById(R.id.template_list_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setClickable(attr.editable);
        String[] ret = null;
        if(!TextUtils.isEmpty(value.toString()))
            ret =  value.toString().split("/");

        recyclerView.setAdapter(new Adapter(ret, attr.editable));
    }

    public class Adapter extends RecyclerView.Adapter{
        private String[] value;
        private boolean edit;

        public Adapter(String[] strings, boolean edit){
            this.value = strings;
            this.edit  = edit;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_template_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ImageView delete = (ImageView) ((BaseViewHolder) holder).getViewById(R.id.template_list_item_delete);
            TextView itemText = (TextView) ((BaseViewHolder) holder).getViewById(R.id.template_list_item_text);
            LinearLayout linearLayout = (LinearLayout) ((BaseViewHolder) holder).getViewById(R.id.common_template_box);
            itemText.setText(value[position]);

            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (templateViewListener != null)
                        templateViewListener.onDataDelete(position);
                }
            });
            ((BaseViewHolder) holder).getConvertView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (templateViewListener != null)
                        templateViewListener.onItemViewClick(position);
                }
            });
            ((BaseViewHolder) holder).getConvertView().setClickable(edit);

            if (!edit) {
                delete.setVisibility(GONE);
                linearLayout.setBackgroundResource(R.drawable.bg_color_gray_border);
                itemText.setTextColor(getResources().getColor(R.color.B0));
            } else {
                linearLayout.setBackgroundResource(R.drawable.bg_color_white_border);
                itemText.setTextColor(getResources().getColor(R.color.black));
            }
        }

        @Override
        public int getItemCount() {
            if(value == null)
                return 0;
            return this.value.length;
        }
    }


}