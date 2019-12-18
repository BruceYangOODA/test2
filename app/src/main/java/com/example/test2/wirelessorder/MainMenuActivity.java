package com.example.test2.wirelessorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.test2.R;
import com.example.test2.WirelessOrderActivity;

public class MainMenuActivity extends AppCompatActivity {

    static int girdWidth;
    static int padding =10;
    private Integer[] mThumbIds = {
            R.drawable.diancai, R.drawable.bingtai,
            R.drawable.zhuantai, R.drawable.chatai,
            R.drawable.gengxin, R.drawable.shezhi,
            R.drawable.zhuxiao, R.drawable.jietai
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("點餐系統選單");
        setContentView(R.layout.activity_main_menu);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        girdWidth = metrics.widthPixels/3;
        gridView.setColumnWidth(girdWidth);

    }

    OnClickListener orderLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainMenuActivity.this, OrderActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener updateLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainMenuActivity.this, UpdateActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener checkTableLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainMenuActivity.this, CheckTableActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener payLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            // �Ұʵ��bActivity
            intent.setClass(MainMenuActivity.this, PayActivity.class);
            startActivity(intent);
        }
    };

    OnClickListener exitLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            logout();
        }
    };
    OnClickListener changeTableLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
//            changeTable();
        }
    };
    OnClickListener unionTableLinstener = new OnClickListener() {
        @Override
        public void onClick(View v) {
//            unionTable();
        }
    };

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("登出使用者")
                .setCancelable(false)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences pres = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
                        SharedPreferences.Editor editor = pres.edit();
                        editor.putString("account", "");
                        editor.putString("password", "");
                        Intent intent = new Intent();
                        intent.setClass(MainMenuActivity.this, WirelessOrderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public class ImageAdapter extends BaseAdapter{
        private Context context;
        public ImageAdapter(Context context){ this.context=context; }

        @Override
        public int getCount() {
            return mThumbIds.length;
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
           ImageView imageView;
           if(convertView==null){
               imageView =new ImageView(context);
               imageView.setLayoutParams(new GridView.LayoutParams(girdWidth-padding,girdWidth-padding));
               imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
               imageView.setPadding(padding,padding,padding,padding);
           }
           else{
               imageView=(ImageView) convertView;
           }
           imageView.setImageResource(mThumbIds[position]);

           switch (position){
               case 0:
                   imageView.setOnClickListener(orderLinstener);
                   break;
               case 1:
                   imageView.setOnClickListener(unionTableLinstener);
                   break;
               case 2:
                   imageView.setOnClickListener(changeTableLinstener);
                   break;
               case 3:
                   imageView.setOnClickListener(checkTableLinstener);
                   break;
               case 4:
                   imageView.setOnClickListener(updateLinstener);
                   break;
               case 5:
                   //設定登入員工帳號密碼
                   break;
               case 6:
                   imageView.setOnClickListener(exitLinstener);
                   break;
               case 7:
                   imageView.setOnClickListener(payLinstener);
                   break;

           }
           return imageView;
        }
    }
}
