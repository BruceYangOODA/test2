package com.example.test2.wirelessorder.provider;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Wireless.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLES_TABLE_NAME = "TableTbl";
    public static final String TABLES_TABLE_NAME2 = "MenuTbl";
    public static final String ACCOUNT_TABLE = "AccountTbl";
    public static final String ORDER_TABLE = "OrderTbl";

    private static SQLiteDatabase db;
    private DBHelper dbHelper;
    private static Cursor cursor;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onCreate(SQLiteDatabase db) {
        //this.db = db;

        //        db.execSQL("DROP TABLE IF EXISTS " + TABLES_TABLE_NAME);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_TABLE_NAME + " ("
                + Tables._ID + " INTEGER PRIMARY KEY,"
                + Tables.NUM + " INTEGER,"
                + Tables.DESCRIPTION + " TEXT"
                + ");");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLES_TABLE_NAME2);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_TABLE_NAME2 + " ("
                + Menus._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Menus.TYPE_ID + " INTEGER,"
                + Menus.NAME + " TEXT,"
                + Menus.PRICE + " INTEGER,"
                + Menus.REMARK + " TEXT"
                + ");");
//        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL(" CREATE TABLE IF NOT EXISTS "+ACCOUNT_TABLE+" (_id integer primary key autoincrement,account text,password text,name text) ");

//        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        db.execSQL(" CREATE TABLE IF NOT EXISTS "+ORDER_TABLE+" (_id integer primary key autoincrement, account text, date text, ordernumber text, "
                +Menus.TYPE_ID + " text, "
                +Menus.NAME + " text, "
                +"num integer, "
                +Menus.PRICE + " integer, "
                +Menus.REMARK + " text, "
                +"tableNO text);");

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLES_TABLE_NAME2);
        onCreate(db);
    }

    public static SQLiteDatabase getDatabace(Context context){
        if(db==null || !db.isOpen())
        {//開啟資料庫
            db = new DBHelper(context).getWritableDatabase();
        }
        return db;
    }

    public Cursor queryMenu(){
        db = getWritableDatabase();
        //cursor = db.rawQuery("SELECT * FROM " + TABLES_TABLE_NAME2, null);
        cursor=db.query(TABLES_TABLE_NAME2, null, null, null, null, null, null);
        return cursor;
    }
    public Cursor queryTable(){
        db = getWritableDatabase();
        //cursor = db.rawQuery("SELECT * FROM " + TABLES_TABLE_NAME2, null);
        cursor=db.query(TABLES_TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
    public Cursor queryOrder(){
        db = getWritableDatabase();
        //cursor = db.rawQuery("SELECT * FROM " + ORDER_TABLE, null);
        cursor=db.query(ORDER_TABLE, null, null, null, null, null, null);
        return cursor;
    }
    public void insertDefault(){
        SQLiteDatabase db = getWritableDatabase();
        //       ContentValues values = new ContentValues();
//        values.put("account","guest");
//        values.put("password","123456");
//        db.insert("TempTbl",null,values);
        String sql = "insert into "+ACCOUNT_TABLE+" (account,password)values('guest','123456') ";
        db.execSQL(sql);

    }

    public boolean checkPassword(String account,String password){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c  = db.query(ACCOUNT_TABLE, null, null, null, null, null, null);

        if(c.getCount()>0){
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                int id = c.getInt(0);
                String args1 = c.getString(1);
                String args2 = c.getString(2);
                if(account.equals(args1) && password.equals(args2)){
                    return true;
                }
                if(!c.moveToLast()){
                    c.moveToNext();
                }
            }
        }
        return false;
    }

    public void order(String account,String date,String orderNumber,String typeID,String name,String num,String price,String remark){
        db.execSQL("INSERT INTO "+ORDER_TABLE+" (account,date,ordernumber,"
        +Menus.TYPE_ID + "," + Menus.NAME + ",num," + Menus.PRICE+","+Menus.REMARK+") VALUES ('"
                +account +"','"+date+"','"+orderNumber+"','"+typeID+"','"+name+"','"+num+"','"+price+"','"+remark+"')");
    }

    public void internalUse(String tableNO,String tablePs){
        Cursor cOrder =queryOrder();
        int orderNumber;
        cOrder.moveToLast();
        orderNumber = cOrder.getInt(2);
        db.execSQL("UPDATE "+ORDER_TABLE+" SET "+Tables.NUM+"='"+tableNO+"' WHERE ordernumber='"+orderNumber+"'");
        Cursor c =queryTable();
        c.move(Integer.parseInt(tableNO));
        if(tablePs.equals("")){
            tablePs="0";
        }
        int tablePerson= c.getInt(1)+Integer.parseInt(tablePs);
        db.execSQL("UPDATE "+TABLES_TABLE_NAME+" SET "+Tables.NUM+"='"+tablePerson+"' WHERE "+Tables._ID+"='"+tableNO+"'");
    }

    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLES_TABLE_NAME2, null, null, null, null, null, null);
        return c;
    }

}
