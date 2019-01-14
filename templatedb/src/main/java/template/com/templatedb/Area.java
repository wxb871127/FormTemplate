package template.com.templatedb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Area")
public class Area {

    @Id(autoincrement = true)
    @Property(nameInDb = "_id")
    public Long primaryKey;

    @Index(unique = true)
    @Property(nameInDb = "qhbm")
    public String qhbm;

    @Property(nameInDb = "qhmc")
    public String qhmc;

    @Property(nameInDb = "qhqc")
    public String qhqc;

    @Property(nameInDb = "stamp")
    public String stamp;

    @Property(nameInDb = "zfbz")
    public String zfbz;

    @Property(nameInDb = "pym")
    public String pym;

    @Generated(hash = 88663494)
    public Area(Long primaryKey, String qhbm, String qhmc, String qhqc,
            String stamp, String zfbz, String pym) {
        this.primaryKey = primaryKey;
        this.qhbm = qhbm;
        this.qhmc = qhmc;
        this.qhqc = qhqc;
        this.stamp = stamp;
        this.zfbz = zfbz;
        this.pym = pym;
    }

    @Generated(hash = 179626505)
    public Area() {
    }

    public Long getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(Long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getQhbm() {
        return this.qhbm;
    }

    public void setQhbm(String qhbm) {
        this.qhbm = qhbm;
    }

    public String getQhmc() {
        return this.qhmc;
    }

    public void setQhmc(String qhmc) {
        this.qhmc = qhmc;
    }

    public String getQhqc() {
        return this.qhqc;
    }

    public void setQhqc(String qhqc) {
        this.qhqc = qhqc;
    }

    public String getStamp() {
        return this.stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getZfbz() {
        return this.zfbz;
    }

    public void setZfbz(String zfbz) {
        this.zfbz = zfbz;
    }

    public String getPym() {
        return this.pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }
}
