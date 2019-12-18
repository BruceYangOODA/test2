package com.example.test2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test2.wirelessorder.MainMenuActivity;
import com.example.test2.wirelessorder.provider.DBHelper;

public class WirelessOrderActivity extends AppCompatActivity {
    private Button cancelBtn, loginBtn;
    private EditText userEditText, pwdEditText;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    AlertDialog.Builder builder;
    static final String DATABASE_NAME = "Wireless.db";
    private SharedPreferences pre;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wireless_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cancelBtn = (Button) findViewById(R.id.cancelButton);
        loginBtn = (Button) findViewById(R.id.loginButton);
        userEditText = (EditText) findViewById(R.id.userEditText);
        pwdEditText = (EditText) findViewById(R.id.pwdEditText);

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);//開啟資料庫
        dbHelper = new DBHelper(this);
        dbHelper.onCreate(db);
        dbHelper.insertDefault();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder = new AlertDialog.Builder(WirelessOrderActivity.this);
                builder.setMessage("請問要離該點餐系統嗎?")
                        .setCancelable(false)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validate()) {
                    if (login()) {
                        Intent intent = new Intent(WirelessOrderActivity.this,MainMenuActivity.class);
                        startActivity(intent);

                    } else {
                        showDialog("密碼錯誤");
                        pre = getSharedPreferences("user_msg", MODE_WORLD_READABLE);
                        String account= pre.getString("account","");
                        String password= pre.getString("password","");
                        userEditText.setText(account);
                        pwdEditText.setText(password);
                    }
                }
            }
        });
    }

    private boolean validate() {
        String username = userEditText.getText().toString();
        if (username.equals("")) {
            showDialog("請輸入帳號");
            return false;
        }
        String pwd = pwdEditText.getText().toString();
        if (pwd.equals("")) {
            showDialog("請輸入密碼");
            return false;
        }
        return true;
    }

    private boolean login() {
        String account = userEditText.getText().toString().trim();
        String pwd = pwdEditText.getText().toString().trim();
        saveUserMsg("account=" + account + ";password=" + pwd);
        boolean result = dbHelper.checkPassword(account,pwd);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    private void saveUserMsg(String msg) {
        String account = "";
        String password = "";
        String[] msgs = msg.split(";");
        int idx = msgs[0].indexOf("=");
        account = msgs[0].substring(idx + 1);
        idx = msgs[1].indexOf("=");
        password = msgs[1].substring(idx + 1);
        pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
        editor = pre.edit();
        editor.putString("account", account);
        editor.putString("password", password);
        editor.commit();
    }

    private void showDialog(String msg) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
