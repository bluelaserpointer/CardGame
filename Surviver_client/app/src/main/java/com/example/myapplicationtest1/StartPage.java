package com.example.myapplicationtest1;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.myapplicationtest1.page.HomePage;
import com.example.myapplicationtest1.page.LoginInputPage;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.utils.Urls;
import com.example.myapplicationtest1.utils.Utils;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartPage extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden
     */
    private static final boolean AUTO_HIDE = true;
    public static Activity staticActivity;
    private static boolean connectedTimeOutHappen = false;
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
        }
    };
    private final Runnable mShowPart2Runnable = () -> {
        // Delayed display of UI elements
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = this::hide;
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     *
     * 该位置就是写跳转逻辑的部分。
     * 当完成一个按钮的制作时，设置适当的ID名，这样即可在findViewById(R.id.XXX)里找到该按钮控件。
     * 之后按照已写好的代码指定跳转目标(layout)即可。
     */
    private final View.OnTouchListener loginHideTouchListener = (view, motionEvent) -> {
        view.performClick();
        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (AUTO_HIDE) {
                hide();
            }
            connectedTimeOutHappen = false;
            if (Utils.identifyUser()) {
                System.out.println("StartPage: Identification succeed.");
                Page.jump(this, HomePage.class);
            } else if (connectedTimeOutHappen) {
                System.out.println("StartPage: Connection Timeout.");
            } else {
                System.out.println("StartPage: Identification failed.");
                Page.jump(this, LoginInputPage.class);
            }
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticActivity = this;
        CrashHandler.getInstance().init(this);
        setContentView(R.layout.login);
        Utils.setSharedPreference(this);
        StartPage.staticActivity.findViewById(R.id.ConnectionTimeoutTip).setVisibility(connectedTimeOutHappen ? View.VISIBLE : View.INVISIBLE);
        ((TextView)findViewById(R.id.clientVersion)).setText("客户端版本号：" + Utils.CLIENT_VERSION);

        mVisible = true;

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.login_button).setOnTouchListener(loginHideTouchListener);
        findViewById(R.id.hello_button).setOnTouchListener((v, motionEvent) -> {
            v.performClick();
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Page.jump(this, LoginInputPage.class);
            }
            return false;
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide() {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);
    }



    /**
     * These are for further implementations of methods for calculating the data
     * and initializing specific listViewers...for horizontal scrolling
     * Usage:
     * Call calculate......() in onTouch functions
     */

    private ArrayList<String> mTexts = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();

    private void calculateBasicExp(){
        /**
         * Calculations here
         * Usage:
         * for(...)
         *  {
         *      mImages.add("http://apple.jpg");
         *      mTexts.add("+1exp");
         *  }
         */

        initRecyclerView(mTexts, mImages, R.id.cardExpList);
    }

    private void calculateBasicItem(){
        /**
         * Calculations here
         * Usage:
         * for(...)
         *  {
         *      mImages.add("http://apple.jpg");
         *      mTexts.add("+1exp");
         *  }
         */

        initRecyclerView(mTexts, mImages, R.id.basicItemList);
    }

    private void calculateFallenItem(){
        /**
         * Calculations here
         * Usage:
         * for(...)
         *  {
         *      mImages.add("http://apple.jpg");
         *      mTexts.add("+1exp");
         *  }
         */

        initRecyclerView(mTexts, mImages, R.id.fallenItemList);
    }

    private void initRecyclerView(ArrayList<String> mTexts, ArrayList<String> mImages, int id){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(id);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter;
        switch(id)
        {
            case R.id.cardExpList:
                adapter = new ExpListViewAdapter(this, mTexts, mImages);
                break;
            case R.id.basicItemList:
                adapter = new BasicItemListViewAdapter(this, mTexts, mImages);
                break;
            case R.id.fallenItemList:
                adapter = new FallenItemListViewAdapter(this, mTexts, mImages);
                break;
            default:
                adapter = null;
        }
        recyclerView.setAdapter(adapter);
    }
    public static void backWithConnectionError() {
        connectedTimeOutHappen = true;
        Urls.token = null;
        Page.jump(StartPage.staticActivity, StartPage.class);
    }
}