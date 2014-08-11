package vn.edu.ptit.android.utils;

import java.util.ArrayList;

import vn.edu.ptit.android.entity.Ride;
import vn.ptit.edu.vn.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RideAdapter extends ArrayAdapter<Ride> {

	Activity context;
	ArrayList<Ride> arr = null;
	int layoutId;

	public RideAdapter(Activity context, int textViewResourceId,
			ArrayList<Ride> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arr = objects;
		this.layoutId = textViewResourceId;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(layoutId, null);

		TextView tvUsername = (TextView) convertView
				.findViewById(R.id.tvUsername);
		TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
		TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
		TextView tvPlace = (TextView) convertView.findViewById(R.id.tvPlace);
		TextView tvMessage = (TextView) convertView
				.findViewById(R.id.tvMessage);

		Ride ride = arr.get(position);

		tvUsername.setText(ride.getUser().getUsername());
		tvPhone.setText(ride.getUser().getPhone());
		tvTime.setText(ride.getTime());
		tvPlace.setText(ride.getAddress());
		tvMessage.setText(ride.getMessage());

		return convertView;
	}
}
