package template.com.templatedb;

import android.content.Context;

import com.templatedb.greendao.DaoMaster;
import com.templatedb.greendao.DaoSession;

import net.sqlcipher.database.SQLiteDatabase;

import org.greenrobot.greendao.database.EncryptedDatabase;

public class DaoManager {

    private volatile static DaoManager instance;

    private DaoSession daoSession;
    private SQLiteDatabase commonDB;
    private Context context;

    public void init(Context context){
        this.context = context;
        SQLiteDatabase.loadLibs(context);
        commonDB = new CommonOpenHelper(context).getWritableDatabase("");
        DaoMaster daoMaster = new DaoMaster(new EncryptedDatabase(commonDB));
        daoSession = daoMaster.newSession();
    }

    public static DaoManager getInstance(){
        if(instance == null){
            synchronized (DaoManager.class){
                if(instance == null)
                    instance = new DaoManager();
            }
        }
        return instance;
    }

    public SQLiteDatabase getDataBase(){
        return commonDB;
    }

    private  DaoManager(){

    }

    public DaoSession getDaoSession(){
        return daoSession;
    }


}
