package com.shmundiak.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final String DATABASE_TABLE = "mainTable";
    private static final String DATABASE_CREATE = "create table if not exists " + DATABASE_TABLE + " ( _id integer primary key autoincrement," + "column_one text not null);";
    private static final String DATABASE_DATA = "insert into " + DATABASE_TABLE + " (column_one) values ('test'), ('test2'), ('test3')";

    SQLiteDatabase myDatabase;
    ListView theListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = createDatabase();

        theListView = findViewById(R.id.listView);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item, data);
        theListView.setAdapter(aa);

        theListView.setOnItemClickListener(listClick);
    }

    private String[] createDatabase() {
        myDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        myDatabase.execSQL(DATABASE_CREATE);


        Cursor myResult = myDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        myResult.moveToFirst();

        String temp;
        ArrayList<String> result = new ArrayList<String>();
        while (!myResult.isAfterLast()) {
            temp = myResult.getString(myResult.getColumnIndex("column_one"));
            result.add(temp);
            myResult.moveToNext();
        }

        myResult.close();
        return result.toArray(new String[result.size()]);
    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener () {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            String itemValue = (String) theListView.getItemAtPosition( position );
            Toast toast = Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_SHORT);
            toast.show();
        }
    };
}
