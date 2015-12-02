package com.lei.lesson14_homework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Homework01 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework01);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.homework01, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_homework01_group01_item01:
                item.setChecked(true);
                Toast.makeText(this,"主题一", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_homework01_group01_item02:
                item.setChecked(true);
                Toast.makeText(this,"主题二", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_homework01_group01_item03:
                item.setChecked(true);
                Toast.makeText(this,"主题三", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_homework01_group02_item01:
                Toast.makeText(this,"Setting1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_homework01_group02_item02:
                Toast.makeText(this,"Setting2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_homework01_group02_item03:
                Toast.makeText(this,"Setting3", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
