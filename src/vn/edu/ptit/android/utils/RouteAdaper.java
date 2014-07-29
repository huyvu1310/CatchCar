package vn.edu.ptit.android.utils;

import java.util.ArrayList;
import java.util.List;

import vn.ptit.edu.vn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class RouteAdaper extends ArrayAdapter<String>{	

	private TextView tvRoute;
	private ImageButton btDelete;
	private List<String> routeList = new ArrayList<String>();
	
	public RouteAdaper(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void add(String object) {
		// TODO Auto-generated method stub
		routeList.add(object);
		super.add(object);
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return routeList.get(position);
	}

	@Override
	public void remove(String object) {
		// TODO Auto-generated method stub
		routeList.remove(object);
		super.remove(object);
	}

	public List<String> getRoute(){
		return routeList;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		final int pos = position;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.offer_trip_listview, parent, false);
		}
		tvRoute = (TextView) row.findViewById(R.id.tvRoute);
		btDelete = (ImageButton) row.findViewById(R.id.btDelete);
		tvRoute.setText(getItem(pos));
		btDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				remove(getItem(pos));
			}
		});
		return row;
	}
	
}
