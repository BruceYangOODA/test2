package com.example.test2.wirelessorder;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.test2.R;

public class UpdateActivity extends ListActivity {


    final static int MENUVIEW=0;
    final static int MENUEDIT=1;
    final static int TABLEVIEW=2;
    final static int TABLEEDIT=3;
    final static int EXIT=4;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("更新菜單");
        getActionBar().setIcon(R.drawable.editicon);
        builder = new AlertDialog.Builder(this);

        ListView listView = getListView();
        listView.setPadding(20,20,20,20);
        listView.setDividerHeight(20);
        listView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String[] items = {"檢視[MenuTbl]","編輯[MenuTbl]","檢視[TableTbl]","編輯[TableTbl]","返回首頁" };

        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case MENUVIEW:
                intent = new Intent(UpdateActivity.this,QueryMenuActivity.class);
                startActivity(intent);
                break;
            case MENUEDIT:
                builder = new AlertDialog.Builder(this);
                builder.setMessage("編輯[MenuTbl]")
                        .setCancelable(false)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(UpdateActivity.this,EditMenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                break;
            case TABLEVIEW:
                intent = new Intent(UpdateActivity.this,QueryTableActivity.class);
                startActivity(intent);
                break;
            case TABLEEDIT:
                builder.setMessage("編輯[TableTbl]")
                        .setCancelable(false)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(UpdateActivity.this,EditTableActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                break;
            case EXIT:
                finish();
                break;
            default:
                break;
        }
    }
}
