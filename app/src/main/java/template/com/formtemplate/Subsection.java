package template.com.formtemplate;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import base.util.TemplateList;
import template.bean.CustomTemplate;
import template.config.CustomView;
import template.interfaces.OnTemplateListener;
import template.widget.BaseViewHolder;

public class Subsection implements CustomView {
    private RecyclerView recyclerView;
    private Map<String, Object> map;
    private Map<String, String> bzMap = new HashMap<>();//医生备注
    private Context mContext;
    private OnTemplateListener listener;
    private CustomTemplate template;

    @Override
    public int getLayout() {
        return R.layout.xj_layout;
    }

    @Override
    public void initView(Context context, BaseViewHolder holder, Map valueMap, TemplateList templates, CustomTemplate template, Map attrMap, final OnTemplateListener listener) {
        recyclerView = (RecyclerView) holder.getViewById(R.id.list);
        this.mContext = context;
        this.listener = listener;
        this.template = template;
        StringBuilder builder = new StringBuilder();
        map = new HashMap<>();
        for(Object key : attrMap.keySet()){
            boolean ret = ((JSONObject)attrMap.get(key)).optBoolean("exception");
            if(ret){
                builder.append(templates.getTemplate(key.toString()).label).append(":");
                String showName = templates.getTemplate(key.toString()).getShowName(valueMap.get(key), context);
                if(showName != null)
                    builder.append(showName);
                map.put(key.toString(), builder.toString());
                builder.delete(0, builder.length());
            }
        }
        Adapter adapter = new Adapter(map.keySet().toArray());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    public void onDataChanged(){
        StringBuilder builder = new StringBuilder();
        for(String key : map.keySet()){
            builder.append(map.get(key));
            builder.append(bzMap.get(key)).append(";");
        }

        if(listener != null){
            listener.onDataChanged(template, builder.toString());
        }

    }

    public class Adapter extends RecyclerView.Adapter{
        private Object keys[];
        public Adapter(Object keys[]){
            this.keys = keys;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.xj_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TextView excption =  (TextView) ((BaseViewHolder) holder).getViewById(R.id.excption);
            EditText bz = (EditText)((BaseViewHolder)holder).getViewById(R.id.bz);
            excption.setText(map.get(keys[position]).toString());
            bz.setText(bzMap.get(keys[position]));

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bzMap.put(keys[position].toString(), s.toString());
                    onDataChanged();
                }
            };
            bz.setTag(textWatcher);
            bz.addTextChangedListener(textWatcher);

        }

        @Override
        public int getItemCount() {
            if(keys == null)
                return 0;
            return keys.length;
        }
    }
}
