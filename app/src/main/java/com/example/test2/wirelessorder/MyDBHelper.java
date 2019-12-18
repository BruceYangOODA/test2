package com.example.test2.wirelessorder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SQL = " create table TempTbl(_id integer primary key autoincrement,name text,password text) ";
    private SQLiteDatabase db;
    public MyDBHelper(Context c){
        super(c,"test.db", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDefault(){
        SQLiteDatabase db = getWritableDatabase();
 //       ContentValues values = new ContentValues();
//        values.put("account","guest");
//        values.put("password","123456");
//        db.insert("TempTbl",null,values);
        String sql = "insert into TempTbl(name,password)values('guest','123456') ";
        db.execSQL(sql);

    }

    public boolean checkPassword(String account,String password){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c  = db.query("TempTbl", null, null, null, null, null, null);

        if(c.getCount()>0){
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                int id = c.getInt(0);
                String args1 = c.getString(1);
                String args2 = c.getString(2);
                if(account.equals(args1) && password.equals(args2)){
                    return true;
                }
                c.moveToNext();
            }
        }
        return false;
    }
}
