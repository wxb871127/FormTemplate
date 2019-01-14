package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;
import activity.state.TemplateStateContext;
import template.com.form.R;
import template.widget.TemplateMenuView;
import template.widget.TemplateView;
import template.widget.decorator.TemplateViewDecor;

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
        stateContext = new TemplateStateContext(this);
        String templateName = getIntent().getStringExtra("templateName");
        initView(templateName);
    }

    private void initView(String templateName){
        stateContext.setTemplateState(templateName);
        setTitle(stateContext.getTemplateState().getTemplateName());
        if(stateContext.getTemplateState() == null){
            Toast.makeText(this, "找不到对应的服务配置", Toast.LENGTH_LONG).show();
            return;
        }
        stateContext.getTemplateState().showBottomView(this,(ViewGroup)findViewById(R.id.template_bottom));
        //暂时手动配置需要使用的DecorView
        TemplateViewDecor templateView = (TemplateViewDecor) findViewById(R.id.templateView);
        templateView.initView();
        templateView.setNavigationBar(new TemplateMenuView(this), this);
        stateContext.getTemplateState().initTempltaView(templateView);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        stateContext.getTemplateState().addMenuView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        stateContext.getTemplateState().onMenuSelected(item.getItemId());
//        stateContext.setTemplateState(new PGTemplateState());
//        initView("高血压随访");
//        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }
}
