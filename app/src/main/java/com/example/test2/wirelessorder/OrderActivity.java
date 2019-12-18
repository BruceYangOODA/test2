package com.example.test2.wirelessorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.wirelessorder.provider.DBHelper;
import com.example.test2.wirelessorder.provider.Menus;
import com.example.test2.wirelessorder.provider.Tables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private Spinner tableNoSpinner;
    private Button orderBtn, addBtn, startBtn;
    private EditText personNumEt;
    private ListView listView;
    private int[] to = new int[6];      //listview.xml 上面物件的ID
    private String[] from = { "id", "name", "num", "price", "remark","No" };
    private SimpleAdapter simpleAdapter;
    private List listData = new ArrayList();
    private Map map;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursor;
    static Cursor cursorTbl;
    SubMenu subMenu;
    SharedPreferences pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("點餐選單");
        setContentView(R.layout.activity_order);
        tableNoSpinner = (Spinner) findViewById(R.id.tableNoSpinner);
        setTableAdapter();

        personNumEt = (EditText) findViewById(R.id.personNumEditText02);
        listView = (ListView) findViewById(R.id.orderDetailListView01);
        startBtn = (Button) findViewById(R.id.startButton02);
        addBtn = (Button) findViewById(R.id.addMealButton01);
        orderBtn = (Button) findViewById(R.id.orderButton02);
        startBtn.setOnClickListener(startListener);
        addBtn.setOnClickListener(addListener);
        orderBtn.setOnClickListener(orderListener);

        setMenusAdapter();
    }

    private void setTableAdapter(){
//        String[] projection = { Tables._ID,Tables.NUM, Tables.DESCRIPTION };
 //       Uri uri = Uri.parse("content://com.example.test2.wirelessorder.provider.TABLES/table");
//       ContentResolver cr = getContentResolver();
 //       Cursor c = cr.query(uri, projection, null, null, null);
        dbHelper = new DBHelper(this);
        db=dbHelper.getDatabace(this);

        cursorTbl=dbHelper.queryTable();
        final SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
                R.layout.myspinner, cursorTbl,
                new String[] { Tables._ID }, new int[] { android.R.id.text1});
        adapter2.setDropDownViewResource(R.layout.myspinner);
        tableNoSpinner.setAdapter(adapter2);

        tableNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                //  myTextView.setText("您选择的是：" + arg2+"个");//文本说明
                TextView textView =findViewById(R.id.text8);
                TextView textView1=findViewById(R.id.text9);
                textView.setText(cursorTbl.getString(2));
                textView1.setText(cursorTbl.getString(1));
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //myTextView.setText("Nothing");
            }
        });

    }

    //listView的adapter listview.xml
    private void setMenusAdapter(){
        to[0] = R.id.id_ListView;
        to[1] = R.id.name_ListView;
        to[2] = R.id.num_ListView;
        to[3] = R.id.price_ListView;
        to[4] = R.id.remark_ListView;
        to[5] = R.id.No_ListView;

        simpleAdapter = new SimpleAdapter(this, listData, R.layout.listview, from, to);
        listView.setAdapter(simpleAdapter);
    }

    private OnClickListener startListener = new OnClickListener() {
        public void onClick(View v) {
 //           SharedPreferences preferences =getSharedPreferences("user_msg",MODE_WORLD_READABLE);
 //           String userId = preferences.getString("id","");
  //          TextView textView = (TextView) tableNoSpinner.getSelectedView();
  //          String tableId = textView.getText().toString();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy:mm:dd hh:MM");
            String orderTime = simpleDateFormat.format(date);
            String tableNum = cursorTbl.getString(0);
            String personNum = personNumEt.getText().toString();
            String result = "桌號" +tableNum+ " 用餐人數" +personNum+" 人"+"\n"+"點單時間: "+orderTime;
            dbHelper.internalUse(tableNum,personNum);
            Toast.makeText(OrderActivity.this, result, Toast.LENGTH_LONG).show();


        }
    };

    private OnClickListener addListener = new OnClickListener() {
        //@Override
        public void onClick(View v) {
            // �I�s�I���k
            addMeal();
        }
    };

    private void addMeal() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View v = inflater.inflate(R.layout.order_detail, null);
        //R.layout.order_detail
        final Spinner menuSpinner = (Spinner) v.findViewById(R.id.menuSpinner);
        EditText numEt = (EditText) v.findViewById(R.id.numEditText);
        EditText remarkEt = (EditText) v.findViewById(R.id.add_remarkEditText);
 //       Uri uri = Uri.parse("content://com.example.test2.wirelessorder.provider.MENUS/menu1");
 //       String[] projection = { "_id", "name", "price" };
 //       ContentResolver cr = getContentResolver();
//        Cursor cursor = cr.query(uri, projection, null, null, null);
        cursor=dbHelper.queryMenu();
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
                R.layout.spinner_lo, cursor,
                new String[] { Menus._ID, Menus.PRICE, Menus.NAME },
                new int[] {R.id.id_TextView01, R.id.price_TextView02,
                        R.id.name_TextView03 }
        );
        menuSpinner.setAdapter(adapter2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
            .setMessage("選擇餐點")
            .setView(v)
            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //spinner_lo.xml
                    LinearLayout linearLayout = (LinearLayout) menuSpinner.getSelectedView();
                    TextView id_tv = (TextView) linearLayout.findViewById(R.id.id_TextView01);
                    TextView price_tv = (TextView) linearLayout.findViewById(R.id.price_TextView02);
                    TextView name_tv = (TextView) linearLayout.findViewById(R.id.name_TextView03);

                    //listview.xml
                    EditText num_et = (EditText) v.findViewById(R.id.numEditText);
                    EditText remark_et = (EditText) v.findViewById(R.id.add_remarkEditText);
                    String idStr = id_tv.getText().toString();
                    String priceStr = price_tv.getText().toString();
                    String nameStr = name_tv.getText().toString();
                    String numStr = num_et.getText().toString();
                    String remarkStr = remark_et.getText().toString();

                    map = new HashMap();
                    if(Integer.parseInt(idStr)>9){
                        map.put("id", idStr);
                    }
                    else {
                        map.put("id", "0"+idStr);
                    }
                    map.put("name", nameStr);
                    map.put("num", numStr);
                    map.put("price", priceStr);
                    map.put("remark", remarkStr);
                    if(listData.size()>8){
                        map.put("No", listData.size());
                    }
                    else {
                        map.put("No", "0" + (listData.size() + 1));
                    }
                    // ListView
                    listData.add(map);
                    to[0] = R.id.id_ListView;
                    to[1] = R.id.name_ListView;
                    to[2] = R.id.num_ListView;
                    to[3] = R.id.price_ListView;
                    to[4] = R.id.remark_ListView;
                    to[5] =R.id.No_ListView;

                    simpleAdapter = new SimpleAdapter(OrderActivity.this, listData,
                            R.layout.listview, from, to);
                    listView.setAdapter(simpleAdapter);
                }
            }).setNegativeButton("取消",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    }

    private OnClickListener orderListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy:mm:dd hh:MM");
            String orderTime = simpleDateFormat.format(date);
            pre = getSharedPreferences("user_msg", MODE_WORLD_READABLE);
            String account= pre.getString("account","");
            int orderNumber;
            Cursor cursor =dbHelper.queryOrder();
            if(cursor.getCount()!=0)
            {
            cursor.moveToLast();
                orderNumber =  (cursor.getInt(3)+1);
            }
            else{
                orderNumber=1;
            }
            for (int i = 0; i < listData.size(); i++) {
                Map map = (Map)listData.get(i);
                String menuId = (String) map.get("id");
                String name = (String)map.get("name");
                String num = (String)map.get("num");
                String price = (String)map.get("price");
                String remark = (String)map.get("remark");
                dbHelper.order(account,orderTime,orderNumber+"",menuId,name,num,price,remark);
            }
            listData.clear();
            simpleAdapter = new SimpleAdapter(OrderActivity.this, listData,
                    R.layout.listview, from, to);
            listView.setAdapter(simpleAdapter);

            String result ="員工編號" +account+"點單"+"\n單號: "+orderNumber;
            Toast.makeText(OrderActivity.this, result, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        subMenu = menu.addSubMenu(0,0,0,"返回");
        MenuItem MenuItem1=subMenu.getItem();
        MenuItem1.setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM |
                        MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        db.close();
        finish();
        return false;
    }

}
