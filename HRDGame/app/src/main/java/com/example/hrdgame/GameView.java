package com.example.hrdgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class GameView extends View {
    private final Context my_context;
    private float x,y;
    private int [] position;
    private TranslateAnimation animation;
    private int winPoint = 0;
    private int viewId;
    private int stepLength;
    private static Chronometer chronometer;
    private static EditText editText;
    public static List<GameView> viewList = new ArrayList<>();
    public static Stack<moveStep> stepStack = new Stack<>();
    private static final int UP = 848;
    private static final int LEFT = 334;
    private static final int RIGHT = 739;
    private static final int DOWN = 190;
    private int moveX = 0;
    private int moveY = 0;
    private static TextView number;
    private boolean canReset = true;
    private static int stage;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                y = event.getRawY();
//                for(int v:position){
//                String te = String.valueOf(v);
//                Log.d("testbug",te);}
//                Log.d("movedebug",String.valueOf(getTop()));
                break;

            case MotionEvent.ACTION_MOVE:
                    break;

            case MotionEvent.ACTION_UP:
                float destX = event.getRawX() - x;
                float destY = event.getRawY() - y;
//                Log.d("movedebug",String.valueOf(stepLength));
                if(Math.abs(destX) >= Math.abs(destY) && destX != 0) {
                    if (destX > 0){
                        if(canMove(RIGHT)) {
                            move(stepLength, 0,1,200);
                            moveX += 1;
                            for (int posi = 0; posi < position.length; posi++)
                                position[posi]++;
                            stepStack.push(new moveStep(this,LEFT));
                        }
                    }
                    else {
                        if(canMove(LEFT)) {
                            move(-stepLength, 0,1,200);
                            moveX -= 1;
                            for (int posi = 0; posi < position.length; posi++)
                                position[posi]--;
                            stepStack.push(new moveStep(this,RIGHT));
                        }
                    }
                }
                else {
                    if (destY > 0 && canMove(DOWN)) {
                        move(0, stepLength,1,200);
                        moveY += 1;
                        for (int posi = 0; posi < position.length; posi++)
                            position[posi]+=4;
                        stepStack.push(new moveStep(this,UP));
                    }
                    if (destY < 0 && canMove(UP)) {
                        move(0, -stepLength,1,200);
//                        Log.d("testbug","up");
                        moveY -= 1;
                        for (int posi = 0; posi < position.length; posi++)
                            position[posi]-=4;
                        stepStack.push(new moveStep(this,DOWN));
                    }
                }
                for(GameView cao :viewList){
                    if(cao.getViewId() == 1){
                        for(int fp:cao.getPosition()){
                            if(fp == 17 || fp == 18){
                                winPoint++;
                            }
                        }

                        if(winPoint == 2) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(my_context);
                            final View view = LayoutInflater.from(my_context).inflate(R.layout.win_dialog, null);
                            Toast.makeText(org.litepal.LitePalApplication.getContext(), "You Win!", Toast.LENGTH_LONG).show();
                            builder.setView(view);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int temp0 = Integer.parseInt(chronometer.getText().toString().split(":")[0]);
                                    int temp1 =Integer.parseInt(chronometer.getText().toString().split(":")[1]);
                                    int temp=temp0*60+temp1;
                                    Rank rank = new Rank();
                                    editText = view.findViewById(R.id.my_name);
                                    String name = editText.getText().toString();
                                    if(name.equals(""))
                                        name = "匿名 ";
                                    rank.setPlayer(name);
                                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                                    rank.setCreated_at(s_format.format(new Date(System.currentTimeMillis())));
                                    rank.setStage(stage);
                                    int num = Integer.parseInt(number.getText().toString());
                                    rank.setSteps(num);
                                    rank.setTime(temp);
                                    rank.save();
                                    Intent mIntent = new Intent(my_context,LoginActivity.class);
                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    my_context.startActivity(mIntent);
                                }
                            });
                            builder.show();
                        }
                    }
                    winPoint = 0;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

        }

        return true;
    }
    void move(final float destX, final float destY, int grad,int duration){
        int num = Integer.parseInt(number.getText().toString());
        num += grad;
        number.setText(String.valueOf(num));
        animation=
                new TranslateAnimation(0,destX ,0,destY);
        animation.setDuration(duration);
        animation.setFillAfter(true);

        final GameView view = this;
//        Log.d("tanglei","reset");
        canReset = false;
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = getLeft()+(int)destX;
                int top = getTop()+(int)destY;
                int width = getWidth();
                int height = getHeight();
                clearAnimation();
                view.layout(left, top, left+width, top+height);
                setParams(left,top);
                view.invalidate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.startAnimation(animation);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewList.add(this);
        this.my_context = context;
    }
    public void setParams(int left,int top){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        layoutParams.setMarginStart(left);
        layoutParams.topMargin = top;
        this.setLayoutParams(layoutParams);
    }
    public void setPosition(int []p){
        position = p;
    }

    public int[] getPosition() {
        return position;
    }

    public static void setStage(int stage) {
        GameView.stage = stage;
    }

    public static void setChronometer(Chronometer chron) {
        chronometer = chron;
    }

    public void setViewId(int id){
        viewId = id;
    }

    public void setStepLength(int stepLength) {
        this.stepLength = stepLength;
    }

    public int getViewId() {
        return viewId;
    }

    public static void clear(){
        viewList.clear();
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public static void setEditText(EditText editText) {
        GameView.editText = editText;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public static void setNumber(TextView number) {
        GameView.number = number;
    }




    public void directMove(){
//        Log.d("movedebug","direct"+String.valueOf(this.viewId));
        int left = getLeft()+moveX*stepLength;
        int top = getTop()+moveY*stepLength;

        int width = getWidth();
        int height = getHeight();
        this.layout(left, top, left+width, top+height);
        setParams(left,top);
        invalidate();
    }
    boolean canMove(int direction){
        if(direction == LEFT){
            if(position != null){
                for(int pos:position){
                    if(pos == 0 || pos == 4 || pos == 8 || pos == 12 || pos == 16)
                        return false;

                    int nextPos = pos - 1;
                    for(GameView destView:viewList){
                        if(destView.getViewId() != this.viewId) {
                            int[] viewPos = destView.getPosition();
                            for (int vp : viewPos) {
                                if (nextPos == vp) {
//                                    Log.d("movefalse",String.valueOf(destView.getViewId()));
                                    for(int i:viewPos)
//                                        Log.d("falsepos",String.valueOf(i));
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(direction == UP){
            if(position != null){
                for(int pos:position){
                    if(pos == 0 || pos == 1 || pos == 2 || pos == 3)
                        return false;

                    int nextPos = pos - 4;
                    for(GameView destView:viewList){
                        if(destView.getViewId() != this.viewId) {
                            int[] viewPos = destView.getPosition();
                            for (int vp : viewPos) {
                                if (nextPos == vp)
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        if(direction == RIGHT){
            if(position != null){
                for(int pos:position){
                    if(pos == 3 || pos == 7 || pos == 11 || pos == 15 || pos == 19)
                        return false;

                    int nextPos = pos + 1;
                    for(GameView destView:viewList){
                        if(destView.getViewId() != this.viewId) {
                            int[] viewPos = destView.getPosition();
                            for (int vp : viewPos) {
                                if (nextPos == vp)
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        if(direction == DOWN){
            if(position != null){
                for(int pos:position){
                    if(pos == 16 || pos == 17 || pos == 18 || pos == 19)
                        return false;

                    int nextPos = pos + 4;
                    for(GameView destView:viewList){
                        if(destView.getViewId() != this.viewId) {
                            int[] viewPos = destView.getPosition();
                            for (int vp : viewPos) {
                                if (nextPos == vp)
                                    return false;
                            }
                        }
                    }


                }
            }
        }
    return true;
    }
    public class moveStep {
        private GameView view;
        private int direction;
        moveStep(GameView gameView,int direct){
            view = gameView;
            direction = direct;
        }

        public GameView getView() {
            return view;
        }

        public void setView(GameView view) {
            this.view = view;
        }

        public void action(){
            switch (direction){
                case UP:
                    view.move(0, -view.stepLength,-1,0);
                    view.moveY -= 1;
                    for (int posi = 0; posi < view.position.length; posi++)
                        view.position[posi]-=4;
                    break;
                case DOWN:
                    view.move(0, view.stepLength,-1,0);
                    view.moveY += 1;
                    for (int posi = 0; posi < view.position.length; posi++)
                        view.position[posi]+=4;
                    break;
                case LEFT:
                    view.move(-view.stepLength, 0,-1,0);
                    view.moveX -= 1;
                    for (int posi = 0; posi < view.position.length; posi++)
                        view.position[posi]--;
                    break;
                case RIGHT:
                    view.move(view.stepLength, 0,-1,0);
                    view.moveX += 1;
                    for (int posi = 0; posi < view.position.length; posi++)
                        view.position[posi]++;
                    break;
            }
        }
    }
    public static void backUp(){
        moveStep step;
        if(!stepStack.empty()) {
            step = stepStack.pop();
            for(GameView gameView:viewList)
                if(gameView.getViewId() == step.getView().getViewId())
                    step.setView(gameView);
                step.action();
        }
    }
}
