package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import activity.state.TemplateStateContext;
import template.com.form.R;
import template.config.TemplateConfig;
import template.widget.TemplateView;

/**
 * 废弃继承方式，防止Activity类数量爆炸
 * 采用状态模式 简化Activity并扩展state 可实现Acitivy状态切换
 */
public class TemplateActivity extends AppCompatActivity {
    TemplateStateContext stateContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity);
        String templateName = getIntent().getStringExtra("templateName");
        setTitle(templateName);

        stateContext = new TemplateStateContext();
        stateContext.initContext(this, templateName);
        if(stateContext.getTemplateState() == null){
            Log.e("TemplateActivity", "找不到对应的服务配置");
            return;
        }
        stateContext.getTemplateState().showBottomView(this,(ViewGroup)findViewById(R.id.template_bottom));

        TemplateConfig.initConfig(this);
        TemplateView templateView = (TemplateView) findViewById(R.id.templateView);
        templateView.initTemplate(stateContext.getTemplateState().getTemplateRes());
        templateView.setValue("xm", "林浩");
        templateView.setValue("jtdh","122");
        templateView.setValue("jzlx","1");
        templateView.setValue("lrr","124");
        templateView.setValue("ycqshfszlcsxs","1,3");
        try {
            templateView.setValue("gmsList",new JSONArray("[ {\"xh\":\"1\",\"gmy\":\"1\",\"fsrq\":\"2018-11-21\",\"bz\":\"是非观\"}, {\"xh\":\"2\",\"gmy\":\"2\",\"fsrq\":\"2018-12-21\",\"bz\":\"手工课\"}]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        templateView.notifyData();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        stateContext.getTemplateState().addMenuView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        stateContext.getTemplateState().onMenuSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
