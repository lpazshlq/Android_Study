package com.lei.lesson11_homework;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Homework03 extends Activity implements AdapterView.OnItemSelectedListener {
    static final String URL_SH = "http://api.k780.com:88/?app=weather.future&weaid=36&appkey=12445&sign=52e38d92690b729a65ed10871a604d56&format=json";
    static final String URL_BJ = "http://api.k780.com:88/?app=weather.future&weaid=1&appkey=12445&sign=52e38d92690b729a65ed10871a604d56&format=json";
    /*数据站点数组*/
    private String[] urls = {URL_SH, URL_BJ};
    private Spinner spn;
    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<Weather> weather_list;
    private ArrayList<Bitmap> icon_list;
    private String[] cities = {"上海", "北京"};
    private int select = 0, success = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework03);
        init();
        listener();
        new MyAsyncTask().execute();
    }

    private void init() {
        spn = (Spinner) findViewById(R.id.spn_homework03);
        lv = (ListView) findViewById(R.id.lv_homework03);
        weather_list = new ArrayList<>();
        icon_list = new ArrayList<>();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        spn.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1, cities));
    }

    private void listener() {
        spn.setOnItemSelectedListener(this);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /*网络数据更新下载完毕后进行adapter刷新显示*/
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(urls[select]);
                URLConnection conn = url.openConnection();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String temp;
                while ((temp = bfr.readLine()) != null) {
                    sb.append(temp);
                }
                bfr.close();
                getJsonData(sb);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /*对网络获得的json模式数据进行json解析并将解析获得的Weather对象加入weather_list中*/
    private void getJsonData(StringBuilder sb) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(sb.toString());
        success = jsonObject.getInt("success");
                /*通过解析分析是否获取到想要的数据，1为成功，0为失败*/
        if (success == 1) {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            weather_list.clear();
            /*遍历解析获得到的每一天的天气数据*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String day = object.getString("week");
                String temperature = object.getString("temperature");
                String weather = object.getString("weather");
                String wind = object.getString("wind");
                String weather_picture = object.getString("weather_icon");
                weather_list.add(new Weather(day, temperature, weather, weather_picture, wind));
                getWeatherBitmap(weather_picture);//调用获取气候图片Bitmap对象方法
            }
        } else {
            Toast.makeText(Homework03.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
        }
    }

    /*通过网络图片地址下载图片数据并返回Bitmap类型，同时加入图片list中*/
    private void getWeatherBitmap(String weather_picture) throws IOException {
        URL url_picture = new URL(weather_picture);
        HttpURLConnection conn_picture;
        conn_picture = (HttpURLConnection) url_picture.openConnection();
        InputStream is_picture = conn_picture.getInputStream();
        Bitmap bm = BitmapFactory.decodeStream(is_picture);
        icon_list.add(bm);
        is_picture.close();
    }

    /*天气类*/
    class Weather {
        private String day;
        private String weather;
        private String weather_picture;
        private String temperature;
        private String wind;

        public String getTemperature() {
            return temperature;
        }

        public String getDay() {
            return day;
        }

        public String getWeather() {
            return weather;
        }

        public String getWeather_picture() {
            return weather_picture;
        }

        public String getWind() {
            return wind;
        }

        public Weather(String day, String temperature, String weather, String weather_picture, String wind) {
            this.day = day;
            this.temperature = temperature;
            this.weather = weather;
            this.weather_picture = weather_picture;
            this.wind = wind;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return weather_list.size();
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
            convertView = getLayoutInflater().inflate(R.layout.homework03_item, parent, false);
            TextView tv_day = (TextView) convertView.findViewById(R.id.tv_day_homework03_item);
            TextView tv_weather = (TextView) convertView.findViewById(R.id.tv_weather_homework03_item);
            ImageView iv_weather_icon = (ImageView) convertView.findViewById(R.id.iv_homework03_item);
            TextView tv_temperature = (TextView) convertView.findViewById(R.id.tv_temperature_homework03_item);
            TextView tv_wind = (TextView) convertView.findViewById(R.id.tv_wind_homework03_item);
            /*week显示*/
            tv_day.setText(weather_list.get(position).getDay());
            /*气候显示*/
            tv_weather.setText(weather_list.get(position).getWeather());
            /*气候图片显示*/
            iv_weather_icon.setImageBitmap(icon_list.get(position));
            /*温度显示*/
            tv_temperature.setText(weather_list.get(position).getTemperature());
            /*风向显示*/
            tv_wind.setText(weather_list.get(position).getWind());
            return convertView;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        select = position;
        new MyAsyncTask().execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
