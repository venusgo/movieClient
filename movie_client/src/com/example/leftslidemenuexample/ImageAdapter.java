package com.example.leftslidemenuexample;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends CursorAdapter {
	
	public ImageAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
//		Log.e("tag", String.valueOf(columnIndex));
//		Log.e("tag", cursor.getString(columnIndex));
		ImageButton imgButton = (ImageButton) view.findViewById(R.id.imageButton);
//		TextView txtView = (TextView) view.findViewById(R.id.textView1);
//		txtView.setText(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BaseColumns._ID);
//		Log.e("tag", String.valueOf(columnIndex));
//		Log.e("tag", String.valueOf(cursor.getLong(columnIndex)));
		
		long id = cursor.getLong(columnIndex); // "_ID" 칼럼 값을 얻어 와서 개별 이미지에 대한 URI 생성, CONTENT_URL/2
		
		Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
		
		try {
			//Bitmap 로드
			Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			
			imgButton.setImageBitmap(bm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//항목 한개에 대한 레이아웃만 지정하면 이 메소드는 Cursor가 갖고 있는 row의 갯수만큼
	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) arg0.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = (View) inflater.inflate(R.layout.item, null);
		return view;
	}

}
