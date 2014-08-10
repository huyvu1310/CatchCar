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

import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SigninActivity extends Activity {

	private static String url = "http://192.168.130.1:8080/CatchCar/CheckUserServlet";

	EditText etUsername, etPassword, etConfirm, etPhone, etFullname, etEmail;
	ImageButton bt_OK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfirm = (EditText) findViewById(R.id.etConfirm);
		etPhone = (EditText) findViewById(R.id.etPhone);

		bt_OK = (ImageButton) findViewById(R.id.bt_OK_Signin);
		bt_OK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// check confirm pass and pass
				String pass = etPassword.getText().toString();
				String confirm = etConfirm.getText().toString();
				if (!pass.equals(confirm)) {
					Toast.makeText(SigninActivity.this,
							"Check Confirm Password", Toast.LENGTH_LONG).show();
					return;
				}

				SignInMethod signInMethod = new SignInMethod();
				signInMethod.execute(new String[] { url });
			}
		});

	}

	private class SignInMethod extends AsyncTask<String, Void, String> {

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
				jObject.put("username", etUsername.getText().toString());
				jObject.put("password", etPassword.getText().toString());
				jObject.put("fullname", etFullname.getText().toString());
				jObject.put("phone", etPhone.getText().toString());
				jObject.put("email", etEmail.getText().toString());
				String sendObj = jObject.toString();
				list.add(new BasicNameValuePair("checkuser", sendObj));
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
			// TODO Auto-generated method stub
			System.out.println(result);
			if (result.equals("YES")) {
				// ok

			} else {
				// fail
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}
}
