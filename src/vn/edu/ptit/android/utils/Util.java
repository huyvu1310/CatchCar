package vn.edu.ptit.android.utils;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
public class Util {
	public static ArrayList<LatLng> doubleToLatLng(double[] arg){
		ArrayList<LatLng> res = new ArrayList<LatLng>();
		for (int i = 0; i < arg.length; i+=2) {
			res.add(new LatLng(arg[i], arg[i+1]));
		}
		return res;
	}
	public static double[] latLngToDouble(ArrayList<LatLng> arg){
		double[] res = null;
		for (int i = 0; i < arg.size(); i++) {
			res[i]=arg.get(i).latitude;
			res[i*2+1]= arg.get(i).longitude;
		}
		return res;
	}
}
