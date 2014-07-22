package vn.edu.ptit.android.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_app);

		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int screenWidth = size.x;
		@SuppressWarnings("unused")
		int screenHeight = size.y;
		int halfScreenWidth = (int) (screenWidth * 0.5);
		int quarterScreenWidth = (int) (halfScreenWidth * 0.5);

		Spec row1 = GridLayout.spec(0, 2);
		Spec row2 = GridLayout.spec(2);
		Spec row3 = GridLayout.spec(3);
		Spec row4 = GridLayout.spec(4, 2);

		Spec col0 = GridLayout.spec(0);
		Spec col1 = GridLayout.spec(1);
		Spec colspan2 = GridLayout.spec(0, 2);

		GridLayout gridLayout = new GridLayout(this);
		gridLayout.setColumnCount(2);
		gridLayout.setRowCount(15);

		TextView twoByTwo1 = new TextView(this);
		GridLayout.LayoutParams first = new GridLayout.LayoutParams(row1,
				colspan2);
		first.width = screenWidth;
		first.height = quarterScreenWidth * 2;
		twoByTwo1.setLayoutParams(first);
		twoByTwo1.setGravity(Gravity.CENTER);
		twoByTwo1.setBackgroundColor(Color.RED);
		twoByTwo1.setText("TOP");
		twoByTwo1.setTextAppearance(this, android.R.style.TextAppearance_Large);
		gridLayout.addView(twoByTwo1, first);

		TextView twoByOne1 = new TextView(this);
		GridLayout.LayoutParams second = new GridLayout.LayoutParams(row2, col0);
		second.width = halfScreenWidth;
		second.height = quarterScreenWidth;
		twoByOne1.setLayoutParams(second);
		twoByOne1.setBackgroundColor(Color.BLUE);
		twoByOne1.setText("Staff Choices");
		twoByOne1.setTextAppearance(this, android.R.style.TextAppearance_Large);
		gridLayout.addView(twoByOne1, second);

		TextView twoByOne2 = new TextView(this);
		GridLayout.LayoutParams third = new GridLayout.LayoutParams(row2, col1);
		third.width = halfScreenWidth;
		third.height = quarterScreenWidth;
		twoByOne2.setLayoutParams(third);
		twoByOne2.setBackgroundColor(Color.GREEN);
		twoByOne2.setText("Games");
		twoByOne2.setTextAppearance(this, android.R.style.TextAppearance_Large);
		gridLayout.addView(twoByOne2, third);

		TextView twoByOne3 = new TextView(this);
		GridLayout.LayoutParams fourth = new GridLayout.LayoutParams(row3, col0);
		fourth.width = halfScreenWidth;
		fourth.height = quarterScreenWidth;
		twoByOne3.setLayoutParams(fourth);
		twoByOne3.setBackgroundColor(Color.YELLOW);
		twoByOne3.setText("Editor's Choices");
		twoByOne3.setTextAppearance(this,
				android.R.style.TextAppearance_Large_Inverse);
		gridLayout.addView(twoByOne3, fourth);

		TextView twoByOne4 = new TextView(this);
		GridLayout.LayoutParams fifth = new GridLayout.LayoutParams(row3, col1);
		fifth.width = halfScreenWidth;
		fifth.height = quarterScreenWidth;
		twoByOne4.setLayoutParams(fifth);
		twoByOne4.setBackgroundColor(Color.MAGENTA);
		twoByOne4.setText("Something Else");
		twoByOne4.setTextAppearance(this, android.R.style.TextAppearance_Large);
		gridLayout.addView(twoByOne4, fifth);

		TextView twoByTwo2 = new TextView(this);
		GridLayout.LayoutParams sixth = new GridLayout.LayoutParams(row4,
				colspan2);
		sixth.width = screenWidth;
		sixth.height = quarterScreenWidth * 2;
		twoByTwo2.setLayoutParams(sixth);
		twoByTwo2.setGravity(Gravity.CENTER);
		twoByTwo2.setBackgroundColor(Color.WHITE);
		twoByTwo2.setText("BOTOM");
		twoByTwo2.setTextAppearance(this,
				android.R.style.TextAppearance_Large_Inverse);
		gridLayout.addView(twoByTwo2, sixth);

		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.addView(gridLayout);
		setContentView(relativeLayout);
	}
}
