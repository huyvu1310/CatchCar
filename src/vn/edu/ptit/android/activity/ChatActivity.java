package vn.edu.ptit.android.activity;

import vn.edu.ptit.android.entity.Comment;
import vn.edu.ptit.android.utils.CommentAdapter;
import vn.ptit.edu.vn.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ChatActivity extends Activity{
	private ListView lvChat;
	private EditText etChat;
	private ImageButton btChatInfor;
	private CommentAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
		lvChat = (ListView) findViewById(R.id.lvChat);
		lvChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		//lvChat.setStackFromBottom(true);
		adapter = new CommentAdapter(getApplicationContext(), R.layout.chat_listview);
		lvChat.setAdapter(adapter);
		etChat = (EditText) findViewById(R.id.etChat);
		btChatInfor = (ImageButton) findViewById(R.id.btChatInfor);
		adapter.add(new Comment(true, "Hello bubbles!"));
		lvChat.setSelection(adapter.getCount()-1);
		etChat.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					adapter.add(new Comment(false, etChat.getText().toString()));
					lvChat.setSelection(adapter.getCount()-1);
					etChat.setText("");
					return true;
				}
				return false;
			}

		});
	}
	
}
