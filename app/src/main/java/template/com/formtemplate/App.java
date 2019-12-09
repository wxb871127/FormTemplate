package template.com.formtemplate;

import android.app.Application;

import com.templatedb.greendao.CommonExpressionDao;

import template.com.templatedb.CommonExpression;
import template.com.templatedb.DaoManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.getInstance().init(this);

//       CommonExpression commonExpression1 = new CommonExpression();
//       commonExpression1.fieldid = "tz";
//       commonExpression1.id = "1";
//       commonExpression1.name = "李朝晖";
//
//        CommonExpression commonExpression2 = new CommonExpression();
//        commonExpression2.fieldid = "tz";
//        commonExpression2.id = "2";
//        commonExpression2.name = "林浩";
//
//        CommonExpression commonExpression3 = new CommonExpression();
//        commonExpression3.fieldid = "tz";
//        commonExpression3.id = "3";
//        commonExpression3.name = "王俊俊";
//
//        CommonExpression commonExpression4 = new CommonExpression();
//        commonExpression4.fieldid = "tz";
//        commonExpression4.id = "4";
//        commonExpression4.name = "结束";
//
//        CommonExpression commonExpression5 = new CommonExpression();
//        commonExpression5.fieldid = "tz";
//        commonExpression5.id = "5";
//        commonExpression5.name = "回复";
//
//        CommonExpression commonExpression6 = new CommonExpression();
//        commonExpression6.fieldid = "tz";
//        commonExpression6.id = "6";
//        commonExpression6.name = "阿文";
//
//        CommonExpression commonExpression7 = new CommonExpression();
//        commonExpression7.fieldid = "tz";
//        commonExpression7.id = "7";
//        commonExpression7.name = "沟通";
//
//        CommonExpression commonExpression8 = new CommonExpression();
//        commonExpression8.fieldid = "tz";
//        commonExpression8.id = "8";
//        commonExpression8.name = "所有";
//
//        CommonExpression commonExpression9 = new CommonExpression();
//        commonExpression9.fieldid = "tz";
//        commonExpression9.id = "9";
//        commonExpression9.name = "升高";
//
//        CommonExpression commonExpression10 = new CommonExpression();
//        commonExpression10.fieldid = "tz";
//        commonExpression10.id = "10";
//        commonExpression10.name = "包含";
//
//        CommonExpression commonExpression11 = new CommonExpression();
//        commonExpression11.fieldid = "tz";
//        commonExpression11.id = "11";
//        commonExpression11.name = "继续";
//
//        CommonExpression commonExpression12 = new CommonExpression();
//        commonExpression12.fieldid = "tz";
//        commonExpression12.id = "12";
//        commonExpression12.name = "快乐";
//
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression1);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression2);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression3);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression4);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression5);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression6);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression7);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression8);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression9);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression10);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression11);
//        DaoManager.getInstance().getDaoSession().getCommonExpressionDao().insert(commonExpression12);
    }
}
