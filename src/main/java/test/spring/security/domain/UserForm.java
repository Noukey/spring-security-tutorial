package test.spring.security.domain;

public class UserForm extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -689764531207693811L;
	
	private Integer ageMin;
	
	private Integer ageMax;

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
	
	

}
