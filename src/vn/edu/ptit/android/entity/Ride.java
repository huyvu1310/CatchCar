package vn.edu.ptit.android.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Ride implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	User user;
	String address;
	String time;
	String message;

	public Ride(int id, User user, String address, String time, String message) {
		super();
		this.id = id;
		this.user = user;
		this.address = address;
		this.time = time;
		this.message = message;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("id", id);
		o.put("user", user.toJSON());
		o.put("address", address);
		o.put("time", time);
		o.put("message", message);
		return o;
	}

	public Ride(JSONObject o) throws JSONException {
		this.id = o.getInt("id");
		this.user = new User(o.getJSONObject("user"));
		this.address = o.getString("address");
		this.time = o.getString("time");
		this.message = o.getString("message");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
