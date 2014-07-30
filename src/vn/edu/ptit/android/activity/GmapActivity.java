package vn.edu.ptit.android.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import vn.edu.ptit.android.utils.DirectionsJSONParser;
import vn.edu.ptit.android.utils.Util;
import vn.ptit.edu.vn.R;
import android.app.Dialog;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class GmapActivity extends FragmentActivity {

	GoogleMap mGoogleMap;
	ArrayList<LatLng> mMarkerPoints;
	double mLatitude = 0;
	double mLongitude = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.gmap_activity);
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		//double[] trips = getIntent().getDoubleArrayExtra("lotrinh");
		double[] trips = getIntent().getDoubleArrayExtra("list");
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		} else {
			mMarkerPoints = Util.doubleToLatLng(trips);
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			mGoogleMap = fm.getMap();
			mGoogleMap.setMyLocationEnabled(true);
//			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//			Criteria criteria = new Criteria();
//			String provider = locationManager.getBestProvider(criteria, true);
//			Location location = locationManager.getLastKnownLocation(provider);
//			LocationListener locationListener = new LocationListener() {
//
//				@Override
//				public void onLocationChanged(Location location) {
//					// TODO Auto-generated method stub
//					drawMarker(location);
//				}
//
//				@Override
//				public void onStatusChanged(String provider, int status,
//						Bundle extras) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onProviderEnabled(String provider) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onProviderDisabled(String provider) {
//					// TODO Auto-generated method stub
//
//				}
//			};
//			if (location != null) {
//				drawMarker(location);
//			}
//			locationManager.requestLocationUpdates(provider, 20000, 0,
//					locationListener);
//			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(20.971358, 105.783663)));
//            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
			if (mMarkerPoints.size()>=2){
				for (int i = 0; i < mMarkerPoints.size()-1; i++) {
					LatLng origin = mMarkerPoints.get(i);
                    LatLng dest = mMarkerPoints.get(i+1);
                    String url = getDirectionsUrl(origin, dest);
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);
				}
			}
			drawMarker();
		}
	}



	protected void drawMarker(Location location) {
		mGoogleMap.clear();
		LatLng currentPosition = new LatLng(location.getLatitude(),
				location.getLongitude());
	}



	private String getDirectionsUrl(LatLng origin, LatLng dest) {
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
		String sensor = "sensor=false";
		String parameters = str_origin + "&" + str_dest + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;
		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			data = sb.toString();
			br.close();
		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	/** A class to download data from Google Directions URL */
	private class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask parserTask = new ParserTask();
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Directions in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);
			}

			// Drawing polyline in the Google Map for the i-th route
			mGoogleMap.addPolyline(lineOptions);
		}
	}

	private void drawMarker() {
		// Creating MarkerOptions
		MarkerOptions options = new MarkerOptions();
		options.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		// Setting the position of the marker
		for (int i = 0; i < mMarkerPoints.size(); i++) {
			options.position(mMarkerPoints.get(i));
			b.include(mMarkerPoints.get(i));
			mGoogleMap.addMarker(options);
		}
		LatLngBounds bounds = b.build();
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,5);
		mGoogleMap.animateCamera(cu);
	}
}
