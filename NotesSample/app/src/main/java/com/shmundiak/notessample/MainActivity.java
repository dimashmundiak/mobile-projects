package com.shmundiak.notessample;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int CM_DELETE_ID = 1;
    SQLAdapter db;
    Cursor cursor;
    ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theListView = findViewById(R.id.myListView);

        db = new SQLAdapter(this);
        db.open();

        updateData();

        registerForContextMenu(theListView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            db.delRec(info.id);
            updateData();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Видалити");
    }

    public void onButtonClick(View view) {
        EditText editText = findViewById(R.id.edit_text);
        db.addRec(editText.getText().toString());
        editText.clearFocus();
        editText.setText("");
        updateData();
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    protected void updateData() {
        cursor = db.getAllData();
        cursor.moveToFirst();

        String temp;
        ArrayList<String> result = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            temp = cursor.getString(cursor.getColumnIndex(SQLAdapter.COLUMN_TXT));
            result.add(temp);
            cursor.moveToNext();
        }

        String[] data = result.toArray(new String[result.size()]);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item, data);

        theListView.setAdapter(aa);
    }
}
