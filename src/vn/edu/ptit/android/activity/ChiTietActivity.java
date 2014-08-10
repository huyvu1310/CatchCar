package vn.edu.ptit.android.activity;

import java.util.ArrayList;

import vn.edu.ptit.android.entity.Trips;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChiTietActivity extends Activity {
	
	TextView tvMota, tvSoGheCon, tvNgay, tvGio;
	Button btLienLac, btGui;
	Trips trip = null;
	String thoiGian, gio, ngay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chi_tiet_chuyen_xe);
		
		tvMota = (TextView) findViewById(R.id.tvMoTa);
		tvSoGheCon = (TextView) findViewById(R.id.tvSoGheCon);
		//tvNgay = (TextView) findViewById(R.id.tvNgay);
		//tvGio = (TextView) findViewById(R.id.tvGio);
		
		
		btLienLac = (Button) findViewById(R.id.btLienLac);
		btGui = (Button) findViewById(R.id.btGuiYeuCau);
		
		ArrayList<Trips> arr = getIntent().getParcelableArrayListExtra("listResultTrips");
		int position = getIntent().getIntExtra("position", 0);
		
		System.out.println(position);
		
		if(arr!=null&&arr.get(position)!=null){
			
			trip = arr.get(position);
			
			tvMota.setText(trip.getLoTrinh());
			tvSoGheCon.setText(String.valueOf(trip.getSoGheCon()));
			tvGio.setText(trip.getThoiGian());
			tvNgay.setText(trip.getThoiGian());
			
//			String[] tmp = trip.getThoiGian().split(" ");
//			
//			tvGio.setText(tmp[0] + ":" + tmp[1]);
//			tvNgay.setText(tmp[2] + "/" + tmp[3] + "/" + tmp[4]);
		}
		
		
		btLienLac.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(), ChatActivity.class);
				startActivity(in);
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
