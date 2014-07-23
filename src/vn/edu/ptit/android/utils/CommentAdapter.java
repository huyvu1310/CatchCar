package vn.edu.ptit.android.utils;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.android.entity.Comment;
import vn.ptit.edu.vn.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Comment>{
	
	private LinearLayout wrapper;
	private List<Comment> cmtList = new ArrayList<Comment>();
	private TextView tvComment;

	public CommentAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void add(Comment cmt){
		cmtList.add(cmt);
		super.add(cmt);
	}
	
	public Comment getItem(int index){
		return cmtList.get(index);
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.chat_listview, parent, false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

		Comment cmt = getItem(position);

		tvComment = (TextView) row.findViewById(R.id.comment);

		tvComment.setText(cmt.comment);

		tvComment.setBackgroundResource(cmt.left ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		wrapper.setGravity(cmt.left ? Gravity.LEFT : Gravity.RIGHT);

		return row;
	}
	
	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
	
}
