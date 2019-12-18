package com.example.test2.wirelessorder;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.test2.R;
import com.example.test2.wirelessorder.provider.DBHelper;
import com.example.test2.wirelessorder.provider.Tables;

public class QueryTableActivity extends ListActivity {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursor;
    static final String DATABASE_NAME = "Wireless.db";
    static final String TABLES_TABLE_NAME = "TableTbl";
    private SubMenu subMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("返回");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.drawable.return1);
        dbHelper = new DBHelper(this);
        db=dbHelper.getDatabace(this);
        cursor=dbHelper.queryTable();
//        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟資料庫
//        cursor = db.rawQuery("SELECT * FROM " + TABLES_TABLE_NAME, null);

        String[] from = {Tables._ID,Tables.NUM, Tables.DESCRIPTION};
        int[] to = { R.id.text5, R.id.text6, R.id.text7 };
        final SimpleCursorAdapter tableAdapter = new SimpleCursorAdapter(this,R.layout.table_detail,cursor,from,to);
        final ListView listViewTable = getListView();
        listViewTable.setPadding(10,30,10,30);
        listViewTable.setDividerHeight(20);
        listViewTable.setAdapter(tableAdapter);

        listViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder =new AlertDialog.Builder(QueryTableActivity.this);
                builder.setMessage("  編輯桌椅資料 "+cursor.getString(0)+" ?")
                        .setCancelable(false)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id=cursor.getInt(0);
                                Intent intent = new Intent(QueryTableActivity.this,EditTableActivity.class);
                                intent.putExtra("_id",id);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*
        subMenu = menu.addSubMenu(0,0,0,"返回");
        MenuItem MenuItem1=subMenu.getItem();
        MenuItem1.setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM |
                        MenuItem.SHOW_AS_ACTION_WITH_TEXT);
*/
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        db.close();
        finish();
        return false;
    }

}
