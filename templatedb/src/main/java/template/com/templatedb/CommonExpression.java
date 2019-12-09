package template.com.templatedb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "CommonExpression", indexes = {@Index(value = "id", unique = true)})
public class CommonExpression {

    /*主键(为了兼容老版本)*/
    @Id(autoincrement = true)
    @Property(nameInDb = "_id")
    private Long primaryKey;

    @Property(nameInDb = "id")
    public String id;


    @Property(nameInDb = "name")
    public String name;


    @Property(nameInDb = "stamp")
    public String stamp;

    @Property(nameInDb = "fieldid")
    public String fieldid;

    @Generated(hash = 855772788)
    public CommonExpression(Long primaryKey, String id, String name, String stamp,
                            String fieldid) {
        this.primaryKey = primaryKey;
        this.id = id;
        this.name = name;
        this.stamp = stamp;
        this.fieldid = fieldid;
    }

    @Generated(hash = 1220105149)
    public CommonExpression() {
    }

    public Long getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(Long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStamp() {
        return this.stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getFieldid() {
        return this.fieldid;
    }

    public void setFieldid(String fieldid) {
        this.fieldid = fieldid;
    }
    
}
