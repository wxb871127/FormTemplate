package template.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import template.bean.ListTemplate;
import template.com.form.R;

public class ListTemplateView extends BaseTemplateView<ListTemplate>{
    private RecyclerView recyclerView;
    private JSONArray data;
    private OnListTemplateViewListener templateViewListener;

    public interface OnListTemplateViewListener{
        public void onDataDelete(Object object);
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
    public  void initView(BaseViewHolder holder, ListTemplate template, String value, boolean editable) {
        holder.getConvertView().setClickable(false);
        super.initView(holder, template, value, editable);
        ImageView add = (ImageView) holder.getViewById(R.id.template_list_add);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(templateViewListener != null)
                    templateViewListener.onClickAdd();
            }
        });

        recyclerView = (RecyclerView) holder.getViewById(R.id.template_list_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setClickable(editable);

        String[] ret =  value.split("/");
        recyclerView.setAdapter(new Adapter(ret));
    }

    public class Adapter extends RecyclerView.Adapter{
        private String[] value;

        public Adapter(String[] strings){
            this.value = strings;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_template_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ImageView delete = (ImageView) ((BaseViewHolder)holder).getViewById(R.id.template_list_item_delete);
            TextView itemText = (TextView)((BaseViewHolder)holder).getViewById(R.id.template_list_item_text);
            itemText.setText(value[position]);

            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = value[position];
                    if(templateViewListener != null)
                        templateViewListener.onDataDelete(str);
                }
            });
            ((BaseViewHolder) holder).getConvertView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(templateViewListener != null)
                        templateViewListener.onItemViewClick(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return this.value.length;
        }
    }


}