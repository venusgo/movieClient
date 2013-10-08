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
	ArrayList<String> list; // listview에 연결할 모델 객체
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.keyword);
		listView = (ListView) findViewById(R.id.listView);
		list = new ArrayList<String>();
		// 리스트뷰에 모델객체를 연결할 아답타 객체
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		// 리스트뷰에 아답타 연결하기
		listView.setAdapter(adapter);

	}

	// 버튼을 눌렀을때 콜백 메소드
	public void find(View v) throws Exception {
		// 버튼이 눌릴때마다 데이터가 쌓이는 것을 방지하기 위해
		list.clear();
		// 요청 url 만들기
		String keyWord = editText.getText().toString();
		// 한글이 깨지지 않게 하기 위해
		String encodedK = URLEncoder.encode(keyWord, "utf-8");
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://apis.daum.net/contents/movie?");
		buffer.append("apikey=15a987ee6dac45509a54c36d38b1c8dbbb5c8e10&");
		buffer.append("q=" + encodedK);
		buffer.append("&output=json");
//		buffer.append("http://apis.daum.net/local/geo/addr2coord?");
//		// 한글일 경우 인코딩 필요!(영어로 가정한다)
//		buffer.append("q=" + encodedK);
//		buffer.append("&apikey=52fb86a9ff1be6b9369779d94af5c8933d23cd13");
//		buffer.append("&output=json");

		String url = buffer.toString();

		// 스레드 객체를 생성해서 다운로드 받는다.
		GetJSONThread thread = new GetJSONThread(handler, null, url);
		thread.start();
	}

	// 핸들러
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: // success
				Toast.makeText(MainActivity.this, "성공?", 0).show();
				// json문자열을 ㅡ읽어오기
				String jsonStr = (String) msg.obj;
				try {
					// 문자열을 json 객체로 변환
					// 1. channel이라는 키값으로 {} jsonObject가 들어있다)
					// 2. jsonObject안에는 item이라는 키값으로 [] jsonArray 벨류값을 가지고 있다.
					JSONObject jsonObj = new JSONObject(jsonStr);
					// 1.
					JSONObject channel = jsonObj.getJSONObject("channel");
					// 2.
					JSONArray items = channel.getJSONArray("item");
					// 3.반복문 돌면서 필요한 정보만 얻어온다.
					for (int i = 0; i < items.length(); i++) {
						// 4. 검색결과 값을 얻어온다.
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
						JSONArray actor = itemObj.getJSONArray("actor");		// n개
						JSONArray director = itemObj.getJSONArray("director");
						JSONArray openInfo = itemObj.getJSONArray("open_info");	// 3개
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
											
						} // title 갯수 for
												
					}
					// 모델의 데이터가 바뀌었다고 아답타 객체에 알린다.
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