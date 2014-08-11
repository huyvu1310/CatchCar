package vn.edu.ptit.android.tab;

import vn.edu.ptit.android.activity.SearchActivity;
import vn.edu.ptit.android.utils.Key;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchTab extends Activity {
	
	Button btSearchOther;
	
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
		String id = key.Read_SharedPreferences(Key.ID, SearchTab.this);
		if (id.equals("empty")) {
			setContentView(R.layout.abc);
		} else {
			setContentView(R.layout.search_tab_activity);
			btSearchOther = (Button) findViewById(R.id.btSearchOther);
			
			btSearchOther.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(SearchTab.this,SearchActivity.class);
					startActivity(intent);
				}
			});
		}
	}
}
