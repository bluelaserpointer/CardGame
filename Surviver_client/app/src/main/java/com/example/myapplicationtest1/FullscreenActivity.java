package com.example.myapplicationtest1;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     *
     * 该位置就是写跳转逻辑的部分。
     * 当完成一个按钮的制作时，设置适当的ID名，这样即可在findViewById(R.id.XXX)里找到该按钮控件。
     * 之后按照已写好的代码指定跳转目标(layout)即可。
     */
    private final View.OnTouchListener loginHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        hide();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    toHomeTouchListener.onTouch(view, motionEvent); //防止相同内容重写
                break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toHomeTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.home);
                    findViewById(R.id.toBattle_button).setOnTouchListener(toBattleTouchListener);
                    findViewById(R.id.toOption_button).setOnTouchListener(toOptionTouchListener);
                    findViewById(R.id.toShop_button).setOnTouchListener(toShopTouchListener);
                    findViewById(R.id.toMail_button).setOnTouchListener(toMailTouchListener);
                    findViewById(R.id.toAnnounce_button).setOnTouchListener(toAnnounceTouchListener);
                    findViewById(R.id.toBag_button).setOnTouchListener(toTeamTouchListener);


                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toMailTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.mail);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toAnnounceTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.announce);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toTeamTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.team);
                    findViewById(R.id.toItemStorage_Button).setOnTouchListener(toItemStorageTouchListener);
                    findViewById(R.id.toCardStorage_Button).setOnTouchListener(toCardStorageTouchListener);
                    findViewById(R.id.toGallery_Button).setOnTouchListener(toGalleryTouchListener);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toGalleryTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.gallery);
                    findViewById(R.id.toTeam_Button).setOnTouchListener(toTeamTouchListener);
                    findViewById(R.id.toItemStorage_Button).setOnTouchListener(toItemStorageTouchListener);
                    findViewById(R.id.toCardStorage_Button).setOnTouchListener(toCardStorageTouchListener);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toItemStorageTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.itemstorage);
                    findViewById(R.id.toTeam_Button).setOnTouchListener(toTeamTouchListener);
                    findViewById(R.id.toCardStorage_Button).setOnTouchListener(toCardStorageTouchListener);
                    findViewById(R.id.toGallery_Button).setOnTouchListener(toGalleryTouchListener);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toCardStorageTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.cardstorage);
                    findViewById(R.id.toTeam_Button).setOnTouchListener(toTeamTouchListener);
                    findViewById(R.id.toItemStorage_Button).setOnTouchListener(toItemStorageTouchListener);
                    findViewById(R.id.toGallery_Button).setOnTouchListener(toGalleryTouchListener);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener toBattleTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.map);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };
    private final View.OnTouchListener toShopTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.shop);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    break;
            }
            return false;
        }
    };

    // TODO: Waiting to turn it to "toFriendTouchListener"
    private final View.OnTouchListener toOptionTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.friend_list);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    findViewById(R.id.friendList_button).setOnTouchListener(toOptionTouchListener);
                    findViewById(R.id.friendAdd_button).setOnTouchListener(toFriendAddTouchListener);
                    break;
            }
            return false;
        }
    };


    private final View.OnTouchListener toFriendAddTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    setContentView(R.layout.friend_add);
                    findViewById(R.id.return_button).setOnTouchListener(toHomeTouchListener);
                    findViewById(R.id.friendList_button).setOnTouchListener(toOptionTouchListener);
                    findViewById(R.id.friendAdd_button).setOnTouchListener(toFriendAddTouchListener);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.login_button).setOnTouchListener(loginHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
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
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}