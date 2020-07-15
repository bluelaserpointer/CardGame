package com.example.myapplicationtest1.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

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
}
