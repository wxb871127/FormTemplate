package template.com.templatedb;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import android.support.annotation.Nullable;

import com.templatedb.greendao.AreaDao;
import com.templatedb.greendao.CommonExpressionDao;
import com.templatedb.greendao.DrugDao;
import com.templatedb.greendao.InstitutionDao;
import com.templatedb.greendao.UserDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.EncryptedDatabase;

public class CommonOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public CommonOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CommonOpenHelper(Context context)
    {
        super(context, "common.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Database greenDaoDb = new EncryptedDatabase(db);
        DrugDao.createTable(greenDaoDb, true);
        AreaDao.createTable(greenDaoDb, true);
        InstitutionDao.createTable(greenDaoDb, true);
        UserDao.createTable(greenDaoDb, true);
        CommonExpressionDao.createTable(greenDaoDb, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
