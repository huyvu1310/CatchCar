package vn.edu.ptit.android.entity;

import java.io.Serializable;

public class Place implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String place;
	String[] district;

	public Place() {
		// TODO Auto-generated constructor stub
	}

	public Place(String place, String[] district) {
		super();
		this.place = place;
		this.district = district;
	}

	public Place(String string) {
		String[] tmp = string.split(":");
		this.place = tmp[0].trim();
		this.district= tmp[1].split("-");
		for (String s : district) {
			s.trim();
		}
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String[] getDistrict() {
		return district;
	}

	public void setDistrict(String[] district) {
		this.district = district;
	}

}
