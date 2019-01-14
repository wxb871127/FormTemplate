package template.com.templatedb;

import android.content.Context;
import com.templatedb.greendao.DaoMaster;
import com.templatedb.greendao.DaoSession;

import net.sqlcipher.database.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.EncryptedDatabase;

public class DaoManager {

    private volatile static DaoManager instance;

    private DaoSession daoSession;

    public static DaoManager getInstance(Context context){
        if(instance == null){
            synchronized (DaoManager.class){
                if(instance == null)
                    instance = new DaoManager(context);
            }
        }
        return instance;
    }

    private  DaoManager(Context context){
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "template.db");
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase commonDB = new CommonOpenHelper(context).getWritableDatabase("");
//        Database database = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(new EncryptedDatabase(commonDB));
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }


}
