package template.com.formtemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import activity.TemplateActivity;
import template.config.TemplateConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TemplateConfig.initConfig(this);//静态类初始化方法应只调用一次
        TemplateConfig.registerCustomView("1001", new Subsection());
        TemplateConfig.registerCustomView("1002", new Subsection2());
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
        }else if(v.getId() == R.id.history_zx){
            intent.putExtra("templateName", "专项记录");
        }else if(v.getId() == R.id.history_sf){
            intent.putExtra("templateName", "随访记录");
            intent.putExtra("templateData", getJson("sf.json"));
        }else if(v.getId() == R.id.history_pg){
            intent.putExtra("templateName", "评估记录");
        }else if(v.getId() == R.id.history_tj){
            intent.putExtra("templateName","体检记录");
            intent.putExtra("templateData", getJson("tj.json"));
        }
        startActivity(intent);
    }

    private String getJson(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
