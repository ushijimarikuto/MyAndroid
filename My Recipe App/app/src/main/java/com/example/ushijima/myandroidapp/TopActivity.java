package com.example.ushijima.myandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TopActivity extends AppCompatActivity {

    //新規作成、リストに遷移するボタン
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Button firstButton = findViewById(R.id.first_button);
        firstButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CreateActivity.class);
            startActivity(intent);
        });

        Button secondButton = findViewById(R.id.second_button);
        secondButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CatalogActivity.class);
            startActivity(intent);
        });
    }
}