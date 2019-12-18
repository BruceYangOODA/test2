package com.example.test2.wirelessorder;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.wirelessorder.provider.CheckTable;
import com.example.test2.wirelessorder.provider.DBHelper;

import java.util.ArrayList;
import java.util.List;



public class CheckTableActivity extends Activity {
    private GridView gv;
    private int count;
    private List list = new ArrayList();
    static final String DATABASE_NAME = "Wireless.db";
    static final String TABLES_TABLE_NAME = "TableTbl";
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_table);

        setTitle("返回");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.drawable.return1);

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟
        dbHelper = new DBHelper(this);
        cursor = dbHelper.queryTable();

        // check_table.xml
        gv = (GridView) findViewById(R.id.check_table_gridview);
        getTableList();
        // ��GridViewô�����
        gv.setAdapter(new ImageAdapter(this));
    }

    private void getTableList(){
/*
        String url = HttpUtil.BASE_URL+"servlet/CheckTableServlet";
        String result = HttpUtil.queryStringForPost(url);
        String[] strs = result.split(";");
        for (int i = 0; i < strs.length; i++) {
            int idx = strs[i].indexOf(",");
            int num = Integer.parseInt(strs[i].substring(0, idx));
            int flag = Integer.parseInt(strs[i].substring(idx+1));
            CheckTable ct = new CheckTable();
            ct.setFlag(flag);
            ct.setNum(num);
            list.add(ct);
        }
*/
            cursor.moveToFirst();
            for (int i=0;i<cursor.getCount();i++){
            int id=cursor.getInt(0);
            int amount=cursor.getInt(1);
            CheckTable ct = new CheckTable();
            if(amount>0){ ct.setFlag(1); }
            else {ct.setFlag(0);}
            ct.setAmoumt(amount);
            ct.setNum(id);
            list.add(ct);
            cursor.moveToNext();
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        // �غc�l
        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =
                    LayoutInflater.from(CheckTableActivity.this);
            View v = null;
            ImageView imageView =null;
            TextView tv =null;
            TextView av =null;
            if (convertView == null) {
                v = inflater.inflate(R.layout.check_table_view,null);
                v.setPadding(8, 8, 8, 8);
            } else {
                v = (View) convertView;
            }
            imageView = (ImageView) v.findViewById(R.id.check_table_ImageView01);
            tv = (TextView) v.findViewById(R.id.check_tableTextView01);
            av = (TextView) v.findViewById(R.id.check_tableTextView02);
            CheckTable ct = (CheckTable) list.get(position);
            if(ct.getFlag()==1){
                imageView.setImageResource(R.drawable.youren);
            }else{
                imageView.setImageResource(R.drawable.kongwei);
            }
            tv.setText(ct.getNum()+"");
            av.setText(ct.getAmoumt()+"");
            return v;
        }
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
