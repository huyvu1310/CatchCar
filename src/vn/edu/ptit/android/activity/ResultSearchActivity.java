package vn.edu.ptit.android.activity;

import java.util.ArrayList;

import vn.edu.ptit.android.entity.Trips;
import vn.edu.ptit.android.utils.TripsAdapter;
import vn.ptit.edu.vn.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ResultSearchActivity extends Activity {

	ActionBar actionBar;
	ListView lvResult;
	TripsAdapter tripsAdapter;
	ArrayList<Trips> arrTrips = null;
	TextView tvResultCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setTitle("");
		actionBar.setSubtitle(getResources().getString(R.string.app_name));
		actionBar.hide();
		setContentView(R.layout.search_car_result_activity);

		//listview show result
		lvResult = (ListView) findViewById(R.id.lv_result_trips);
		arrTrips = getIntent().getParcelableArrayListExtra("listResultTrips");
		tripsAdapter = new TripsAdapter(ResultSearchActivity.this,
				R.layout.search_car_listview, arrTrips);
		lvResult.setAdapter(tripsAdapter);
		
		lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//thay doi MainApp.class cho phu hop
				Intent intent = new Intent(ResultSearchActivity.this, ChiTietActivity.class);
				intent.putParcelableArrayListExtra("listResultTrips", arrTrips);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
		
		//textview show number of results
		tvResultCount = (TextView) findViewById(R.id.tvResultCount);
		tvResultCount.setText(Integer.toString(arrTrips.size()));
	}

}
