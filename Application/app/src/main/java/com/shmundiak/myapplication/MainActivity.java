package com.shmundiak.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ship = (ImageView) findViewById(R.id.imageView2);
        Animation shipAnim = AnimationUtils.loadAnimation(this, R.anim.ship_anim); ship.startAnimation(shipAnim);
    }
}
