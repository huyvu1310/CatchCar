package vn.edu.ptit.android.utils;

import java.util.ArrayList;

import vn.edu.ptit.android.entity.TripDetail;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TripDetailAdapter extends ArrayAdapter<TripDetail> {

	Activity context;
	ArrayList<TripDetail> arr = null;
	int layoutId;

	public TripDetailAdapter(Activity context, int textViewResourceId,
			ArrayList<TripDetail> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arr = objects;
		this.layoutId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(layoutId, null);

		TextView tvGioKhoiHanh = (TextView) convertView
				.findViewById(R.id.tvGioKhoiHanh);
		TextView tvGioDen = (TextView) convertView.findViewById(R.id.tvGioDen);
		TextView tvTenXe = (TextView) convertView.findViewById(R.id.tvTenXe);
		TextView tvDistance = (TextView) convertView
				.findViewById(R.id.tvDistance);

		TripDetail tripDetail = arr.get(position);

		tvGioKhoiHanh.setText(tripDetail.getTrips().getTimestart());
		tvGioDen.setText(tripDetail.getTrips().getTimeend());
		tvTenXe.setText(tripDetail.getCar().getName());
		tvDistance.setText(tripDetail.getTrips().getDistance());

		return convertView;
	}
}
