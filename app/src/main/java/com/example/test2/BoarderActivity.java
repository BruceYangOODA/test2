package com.example.test2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class BoarderActivity extends Activity {

 //   public final int padding = 30;
    public final int buttonHeight = 100;
    MoveBoarder moveBoarder;
    long curDate;
    long clickTime;
    long timeSpan = 100;
    boolean canChange = true;
    public Button btExec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//設定螢幕不隨手機旋轉
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//設定全螢幕
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//設定螢幕不進入休眠
        buildController();
    }

    private void buildController(){
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int buttonWidth = metrics.widthPixels/5;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setPadding(padding,0,padding,padding);
        LinearLayout.LayoutParams layoutParams;
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams controlLayoutParams;
        controlLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout controlLayout = new LinearLayout(this);
        controlLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout leftControlLayout = new LinearLayout(this);
        leftControlLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout rightControlLayout = new LinearLayout(this);
        rightControlLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout upControlLayout = new LinearLayout(this);
        upControlLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout downControlLayout = new LinearLayout(this);
        downControlLayout.setOrientation(LinearLayout.HORIZONTAL);

        leftControlLayout.addView(upControlLayout,metrics.widthPixels/5*4,buttonHeight);
        leftControlLayout.addView(downControlLayout,metrics.widthPixels/5*4,buttonHeight);
        controlLayout.addView(leftControlLayout,metrics.widthPixels/5*4,buttonHeight*2);
        controlLayout.addView(rightControlLayout,metrics.widthPixels/5,buttonHeight*2);

        Button b1 = new Button(this);
        b1.setText("B1");
        b1.setTag("B1");
        b1.setOnClickListener(btnListener);
        Button b2 = new Button(this);
        b2.setText("B2");
        b2.setTag("B2");
        b2.setOnClickListener(btnListener);
        Button b3 = new Button(this);
        b3.setText("B3");
        b3.setTag("B3");
        b3.setOnClickListener(btnListener);
        Button b4 = new Button(this);
        b4.setText("B4");
        b4.setTag("B4");
        b4.setOnClickListener(btnListener);
        Button b5 = new Button(this);
        b5.setText("B5");
        b5.setTag("B5");
        b5.setOnClickListener(btnListener);
        Button b6 = new Button(this);
        b6.setText("B6");
        b6.setTag("B6");
        b6.setOnClickListener(btnListener);
        Button b7 = new Button(this);
        b7.setText("B7");
        b7.setTag("B7");
        b7.setOnClickListener(btnListener);
        Button b8 = new Button(this);
        b8.setText("B8");
        b8.setTag("B8");
        b8.setOnClickListener(btnListener);

        upControlLayout.addView(b1,buttonWidth,buttonHeight);
        upControlLayout.addView(b2,buttonWidth,buttonHeight);
        upControlLayout.addView(b3,buttonWidth,buttonHeight);
        upControlLayout.addView(b4,buttonWidth,buttonHeight);
        downControlLayout.addView(b5,buttonWidth,buttonHeight);
        downControlLayout.addView(b6,buttonWidth,buttonHeight);
        downControlLayout.addView(b7,buttonWidth,buttonHeight);
        downControlLayout.addView(b8,buttonWidth,buttonHeight);

        btExec = new Button(this);
        btExec.setText("執行");
        btExec.setOnClickListener(btnExecListener);
        rightControlLayout.addView(btExec,LinearLayout.LayoutParams.MATCH_PARENT,buttonHeight*2);


        moveBoarder = new MoveBoarder(this);
        linearLayout.addView(moveBoarder,LinearLayout.LayoutParams.MATCH_PARENT,
                metrics.heightPixels - buttonHeight*2);
        linearLayout.addView(controlLayout,controlLayoutParams);
        setContentView(linearLayout,layoutParams);
    }

    private OnClickListener btnListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(BoarderActivity.this.moveBoarder.bA==0 ){
                BoarderActivity.this.moveBoarder.bA = Integer.parseInt(v.getTag().toString().substring(1,2));
                BoarderActivity.this.moveBoarder.bAA = BoarderActivity.this.moveBoarder.bA;
                clickTime = System.currentTimeMillis();
            }
            curDate = System.currentTimeMillis();
            if(BoarderActivity.this.moveBoarder.bA!=0 && curDate>clickTime+timeSpan &&
                    Integer.parseInt(v.getTag().toString().substring(1,2))!=BoarderActivity.this.moveBoarder.bA ){
                if(BoarderActivity.this.moveBoarder.bB==0){
                    BoarderActivity.this.moveBoarder.bB = Integer.parseInt(v.getTag().toString().substring(1,2));
                    BoarderActivity.this.moveBoarder.bBB = BoarderActivity.this.moveBoarder.bB;
                }
            }
        }
    };

    private OnClickListener btnExecListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
                moveBoarder.bA =0;
                moveBoarder.bB =0;
                moveBoarder.tutorialThread.status=0;
//                btExec.setEnabled(false);
            }
    };

}
