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
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.ptit.android.tab.MainTab;
import vn.edu.ptit.android.utils.Key;
import vn.ptit.edu.vn.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends Activity {
	private static String url = "http://192.168.130.1:8080/CatchCar/CheckUserServlet";

	ActionBar actionBar;

	EditText etUsername, etPassword;

	ImageView btSignin, btLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);

		btSignin = (ImageView) findViewById(R.id.btSigin);
		btLogin = (ImageView) findViewById(R.id.btLogin);

		btSignin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						SigninActivity.class);
				startActivity(intent);
			}
		});

		btLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginMethod loginMethod = new LoginMethod();
				loginMethod.execute(new String[] { url });
			}
		});
	}

	private class LoginMethod extends AsyncTask<String, Void, String> {

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

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println(result);
			if (result.equals("NO")) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						LoginActivity.this).create();
				alertDialog.setTitle("Đăng nhập"); // set title
				alertDialog.setMessage("Đăng nhập không thành công..!"); // set
																			// Message
				alertDialog.setIcon(R.drawable.ic_error); // set icon/image
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etPassword.setText("");
							}
						});
				// Showing Alert Message
				alertDialog.show();

			} else {
				try {
					JSONObject obj = new JSONObject(result);
					System.out.println(result);
					int id = obj.getInt("id");
					Key key = new Key();
					key.Write_SharedPreferences(Key.ID, Integer.toString(id),
							LoginActivity.this);

					key.Write_SharedPreferences(Key.CHECK_LOGIN, Key.YES_LOGIN,
							LoginActivity.this);
					Intent intent = new Intent(LoginActivity.this,
							MainTab.class);
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}
}
