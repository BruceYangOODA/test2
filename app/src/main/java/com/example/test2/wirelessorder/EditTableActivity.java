package com.example.test2.wirelessorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test2.R;
import com.example.test2.wirelessorder.provider.DBHelper;
import com.example.test2.wirelessorder.provider.Tables;

public class EditTableActivity extends Activity {
    private EditText txtTableNumber,txtTableAmount,txtTableRemark;
    private Button btnTableReturn,btnTableUpload,btnTablePrevious,btnTableNext,
            btnTableEdit,btnTableAdd,btnTableDelete;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursor;
    static final String DATABASE_NAME = "Wireless.db";
    static final String TABLES_TABLE_NAME = "TableTbl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);
        txtTableNumber = findViewById(R.id.txtTableNumber);
        txtTableAmount = findViewById(R.id.txtTableAmount);
        txtTableRemark = findViewById(R.id.txtTableRemark);

        btnTableReturn = findViewById(R.id.btnTableReturn);
        btnTablePrevious = findViewById(R.id.btnTablePrevious);
        btnTableNext = findViewById(R.id.btnTableNext);
        btnTableEdit = findViewById(R.id.btnTableEdit);
        btnTableAdd = findViewById(R.id.btnTableAdd);
        btnTableDelete = findViewById(R.id.btnTableDelete);

        btnTableReturn.setOnClickListener(btnReturn);
        btnTablePrevious.setOnClickListener(btnPrevious);
        btnTableNext.setOnClickListener(btnNext);
        btnTableEdit.setOnClickListener(btnEdit);
        btnTableAdd.setOnClickListener(btnAdd);
        btnTableDelete.setOnClickListener(btnDelete);

//        db=dbHelper.getDatabace(this);
        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟資料庫
//        db.execSQL("DROP TABLE IF EXISTS "+TABLES_TABLE_NAME);
        dbHelper = new DBHelper(this);
        dbHelper.onCreate(db);

//        cursor = db.rawQuery("SELECT * FROM " + TABLES_TABLE_NAME, null);
        cursor = dbHelper.queryTable();

        if(cursor.getCount()==0) {
           db.execSQL("INSERT INTO "+TABLES_TABLE_NAME+" ("+Tables._ID+","+Tables.NUM+","+Tables.DESCRIPTION
                   +") VALUES ('1','0','Big')");
            db.execSQL("INSERT INTO "+TABLES_TABLE_NAME+" ("+Tables._ID+","+Tables.NUM+","+Tables.DESCRIPTION
                    +") VALUES ('2','8','Big')");
            db.execSQL("INSERT INTO "+TABLES_TABLE_NAME+" ("+Tables._ID+","+Tables.NUM+","+Tables.DESCRIPTION
                    +") VALUES ('3','0','Big')");
            db.execSQL("INSERT INTO "+TABLES_TABLE_NAME+" ("+Tables._ID+","+Tables.NUM+","+Tables.DESCRIPTION
                    +") VALUES ('4','2','small')");
            db.execSQL("INSERT INTO "+TABLES_TABLE_NAME+" ("+Tables._ID+","+Tables.NUM+","+Tables.DESCRIPTION
                    +") VALUES ('5','3','small')");
            db.execSQL("INSERT INTO "+TABLES_TABLE_NAME+" ("+Tables._ID+","+Tables.NUM+","+Tables.DESCRIPTION
                    +") VALUES ('6','0','medium')");
        }

        Intent intent = getIntent();
        int id =intent.getIntExtra("_id",0);
        if(cursor.getCount()>0 ){
            if(id!=0){
                cursor.move(id);
            }
            else{cursor.moveToFirst();}
            txtTableNumber.setText(cursor.getString(0));
            txtTableAmount.setText(cursor.getString(1));
            txtTableRemark.setText(cursor.getString(2));
        }
    }

    private View.OnClickListener btnReturn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            db.close();
            finish();
        }
    };
    private View.OnClickListener btnPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!cursor.isFirst()) {
                cursor.moveToPrevious();
                txtTableNumber.setText(cursor.getString(0));
                txtTableAmount.setText(cursor.getString(1));
                txtTableRemark.setText(cursor.getString(2));
            }
        }
    };
    private View.OnClickListener btnNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //  if(cursor.getInt(0)!=cursor.getCount()) {
            if (!cursor.isLast()) {
                cursor.moveToNext();
                txtTableNumber.setText(cursor.getString(0));
                txtTableAmount.setText(cursor.getString(1));
                txtTableRemark.setText(cursor.getString(2));
            }
        }

    };
    private View.OnClickListener btnEdit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db.execSQL("UPDATE " + TABLES_TABLE_NAME + " SET "
                    + Tables.NUM + "='" + txtTableAmount.getText().toString() + "', "
                    + Tables.DESCRIPTION+ "='" + txtTableRemark.getText().toString()
                    + "' WHERE " + Tables._ID + "='" + cursor.getString(0) + "'"
            );
            int id = cursor.getInt(0);
            cursor = dbHelper.queryTable();
            cursor.move(id);

        }
    };
    private View.OnClickListener btnAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db.execSQL("INSERT INTO " + TABLES_TABLE_NAME + " (" + Tables._ID + "," + Tables.NUM + "," + Tables.DESCRIPTION
                    + ") VALUES ('" + txtTableNumber.getText().toString() + "','"
                    + txtTableAmount.getText().toString() + "','" + txtTableRemark.getText().toString() +"')");

            cursor = dbHelper.queryTable();
            cursor.moveToLast();
            txtTableNumber.setText(cursor.getString(0));
            txtTableAmount.setText(cursor.getString(1));
            txtTableRemark.setText(cursor.getString(2));

        }
    };
    private View.OnClickListener btnDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db.execSQL("DELETE FROM " + TABLES_TABLE_NAME + " WHERE "
                    + Tables._ID + "='" + cursor.getString(0) + "'");
            cursor = dbHelper.queryTable();

            cursor.moveToFirst();
            txtTableNumber.setText(cursor.getString(0));
            txtTableAmount.setText(cursor.getString(1));
            txtTableRemark.setText(cursor.getString(2));
        }
    };

}
