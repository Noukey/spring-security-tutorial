package test.spring.security.web.form;

import java.util.Date;

import test.spring.security.domain.User;



public class UserQueryForm extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9182269534124428935L;
	private Integer ageMin;
	private Integer ageMax;
	
	private Date createDateMin;
	private Date createDateMax;
	public Integer getAgeMin() {
		return ageMin;
	}
	public void setAgeMin(Integer ageMin) {
		this.ageMin = ageMin;
	}
	public Integer getAgeMax() {
		return ageMax;
	}
	public void setAgeMax(Integer ageMax) {
		this.ageMax = ageMax;
	}
	public Date getCreateDateMin() {
		return createDateMin;
	}
	public void setCreateDateMin(Date createDateMin) {
		this.createDateMin = createDateMin;
	}
	public Date getCreateDateMax() {
		return createDateMax;
	}
	public void setCreateDateMax(Date createDateMax) {
		this.createDateMax = createDateMax;
	}
	
	
	

}
