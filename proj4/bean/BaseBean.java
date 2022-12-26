package in.co.rays.proj4.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class BaseBean implements Serializable , DropdownListBean,Comparable<BaseBean> {
	protected long id;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDatetime;
	protected Timestamp modifieddatetime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public Timestamp getModifieddatetime() {
		return modifieddatetime;
	}
	public void setModifieddatetime(Timestamp modifieddatetime) {
		this.modifieddatetime = modifieddatetime;
	}
	
	public int compareTo (BaseBean next) {
		return getKey().compareTo(getValue());
		
	
	}
}
	
	


