package vn.edu.ptit.android.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

import com.google.android.gms.internal.gt;
import com.google.android.gms.maps.model.LatLng;

import vn.edu.ptit.android.entity.Car;
import vn.edu.ptit.android.entity.District;
import vn.edu.ptit.android.entity.Place;
import vn.edu.ptit.android.entity.Trips;
import vn.edu.ptit.android.tab.PersonalTab;
import vn.edu.ptit.android.utils.Key;
import vn.edu.ptit.android.utils.PlacesAutoCompleteAdapter;
import vn.edu.ptit.android.utils.RouteAdaper;
import vn.edu.ptit.android.utils.TripsAdapter;
import vn.edu.ptit.android.utils.Util;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class OfferTripActivity extends Activity implements OnClickListener {

	private static String url = "http://192.168.130.1:8080/CatchCar/TripServlet";
	private static String url2 = "http://192.168.130.1:8080/CatchCar/PersonServlet";

	private AutoCompleteTextView actvCity;
	private AutoCompleteTextView actvDistrict;
	private ListView lvRoutes;
	private RouteAdaper routeAdapter;
	private EditText etNumOfSeat;
	private EditText etDate, etNgayden, etGioden;
	private EditText etTime;
	private EditText etCarType;
	private Button btPublish, btCancel;
	private ImageButton btDate, btTime, btAddTown, btGmap, btNgayden, btGioden;

	Spinner spCarType;
	private List<String> listspCarType = new ArrayList<String>();
	private ArrayList<Car> arrCar = new ArrayList<Car>();
	private int idCarSelect;
	private OnItemSelectedListener onClickListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			idCarSelect = arrCar.get(position).getId();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

	};

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static final int DATE_DIALOG_ID_DEN = 2;
	static final int TIME_DIALOG_ID_DEN = 3;
	private int year, month, day, hour, minute;

	private final List<String> PLACE = new LinkedList<String>();
	private List<String> DISTRICT = new LinkedList<String>();
	private ArrayList<Place> place_district = new ArrayList<Place>();
	private ArrayList<LatLng> coordinateList = new ArrayList<LatLng>();

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

	private DatePickerDialog.OnDateSetListener dateListener1 = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year1, int monthOfYear,
				int dayOfMonth) {
			year = year1;
			month = monthOfYear;
			day = dayOfMonth;
			updateDate1();
		}
	};

	private TimePickerDialog.OnTimeSetListener timeListener1 = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
			hour = hourOfDay;
			minute = minute1;
			updateTime1();
		}
	};
	public String idUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_trip_activity);

		actvCity = (AutoCompleteTextView) findViewById(R.id.actvCity);
		actvDistrict = (AutoCompleteTextView) findViewById(R.id.actvDistrict);
		etNumOfSeat = (EditText) findViewById(R.id.etNumOfSeat);
		etDate = (EditText) findViewById(R.id.etDate);
		etTime = (EditText) findViewById(R.id.etTime);
		etNgayden = (EditText) findViewById(R.id.etNgayden);
		etGioden = (EditText) findViewById(R.id.etGioden);

		btNgayden = (ImageButton) findViewById(R.id.btNgayden);
		btGioden = (ImageButton) findViewById(R.id.btGioden);
		btNgayden.setOnClickListener(this);
		btGioden.setOnClickListener(this);

		// etCarType = (EditText) findViewById(R.id.etCarType);
		btPublish = (Button) findViewById(R.id.btPublish);
		btPublish.setOnClickListener(this);
		btCancel = (Button) findViewById(R.id.btCancel);
		btCancel.setOnClickListener(this);
		btDate = (ImageButton) findViewById(R.id.btDate);
		btDate.setOnClickListener(this);
		btTime = (ImageButton) findViewById(R.id.btTime);
		btTime.setOnClickListener(this);
		btAddTown = (ImageButton) findViewById(R.id.btAddTown);
		btAddTown.setOnClickListener(this);
		btGmap = (ImageButton) findViewById(R.id.btGmap);
		btGmap.setOnClickListener(this);
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		lvRoutes = (ListView) findViewById(R.id.lvRoutes);
		routeAdapter = new RouteAdaper(getApplicationContext(),
				R.layout.offer_trip_listview, new ArrayList<String>());
		lvRoutes.setAdapter(routeAdapter);

		// init SpCarType
		spCarType = (Spinner) findViewById(R.id.spCarType);
		spCarType.setOnItemSelectedListener(onClickListener);
		GetListCarMethod getCar = new GetListCarMethod();
		getCar.execute(new String[] { url2 });

		// init autotextview
		// loadPlaceDistrict();
		ArrayAdapter<String> adapterPLACE = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, PLACE);
		actvCity.setAdapter(adapterPLACE);
		// actvCity.setAdapter(new PlacesAutoCompleteAdapter(
		// getApplicationContext(),
		// android.R.layout.simple_dropdown_item_1line));
		actvCity.addTextChangedListener(new TextWatcher() {

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
				String place = actvCity.getText().toString();
				for (Place p : place_district) {
					if (p.getPlace().equals(place)) {
						List<District> list = p.getDistrictList();
						DISTRICT.clear();
						for (int i = 0; i < list.size(); i++) {
							DISTRICT.add(list.get(i).getName());
						}
						ArrayAdapter<String> adapterDISTRICT = new ArrayAdapter<String>(
								OfferTripActivity.this,
								android.R.layout.simple_dropdown_item_1line,
								DISTRICT);
						actvDistrict.setAdapter(adapterDISTRICT);
						break;
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

	protected void updateTime() {
		etTime.setText(new StringBuilder().append(hour).append(":")
				.append(minute));
	}

	protected void updateDate() {
		etDate.setText(new StringBuilder().append(day).append("/")
				.append(month + 1).append("/").append(year).append(" "));
	}

	protected void updateTime1() {
		etGioden.setText(new StringBuilder().append(hour).append(":")
				.append(minute));
	}

	protected void updateDate1() {
		etNgayden.setText(new StringBuilder().append(day).append("/")
				.append(month + 1).append("/").append(year).append(" "));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btAddTown:
			btAddTownListener();
			break;
		case R.id.btDate:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.btTime:
			showDialog(TIME_DIALOG_ID);
			break;
		case R.id.btCancel:
			this.finish();
			break;
		case R.id.btPublish:
			AddMethod addMethod = new AddMethod();
			addMethod.execute(new String[] { url });
			break;
		case R.id.btGmap:
			btGmapListener();
			break;
		case R.id.btNgayden:
			showDialog(DATE_DIALOG_ID_DEN);
			break;
		case R.id.btGioden:
			showDialog(TIME_DIALOG_ID_DEN);
			break;
		default:
			break;
		}
	}

	public void btAddTownListener() {
		if (!actvCity.equals("") && !actvDistrict.equals("")) {
			routeAdapter.add(actvCity.getText().toString() + "-"
					+ actvDistrict.getText().toString());
			actvCity.setText("");
			actvDistrict.setText("");
		}
	}

	public void btGmapListener() {
		String[] tmp;
		coordinateList = new ArrayList<LatLng>();
		for (int i = 0; i < routeAdapter.getCount(); i++) {
			tmp = routeAdapter.getItem(i).split("-");
			for (Place p : place_district) {
				if (p.getPlace().equalsIgnoreCase(tmp[0])) {
					List<District> list = p.getDistrictList();
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).getName().equalsIgnoreCase(tmp[1])) {
							coordinateList.add(list.get(j).getCoordinate());
						}
					}
					break;
				}
			}
		}
		Intent intent = new Intent(this, GmapActivity.class);
		double[] list = Util.latLngToDouble(coordinateList);
		intent.putExtra("list", list);
		startActivity(intent);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timeListener, 0, 0, true);
		case DATE_DIALOG_ID_DEN:
			return new DatePickerDialog(this, dateListener1, year, month, day);
		case TIME_DIALOG_ID_DEN:
			return new TimePickerDialog(this, timeListener1, 0, 0, true);
		}
		return null;
	}

	private class AddMethod extends AsyncTask<String, Void, String> {

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
				int soghe = Integer.parseInt(etNumOfSeat.getText().toString());
				StringBuilder route = new StringBuilder();
				List<String> routeList = routeAdapter.getRoute();
				for (int i = 0; i < routeList.size(); i++) {
					route.append(routeList.get(i));
					route.append(";");
				}

				String timestart = etTime.getText().toString() + "-"
						+ etDate.getText().toString();
				String timeend = etGioden.getText().toString() + "-"
						+ etNgayden.getText().toString();
				int numberSeats = Integer.parseInt(etNumOfSeat.getText()
						.toString());

				Trips trip = new Trips(Integer.parseInt(idUser), idCarSelect,
						route.toString(), timestart, timeend, numberSeats);

				JSONObject obj = trip.toJSONObject();

				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("addTrip", obj.toString()));
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
			// TODO Auto-generated method stub
			if (result.equals("YES")) {
				Toast.makeText(getApplicationContext(), "THanh cong", 3).show();
			}else{
				Toast.makeText(getApplicationContext(), "K thanh cong", 3).show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}

	private class GetListCarMethod extends AsyncTask<String, Void, String> {

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
				Key key = new Key();
				idUser = key.Read_SharedPreferences(Key.ID,
						OfferTripActivity.this);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				JSONObject jObject = new JSONObject();
				jObject.put("iduser", Integer.parseInt(idUser));
				String sendObj = jObject.toString();
				System.out.println(sendObj);
				list.add(new BasicNameValuePair("getCar", sendObj));
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
			System.out.println(result);
			try {
				JSONArray arr = new JSONArray(result);
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					Car car = new Car(obj);
					arrCar.add(car);
				}
				List<String> list = new ArrayList<String>();

				for (Car car : arrCar) {
					list.add(car.getName());
				}
				System.out.println(arrCar.size());
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
						OfferTripActivity.this,
						android.R.layout.simple_spinner_item, list);
				dataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spCarType.setAdapter(dataAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

}
