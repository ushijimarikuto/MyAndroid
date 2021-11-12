package com.example.ushijima.myandroidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Recipe.db";
    public static final int VERSION = 1;
    public static final String CREATE_TABLE = "CREATE TABLE 'RecipeList' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT , 'title' TEXT , 'content' TEXT , 'ingredients' TEXT);";

    public RecipeOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * dbがない時、新規に作成
     * @param db　Recipe.db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * dbがある時、継続
     * @param db Recipe.db
     * @param oldVersion 古いバージョンdb
     * @param newVersion　新しいバージョンdb
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
