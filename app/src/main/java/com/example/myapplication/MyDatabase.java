package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="student.name";
    private static final String TABLE_NAME="Student_details";
    private static final String ID="_id";
    private static final String NAME="name";
    private static final String AGE="age";
    private static final String GENDER="gender";
    private static final String TABLE_STRUCTURE="CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(250), " + AGE + " INTEGER,"+GENDER+ " VARCHAR(15))";
    private static final int VERSION_NUMBER=1;
    private static final String Drop_Table="DROP TABLE IF EXISTS "+TABLE_NAME;
    private  static final String SELECT_ALL="SELECT * FROM "+TABLE_NAME;
    private  Context context;
    public MyDatabase(@Nullable Context context) {
        super(context,DATABASE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context,"OnCreate is create ",Toast.LENGTH_LONG).show();
            db.execSQL(TABLE_STRUCTURE);
        }catch (Exception e){
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context,"Upgrade is create ",Toast.LENGTH_LONG).show();
            db.execSQL(Drop_Table);
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context,"Upgrade is not create create ",Toast.LENGTH_LONG).show();
        }
    }
    public long  InsertData(String name,String age,String gender){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
       long rowId= sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
       return rowId;
    }
    public Cursor displayAllData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return cursor;
    }
    public boolean updateData(String id,String name,String age, String gender){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ?", new String[]{id});
    return true;
    }
   public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
//        return sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{});
       return sqLiteDatabase.delete(TABLE_NAME, ID + " = ?", new String[]{id});
    }
}
