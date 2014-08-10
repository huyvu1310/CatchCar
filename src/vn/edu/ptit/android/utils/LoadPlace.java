package vn.edu.ptit.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import vn.edu.ptit.android.entity.Place;

public class LoadPlace {
	private List<String> PLACE = new LinkedList<String>();
	private ArrayList<Place> place_district = new ArrayList<Place>();

	private Context context;

	public LoadPlace(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public List<String> getPLACE() {
		return PLACE;
	}

	public void setPLACE(List<String> pLACE) {
		PLACE = pLACE;
	}

	public ArrayList<Place> getPlace_district() {
		return place_district;
	}

	public void setPlace_district(ArrayList<Place> place_district) {
		this.place_district = place_district;
	}

	public void loadPlaceDistrict() {
		try {
			InputStream inputStream = context.getAssets().open(
					"PLACE-DISTRICT.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				Place _place = new Place(str);
				place_district.add(_place);
				PLACE.add(_place.getPlace());
			}
			System.out.println("log" + PLACE.size() + " " +place_district.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}

	}
}
