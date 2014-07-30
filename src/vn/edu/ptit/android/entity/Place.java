package vn.edu.ptit.android.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Place implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String place;
	List<District> districtList;

	public Place() {
		// TODO Auto-generated constructor stub
	}

	public Place(String place, List<District> district) {
		super();
		this.place = place;
		this.districtList = district;
	}

	public Place(String string) {
		String[] tmp = string.split(":");
		this.place = tmp[0].trim();
		String[] disTmp = tmp[1].split("-");
		districtList = new ArrayList<District>();
		for (int i = 0; i < disTmp.length; i++) {
			districtList.add(new District(disTmp[i]));
		}
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}
	

}
