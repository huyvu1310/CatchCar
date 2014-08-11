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

import vn.edu.ptit.android.activity.AddNewCar;
import vn.edu.ptit.android.entity.Car;
import vn.edu.ptit.android.utils.CarAdapter;
import vn.edu.ptit.android.utils.Key;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalTab extends Activity {

	private static String url = "http://192.168.130.1:8080/CatchCar/PersonServlet";

	TextView tvUsername;
	EditText etPassword, etConfirm, etPhone;
	ImageView btUpdate, btAddNewCar, btLogout;
	ListView lvCar;
	String username;
	String password;

	Car selectCar;
	CarAdapter carAdapter;
	ArrayList<Car> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item_delete:
			DeleteCarMethod deleteCarMethod = new DeleteCarMethod();
			deleteCarMethod.execute(new String[] { url });
			list.remove(selectCar);
			carAdapter.notifyDataSetChanged();
			break;
		}
		return super.onContextItemSelected(item);
	}

	private class PersonalMethod extends AsyncTask<String, Void, String> {

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
				String id = key
						.Read_SharedPreferences(Key.ID, PersonalTab.this);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				JSONObject jObject = new JSONObject();
				jObject.put("personid", Integer.parseInt(id));
				String sendObj = jObject.toString();
				list.add(new BasicNameValuePair("person", sendObj));
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
			try {
				JSONArray arr = new JSONArray(result);
				JSONObject obj = (JSONObject) arr.get(0);
				String username = obj.getString("username");
				// String password = obj.getString("password");
				String phone = obj.getString("phone");
				tvUsername.setText(username);
				etPhone.setText(phone);

				JSONArray arrCar = (JSONArray) arr.get(1);
				for (int i = 0; i < arrCar.length(); i++) {
					JSONObject ob = arrCar.getJSONObject(i);
					Car car = new Car(ob);
					list.add(car);
				}

				carAdapter = new CarAdapter(PersonalTab.this,
						R.layout.list_car_adapter, list);
				carAdapter.notifyDataSetChanged();
				lvCar.setAdapter(carAdapter);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	private class UpdateInformationMethod extends
			AsyncTask<String, Void, String> {

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
				String id = key
						.Read_SharedPreferences(Key.ID, PersonalTab.this);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				JSONObject jObject = new JSONObject();
				jObject.put("id", Integer.parseInt(id));
				jObject.put("username", tvUsername.getText().toString());
				jObject.put("password", etPassword.getText().toString());
				jObject.put("phone", etPhone.getText().toString());
				String sendObj = jObject.toString();
				list.add(new BasicNameValuePair("updateInfo", sendObj));
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
			if (result.equals("YES")) {
				Toast.makeText(PersonalTab.this, "Update thành công..!",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(PersonalTab.this, "Update không thành công..!",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	private class DeleteCarMethod extends AsyncTask<String, Void, String> {

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
				DefaultHttpClient httpClient = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				JSONObject jObject = new JSONObject();
				jObject.put("idCar", selectCar.getId());

				String sendObj = jObject.toString();
				list.add(new BasicNameValuePair("deleteCar", sendObj));
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
			if (result.equals("YES")) {
				Toast.makeText(PersonalTab.this, "thanh cong",
						Toast.LENGTH_LONG).show();
			} else {
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Key key = new Key();
		String id = key.Read_SharedPreferences(Key.ID, PersonalTab.this);
		if (id.equals("empty")) {
			setContentView(R.layout.abc);
		} else {
			setContentView(R.layout.personal_tab_activity);
			tvUsername = (TextView) findViewById(R.id.tvUsername);
			etPassword = (EditText) findViewById(R.id.etPassword);
			etConfirm = (EditText) findViewById(R.id.etConfirm);
			etPhone = (EditText) findViewById(R.id.etPhone);
			btUpdate = (ImageView) findViewById(R.id.btUpdate);
			btAddNewCar = (ImageView) findViewById(R.id.btAddNewCar);
			btLogout = (ImageView) findViewById(R.id.btLogout);

			lvCar = (ListView) findViewById(R.id.lvCar);

			btUpdate.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String pass = etPassword.getText().toString();
					String repass = etConfirm.getText().toString();
					if (!pass.equals(repass)) {
						Toast.makeText(PersonalTab.this,
								"Kiểm tra lại Password và Confirm..!",
								Toast.LENGTH_LONG).show();
						return;
					}
					UpdateInformationMethod upMethod = new UpdateInformationMethod();
					upMethod.execute(new String[] { url });
				}
			});

			btAddNewCar.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(PersonalTab.this,
							AddNewCar.class);
					startActivity(intent);
				}
			});

			btLogout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Key key = new Key();
					key.Write_SharedPreferences(Key.CHECK_LOGIN, Key.NO_LOGIN,
							PersonalTab.this);
					Intent intent = new Intent(PersonalTab.this, MainTab.class);
					startActivity(intent);
				}
			});

			lvCar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					selectCar = list.get(position);
					return false;
				}
			});
			list= new ArrayList<Car>();
			registerForContextMenu(lvCar);
			PersonalMethod personalMethod = new PersonalMethod();
			personalMethod.execute(new String[] { url });
			
		}

	}

}
