package com.example.showapi;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

public class MainActivity extends Activity {
	ArrayList<Song> list = new ArrayList<Song>();
	MyAdapter adapter = new MyAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView txt = (TextView) this.findViewById(R.id.textView1);
		Button myBtn = (Button) this.findViewById(R.id.button1);
		final AsyncHttpResponseHandler resHandler=new AsyncHttpResponseHandler(){
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
				//做一些异常处理
				e.printStackTrace();
			}

			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					String str = new String(responseBody);
					Log.i("test",str);
					new MyAsyncTask().execute(str);
			}};
		myBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				txt.setTag(System.currentTimeMillis());
				new ShowApiRequest("http://route.showapi.com/213-1", "10971", "b87d4967ca7046ccac77c8be6f732378")
						.setResponseHandler(resHandler)
						.addTextPara("keyword", "林俊杰")
						.addTextPara("page", "1")
						.post();
			}
		});
		Song song = new Song();
		song.setSong_Name("12345");
		ListView lv = (ListView) findViewById(R.id.listView);
		list.add(song);
		list.add(song);
		list.add(song);
		lv.setAdapter(adapter);
		list.add(song);
		list.add(song);
		list.add(song);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getLayoutInflater().inflate(R.layout.item,parent,false);
			TextView textView = (TextView) convertView.findViewById(R.id.tv_item);
			textView.setText(list.get(position).getSong_Name());
			return convertView;
		}
	}

	class MyAsyncTask extends AsyncTask<String,Void,Void> {

		@Override
		protected void onPostExecute(Void aVoid) {
			adapter.notifyDataSetChanged();
			super.onPostExecute(aVoid);
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
			JSONObject jsonObject = new JSONObject(params[0]);
				Log.i("test",jsonObject.toString());
			JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
			list.clear();
				Log.i("test", jsonArray.toString());
				Log.i("test",jsonArray.length()+"");
			for (int i = 0;i<jsonArray.length();i++){
				JSONObject object = jsonArray.getJSONObject(i);
				Song song = new Song();
				String album_ID = object.getString("albumid");
				song.setAlbum_ID(album_ID);
				String album_Name = object.getString("albumname");
				song.setAlbum_Name(album_Name);
				String album_Pic_Big = object.getString("albumpic_big");
				song.setAlbum_Pic_Big(album_Pic_Big);
				String album_Pic_Small = object.getString("albumpic_small");
				String down_Url = object.getString("downUrl");
				String stream_Url = object.getString("m4a");
				String singer_ID = object.getString("singerid");
				String singer_Name = object.getString("singername");
				String song_ID = object.getString("songid");
				String song_Name = object.getString("songname");
				song.setSong_Name(song_Name);
				list.add(song);
				Log.e("test","test"+song.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
			return null;
		}
	}

}
