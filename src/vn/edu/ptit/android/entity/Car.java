package vn.edu.ptit.android.entity;

import java.io.Serializable;

import org.json.JSONObject;

public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String type;
	private String numberOfSeats;
	private String license;
	private String des;

	public Car() {
		// TODO Auto-generated constructor stub
	}

	public Car(JSONObject obj) {
		try {
			this.id = obj.getInt("id");
			this.name = obj.getString("name");
			this.type = obj.getString("type");
			this.numberOfSeats = obj.getString("numberofseats");
			this.license = obj.getString("license");
			this.des = obj.getString("description");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Car(int id, String name, String type, String numberOfSeats,
			String license, String des) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.type = type;
		this.license = license;
		this.des = des;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(String numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getLecense() {
		return license;
	}

	public void setLecense(String lecense) {
		this.license = lecense;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
