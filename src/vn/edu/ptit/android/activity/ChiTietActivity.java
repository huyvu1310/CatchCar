package vn.edu.ptit.android.activity;

import vn.edu.ptit.android.entity.Trips;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChiTietActivity extends Activity {
	
	TextView tvMota, tvSoGhe, tvNgay, tvGio;
	Button btLienLac, btGui;
	Trips trip = null;
	String thoiGian, gio, ngay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chi_tiet_chuyen_xe);
		
		tvMota = (TextView) findViewById(R.id.tvMoTa);
		tvSoGhe = (TextView) findViewById(R.id.tvSoGhe);
		tvNgay = (TextView) findViewById(R.id.tvNgay);
		tvGio = (TextView) findViewById(R.id.tvGio);
		
		
		btLienLac = (Button) findViewById(R.id.btLienLac);
		btGui = (Button) findViewById(R.id.btGuiYeuCau);
		
		trip = (Trips) getIntent().getSerializableExtra("trip");
		
		if(trip!=null){
			
			
			
			tvMota.setText(trip.getLoTrinh());
			tvSoGhe.setText(trip.getSoGhe());
			
		}
		
		btLienLac.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
			}
		});
		
		btGui.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
