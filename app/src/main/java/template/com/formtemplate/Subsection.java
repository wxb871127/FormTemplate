package template.com.formtemplate;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import base.util.TemplateList;
import template.bean.CustomTemplate;
import template.bean.TemplateValue;
import template.config.CustomView;
import template.interfaces.OnTemplateListener;
import template.widget.BaseViewHolder;

public class Subsection extends CustomView {
    private RecyclerView recyclerView;
    private EditText kszj;
    private Map<String, Object> map;
    private Context mContext;
    private OnTemplateListener listener;
    private CustomTemplate template;
    private Map<String, TemplateValue> valueMap;

    @Override
    protected int getLayout() {
        return R.layout.xj_layout;
    }

    @Override
    public void initView(Context context, BaseViewHolder holder, final Map<String, TemplateValue> valueMap,
                         TemplateList templates, final CustomTemplate template, Map<String, Object> codeMap, final OnTemplateListener listener) {
        recyclerView = (RecyclerView) holder.getViewById(R.id.list);
        kszj = (EditText)holder.getViewById(R.id.kszj);
        this.mContext = context;
        this.listener = listener;
        this.template = template;
        StringBuilder builder = new StringBuilder();
        map = new HashMap<>();
        this.valueMap = valueMap;

        for(String key : valueMap.keySet()){
            boolean ret = valueMap.get(key).exception;
            if(ret){
                builder.append(templates.getTemplate(key).label).append(":");
                String showName = templates.getTemplate(key).getShowName(valueMap.get(key).value, context);
                if(showName != null)
                    builder.append(showName);
                map.put(key, builder.toString());
                builder.delete(0, builder.length());
            }
        }
        Adapter adapter = new Adapter(map.keySet().toArray());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        TemplateValue value = valueMap.get(template.name);
        kszj.setText(value.exceptionDesc);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TemplateValue templateValue = valueMap.get(template.name);
                templateValue.exceptionDesc = s.toString();
                valueMap.put(template.name, templateValue);
            }
        };
        kszj.setTag(textWatcher);
        kszj.addTextChangedListener(textWatcher);
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
            TemplateValue templateValue = valueMap.get(keys[position]);
            bz.setText(templateValue.exceptionDesc);


            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    TemplateValue templateValue = valueMap.get(keys[position]);
                    templateValue.exceptionDesc = s.toString();
                    valueMap.put(keys[position].toString(), templateValue);


                    TemplateValue templateValue2 = valueMap.get(template.name);
                    StringBuilder builder = new StringBuilder();
                    for(String key : map.keySet()){
                        builder.append(map.get(key));
                        builder.append(valueMap.get(key).exceptionDesc).append(";");
                    }
                    templateValue2.value = builder.toString();
                    valueMap.put(template.name, templateValue2);
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
