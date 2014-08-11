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
import org.json.JSONObject;

import vn.edu.ptit.android.utils.Key;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNewCar extends Activity {

	EditText etNameCar, etTypeCar, etSoghe, etBienso, etDes;
	ImageView btAddNewCar;

	private static String url = "http://192.168.130.1:8080/CatchCar/PersonServlet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_car);

		etNameCar = (EditText) findViewById(R.id.etNameCar);
		etTypeCar = (EditText) findViewById(R.id.etTypecar);
		etSoghe = (EditText) findViewById(R.id.etNumberofseat);
		etBienso = (EditText) findViewById(R.id.etBienso);
		etDes = (EditText) findViewById(R.id.etDescription);

		btAddNewCar = (ImageView) findViewById(R.id.btAddNewCar);
		btAddNewCar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddNewCarMethod method = new AddNewCarMethod();
				method.execute(new String[] { url });
			}
		});

	}

	private class AddNewCarMethod extends AsyncTask<String, Void, String> {

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
				String id = key.Read_SharedPreferences(Key.ID, AddNewCar.this);

				System.out.println("dfafafd" + id);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				JSONObject jObject = new JSONObject();
				jObject.put("name", etNameCar.getText().toString());
				jObject.put("type", etTypeCar.getText().toString());
				String s = etSoghe.getText().toString();
				System.out.println("soghe" + s);
				jObject.put("numberofseats", Integer.parseInt(s));

				jObject.put("license", etBienso.getText().toString());
				jObject.put("description", etDes.getText().toString());
				jObject.put("iduser", Integer.parseInt(id));
				String sendObj = jObject.toString();
				list.add(new BasicNameValuePair("addCar", sendObj));
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
			System.out.println("Result" + result);
			if (result.equals("YES")) {
				Toast.makeText(AddNewCar.this, "Thêm thành công..!",
						Toast.LENGTH_LONG).show();
				etNameCar.setText("");
				etTypeCar.setText("");
				etBienso.setText("");
				etSoghe.setText("");
				etDes.setText("");
			} else {
				Toast.makeText(AddNewCar.this, "Không thành công..!",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}
}
