package vn.edu.ptit.android.entity;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class District implements Serializable{
	
	private String name;
	private LatLng coordinate;
	
	public District(String s){
		String[] tmp = s.split("|");
		this.name = tmp[0].trim();
		this.coordinate = new LatLng(Double.parseDouble(tmp[1].trim()), Double.parseDouble(tmp[2].trim()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(LatLng coordinate) {
		this.coordinate = coordinate;
	}

	public District(String name, LatLng coordinate) {
		super();
		this.name = name;
		this.coordinate = coordinate;
	}
	
}
