package com.luo.ming.delicipe.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;
import com.luo.ming.delicipe.Helpers.Constants;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("dbhandler","onCreate has been called");

        // create shopping list table from ingredient list
        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE " + Constants.TABLE_SHOPPINGLIST_NAME + "("
                + Constants.KEY_STRING_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_COUNT + " DOUBLE,"
                + Constants.KEY_UNIT + " TEXT,"+ Constants.KEY_STRING_ITEM_ID + " TEXT"+ " );";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPINGLIST_NAME);
        onCreate(db);

    }
}
