package vn.edu.ptit.android.utils;

import java.util.ArrayList;

import vn.edu.ptit.android.entity.Car;
import vn.ptit.edu.vn.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class CarAdapter extends ArrayAdapter<Car> {
	Activity context;
	ArrayList<Car> arrCar = null;
	int layoutId;

	Car car;

	public CarAdapter(Activity context, int textViewResourceId,
			ArrayList<Car> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arrCar = objects;
		this.layoutId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(layoutId, null);

		TextView tvNameCar = (TextView) convertView
				.findViewById(R.id.tvNameCar);
		TextView tvLicense = (TextView) convertView
				.findViewById(R.id.tvLicense);
		TextView tvDes = (TextView) convertView.findViewById(R.id.tvDes);

		car = arrCar.get(position);
		tvNameCar.setText(car.getName());
		tvLicense.setText(car.getLecense());
		tvDes.setText(car.getDes());

		return convertView;
	}

}
