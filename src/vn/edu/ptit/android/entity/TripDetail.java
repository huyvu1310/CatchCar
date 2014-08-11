package vn.edu.ptit.android.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TripDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User taixe;
	Trips trips;
	Car car;
	ArrayList<Ride> listCatch;

	public TripDetail() {
		// TODO Auto-generated constructor stub
	}

	public TripDetail(User taixe, Trips trips, Car car,
			ArrayList<Ride> listCatch) {
		super();
		this.taixe = taixe;
		this.trips = trips;
		this.car = car;
		this.listCatch = listCatch;
	}

	public TripDetail(JSONObject o) throws JSONException {
		this.taixe = new User(o.getJSONObject("user"));
		this.trips = new Trips(o.getJSONObject("trip"));
		this.car = new Car(o.getJSONObject("car"));
		JSONArray arr = o.getJSONArray("arrRide");
		this.listCatch = new ArrayList<Ride>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = (JSONObject) arr.get(i);
			this.listCatch.add(new Ride(obj));
		}
	}
	public TripDetail(JSONObject o,int type) throws JSONException {
		this.taixe = new User(o.getJSONObject("user"));
		this.trips = new Trips(o.getJSONObject("trip"));
		this.car = new Car(o.getJSONObject("car"));
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public User getTaixe() {
		return taixe;
	}

	public void setTaixe(User taixe) {
		this.taixe = taixe;
	}

	public Trips getTrips() {
		return trips;
	}

	public void setTrips(Trips trips) {
		this.trips = trips;
	}

	public ArrayList<Ride> getListCatch() {
		return listCatch;
	}

	public void setListCatch(ArrayList<Ride> listCatch) {
		this.listCatch = listCatch;
	}

}
