package com.example.ushijima.myandroidapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class CatalogActivity extends android.app.ListActivity {
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), TopActivity.class);
            startActivity(intent);
            finish();
        });

    }

    /**
     * dbのデータを再表示
     * onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase database;
        try{
            SQLiteOpenHelper helper = new RecipeOpenHelper(CatalogActivity.this);
            database = helper.getReadableDatabase();

            cursor = database.query("RecipeList",null,null,null,null,null,null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(CatalogActivity.this,
                    R.layout.list_row,
                    cursor,
                    new String[]{"title"},
                    new int[]{R.id.list_row},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        }catch (Exception e){
            Log.e("エラー",e.getMessage(),e);
        }
    }

    /**
     *
     * @param l タップされたListView
     * @param v　
     * @param position タップされた行番号
     * @param id 料理名
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(CatalogActivity.this,EditActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cursor != null || !cursor.isClosed()){
            cursor.close();
        }
    }
}