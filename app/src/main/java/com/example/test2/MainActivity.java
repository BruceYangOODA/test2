package com.example.test2;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    private ListView lvAct;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildViews();  //user define
    }

    private void buildViews(){
        lvAct = getListView();
        ArrayAdapter<CharSequence> adActList = ArrayAdapter.createFromResource(
                this, R.array.activityList, android.R.layout.simple_list_item_1);
        setListAdapter(adActList);
        lvAct.setTextFilterEnabled(true);
        lvAct.setOnItemClickListener(lvListener);
    }

    private OnItemClickListener lvListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    intent = new Intent(MainActivity.this,YoutubeViewActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this,BoarderActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this,WirelessOrderActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

}
