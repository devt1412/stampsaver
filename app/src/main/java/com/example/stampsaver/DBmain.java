package com.example.stampsaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBmain extends SQLiteOpenHelper {
    public static final String DBNAME="Stampsaver.db";

    public static final int VER=1;
    public DBmain( Context context ) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create Table Stamps(title TEXT primary key, link TEXT, stamp TEXT, thumb TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists Stamps");
    }
    public Boolean insertuserdata(String title ,String link, String stamp, String thumb){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("link",link);
        contentValues.put("stamp",stamp);
        contentValues.put("thumb",thumb);
        long result=db.insert("Stamps",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getdata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from Stamps",null);
        return cursor;
    }
    public void delete(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("Stamps", "title=?",new String[]{name});
    }
    public void update(String title,String name, String time,String link){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",name);
        contentValues.put("stamp",time);
        contentValues.put("link",link);
        db.update("Stamps", contentValues, "title=?", new String[]{title});
    }
}
