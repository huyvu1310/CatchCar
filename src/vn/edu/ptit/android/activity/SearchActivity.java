package vn.edu.ptit.android.activity;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.ptit.android.entity.Place;
import vn.edu.ptit.android.entity.TripDetail;
import vn.edu.ptit.android.entity.Trips;
import vn.ptit.edu.vn.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

public class SearchActivity extends Activity implements OnClickListener {

	ActionBar actionBar;
	ImageView btSearch, btDate, btTime;
	EditText etDate, etTime;

	private static String url = "http://192.168.130.1:8080/CatchCar/SearchServlet";

	AutoCompleteTextView tvFromPlace, tvFromDistrict, tvToPlace, tvToDistrict;

	private final List<String> PLACE = new LinkedList<String>();
	private List<String> DISTRICT = new LinkedList<String>();
	private ArrayList<Place> place_district = new ArrayList<Place>();

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	private int year, month, day, hour, minute;

	private DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year1, int monthOfYear,
				int dayOfMonth) {
			year = year1;
			month = monthOfYear;
			day = dayOfMonth;
			updateDate();
		}
	};

	private TimePickerDialog.OnTimeSetListener timeListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
			hour = hourOfDay;
			minute = minute1;
			updateTime();
		}
	};

	protected void updateTime() {
		etTime.setText(new StringBuilder().append(hour).append(":")
				.append(minute));
	}

	protected void updateDate() {
		etDate.setText(new StringBuilder().append(day).append("/")
				.append(month + 1).append("/").append(year).append(" "));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btDate:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.btTime:
			showDialog(TIME_DIALOG_ID);
			break;
		default:
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timeListener, 0, 0, true);
		}
		return null;
	}

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

		etDate = (EditText) findViewById(R.id.etDate);
		etTime = (EditText) findViewById(R.id.etTime);

		btDate = (ImageView) findViewById(R.id.btDate);
		btTime = (ImageView) findViewById(R.id.btTime);
		btDate.setOnClickListener(this);
		btTime.setOnClickListener(this);
		
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);

		// set auto Text for TextField
		// loadPlaceDistrict();
		tvFromPlace = (AutoCompleteTextView) findViewById(R.id.atTvFromPlace);
		tvFromDistrict = (AutoCompleteTextView) findViewById(R.id.atTvFromDistrict);
		tvToPlace = (AutoCompleteTextView) findViewById(R.id.atTvToPlace);
		tvToDistrict = (AutoCompleteTextView) findViewById(R.id.atTvToDistrict);

		// ArrayAdapter<String> adapterPLACE = new ArrayAdapter<String>(this,
		// android.R.layout.simple_dropdown_item_1line, PLACE);
		// tvFromPlace.setAdapter(adapterPLACE);
		// tvToPlace.setAdapter(adapterPLACE);
		//
		// tvFromPlace.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		// String place = tvFromPlace.getText().toString();
		// for (Place p : place_district) {
		// if (p.getPlace().equals(place)) {
		// List<District> list = p.getDistrictList();
		// DISTRICT.clear();
		// for (int i = 0; i < list.size(); i++) {
		// DISTRICT.add(list.get(i).getName());
		// }
		// ArrayAdapter<String> adapterDISTRICT = new ArrayAdapter<String>(
		// SearchActivity.this,
		// android.R.layout.simple_dropdown_item_1line,
		// DISTRICT);
		// tvFromDistrict.setAdapter(adapterDISTRICT);
		// break;
		// }
		// }
		// }
		// });
		//
		// tvToPlace.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		// String place = tvToPlace.getText().toString();
		// for (Place p : place_district) {
		// if (p.getPlace().equals(place)) {
		// List<District> list = p.getDistrictList();
		// DISTRICT.clear();
		// for (int i = 0; i < list.size(); i++) {
		// DISTRICT.add(list.get(i).getName());
		// }
		// ArrayAdapter<String> adapterDISTRICT = new ArrayAdapter<String>(
		// SearchActivity.this,
		// android.R.layout.simple_dropdown_item_1line,
		// DISTRICT);
		// tvToDistrict.setAdapter(adapterDISTRICT);
		// break;
		// }
		// }
		// }
		// });
		// }
		//
		// private void loadPlaceDistrict() {
		// try {
		// InputStream inputStream = getAssets().open("PLACE-DISTRICT.txt");
		// BufferedReader in = new BufferedReader(new InputStreamReader(
		// inputStream, "UTF-8"));
		// String str;
		// while ((str = in.readLine()) != null) {
		// Place _place = new Place(str);
		// place_district.add(_place);
		// PLACE.add(_place.getPlace());
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
	}

	private OnClickListener searchListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SearchMethod searchMethod = new SearchMethod();
			searchMethod.execute(new String[] { url });
		}
	};

	public class SearchMethod extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String out = null;
			for (String param : params) {
				out = getOutput(param);
			}
			return out;
		}

		private String getOutput(String param) {
			// TODO Auto-generated method stub
			String out = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String fromPlace = tvFromPlace.getText().toString();
				String fromDistrict = tvFromDistrict.getText().toString();
				String toPlace = tvToPlace.getText().toString();
				String toDistrict = tvToDistrict.getText().toString();
				String time = etTime.getText().toString()+"-"+etDate.getText().toString();
				
				JSONObject obj = new JSONObject();
				obj.put("diemdi", fromPlace+"-"+fromDistrict);
				obj.put("diemden", toPlace+"-"+toDistrict);
				obj.put("date", time);
				
				String sendObj = obj.toString();
				
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("searchOther", sendObj));
				UrlEncodedFormEntity e = new UrlEncodedFormEntity(list,
						HTTP.UTF_8);
				HttpPost httpPost = new HttpPost(param);
				httpPost.setEntity(e);
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();
				String res = EntityUtils.toString(httpEntity);
				out = res;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return out;
		}

		@Override
		
		protected void onPostExecute(String result) {
			List<TripDetail> list=new ArrayList<TripDetail>();
			
			try {
				JSONArray arr=new JSONArray(result);
				for(int i=0;i<arr.length();i++){
					TripDetail tripDetail=new TripDetail((JSONObject) arr.get(i),1);
					list.add(tripDetail);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
//			ArrayList<Trips> list = new ArrayList<Trips>();
//			for (int i = 0; i < result.length(); i++) {
//				try {
//					JSONObject jsonObject = result.getJSONObject(i);
//					list.add(new Trips(jsonObject));
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//
//			Intent intent = new Intent(SearchActivity.this,
//					ResultSearchActivity.class);
//			// intent.putParcelableArrayListExtra("listResultTrips", list);
//			startActivity(intent);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

}
