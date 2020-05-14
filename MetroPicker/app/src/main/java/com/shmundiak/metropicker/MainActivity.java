package com.shmundiak.metropicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById (R.id.textView);
        button = findViewById (R.id.button);
        button.setOnClickListener(handler);    }

    View.OnClickListener handler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    };
}
