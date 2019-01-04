package template.com.formtemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import template.config.TemplateConfig;
import template.widget.TemplateView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TemplateConfig.initConfig(this);
        TemplateView templateView = (TemplateView) findViewById(R.id.templateView);
//        templateView.initTemplate(this,"test.xml");
        templateView.initTemplate("gxy_zx.xml");
        templateView.setValue("xm", "林浩");
        templateView.setValue("jtdh","122");
        templateView.setValue("jzlx","1");
        templateView.setValue("ycqshfszlcsxs","1,3");
        try {
            templateView.setValue("gmsList",new JSONArray("[ {\"xh\":\"1\",\"gmy\":\"1\",\"fsrq\":\"2018-11-21\",\"bz\":\"是非观\"}, {\"xh\":\"2\",\"gmy\":\"2\",\"fsrq\":\"2018-12-21\",\"bz\":\"手工课\"}]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        templateView.notifyData();

//        AreaDao areaDao = DaoManager.getInstance(this).getDaoSession().getAreaDao();
//        Area area = new Area();
//        area.pym = "lzh";
//        area.qhbm = "123";
//        area.qhmc = "李昭辉";
//        area.qhqc = "卫宁李昭辉";
//        areaDao.insert(area);
//
//        List<Area> list =  areaDao.queryBuilder().where(AreaDao.Properties.Pym.eq("lzh")).list();
//
//        for(Area area1: list)
//            Log.e("xxxxxxx", area1.qhmc);
    }
}
