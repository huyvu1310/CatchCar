package vn.edu.ptit.android.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Trips implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	int iduser;
	int idcar;
	String distance;
	String timestart;
	String timeend;
	int emptyseats;

	public Trips() {
		// TODO Auto-generated constructor stub
	}

	public Trips(JSONObject obj) throws JSONException {
		this.id = obj.getInt("id");
		this.iduser = obj.getInt("iduser");
		this.idcar = obj.getInt("idcar");
		this.distance = obj.getString("distance");
		this.timestart = obj.getString("timestart");
		this.timeend = obj.getString("timeend");
		this.emptyseats = obj.getInt("emptyseats");
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("iduser", iduser);
		obj.put("idcar", idcar);
		obj.put("distance", distance);
		obj.put("timestart", timestart);
		obj.put("timeend", timeend);
		obj.put("emptyseats", emptyseats);
		return obj;
	}

	public Trips(int id, int iduser, int idcar, String distance,
			String timestart, String timeend, int emptyseats) {
		super();
		this.id = id;
		this.iduser = iduser;
		this.idcar = idcar;
		this.distance = distance;
		this.timestart = timestart;
		this.timeend = timeend;
		this.emptyseats = emptyseats;
	}

	public Trips(int iduser, int idcar, String distance, String timestart,
			String timeend, int emptyseats) {
		super();
		this.iduser = iduser;
		this.idcar = idcar;
		this.distance = distance;
		this.timestart = timestart;
		this.timeend = timeend;
		this.emptyseats = emptyseats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public int getIdcar() {
		return idcar;
	}

	public void setIdcar(int idcar) {
		this.idcar = idcar;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTimestart() {
		return timestart;
	}

	public void setTimestart(String timestart) {
		this.timestart = timestart;
	}

	public String getTimeend() {
		return timeend;
	}

	public void setTimeend(String timeend) {
		this.timeend = timeend;
	}

	public int getEmptyseats() {
		return emptyseats;
	}

	public void setEmptyseats(int emptyseats) {
		this.emptyseats = emptyseats;
	}

}
