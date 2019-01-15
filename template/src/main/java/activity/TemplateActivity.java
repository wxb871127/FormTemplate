package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import activity.state.TemplateStateContext;


/**
 * 废弃继承方式，防止Activity类数量爆炸
 * 采用状态模式 简化Activity并扩展state 可实现Acitivy状态切换
 */
public class TemplateActivity extends AppCompatActivity {
    TemplateStateContext stateContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateContext = new TemplateStateContext(this, this);
        String templateName = getIntent().getStringExtra("templateName");
        stateContext.setTemplateState(templateName);
        setContentView(stateContext.getTemplateState().getBusinessLayout());
        initView();
    }

    /**
     *  组装当前状态下的View
     */
    private void initView(){
        setTitle(stateContext.getTemplateState().getTemplateName());
        if(stateContext.getTemplateState() == null){
            Toast.makeText(this, "找不到对应的服务配置", Toast.LENGTH_LONG).show();
            return;
        }
        stateContext.getTemplateState().initContentView();
        stateContext.getTemplateState().initBottomView();
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        stateContext.getTemplateState().addMenuView(menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        stateContext.getTemplateState().onMenuSelected(item.getItemId());
////        stateContext.setTemplateState(new PGTemplateState());
////        initView("高血压随访");
////        invalidateOptionsMenu();
//        return super.onOptionsItemSelected(item);
//    }
}
