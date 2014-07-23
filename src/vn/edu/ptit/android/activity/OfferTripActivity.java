package vn.edu.ptit.android.activity;

import java.util.Calendar;

import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class OfferTripActivity extends Activity implements OnClickListener {
	private AutoCompleteTextView actvCity;
	private AutoCompleteTextView actvDistrict;
	private TextView tvTrip;
	private EditText etNumOfSeat;
	private EditText etDate;
	private EditText etTime;
	private Button btPublish, btCancel;
	private ImageButton btDate, btTime, btAddTown;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_trip_activity);
		actvCity = (AutoCompleteTextView) findViewById(R.id.actvCity);
		actvDistrict = (AutoCompleteTextView) findViewById(R.id.actvDistrict);
		tvTrip = (TextView) findViewById(R.id.tvTrip);
		etNumOfSeat = (EditText) findViewById(R.id.etNumOfSeat);
		etDate = (EditText) findViewById(R.id.etDate);
		etTime = (EditText) findViewById(R.id.etTime);
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
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
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
				tvTrip.setText(tvTrip.getText().toString()
						+ actvCity.getText().toString() + "-"
						+ actvDistrict.getText().toString() + '\n');
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
			Toast.makeText(this, "????", 4).show();
			break;
		case R.id.btPublish:
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
}
