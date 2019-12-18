package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import activity.state.TemplateStateContext;
import template.com.templatebusiness.R;


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
        ViewGroup content =  findViewById(R.id.template_content);
        ViewGroup bottom = findViewById(R.id.template_bottom);
        stateContext = new TemplateStateContext(this);
        String templateName = getIntent().getStringExtra("templateName");
        stateContext.setTemplateState(templateName);
        String templateRes = getIntent().getStringExtra("templateRes");
        if(templateRes != null)
            stateContext.getTemplateState().setRes(templateRes);
        String templateData = getIntent().getStringExtra("templateData");
        try {
            if(!TextUtils.isEmpty(templateData))
                stateContext.getTemplateState().setTemplateData(new JSONObject(templateData));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initView(content, bottom);
    }

    /**
     *  组装当前状态下的View
     */
    private void initView(ViewGroup content, ViewGroup bottom){
        setTitle(stateContext.getTemplateState().getTemplateName());
        if(stateContext.getTemplateState() == null){
            Toast.makeText(this, "找不到对应的服务配置", Toast.LENGTH_LONG).show();
            return;
        }
        stateContext.getTemplateState().initContentView(content);
        stateContext.getTemplateState().initBottomView(bottom);
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
