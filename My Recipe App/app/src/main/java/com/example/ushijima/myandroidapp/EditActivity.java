package com.example.ushijima.myandroidapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     *  title 料理のタイトル
     *  content 内容　料理の工程
     *  ingredients　材料
     */


    //dbから表示
    @Override
    protected void onResume() {
        super.onResume();
        Long id = getIntent().getLongExtra("id", 0L);

        SQLiteOpenHelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            helper = new RecipeOpenHelper(EditActivity.this);
            database = helper.getReadableDatabase();

            cursor = database.query(
                    "RecipeList",
                    null,
                    "_id=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null);

            //編集画面
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("title");
                String strTitle = cursor.getString(columnIndex);
                columnIndex = cursor.getColumnIndex("content");
                String strContent = cursor.getString(columnIndex);
                columnIndex = cursor.getColumnIndex("ingredients");
                String strIngredients = cursor.getString(columnIndex);

                EditText titleView = findViewById(R.id.text_title);
                EditText contentView = findViewById(R.id.text_content);
                EditText ingredientsView = findViewById(R.id.text_ingredients);

                titleView.setText(strTitle);
                contentView.setText(strContent);
                ingredientsView.setText(strIngredients);
            }
        } catch (Exception e) {
            Log.e("エラー", e.getMessage(), e);
        } finally {
            cursor.close();
            database.close();
        }
    }

    /**
     * dbのdateをupdate　更新
     * @param view
     */

    public void update(View view) {
        SQLiteOpenHelper helperUpdate = null;
        SQLiteDatabase databaseUpdate = null;

        String title = ((EditText) findViewById(R.id.text_title)).getText().toString();
        String content = ((EditText) findViewById(R.id.text_content)).getText().toString();
        String ingredients = ((EditText) findViewById(R.id.text_ingredients)).getText().toString();

        try {
            helperUpdate = new RecipeOpenHelper(EditActivity.this);
            databaseUpdate = helperUpdate.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("content", content);
            contentValues.put("ingredients", ingredients);

            int updateCount = databaseUpdate.update(
                    "RecipeList",
                    contentValues,
                    "_id=?",
                    new String[]{String.valueOf(getIntent().getLongExtra("id", 0L))});

            if (updateCount == 1) {
                Toast.makeText(EditActivity.this, R.string.toast_update, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(EditActivity.this, R.string.toast_failed, Toast.LENGTH_LONG).show();
            }
            finish();
        } catch (Exception e) {
            Log.e("エラー", e.getMessage(), e);
        } finally {
            databaseUpdate.close();
        }
    }

    /**
     * dbのdateをdelete 削除
     * @param view
     */
    public void delete(View view) {
        new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("削除しますか？")
                .setPositiveButton("はい", ((dialog, which) -> {

                    SQLiteOpenHelper helperDelete = null;
                    SQLiteDatabase databaseDelete = null;

                    try {
                        helperDelete = new RecipeOpenHelper(EditActivity.this);
                        databaseDelete = helperDelete.getWritableDatabase();

                        int deleteCount = databaseDelete.delete(
                                "RecipeList",
                                "_id=?",
                                new String[]{String.valueOf(getIntent().getLongExtra("id", 0L))});

                        if (deleteCount == 1) {
                            Toast.makeText(EditActivity.this, R.string.toast_delete, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditActivity.this, R.string.toast_failed, Toast.LENGTH_LONG).show();
                        }
                        finish();
                    } catch (Exception e) {
                        Log.e("エラー", e.getMessage(), e);
                    } finally {
                        databaseDelete.close();
                    }
                }))

                .setNegativeButton("戻る", null)
                .show();
    }

}