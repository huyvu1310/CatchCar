package vn.edu.ptit.android.tab;

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

import vn.edu.ptit.android.activity.ChiTietActivity;
import vn.edu.ptit.android.activity.RideDetail;
import vn.edu.ptit.android.entity.TripDetail;
import vn.edu.ptit.android.utils.Key;
import vn.edu.ptit.android.utils.TripDetailAdapter;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HitchhikeTab extends Activity {

	private static String url = "http://192.168.130.1:8080/CatchCar/TripServlet";

	ListView lvTripRegistry;
	TripDetailAdapter adapter;
	TripDetail rideSelect;
	ArrayList<TripDetail> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Key key = new Key();
		String id = key.Read_SharedPreferences(Key.ID, HitchhikeTab.this);
		if (id.equals("empty")) {
			setContentView(R.layout.abc);
		} else {
			setContentView(R.layout.hitchhike_tab_activity);
			lvTripRegistry = (ListView) findViewById(R.id.lvTripRegistry);

			TripRegistryMethod trRegistryMethod = new TripRegistryMethod();
			trRegistryMethod.execute(new String[] { url });

			lvTripRegistry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					rideSelect = list.get(position);
					Intent intent = new Intent(HitchhikeTab.this,
							RideDetail.class);
					intent.putExtra("rideSelect", rideSelect);
					startActivity(intent);
				}
			});
		}
	}

	private class TripRegistryMethod extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String out = null;
			for (String param : params) {
				out = getOutput(param);
			}
			return out;
		}

		private String getOutput(String param) {
			// TODO Auto-generated method stub
			String out = null;
			try {
				Key key = new Key();
				String id = key.Read_SharedPreferences(Key.ID,
						HitchhikeTab.this);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				JSONObject jObject = new JSONObject();
				jObject.put("iduser", Integer.parseInt(id));
				String sendObj = jObject.toString();
				System.out.println("");
				list.add(new BasicNameValuePair("tripRegistry", sendObj));
				UrlEncodedFormEntity e = new UrlEncodedFormEntity(list,
						HTTP.UTF_8);
				HttpPost httpPost = new HttpPost(param);
				httpPost.setEntity(e);
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();
				String res = EntityUtils.toString(httpEntity);
				out = res;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return out;
		}

		@Override
		protected void onPostExecute(String result) {

			System.out.println(result);

			list = new ArrayList<TripDetail>();

			try {
				JSONArray arr = new JSONArray(result);
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					TripDetail tripDetail = new TripDetail(obj);
					list.add(tripDetail);
				}

				adapter = new TripDetailAdapter(HitchhikeTab.this,
						R.layout.list_trip_adapter, list);
				adapter.notifyDataSetChanged();
				lvTripRegistry.setAdapter(adapter);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}
}
