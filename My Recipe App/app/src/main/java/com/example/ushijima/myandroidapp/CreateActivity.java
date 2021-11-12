package com.example.ushijima.myandroidapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    //遷移ボタン
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(v -> finish());
    }

    //Listに遷移する

    /**
     * title 料理のタイトル
     * content 内容　料理の工程
     * ingredients　材料
     * @param v
     */

    public void register(View v) {
        Intent intent = new Intent(this, CatalogActivity.class);

        EditText editTitle = findViewById(R.id.edit_title);
        EditText editContent = findViewById(R.id.edit_content);
        EditText editIngredients = findViewById(R.id.edit_ingredients);

        SQLiteDatabase database;

        try {
            RecipeOpenHelper helper = new RecipeOpenHelper(this);
            database = helper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("title", editTitle.getText().toString());
            contentValues.put("content", editContent.getText().toString());
            contentValues.put("ingredients", editIngredients.getText().toString());

            //DBにinsertする
            database.insert("RecipeList", null, contentValues);
        } catch (Exception e) {
            Toast.makeText(this, R.string.toast_failed, Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, R.string.toast_register, Toast.LENGTH_LONG).show();

        startActivity(intent);
    }
}