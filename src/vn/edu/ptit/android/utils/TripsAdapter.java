package vn.edu.ptit.android.utils;

import java.util.ArrayList;

import vn.edu.ptit.android.entity.Trips;
import vn.ptit.edu.vn.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TripsAdapter extends ArrayAdapter<Trips> {
	Activity context;
	ArrayList<Trips> arrContact = null;
	int layoutId;

	public TripsAdapter(Activity context, int textViewResourceId,
			ArrayList<Trips> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arrContact = objects;
		this.layoutId = textViewResourceId;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(layoutId, null);

		TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
		TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
		TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

		Trips trips = arrContact.get(position);
		tvAuthor.setText(String.valueOf(trips.getTaiXeid()));
		tvDate.setText(trips.getThoiGian());
		tvTime.setText(trips.getThoiGian());

		return convertView;
	}
}
