package template.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import template.bean.BaseTemplate;
import template.bean.ListTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.config.TemplateConfig;
import template.widget.tree.Node;

public class ListTemplateView extends BaseTemplateView<ListTemplate>{
    private RecyclerView recyclerView;
    private LinearLayout add;
    private Adapter adapter;
    private int attrWidth;
    private OnListTemplateViewListener templateViewListener;

    public interface OnListTemplateViewListener{
        void onDataDelete(BaseTemplate template, int index);
        void onClickAdd(BaseTemplate template);
        void onItemViewClick(BaseTemplate template, int index);
    }

    public void setTemplateViewListener(OnListTemplateViewListener listener){
        templateViewListener = listener;
    }

    public ListTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return TemplateConfig.LIST_TYPE;
    }


    @Override
    public int getContentLayout() {
        return R.layout.template_list_content;
    }

    @Override
    protected int getSpinnerLayout() {
        return R.layout.template_list_spinner;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        add = (LinearLayout) holder.getViewById(R.id.template_list_add);
        recyclerView = (RecyclerView) holder.getViewById(R.id.template_list_list);
//        attr.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                attr.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                attrWidth = attr.getMeasuredWidth();
//            }
//        });
//
//        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                label.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                int width = layout.getMeasuredWidth();
//                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - attrWidth - node.getLevel()*20, ViewGroup.LayoutParams.WRAP_CONTENT);
//                spinner.setLayoutParams(layoutParams);
//            }
//        });
    }

    @Override
    public  void initView(BaseViewHolder holder, Node node, final ListTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(false);
        adapter = new Adapter();
        super.initView(holder, node, template, value);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(templateViewListener != null)
                    templateViewListener.onClickAdd(template);
            }
        });
        setEdit(value.editable);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setClickable(value.editable);
        String[] ret = null;
        if(value.showValue != null && !TextUtils.isEmpty(value.showValue))
            ret =  value.showValue.split("/");
        adapter.setEdit(value.editable);
        adapter.setValue(ret);
        recyclerView.setAdapter(adapter);
    }

    protected void setEdit(boolean editable) {
        adapter.setEdit(editable);
        if(!editable || value.refuse)
            add.setVisibility(View.GONE);
        else
            add.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    public class Adapter extends RecyclerView.Adapter{
        private String[] value;
        private boolean edit;

        public Adapter(){

        }

        public void setEdit(boolean edit){
            this.edit = edit;
        }

        public void setValue(String[] strings){
            this.value = strings;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.template_list_item, parent, false));
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
                        templateViewListener.onDataDelete(template, position);
                }
            });
            ((BaseViewHolder) holder).getConvertView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (templateViewListener != null)
                        templateViewListener.onItemViewClick(template, position);
                }
            });
            ((BaseViewHolder) holder).getConvertView().setClickable(edit);

            if (!edit) {
                delete.setVisibility(GONE);
                linearLayout.setBackgroundResource(R.drawable.template_bg_color_gray_border);
                itemText.setTextColor(getResources().getColor(R.color.B0));
            } else {
                delete.setVisibility(VISIBLE);
                linearLayout.setBackgroundResource(R.drawable.template_bg_color_white_border);
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