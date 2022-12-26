package in.co.rays.proj4.bean;

import java.util.Date;

public class StudentBean extends BaseBean{
private String firstName;
private String lastname;
private Date dob;
private String mobileNo;
private String email;
private long ColegeId;
private String CollegeName;

public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastname;
}
public void setLastName(String lastname) {
	this.lastname = lastname;
}
public Date getDob() {
	return dob;
}
public void setDob(Date dob) {
	this.dob = dob;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public long getColegeId() {
	return ColegeId;
}
public void setColegeId(long colegeId) {
	ColegeId = colegeId;
}
public String getCollegeName() {
	return CollegeName;
}
public void setCollegeName(String collegeName) {
	CollegeName = collegeName;
}
 public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
    public String getValue() {
		// TODO Auto-generated method stub
		return firstName+""+lastname;
	}

}
