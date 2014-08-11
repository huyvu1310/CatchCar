package vn.edu.ptit.android.activity;

import vn.edu.ptit.android.entity.TripDetail;
import vn.edu.ptit.android.entity.Trips;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RideDetail extends Activity {

	TextView tvMotaxe, tvSoGheCon, tvGiodi, tvGioden, tvLotrinh, tvBienSo,
			tvLoaixe, tvTaixe, tvPhone, tvGiodon, tvDiadiem;
	Trips trip = null;
	String thoiGian, gio, ngay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chi_tiet_di_nho);

		tvLotrinh = (TextView) findViewById(R.id.tvMoTa);
		tvGiodi = (TextView) findViewById(R.id.tvGiodi);
		tvGioden = (TextView) findViewById(R.id.tvGioden);
		tvBienSo = (TextView) findViewById(R.id.tvLicense);
		tvLoaixe = (TextView) findViewById(R.id.tvTypeCar);
		tvSoGheCon = (TextView) findViewById(R.id.tvGheTrong);
		
		tvTaixe = (TextView) findViewById(R.id.tvUserTaixe);
		tvPhone = (TextView) findViewById(R.id.tvPhone);
		tvGiodon = (TextView) findViewById(R.id.tvTime);
		tvDiadiem = (TextView) findViewById(R.id.tvPlace);

		TripDetail trDetail = (TripDetail) getIntent().getSerializableExtra(
				"rideSelect");
		
		
		
		tvLotrinh.setText(trDetail.getTrips().getDistance());
		tvGiodi.setText(trDetail.getTrips().getTimestart());
		tvGioden.setText(trDetail.getTrips().getTimeend());
		tvBienSo.setText(trDetail.getCar().getLecense());
		tvLoaixe.setText(trDetail.getCar().getName() + " "
				+ trDetail.getCar().getType());
		tvSoGheCon.setText(Integer
				.toString(trDetail.getTrips().getEmptyseats()));

		tvPhone.setText(trDetail.getTaixe().getPhone());
		tvGiodon.setText(trDetail.getListCatch().get(0).getTime());
		tvDiadiem.setText(trDetail.getListCatch().get(0).getAddress());
		tvTaixe.setText(trDetail.getTaixe().getUsername());
	}

}
