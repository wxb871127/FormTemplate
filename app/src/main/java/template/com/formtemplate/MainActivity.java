package template.com.formtemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import activity.TemplateActivity;
import template.config.TemplateConfig;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TemplateConfig.initConfig(this);//静态类初始化方法应只调用一次

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, TemplateActivity.class);
        if(v.getId() == R.id.gxy_zx) {
            intent.putExtra("templateName", "专项");
        }else if(v.getId() == R.id.gxy_pg)
            intent.putExtra("templateName", "评估");
        else if(v.getId() == R.id.gxy_sf)
            intent.putExtra("templateName", "随访");
        else if(v.getId() == R.id.gxy_tj) {
            intent.putExtra("templateName", "体检");
            intent.putExtra("templateRes", "test.xml");
        }
        startActivity(intent);
    }
}
