package com.shmundiak.contactssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();
        Cursor contact = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<String> result = new ArrayList<String>();

        if (contact.getCount() > 0) {
            while (contact.moveToNext()) {
                String name = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                result.add(name);
            }
            contact.close();
        }

        String[] data = result.toArray(new String[0]);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item, data);

        theListView = findViewById(R.id.listView);
        theListView.setAdapter(aa);
    }
}
