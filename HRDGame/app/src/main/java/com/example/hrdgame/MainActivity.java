package com.example.hrdgame;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    private int[] bingpo;
    private int[] bing1po;
    private int[] bing2po;
    private int[] bing3po;
    private int[] ccpo;
    private int[] mapo;
    private int[] guanpo;
    private int[] huangpo;
    private int[] zhaopo;
    private int[] zhangpo;
    private TextView step_num;
    int X;
    int Y;
    private Chronometer chronometer;
    boolean canDirectMove = false;
    @Override
    protected void onPause() {
        super.onPause();
//        Log.d("tanglei","onpause");
        canDirectMove = false;
    }
    @BindView(R.id.text_step)
    public TextView text_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canDirectMove = true;
        step_num = findViewById(R.id.step_num);
        chronometer = findViewById(R.id.chro);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/王汉宗勘亭流一随波.ttf");
        text_step.setTypeface(typeface);
        chronometer.setTypeface(typeface);
        step_num.setTypeface(typeface);
        GameView.setChronometer(chronometer);
        GameView.setStage(1);
//        Log.d("tanglei","oncreate");
        GameView.setNumber(step_num);
        chronometer.start();
        if (savedInstanceState != null) {
            chronometer.setBase(savedInstanceState.getLong("timer"));
            X = savedInstanceState.getInt("bingX");
            Y = savedInstanceState.getInt("bingY");
            bingpo = savedInstanceState.getIntArray("bingPos");
            GameView bing = findViewById(R.id.bing);
            bing.setPosition(bingpo);
            bing.setMoveX(X);
            bing.setMoveY(Y);

            X = savedInstanceState.getInt("bing1X");
            Y = savedInstanceState.getInt("bing1Y");
            bing1po = savedInstanceState.getIntArray("bing1Pos");
            GameView bing1 = findViewById(R.id.bing1);
            bing1.setPosition(bing1po);
            bing1.setMoveX(X);
            bing1.setMoveY(Y);

            X = savedInstanceState.getInt("bing2X");
            Y = savedInstanceState.getInt("bing2Y");
            bing2po = savedInstanceState.getIntArray("bing2Pos");
            GameView bing2 = findViewById(R.id.bing2);
            bing2.setPosition(bing2po);
            bing2.setMoveX(X);
            bing2.setMoveY(Y);

            X = savedInstanceState.getInt("bing3X");
            Y = savedInstanceState.getInt("bing3Y");
            bing3po = savedInstanceState.getIntArray("bing3Pos");
            GameView bing3 = findViewById(R.id.bing3);
            bing3.setPosition(bing3po);
            bing3.setMoveX(X);
            bing3.setMoveY(Y);

            X = savedInstanceState.getInt("ccX");
            Y = savedInstanceState.getInt("ccY");
            ccpo = savedInstanceState.getIntArray("ccPos");
            GameView cc = findViewById(R.id.cao);
            cc.setPosition(ccpo);
            cc.setMoveX(X);
            cc.setMoveY(Y);

            X = savedInstanceState.getInt("guanX");
            Y = savedInstanceState.getInt("guanY");
            guanpo = savedInstanceState.getIntArray("guanPos");
            GameView guan = findViewById(R.id.guan);
            guan.setPosition(guanpo);
            guan.setMoveX(X);
            guan.setMoveY(Y);

            X = savedInstanceState.getInt("maX");
            Y = savedInstanceState.getInt("maY");
            mapo = savedInstanceState.getIntArray("maPos");
            GameView ma = findViewById(R.id.ma);
            ma.setPosition(mapo);
            ma.setMoveX(X);
            ma.setMoveY(Y);

            X = savedInstanceState.getInt("zhangX");
            Y = savedInstanceState.getInt("zhangY");
            zhangpo = savedInstanceState.getIntArray("zhangPos");
            GameView zhang = findViewById(R.id.zhang);
            zhang.setPosition(zhangpo);
            zhang.setMoveX(X);
            zhang.setMoveY(Y);

            X = savedInstanceState.getInt("zhaoX");
            Y = savedInstanceState.getInt("zhaoY");
            zhaopo = savedInstanceState.getIntArray("zhaoPos");
            GameView zhao = findViewById(R.id.zhao);
            zhao.setPosition(zhaopo);
            zhao.setMoveX(X);
            zhao.setMoveY(Y);

            X = savedInstanceState.getInt("huangX");
            Y = savedInstanceState.getInt("huangY");
            huangpo = savedInstanceState.getIntArray("huangPos");
            GameView huang = findViewById(R.id.huang);
            huang.setPosition(huangpo);
            huang.setMoveX(X);
            huang.setMoveY(Y);

            String n = savedInstanceState.getString("stepNum");
            step_num.setText(n);
//            Log.d("movedebug","not null");
        }
        else{
            bingpo = new int[]{14};
            ccpo = new int[]{1, 2, 5, 6};
            zhangpo = new int[]{0, 4};
            zhaopo = new int[]{3, 7};
            guanpo = new int[]{9, 10};
            bing1po = new int[]{13};
            bing2po = new int[]{19};
            bing3po = new int[]{16};
            huangpo = new int[]{8, 12};
            mapo = new int[]{11, 15};
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d("tanglei","onsave");
        outState.putLong("timer",chronometer.getBase());
        GameView bing = findViewById(R.id.bing);
        outState.putInt("bingX", bing.getMoveX());
        outState.putInt("bingY", bing.getMoveY());
        outState.putIntArray("bingPos", bing.getPosition());

        GameView bing1 = findViewById(R.id.bing1);
        outState.putInt("bing1X", bing1.getMoveX());
        outState.putInt("bing1Y", bing1.getMoveY());
        outState.putIntArray("bing1Pos", bing1.getPosition());

        GameView bing2 = findViewById(R.id.bing2);
        outState.putInt("bing2X", bing2.getMoveX());
        outState.putInt("bing2Y", bing2.getMoveY());
        outState.putIntArray("bing2Pos", bing2.getPosition());

        GameView bing3 = findViewById(R.id.bing3);
        outState.putInt("bing3X", bing3.getMoveX());
        outState.putInt("bing3Y", bing3.getMoveY());
        outState.putIntArray("bing3Pos", bing3.getPosition());

        GameView cc = findViewById(R.id.cao);
        outState.putInt("ccX", cc.getMoveX());
        outState.putInt("ccY", cc.getMoveY());
        outState.putIntArray("ccPos", cc.getPosition());

        GameView guan = findViewById(R.id.guan);
        outState.putInt("guanX", guan.getMoveX());
        outState.putInt("guanY", guan.getMoveY());
        outState.putIntArray("guanPos", guan.getPosition());

        GameView zhao = findViewById(R.id.zhao);
        outState.putInt("zhaoX", zhao.getMoveX());
        outState.putInt("zhaoY", zhao.getMoveY());
        outState.putIntArray("zhaoPos", zhao.getPosition());

        GameView zhang = findViewById(R.id.zhang);
        outState.putInt("zhangX", zhang.getMoveX());
        outState.putInt("zhangY", zhang.getMoveY());
        outState.putIntArray("zhangPos", zhang.getPosition());

        GameView ma = findViewById(R.id.ma);
        outState.putInt("maX", ma.getMoveX());
        outState.putInt("maY", ma.getMoveY());
        outState.putIntArray("maPos", ma.getPosition());

        GameView huang = findViewById(R.id.huang);
        outState.putInt("huangX", huang.getMoveX());
        outState.putInt("huangY", huang.getMoveY());
        outState.putIntArray("huangPos", huang.getPosition());

        String n = step_num.getText().toString();
        outState.putString("stepNum",n);
    }

    @Override
    protected void onStop() {
        super.onStop();

//        GameView.clear();
//        Log.d("tanglei","onstop");
//        Log.d("timer",String.valueOf(chronometer.getBase()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setStep();
//        Log.d("tanglei","onchanged");
//        Log.d("tanglei",String.valueOf(canDirectMove));
        if(canDirectMove) {
            for (GameView gameView : GameView.viewList) {
                gameView.directMove();
            }
//            Log.d("tanglei","moved");
        }
    }

    public void setStep() {
//
        GameView bing = findViewById(R.id.bing);
        bing.setPosition(bingpo);
        bing.setViewId(5);
        bing.setStepLength(bing.getWidth());
//        bing.directMove();
        int step = bing.getWidth();
        //
        GameView cc = findViewById(R.id.cao);
        cc.setPosition(ccpo);
        cc.setViewId(1);
        cc.setStepLength(step);
//        cc.directMove();
        //
        GameView zhang = findViewById(R.id.zhang);
        zhang.setPosition(zhangpo);
        zhang.setViewId(2);
        zhang.setStepLength(step);
//        zhang.directMove();
        //
        GameView zhao = findViewById(R.id.zhao);
        zhao.setPosition(zhaopo);
        zhao.setViewId(3);
        zhao.setStepLength(step);
//        zhao.directMove();
        //
        GameView guan = findViewById(R.id.guan);
        guan.setPosition(guanpo);
        guan.setViewId(4);
        guan.setStepLength(step);
//        guan.directMove();
        //

        GameView bing1 = findViewById(R.id.bing1);
        bing1.setPosition(bing1po);
        bing1.setViewId(6);
        bing1.setStepLength(step);
//        bing1.directMove();
        //
        GameView bing2 = findViewById(R.id.bing2);
        bing2.setPosition(bing2po);
        bing2.setViewId(7);
        bing2.setStepLength(step);
//        bing.directMove();
        //
        GameView bing3 = findViewById(R.id.bing3);
        bing3.setPosition(bing3po);
        bing3.setViewId(10);
        bing3.setStepLength(step);
//        bing3.directMove();
//        Log.d("movedebug",String.valueOf(bing.getWidth()));
        //
        GameView huang = findViewById(R.id.huang);
        huang.setPosition(huangpo);
        huang.setViewId(8);
        huang.setStepLength(step);
//        huang.directMove();
//        for (int i : huang.getPosition()) {
//            Log.d("huang", String.valueOf(i));
//        }
        //
        GameView ma = findViewById(R.id.ma);
        ma.setPosition(mapo);
        ma.setViewId(9);
        ma.setStepLength(step);
//        ma.directMove();
    }

    public void backUp(View view) {
        GameView.backUp();
    }

}
