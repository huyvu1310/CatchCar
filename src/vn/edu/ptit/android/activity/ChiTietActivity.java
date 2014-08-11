package vn.edu.ptit.android.activity;

import vn.edu.ptit.android.entity.TripDetail;
import vn.edu.ptit.android.entity.Trips;
import vn.edu.ptit.android.utils.RideAdapter;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ChiTietActivity extends Activity {

	TextView tvMotaxe, tvSoGheCon, tvGiodi, tvGioden, tvLotrinh, tvBienSo,
			tvLoaixe;
	Trips trip = null;
	String thoiGian, gio, ngay;

	ListView lvRide;
	RideAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chi_tiet_chuyen_xe);

		tvLotrinh = (TextView) findViewById(R.id.tvMoTa);
		tvGiodi = (TextView) findViewById(R.id.tvGiodi);
		tvGioden = (TextView) findViewById(R.id.tvGioden);
		tvBienSo = (TextView) findViewById(R.id.tvLicense);
		tvLoaixe = (TextView) findViewById(R.id.tvTypeCar);
		tvSoGheCon = (TextView) findViewById(R.id.tvGheTrong);

		lvRide = (ListView) findViewById(R.id.lvListCatch);

		TripDetail trDetail = (TripDetail) getIntent().getSerializableExtra(
				"tripSelect");
		tvLotrinh.setText(trDetail.getTrips().getDistance());
		tvGiodi.setText(trDetail.getTrips().getTimestart());
		tvGioden.setText(trDetail.getTrips().getTimeend());
		tvBienSo.setText(trDetail.getCar().getLecense());
		tvLoaixe.setText(trDetail.getCar().getName() + " "
				+ trDetail.getCar().getType());
		tvSoGheCon.setText(Integer
				.toString(trDetail.getTrips().getEmptyseats()));

		adapter = new RideAdapter(ChiTietActivity.this,
				R.layout.list_ride_adapter, trDetail.getListCatch());
		lvRide.setAdapter(adapter);
	}

}
