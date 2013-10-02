package com.example.leftslidemenuexample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.Toast;

public class GalleryActivity extends Activity {
	
	Gallery gl;
	ContentResolver resolver;
	Cursor c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.drawable.bear, android.R.layout.simple_gallery_item);
//		resolver = getContentResolver();
//		c = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
				
		
		gl = (Gallery) findViewById(R.id.gallery);
		gl.setAdapter(adapter);
		
//		//갤러리에 어댑터 추가
//		gl = (Gallery) findViewById(R.id.gallery);
//		ImageAdapter adapter = new ImageAdapter(this, c);
//		gl.setAdapter(adapter);
//				
//		//터치시 ImageActivity로 전환(확대)
//		gl.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				
////				Toast.makeText(GalleryActivity.this, c.getColumnName(c.getColumnIndex(MediaStore.MediaColumns.DATA)), Toast.LENGTH_SHORT).show();
//				
//				Intent intent = new Intent(GalleryActivity.this, ImageActivity.class);
//				int columnIndex = c.getColumnIndex(BaseColumns._ID);
//				intent.putExtra("view", c.getLong(columnIndex));
//				startActivity(intent);
//			}			
//		});		
		
	}

}
