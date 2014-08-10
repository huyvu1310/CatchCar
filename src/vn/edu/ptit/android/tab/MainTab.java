package vn.edu.ptit.android.tab;

import vn.ptit.edu.vn.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainTab extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout);

		TabHost tabHost = getTabHost();

		// Khởi tạo tab chuyen di
		TabSpec chuyen_di = tabHost.newTabSpec("");
		// Thiết lập tên Tab và hiển thị icon
		chuyen_di.setIndicator(getResources().getString(R.string.chuyen_di),
				null);
		// Thiết lập nội dung cho tab này là activity TripTab.class
		Intent phoIntent = new Intent(this, TripTab.class);
		chuyen_di.setContent(phoIntent);

		// Khởi tạo tab di nho
		TabSpec di_nho = tabHost.newTabSpec("");
		di_nho.setIndicator(getResources().getString(R.string.di_nho), null);
		Intent musicIntent = new Intent(this, HitchhikeTab.class);
		di_nho.setContent(musicIntent);

		// khoi tao tab tim kiem
		TabSpec tim_kiem = tabHost.newTabSpec("");
		tim_kiem.setIndicator(getResources().getString(R.string.tim_kiem), null);
		Intent searchIntent = new Intent(this, SearchTab.class);
		tim_kiem.setContent(searchIntent);

		// khoi tao tab ca nhan
		TabSpec ca_nhan = tabHost.newTabSpec("");
		ca_nhan.setIndicator(getResources().getString(R.string.ca_nhan), null);
		Intent personanlIntent = new Intent(this, PersonalTab.class);
		ca_nhan.setContent(personanlIntent);

		tabHost.addTab(chuyen_di);
		tabHost.addTab(di_nho);
		tabHost.addTab(tim_kiem);
		tabHost.addTab(ca_nhan);

		tabHost.setCurrentTab(2);
	}
}
