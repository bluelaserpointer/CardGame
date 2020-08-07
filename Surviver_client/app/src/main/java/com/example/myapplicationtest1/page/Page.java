package com.example.myapplicationtest1.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class Page extends Activity {
    protected final void setJump(View view, Class<? extends Activity> targetPage) {
        view.setOnTouchListener((view1, motionEvent) -> {
            view1.performClick();
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                startActivity(new Intent(this, targetPage));
            }
            return false;
        });
    }
    protected final void setJump(int viewId, Class<? extends Activity> targetPageClass) {
        setJump(findViewById(viewId), targetPageClass);
    }
    public static void jump(Context context, Class<? extends Activity> targetPageClass) {
        context.startActivity(new Intent(context, targetPageClass));
    }
    protected final void setTouchEvent(int viewId, Process process) {
        findViewById(viewId).setOnTouchListener((v, motionEvent) -> {
            v.performClick();
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                process.process();
            }
            return false;
        });
    }
    protected final void setImage_ImageView(int viewId, int drawableId) {
        ((ImageView)findViewById(viewId)).setImageResource(drawableId);
    }
    protected final void setText_TextView(int viewId, Object data) {
        ((TextView)findViewById(viewId)).setText(String.valueOf(data));
    }
    protected final void setText_Button(int viewId, Object data) {
        ((Button)findViewById(viewId)).setText(String.valueOf(data));
    }
}
