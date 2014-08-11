package vn.edu.ptit.android.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String username;
	String password;
	String phone;

	public User(int id, String username, String password, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

	public User(JSONObject o) throws JSONException {
		this.id = o.getInt("id");
		this.username = o.getString("username");
		this.password = o.getString("password");
		this.phone = o.getString("phone");
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("id", id);
		o.put("username", username);
		o.put("password", password);
		o.put("phone", phone);
		return o;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
