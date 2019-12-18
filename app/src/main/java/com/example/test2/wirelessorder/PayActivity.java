package com.example.test2.wirelessorder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.wirelessorder.provider.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends AppCompatActivity {
    private WebView wv;
    private Button queryBtn,payBtn;
    private EditText orderIdEt;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Cursor cursorPay;
    private SubMenu subMenu;
    private Map map;
    static final String DATABASE_NAME = "Wireless.db";
    private List listData = new ArrayList();
    int[] to = new int[4];
    String[] from = { "ordernumber", "menuName", "menuNum", "menuPrice" };
    ListView listView;
    SharedPreferences pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setTitle("結帳系統");
        queryBtn = (Button)findViewById(R.id.pay_query_Button01);
        payBtn = (Button)findViewById(R.id.pay_Button01);
        orderIdEt = (EditText)findViewById(R.id.pay_order_number_EditText01);
        queryBtn.setOnClickListener(queryListener);
        payBtn.setOnClickListener(payListener);
        listView = findViewById(R.id.listViewPay);

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟資料庫
        dbHelper = new DBHelper(this);
    }

    OnClickListener queryListener = new OnClickListener() {
        //@Override
        public void onClick(View v) {

            int orderId = Integer.parseInt(orderIdEt.getText().toString());
 //           Cursor cursorPay = dbHelper.queryOrder();
            cursorPay = dbHelper.queryOrder();
/*
            cursorPay.move(orderId);
            TextView textView=findViewById(R.id.test00);
            textView.setText(""+cursorPay.getInt(0)//
  //                  +cursorPay.getString(1)//guest
  //                  +cursorPay.getString(2)//date
                    +"\t"+cursorPay.getInt(3)//22
                    +"\t"+cursorPay.getInt(4)
                    +"\t"+cursorPay.getString(5)//白飯
                    +"\t"+cursorPay.getInt(6)//1
                    +"\t"+cursorPay.getInt(7)//35
//                    +cursorPay.getInt(8)//0
 //                   +cursorPay.getInt(9)//0
            );
*/
            cursorPay.moveToFirst();
            for(int i=0;i<cursorPay.getCount();i++){
                if(cursorPay.getInt(3)==orderId){
                    map = new HashMap();
                    map.put("ordernumber",cursorPay.getString(3));
                    map.put("menuName",cursorPay.getString(5));
                    map.put("menuNum",cursorPay.getString(6));
                    map.put("menuPrice",cursorPay.getString(7));
                    listData.add(map);
                }
               cursorPay.moveToNext();
            }
            to[0] = R.id.No_ListViewPay;
            to[1] = R.id.name_ListViewPay;
            to[2] = R.id.num_ListViewPay;
            to[3] = R.id.price_ListViewPay;

            SimpleAdapter simpleAdapter = new SimpleAdapter(PayActivity.this, listData,
                    R.layout.list_pay, from, to);
            listView.setAdapter(simpleAdapter);
            payBtn.setEnabled(true);

        }
    };

    OnClickListener payListener = new OnClickListener() {
        //@Override
        public void onClick(View v) {


            int total=0;
            for(int i=0;i<listData.size();i++){
                Map map = (Map)listData.get(i);
                String num = (String)map.get("menuNum");
                String price = (String)map.get("menuPrice");
                total += Integer.parseInt(num) * Integer.parseInt(price);
            }

            AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
            builder.setMessage("總共"+total+" 元")
                    .setCancelable(false)
                    .setPositiveButton("結帳", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listData.clear();
                            SimpleAdapter simpleAdapter = new SimpleAdapter(PayActivity.this, listData,
                                    R.layout.list_pay, from, to);
                            listView.setAdapter(simpleAdapter);

                            String orderId = orderIdEt.getText().toString();
                            String result = "";
                            pre = getSharedPreferences("user_msg", MODE_WORLD_READABLE);
                            String account= pre.getString("account","");
                            result = "員工編號"+account +"單號: "+ orderId;
                            Toast.makeText(PayActivity.this, result, Toast.LENGTH_LONG).show();
                            payBtn.setEnabled(false);
                        }
                    })
                    .setNegativeButton("取消",null).show();

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
