package vn.edu.ptit.android.tab;

import vn.edu.ptit.android.activity.OfferTripActivity;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TripTab extends Activity {
	
	ImageButton btAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_tab_activity);
	
		btAdd = (ImageButton) findViewById(R.id.btAddNewTrip);
		btAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TripTab.this, OfferTripActivity.class);
				startActivity(intent);
			}
		});
	}
}
