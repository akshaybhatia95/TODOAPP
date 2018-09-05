package com.example.d.todo_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="TODO";
    public static final String TABLE_NAME="TODO";
    public static final String COL_1="ID";
    public static final String COL_2="TASK_ITEM";
    public static final int  VERSION=1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,VERSION);
      //  SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
               COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String data){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,data);
       long result= db.insert(TABLE_NAME,null,contentValues);
       if(result==-1){
           return false;
       }
       return true;
    }

}
