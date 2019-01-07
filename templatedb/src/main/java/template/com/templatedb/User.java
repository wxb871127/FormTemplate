package template.com.templatedb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 操作用户表，用于选择随访医生等人员时使用
 */
@Entity(nameInDb = "User")
public class User {

	@Id(autoincrement = true)
	@Property(nameInDb = "_id")
	private Long primaryKey;

	@Index(name = "UserIndex",unique = true)
	@Property(nameInDb = "id")
	public String id;
	/**
	 * 用户姓名
	 */
	@Property(nameInDb = "yhxm")
	public String yhxm;

	/**
	 * 签约医生ID
	 */
	@Property(nameInDb = "qyysid")
	public String qyysid;

	@Property(nameInDb = "stamp")
	public String stamp;

	/**
	 * 身份证号
	 */
	@Property(nameInDb = "sfzh")
	public String sfzh;

	/**
	 * 拼音码
	 */
	@Property(nameInDb = "pym")
	public String pym;

	/**
	 * 机构编号
	 */
	@Property(nameInDb = "jgbh")
	public String jgbh;

	@Generated(hash = 1430299096)
	public User(Long primaryKey, String id, String yhxm, String qyysid,
			String stamp, String sfzh, String pym, String jgbh) {
		this.primaryKey = primaryKey;
		this.id = id;
		this.yhxm = yhxm;
		this.qyysid = qyysid;
		this.stamp = stamp;
		this.sfzh = sfzh;
		this.pym = pym;
		this.jgbh = jgbh;
	}

	@Generated(hash = 586692638)
	public User() {
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

	public String getYhxm() {
		return this.yhxm;
	}

	public void setYhxm(String yhxm) {
		this.yhxm = yhxm;
	}

	public String getQyysid() {
		return this.qyysid;
	}

	public void setQyysid(String qyysid) {
		this.qyysid = qyysid;
	}

	public String getStamp() {
		return this.stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getSfzh() {
		return this.sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getPym() {
		return this.pym;
	}

	public void setPym(String pym) {
		this.pym = pym;
	}

	public String getJgbh() {
		return this.jgbh;
	}

	public void setJgbh(String jgbh) {
		this.jgbh = jgbh;
	}
}
