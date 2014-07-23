package vn.edu.ptit.android.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.edu.ptit.android.entity.Place;
import vn.edu.ptit.android.entity.Trips;
import vn.ptit.edu.vn.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

public class SearchActivity extends Activity {

	ActionBar actionBar;
	ImageView btSearch;
	private static String url = "http://192.168.38.1:8080/CatchCar/ChuyenxeServlet";

	AutoCompleteTextView tvFromPlace, tvFromDistrict, tvToPlace, tvToDistrict;

	private final List<String> PLACE = new LinkedList<String>();
	private String[] DISTRICT = new String[50];
	private ArrayList<Place> place_district = new ArrayList<Place>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setTitle("");
		actionBar.setSubtitle(getResources().getString(R.string.app_name));
		actionBar.hide();
		setContentView(R.layout.search_car_activity);

		btSearch = (ImageView) findViewById(R.id.btSearch);
		btSearch.setOnClickListener(searchListener);

		// set auto Text for TextField
		loadPlaceDistrict();
		tvFromPlace = (AutoCompleteTextView) findViewById(R.id.atTvFromPlace);
		tvFromDistrict = (AutoCompleteTextView) findViewById(R.id.atTvFromDistrict);
		tvToPlace = (AutoCompleteTextView) findViewById(R.id.atTvToPlace);
		tvToDistrict = (AutoCompleteTextView) findViewById(R.id.atTvToDistrict);

		ArrayAdapter<String> adapterPLACE = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, PLACE);
		tvFromPlace.setAdapter(adapterPLACE);
		tvToPlace.setAdapter(adapterPLACE);

		tvFromPlace.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String place = tvFromPlace.getText().toString();
				for (Place p : place_district) {
					if (p.getPlace().equals(place)) {
						DISTRICT = p.getDistrict();
						ArrayAdapter<String> adapterDISTRICT = new ArrayAdapter<String>(
								SearchActivity.this,
								android.R.layout.simple_dropdown_item_1line,
								DISTRICT);
						tvFromDistrict.setAdapter(adapterDISTRICT);
					}
				}
			}
		});

		tvToPlace.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String place = tvToPlace.getText().toString();
				for (Place p : place_district) {
					if (p.getPlace().equals(place)) {
						DISTRICT = p.getDistrict();
						ArrayAdapter<String> adapterDISTRICT = new ArrayAdapter<String>(
								SearchActivity.this,
								android.R.layout.simple_dropdown_item_1line,
								DISTRICT);
						tvToDistrict.setAdapter(adapterDISTRICT);
					}
				}
			}
		});
	}

	private void loadPlaceDistrict() {
		try {
			InputStream inputStream = getAssets().open("PLACE-DISTRICT.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				Place _place = new Place(str);
				place_district.add(_place);
				PLACE.add(_place.getPlace());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private OnClickListener searchListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SearchMethod searchMethod = new SearchMethod();
			searchMethod.execute(new String[] { url });
		}
	};

	private class SearchMethod extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONArray out = null;
			for (String param : params) {
				out = getOutput(param);
			}
			return out;
		}

		private JSONArray getOutput(String param) {
			// TODO Auto-generated method stub
			JSONArray out = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String fromPlace = tvFromPlace.getText().toString();
				String fromDistrict = tvFromDistrict.getText().toString();
				String toPlace = tvToPlace.getText().toString();
				String toDistrict = tvToDistrict.getText().toString();
				String search = fromDistrict + "-" + fromPlace + ","
						+ toDistrict + "-" + toPlace;
				System.out.println(search);
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("chuyenxe", search));
				UrlEncodedFormEntity e = new UrlEncodedFormEntity(list,
						HTTP.UTF_8);
				HttpPost httpPost = new HttpPost(param);
				httpPost.setEntity(e);
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();
				String res = EntityUtils.toString(httpEntity);
				out = new JSONArray(res);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return out;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			ArrayList<Trips> list = new ArrayList<Trips>();
			for (int i = 0; i < result.length(); i++) {
				try {
					JSONObject jsonObject = result.getJSONObject(i);
					list.add(new Trips(jsonObject));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			Intent intent = new Intent(SearchActivity.this,
					ResultSearchActivity.class);
			intent.putParcelableArrayListExtra("listResultTrips", list);
			startActivity(intent);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}
}
