package com.figure.android.exercise;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edit;
    TextView tv;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        edit = (EditText) findViewById(R.id.edit_e);
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void onClick(View view) {
        if (!TextUtils.isEmpty(edit.getText())){
            editor.putString("1","");
            editor.commit();
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCheck(View view) {
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        String aaa = preferences.getString("1","nothing");
        tv.setText(aaa);
        Toast.makeText(MainActivity.this, aaa, Toast.LENGTH_SHORT).show();
    }
}
