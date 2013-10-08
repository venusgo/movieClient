package com.example.daum.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.text.style.BulletSpan;
import android.util.Log;
import android.widget.Toast;

public class GetJSONThread extends Thread {
	// ���ν������� �ڵ鷯
	Handler handler;
	// ������ �����Ͱ� ����ִ� map��ü
	Map<String, String> map;
	// ������ url �ּ�
	String url;

	// ������
	public GetJSONThread(Handler handler, Map<String, String> map, String url) {
		this.handler = handler;
		this.map = map;
		this.url = url;
	}

	// ������ ��ü
	@Override
	public void run() {
		HttpURLConnection conn = null;
		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(this.url);
			conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {// ���������� �Ǿ��ٸ�
				conn.setConnectTimeout(10000);// �ִ� ���ð�10��
				conn.setUseCaches(false);// ĳ��������
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					// InputStreamReader ��ü ������
					InputStreamReader isr = new InputStreamReader(
							conn.getInputStream());
					BufferedReader br = new BufferedReader(isr);
					// �ݺ��� ���鼭 �о����
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						// �о�� ���ڿ��� ��ü�� ����
						builder.append(line);
					}
					br.close();
				}// if
			}// if

			Message msg = new Message();
			msg.what = 0; // ����
			msg.obj = builder.toString();
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post ������ ����!", e.getMessage());
			Message msg = new Message();
			msg.what = 1; // ����
			msg.obj = "�����͸� �޾ƿ� �� �����ϴ�.";
			handler.sendMessage(msg);
		} finally {
			conn.disconnect(); // ���� ����
		}
	}// run

}