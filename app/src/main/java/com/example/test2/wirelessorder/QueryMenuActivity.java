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
import com.example.test2.wirelessorder.provider.Menus;

public class QueryMenuActivity extends ListActivity {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursor;
    static final String DATABASE_NAME = "Wireless.db";
    static final String TABLES_TABLE_NAME2 = "menuTbl";
    private SubMenu subMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("返回");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.drawable.return1);

        dbHelper = new DBHelper(this);
        db=dbHelper.getDatabace(this);
        cursor=dbHelper.queryMenu();
//        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟資料庫
//        cursor = db.rawQuery("SELECT * FROM " + TABLES_TABLE_NAME2, null);

        String[] from = {Menus._ID,Menus.TYPE_ID, Menus.NAME, Menus.PRICE, Menus.REMARK};
        //menu_detail.xml
        int[] to = { R.id.text0, R.id.text1, R.id.text2, R.id.text3,R.id.text4  };
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.menu_detail,cursor,from,to);
        final ListView listView = getListView();
        listView.setDividerHeight(20);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder =new AlertDialog.Builder(QueryMenuActivity.this);
                builder.setMessage("  編輯菜單 "+cursor.getString(2)+" ?")
                        .setCancelable(false)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id=cursor.getInt(0);
                                Intent intent = new Intent(QueryMenuActivity.this,EditMenuActivity.class);
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
