package com.example.daum.map;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText editText;
	ListView listView;
	ArrayList<String> list; // listview�� ������ �� ��ü
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.keyword);
		listView = (ListView) findViewById(R.id.listView);
		list = new ArrayList<String>();
		// ����Ʈ�信 �𵨰�ü�� ������ �ƴ�Ÿ ��ü
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		// ����Ʈ�信 �ƴ�Ÿ �����ϱ�
		listView.setAdapter(adapter);

	}

	// ��ư�� �������� �ݹ� �޼ҵ�
	public void find(View v) throws Exception {
		// ��ư�� ���������� �����Ͱ� ���̴� ���� �����ϱ� ����
		list.clear();
		// ��û url �����
		String keyWord = editText.getText().toString();
		// �ѱ��� ������ �ʰ� �ϱ� ����
		String encodedK = URLEncoder.encode(keyWord, "utf-8");
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://apis.daum.net/contents/movie?");
		buffer.append("apikey=15a987ee6dac45509a54c36d38b1c8dbbb5c8e10&");
		buffer.append("q=" + encodedK);
		buffer.append("&output=json");
//		buffer.append("http://apis.daum.net/local/geo/addr2coord?");
//		// �ѱ��� ��� ���ڵ� �ʿ�!(����� �����Ѵ�)
//		buffer.append("q=" + encodedK);
//		buffer.append("&apikey=52fb86a9ff1be6b9369779d94af5c8933d23cd13");
//		buffer.append("&output=json");

		String url = buffer.toString();

		// ������ ��ü�� �����ؼ� �ٿ�ε� �޴´�.
		GetJSONThread thread = new GetJSONThread(handler, null, url);
		thread.start();
	}

	// �ڵ鷯
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: // success
				Toast.makeText(MainActivity.this, "����?", 0).show();
				// json���ڿ��� ���о����
				String jsonStr = (String) msg.obj;
				try {
					// ���ڿ��� json ��ü�� ��ȯ
					// 1. channel�̶�� Ű������ {} jsonObject�� ����ִ�)
					// 2. jsonObject�ȿ��� item�̶�� Ű������ [] jsonArray �������� ������ �ִ�.
					JSONObject jsonObj = new JSONObject(jsonStr);
					// 1.
					JSONObject channel = jsonObj.getJSONObject("channel");
					// 2.
					JSONArray items = channel.getJSONArray("item");
					// 3.�ݺ��� ���鼭 �ʿ��� ������ ���´�.
					for (int i = 0; i < items.length(); i++) {
						// 4. �˻���� ���� ���´�.
						String titleContent;
						String genreContent;						
						String actorContent="";
						String storyContent;
						String openInfoContent;
						String directorContent;
						String nationContent;
						String thumbnailContent;
						String reviewContent;
						
						JSONObject itemObj = items.getJSONObject(i);
						
						JSONArray title = itemObj.getJSONArray("title");
						JSONArray genre = itemObj.getJSONArray("genre");
						JSONArray actor = itemObj.getJSONArray("actor");		// n��
						JSONArray director = itemObj.getJSONArray("director");
						JSONArray openInfo = itemObj.getJSONArray("open_info");	// 3��
						JSONArray nation = itemObj.getJSONArray("nation");
						JSONArray story = itemObj.getJSONArray("story");
						
						for(int j = 0; j < title.length(); j++) {
							
							JSONObject titleObj = title.getJSONObject(j);
							JSONObject genreObj = genre.getJSONObject(j);
							JSONObject storyObj = story.getJSONObject(j);
							JSONObject directorObj = director.getJSONObject(j);
							JSONObject openInfoObj = openInfo.getJSONObject(j);
							JSONObject nationObj = nation.getJSONObject(j);
							
							
							titleContent = titleObj.getString("content");
							genreContent = genreObj.getString("content");
							storyContent = storyObj.getString("content");
							directorContent = directorObj.getString("content");
							openInfoContent = openInfoObj.getString("content");
							nationContent = nationObj.getString("content");
							
							
							for(int k = 0; k < actor.length(); k++) {
								
								JSONObject actorObj = actor.getJSONObject(k);
								actorContent = actorObj.getString("content");
							}
									
							
							list.add(titleContent + 
									" / " 
									+ genreContent + 
									" / "
									+ actorContent +
									" / "
									+ directorContent +
									" / "
									+ openInfoContent +
									" / "
									+ nationContent +
									" / "
									+ storyContent
									);
											
						} // title ���� for
												
					}
					// ���� �����Ͱ� �ٲ���ٰ� �ƴ�Ÿ ��ü�� �˸���.
					adapter.notifyDataSetChanged();
				} catch (Exception e) {

				}

				break;
			case 1: // fail
				break;
			}
		}
	};
}