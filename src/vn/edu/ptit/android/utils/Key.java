package vn.edu.ptit.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Key {
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String CHECK_LOGIN = "check_login";
	public static final String YES_LOGIN = "yes_login";
	public static final String NO_LOGIN = "no_login";

	public void Write_SharedPreferences(String key, String value,
			Context context) {
		SharedPreferences preference = context.getSharedPreferences("Setup",
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String Read_SharedPreferences(String key, Context context) {
		SharedPreferences preference = context.getSharedPreferences("Setup",
				Context.MODE_PRIVATE);
		return preference.getString(key, "");
	}
}
