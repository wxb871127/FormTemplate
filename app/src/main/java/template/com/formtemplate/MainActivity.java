package template.com.formtemplate;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.templatedb.greendao.AreaDao;
import com.templatedb.greendao.DaoSession;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import template.com.templatedb.Area;
import template.com.templatedb.DaoManager;
import template.com.templatedb.Institution;
import template.com.templatedb.User;
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
        templateView.setValue("lrr","124");
        templateView.setValue("ycqshfszlcsxs","1,3");
        try {
            templateView.setValue("gmsList",new JSONArray("[ {\"xh\":\"1\",\"gmy\":\"1\",\"fsrq\":\"2018-11-21\",\"bz\":\"是非观\"}, {\"xh\":\"2\",\"gmy\":\"2\",\"fsrq\":\"2018-12-21\",\"bz\":\"手工课\"}]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        templateView.notifyData();


//        User user = new User();
//        user.id = "124";
//        user.jgbh = "100";
//        user.pym = "lzh";
//        user.sfzh = "330112199110219923";
//        user.yhxm = "李昭辉";
//        user.stamp = "20120344";
//        DaoManager.getInstance(this).getDaoSession().getUserDao().insertOrReplace(user);

//        Institution institution = new Institution();
//        institution.jgbh = "100";
//        institution.jgmc = "西溪水岸花园";
//        institution.pym = "xxsahy";
//        institution.stamp = "";
//        institution.zzjgdm = "145";
//        DaoManager.getInstance(this).getDaoSession().getInstitutionDao().insertOrReplace(institution);


    }
}
