package com.example.leftslidemenuexample;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.SyncStateContract.Columns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends Activity {
	
	ImageView img;
	ContentResolver resolver;
	Uri uri;
	Cursor c;
	long id;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view);
		
		img = (ImageView)findViewById(R.id.imgView); 
		
		Intent intent = getIntent();
		id = intent.getLongExtra("view", 0);
		
				
		resolver = getContentResolver();
		c = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
			
		uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
		try {
			//Bitmap �ε�
			Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, uri);
			
			img.setImageBitmap(bm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
	}// onCreate
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		
		
		
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
				
		String []selectionArgs = { String.valueOf(id) };
		
		
		switch (item.getItemId()) {
		/*case R.id.attach:
			Intent intent2 = new Intent(Intent.ACTION_SEND);
			intent2.setType("plan/text");
			intent2.putExtra(Intent.EXTRA_STREAM, uri);
			startActivity(intent2);
			break;

		case R.id.delete: 
			
			resolver.delete(uri, "_id=?", selectionArgs);
			Toast.makeText(this, "Delete Success!!", Toast.LENGTH_SHORT).show();
			finish(); 		 
			break;
	
		case R.id.rename:
			ContentValues values = new ContentValues();
			values.put(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DISPLAY_NAME + "(2)");
			resolver.update(uri, values , "_id=?", selectionArgs);
			finish();
			break;
*/
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
}
