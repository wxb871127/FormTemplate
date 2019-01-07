package template.com.templatedb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * 药品库表
 */

@Entity(nameInDb = "Drug")
public class Drug {

	@Id(autoincrement = true)
	@Property(nameInDb = "_id")
	private Long primaryKey;

	/**
	 * 药品ID
	 */
	@Index(name = "DrugIndex",unique = true)
	@Property(nameInDb = "ypid")
	public String ypid;

	/**
	 * 药品名称
	 */
	@Property(nameInDb = "ypmc")
	public String ypmc;

	/**
	 * 服用方式
	 */
	@Property(nameInDb = "fyfs")
	public String fyfs;

	/**
	 * 分服次数
	 */
	@Property(nameInDb = "ffcs")
	public String ffcs;

	/**
	 * 剂量
	 */
	@Property(nameInDb = "jl")
	public String jl;

	/**
	 * 备注
	 */
	@Property(nameInDb = "bz")
	public String bz;

	/**
	 * 作废标志
	 */
	@Property(nameInDb = "zfbz")
	public String zfbz;

	/**
	 * 拼音码
	 */
	@Property(nameInDb = "pym")
	public String pym;

	@Property(nameInDb = "stamp")
	public String stamp;

	/**
	 * 不良反应
	 */
	@Property(nameInDb = "blfy")
	public String blfy;

	/**
	 * 疾病种类
	 */
	@Property(nameInDb = "jbzl")
	public String jbzl;

	@Generated(hash = 1954183868)
	public Drug(Long primaryKey, String ypid, String ypmc, String fyfs, String ffcs,
			String jl, String bz, String zfbz, String pym, String stamp, String blfy,
			String jbzl) {
		this.primaryKey = primaryKey;
		this.ypid = ypid;
		this.ypmc = ypmc;
		this.fyfs = fyfs;
		this.ffcs = ffcs;
		this.jl = jl;
		this.bz = bz;
		this.zfbz = zfbz;
		this.pym = pym;
		this.stamp = stamp;
		this.blfy = blfy;
		this.jbzl = jbzl;
	}

	@Generated(hash = 90337038)
	public Drug() {
	}

	public Long getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getYpid() {
		return this.ypid;
	}

	public void setYpid(String ypid) {
		this.ypid = ypid;
	}

	public String getYpmc() {
		return this.ypmc;
	}

	public void setYpmc(String ypmc) {
		this.ypmc = ypmc;
	}

	public String getFyfs() {
		return this.fyfs;
	}

	public void setFyfs(String fyfs) {
		this.fyfs = fyfs;
	}

	public String getFfcs() {
		return this.ffcs;
	}

	public void setFfcs(String ffcs) {
		this.ffcs = ffcs;
	}

	public String getJl() {
		return this.jl;
	}

	public void setJl(String jl) {
		this.jl = jl;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	public String getStamp() {
		return this.stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getBlfy() {
		return this.blfy;
	}

	public void setBlfy(String blfy) {
		this.blfy = blfy;
	}

	public String getJbzl() {
		return this.jbzl;
	}

	public void setJbzl(String jbzl) {
		this.jbzl = jbzl;
	}
}
