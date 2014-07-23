package vn.edu.ptit.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.edu.ptit.android.entity.Trips;
import vn.edu.ptit.android.entity.TripsAdapter;
import vn.ptit.edu.vn.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

public class SearchActivity extends Activity {

	ActionBar actionBar;
	ImageView btSearch;
	private static String url = "http://192.168.38.1:8080/CatchCar/ChuyenxeServlet";

	AutoCompleteTextView tvFromPlace, tvFromDistrict, tvToPlace, tvToDistrict;
	ListView lvResult;
	TripsAdapter tripsAdapter;

	private static final String[] COUNTRIES = new String[] { "HĂ  Ná»™i",
			"Nam Ä�á»‹nh", "VÄ©nh PhĂºc", "ThĂ¡i BĂ¬nh", "Quáº£ng Ninh" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setTitle("");
		actionBar.setSubtitle(getResources().getString(R.string.app_name));
		actionBar.hide();
		setContentView(R.layout.search_car_activity);

		btSearch = (ImageView) findViewById(R.id.btSearch);
		btSearch.setOnClickListener(searchListener);

		// set auto Text for TextField
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, COUNTRIES);
		tvFromPlace = (AutoCompleteTextView) findViewById(R.id.atTvFromPlace);
		tvFromDistrict = (AutoCompleteTextView) findViewById(R.id.atTvFromDistrict);
		tvToPlace = (AutoCompleteTextView) findViewById(R.id.atTvToPlace);
		tvToDistrict = (AutoCompleteTextView) findViewById(R.id.atTvToDistrict);
		tvFromPlace.setAdapter(adapter);

		// lvResult
		lvResult = (ListView) findViewById(R.id.lvResult);
	}

	private OnClickListener searchListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub	
			SearchMethod searchMethod = new SearchMethod();
			searchMethod.execute(new String[]{url});
		}
	};

	private class SearchMethod extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONArray out = null;
			for (String param : params) {
				out = getOutput(param);
			}
			return out;
		}

		private JSONArray getOutput(String param) {
			// TODO Auto-generated method stub
			JSONArray out = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String fromPlace = tvFromPlace.getText().toString();
				String fromDistrict = tvFromDistrict.getText().toString();
				String toPlace = tvToPlace.getText().toString();
				String toDistrict = tvToDistrict.getText().toString();
				String search = fromDistrict + "-" + fromPlace + ","
						+ toDistrict + "-" + toPlace;
				System.out.println(search);
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("chuyenxe", search));
				UrlEncodedFormEntity e = new UrlEncodedFormEntity(list,
						HTTP.UTF_8);
				HttpPost httpPost = new HttpPost(param);
				httpPost.setEntity(e);
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();
				String res = EntityUtils.toString(httpEntity);
				out = new JSONArray(res);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return out;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			ArrayList<Trips> list = new ArrayList<Trips>();
			for (int i = 0; i < result.length(); i++) {
				try {
					JSONObject jsonObject = result.getJSONObject(i);
					list.add(new Trips(jsonObject));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			tripsAdapter = new TripsAdapter(SearchActivity.this,
					R.layout.search_car_listview, list);
			lvResult.setAdapter(tripsAdapter);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}
}
