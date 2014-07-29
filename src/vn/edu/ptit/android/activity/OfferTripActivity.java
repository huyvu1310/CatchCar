package vn.edu.ptit.android.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONObject;

import vn.edu.ptit.android.entity.Place;
import vn.edu.ptit.android.entity.Trips;
import vn.edu.ptit.android.utils.RouteAdaper;
import vn.edu.ptit.android.utils.TripsAdapter;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class OfferTripActivity extends Activity implements OnClickListener {
	private static String url = "http://192.168.38.1:8080/CatchCar/ChuyenxeServlet";

	private AutoCompleteTextView actvCity;
	private AutoCompleteTextView actvDistrict;
	private ListView lvRoutes;
	private RouteAdaper routeAdapter;
	private EditText etNumOfSeat;
	private EditText etDate;
	private EditText etTime;
	private EditText etCarType;
	private Button btPublish, btCancel;
	private ImageButton btDate, btTime, btAddTown,btGmap;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	private int year, month, day, hour, minute;

	private final List<String> PLACE = new LinkedList<String>();
	private String[] DISTRICT = new String[50];
	private ArrayList<Place> place_district = new ArrayList<Place>();

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
		etCarType = (EditText) findViewById(R.id.etCarType);
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
		routeAdapter = new RouteAdaper(getApplicationContext(), R.layout.offer_trip_listview, new ArrayList<String>());
		lvRoutes.setAdapter(routeAdapter);
		// init autotextview
		loadPlaceDistrict();
		ArrayAdapter<String> adapterPLACE = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, PLACE);
		actvCity.setAdapter(adapterPLACE);
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
				// TODO Auto-generated method stub
				String place = actvCity.getText().toString();
				for (Place p : place_district) {
					if (p.getPlace().equals(place)) {
						DISTRICT = p.getDistrict();
						ArrayAdapter<String> adapterDISTRICT = new ArrayAdapter<String>(
								OfferTripActivity.this,
								android.R.layout.simple_dropdown_item_1line,
								DISTRICT);
						actvDistrict.setAdapter(adapterDISTRICT);
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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btAddTown:
			if (!actvCity.equals("") && !actvDistrict.equals("")) {
				routeAdapter.add(actvCity.getText().toString() + "-"
						+ actvDistrict.getText().toString());
				actvCity.setText("");
				actvDistrict.setText("");
			}
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
			Intent intent = new Intent(this,GmapActivity.class);
			startActivity(intent);
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
				Trips trip = new Trips(1, 3, etCarType.getText().toString(),
						etDate.getText().toString() + "-"
								+ etTime.getText().toString(), soghe, soghe,
					route.toString(), "none");
				JSONObject obj = trip.toJSON();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("add", obj.toString()));
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
			if (result.equals("OK")) {
				Toast.makeText(getApplicationContext(), "do hoi", 3).show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}

}
