package vn.edu.ptit.android.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.edu.ptit.android.activity.OfferTripActivity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements
		Filterable {

	private ArrayList<String> resultList;

	public PlacesAutoCompleteAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public String getItem(int index) {
		return resultList.get(index);
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					resultList = autocomplete(constraint.toString());

					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

	protected ArrayList<String> autocomplete(String input) {
		ArrayList<String> resultList = new ArrayList<String>();
		Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocationName(input, 15,
					8.618873, 101.917532, 23.493583, 108.297043);
			if (addresses != null && !addresses.isEmpty()) {
				for (int i = 0; i < addresses.size(); i++) {
					Address a = addresses.get(i);
					if (a.getCountryCode().equalsIgnoreCase("VN")) {
						resultList.add(a.getAddressLine(0) + ","
								+ a.getAddressLine(1) + ","
								+ a.getAddressLine(2));
					}
				}
			}
		} catch (Exception e) {

		}
		return resultList;
	}
}
