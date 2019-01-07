package template.com.templatedb;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * 机构表
 */
@Entity(nameInDb = "Institution")
public class Institution {

	@Id(autoincrement = true)
	@Property(nameInDb = "_id")
	private Long id;

	/**
	 * 机构编号
	 */
	@Index(unique = true)
	@Property(nameInDb = "jgbh")
	public String jgbh;

	/**
	 * 机构名称
	 */
	@Property(nameInDb = "jgmc")
	public String jgmc;

	/**
	 * 拼音码
	 */
	@Property(nameInDb = "pym")
	public String pym;

	@Property(nameInDb = "stamp")
	public String stamp;

	/**
	 * 作废标志
	 */
	@Property(nameInDb = "zfbz")
	public String zfbz;

	/**
 	 *组织机构代码（废弃，服务器没有下发该字段）
	 */
	@Property(nameInDb = "zzjgdm")
	public String zzjgdm;

	/**
	 * 组织机构代码 rhm字段（废弃）
	 */
	@Property(nameInDb = "yljgzzdm")
	public String yljgzzdm;

	@Generated(hash = 190471381)
	public Institution(Long id, String jgbh, String jgmc, String pym, String stamp,
			String zfbz, String zzjgdm, String yljgzzdm) {
		this.id = id;
		this.jgbh = jgbh;
		this.jgmc = jgmc;
		this.pym = pym;
		this.stamp = stamp;
		this.zfbz = zfbz;
		this.zzjgdm = zzjgdm;
		this.yljgzzdm = yljgzzdm;
	}

	@Generated(hash = 1565596376)
	public Institution() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJgbh() {
		return this.jgbh;
	}

	public void setJgbh(String jgbh) {
		this.jgbh = jgbh;
	}

	public String getJgmc() {
		return this.jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
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

	public String getZfbz() {
		return this.zfbz;
	}

	public void setZfbz(String zfbz) {
		this.zfbz = zfbz;
	}

	public String getZzjgdm() {
		return this.zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}

	public String getYljgzzdm() {
		return this.yljgzzdm;
	}

	public void setYljgzzdm(String yljgzzdm) {
		this.yljgzzdm = yljgzzdm;
	}
}
