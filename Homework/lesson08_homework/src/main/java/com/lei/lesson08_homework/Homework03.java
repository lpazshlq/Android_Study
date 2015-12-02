package com.lei.lesson08_homework;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lei.lesson08_model.MyMessage;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Homework03 extends Activity implements View.OnClickListener {
    Button btn_newXML, btn_readXML;
    ListView lv;
    MyAdapter adapter;
    ArrayList<MyMessage> list;
    MyMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework03);
        init();
        listener();
    }

    private void init() {
        btn_newXML = (Button) findViewById(R.id.btn_newXML_homework03);
        btn_readXML = (Button) findViewById(R.id.btn_readXML_homework03);
        lv = (ListView) findViewById(R.id.lv_homework03);
        adapter = new MyAdapter();
    }

    private void listener() {
        btn_newXML.setOnClickListener(this);
        btn_readXML.setOnClickListener(this);
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
            convertView = getLayoutInflater().inflate(R.layout.homework03_item, parent, false);
            TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date_homework03_item);
            TextView tv_body = (TextView) convertView.findViewById(R.id.tv_body_homework03_item);
            TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type_homework03_item);
            TextView tv_address = (TextView) convertView.findViewById(R.id.tv_address_homework03_item);
            MyMessage m = list.get(position);
            tv_date.setText(m.getDate());
            tv_body.setText(m.getBody());
            tv_type.setText(m.getType());
            tv_address.setText(m.getAddress());
            return convertView;
        }
    }

    private void newXML() {
        try {
            String path = Environment.getExternalStorageDirectory().getPath();
            OutputStream os = new FileOutputStream(path + "/backupSms.xml");
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "messages");
            for (int i = 0; i < 10; i++) {
                serializer.startTag(null, "message");
                serializer.startTag(null, "date");
                serializer.text("142517396841" + i);
                serializer.endTag(null, "date");
                serializer.startTag(null, "body");
                serializer.text("hello" + i);
                serializer.endTag(null, "body");
                serializer.startTag(null, "type");
                serializer.text(1 + "");
                serializer.endTag(null, "type");
                serializer.startTag(null, "address");
                serializer.text("1376193100" + (i + 1));
                serializer.endTag(null, "address");
                serializer.endTag(null, "message");
            }
            serializer.endTag(null, "messages");
            serializer.endDocument();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pullXML_SetAdapter() {
        boolean b = fileIsExists();
        if (b) {
            try {
                XmlPullParser parser = Xml.newPullParser();
                String path = Environment.getExternalStorageDirectory().getPath();
                parser.setInput(new FileInputStream(path + "/backupSms.xml"), "utf-8");
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<>();
                            break;
                        case XmlPullParser.START_TAG:
                            switch (parser.getName()) {
                                case "message":
                                    message = new MyMessage();
                                    break;
                                case "date":
                                    parser.next();
                                    message.setDate(parser.getText());
                                    break;
                                case "body":
                                    parser.next();
                                    message.setBody(parser.getText());
                                    break;
                                case "type":
                                    parser.next();
                                    message.setType(parser.getText());
                                    break;
                                case "address":
                                    parser.next();
                                    message.setAddress(parser.getText());
                                    break;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("message")) {
                                list.add(message);
                            }
                            break;
                    }
                    eventType = parser.next();
                }
                lv.setAdapter(adapter);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Homework03.this, "XML文件不存在", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_newXML_homework03:
                newXML();
                Toast.makeText(Homework03.this, "create success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_readXML_homework03:
                pullXML_SetAdapter();
                break;
        }
    }

    private boolean fileIsExists() {
        try {
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path + "/backupSms.xml");
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
