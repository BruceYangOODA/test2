package com.example.test2.wirelessorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.test2.R;
import com.example.test2.wirelessorder.provider.DBHelper;
import com.example.test2.wirelessorder.provider.Menus;

public class EditMenuActivity extends Activity {
    private EditText txtMenuNumber, txtMenuName, txtMenuPrice, txtMenuRemark;
    private Button btnMenuReturn, btnMenuUpload, btnMenuPrevious, btnMenuNext,
            btnMenuEdit, btnMenuAdd, btnMenuDelete;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursor;
    static final String DATABASE_NAME = "Wireless.db";
    static final String TABLES_TABLE_NAME2 = "menuTbl";
    SubMenu subMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        txtMenuNumber = findViewById(R.id.txtMenuNumber);
        txtMenuName = findViewById(R.id.txtMenuName);
        txtMenuPrice = findViewById(R.id.txtMenuPrice);
        txtMenuRemark = findViewById(R.id.txtMenuRemark);
        btnMenuReturn = findViewById(R.id.btnMenuReturn);
        btnMenuUpload = findViewById(R.id.btnMenuUpload);
        btnMenuPrevious = findViewById(R.id.btnMenuPrevious);
        btnMenuNext = findViewById(R.id.btnMenuNext);
        btnMenuEdit = findViewById(R.id.btnMenuEdit);
        btnMenuAdd = findViewById(R.id.btnMenuAdd);
        btnMenuDelete = findViewById(R.id.btnMenuDelete);

        btnMenuReturn.setOnClickListener(btnReturn);
        btnMenuPrevious.setOnClickListener(btnPrevious);
        btnMenuNext.setOnClickListener(btnNext);
        btnMenuEdit.setOnClickListener(btnEdit);
        btnMenuAdd.setOnClickListener(btnAdd);
        btnMenuDelete.setOnClickListener(btnDelete);


//        db = dbHelper.getDatabace(this);//開啟資料庫
        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟資料庫
//        db.execSQL("DROP TABLE IF EXISTS "+TABLES_TABLE_NAME2);
        dbHelper = new DBHelper(this);
//        dbHelper.onCreate(db);

//        cursor = db.rawQuery("SELECT * FROM " + TABLES_TABLE_NAME2, null);
        cursor = dbHelper.queryMenu();

        if (cursor.getCount()==0) {
            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK +") VALUES ('1','白飯','35','hot')");
            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK +") VALUES ('2','雞排','60','hot')");
            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK +") VALUES ('3','薯條','35','hot')");
            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK +") VALUES ('4','可樂','25','ice')");
            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK +") VALUES ('5','紅茶','25','ice')");
            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK +") VALUES ('6','熱狗','35','hot')");
        }

        Intent intent = getIntent();
        int id = intent.getIntExtra("_id", 0);
        if (cursor.getCount() > 0) {
            if (id != 0) {
                cursor.move(id);
            } else {
                cursor.moveToFirst();
            }
            txtMenuNumber.setText(cursor.getString(1));
            txtMenuName.setText(cursor.getString(2));
            txtMenuPrice.setText(cursor.getString(3));
            txtMenuRemark.setText(cursor.getString(4));
        }
    }

    private OnClickListener btnReturn = new OnClickListener() {
        @Override
        public void onClick(View v) {
            db.close();
            finish();
        }
    };
    private OnClickListener btnPrevious = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!cursor.isFirst()) {
                cursor.moveToPrevious();
                txtMenuNumber.setText(cursor.getString(1));
                txtMenuName.setText(cursor.getString(2));
                txtMenuPrice.setText(cursor.getString(3));
                txtMenuRemark.setText(cursor.getString(4));
            }
        }
    };
    private OnClickListener btnNext = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //  if(cursor.getInt(0)!=cursor.getCount()) {
            if (!cursor.isLast()) {
                cursor.moveToNext();
                txtMenuNumber.setText(cursor.getString(1));
                txtMenuName.setText(cursor.getString(2));
                txtMenuPrice.setText(cursor.getString(3));
                txtMenuRemark.setText(cursor.getString(4));
            }
        }

    };
    private OnClickListener btnEdit = new OnClickListener() {
        @Override
        public void onClick(View v) {

            db.execSQL("UPDATE " + TABLES_TABLE_NAME2 + " SET "
                    + Menus.TYPE_ID + "='" + txtMenuNumber.getText().toString() + "', "
                    + Menus.NAME + "='" + txtMenuName.getText().toString() + "', "
                    + Menus.PRICE + "='" + txtMenuPrice.getText().toString() + "', "
                    + Menus.REMARK + "='" + txtMenuRemark.getText().toString()
                    + "' WHERE " + Menus._ID + "='" + cursor.getString(0) + "'"
            );
            int id = cursor.getInt(0);
            cursor = dbHelper.queryMenu();
            cursor.move(id);

        }
    };
    private OnClickListener btnAdd = new OnClickListener() {
        @Override
        public void onClick(View v) {

            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME2 + " (" + Menus.TYPE_ID + "," + Menus.NAME + "," + Menus.PRICE
                    + "," + Menus.REMARK + ") VALUES ('" + txtMenuNumber.getText().toString() + "','"
                    + txtMenuName.getText().toString() + "','" + txtMenuPrice.getText().toString() + "','"
                    + txtMenuRemark.getText().toString() + "')");

            cursor = dbHelper.queryMenu();
            cursor.moveToLast();
            txtMenuNumber.setText(cursor.getString(1));
            txtMenuName.setText(cursor.getString(2));
            txtMenuPrice.setText(cursor.getString(3));
            txtMenuRemark.setText(cursor.getString(4));

        }
    };
    private OnClickListener btnDelete = new OnClickListener() {
        @Override
        public void onClick(View v) {

            db.execSQL("DELETE FROM " + TABLES_TABLE_NAME2 + " WHERE "
                    + Menus._ID + "='" + cursor.getString(0) + "'");
            cursor = dbHelper.queryMenu();

            cursor.moveToFirst();
            txtMenuNumber.setText(cursor.getString(1));
            txtMenuName.setText(cursor.getString(2));
            txtMenuPrice.setText(cursor.getString(3));
            txtMenuRemark.setText(cursor.getString(4));
        }
    };


}
