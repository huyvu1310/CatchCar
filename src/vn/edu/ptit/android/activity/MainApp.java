package vn.edu.ptit.android.activity;

import vn.ptit.edu.vn.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainApp extends Activity {

	ActionBar actionBar;

	TextView tvGmap, tvTimkiem, tvDangtin, tvChat, tvHuongdan, tvLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setTitle("");
		actionBar.setSubtitle(getResources().getString(R.string.app_name));
		actionBar.hide();
		setContentView(R.layout.main_app);

		tvGmap = (TextView) findViewById(R.id.tvGmap);
		tvTimkiem = (TextView) findViewById(R.id.tvTimkiem);
		tvDangtin = (TextView) findViewById(R.id.tvDangtin);
		tvChat = (TextView) findViewById(R.id.tvChat);
		tvHuongdan = (TextView) findViewById(R.id.tvHuongdan);
		tvLogin = (TextView) findViewById(R.id.tvLogin);

		tvGmap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainApp.this, GmapActivity.class);
				startActivity(intent);
			}
		});

		tvLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainApp.this, LoginActivity.class);
				startActivity(intent);
			}
		});

		tvDangtin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainApp.this,
						OfferTripActivity.class);
				startActivity(intent);
			}
		});

		tvTimkiem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainApp.this, SearchActivity.class);
				startActivity(intent);
			}
		});
	}
}
