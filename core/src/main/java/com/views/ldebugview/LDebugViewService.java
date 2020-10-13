package com.views.ldebugview;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.R;
import com.core.utilities.LAppResource;
import com.core.utilities.LDateUtil;
import com.core.utilities.LScreenUtil;
import com.core.utilities.LUIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LDebugViewService extends Service implements View.OnTouchListener {
    private final String logTag = getClass().getSimpleName();
    private WindowManager mWindowManager;
    private View mFloatingView;
    private WindowManager.LayoutParams params;
    private View collapsedView;
    private View expandedView;
    private LinearLayout llRootTv;
    private ScrollView scrollView;

    public LDebugViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);

        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_debug_view_service, null);

        //Add the view to the window.
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        collapsedView = mFloatingView.findViewById(R.id.rlCollapse);
        expandedView = mFloatingView.findViewById(R.id.llExpanded);

        expandedView.getLayoutParams().width = LScreenUtil.Companion.getScreenWidth() / 2;
        expandedView.getLayoutParams().height = LScreenUtil.Companion.getScreenHeight() * 2 / 3;
        expandedView.requestLayout();

        llRootTv = mFloatingView.findViewById(R.id.ll_root_tv);
        scrollView = mFloatingView.findViewById(R.id.scrollView);

        //Set the close button
        ImageView closeButtonCollapsed = mFloatingView.findViewById(R.id.ivClose);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the service and remove the from from the window
                stopSelf();
            }
        });

        //Set the close button
        ImageView closeButton = mFloatingView.findViewById(R.id.ivCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.rlRootContainer).setOnTouchListener(this);
    }

    /**
     * Detect if the floating view is collapsed or expanded.
     *
     * @return true if the floating view is collapsed.
     */
    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.rlCollapse).getVisibility() == View.VISIBLE;
    }

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.rlRootContainer) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //remember the initial position.
                    initialX = params.x;
                    initialY = params.y;

                    //get the touch location
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;
                case MotionEvent.ACTION_UP:
                    int Xdiff = (int) (event.getRawX() - initialTouchX);
                    int Ydiff = (int) (event.getRawY() - initialTouchY);

                    //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                    //So that is click event.
                    if (Xdiff < 10 && Ydiff < 10) {
                        if (isViewCollapsed()) {
                            //When user clicks on the image view of the collapsed layout,
                            //visibility of the collapsed layout will be changed to "View.GONE"
                            //and expanded view will become visible.
                            collapsedView.setVisibility(View.GONE);
                            expandedView.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //Calculate the X and Y coordinates of the view.
                    params.x = initialX + (int) (event.getRawX() - initialTouchX);
                    params.y = initialY + (int) (event.getRawY() - initialTouchY);

                    //Update the layout with new X & Y coordinate
                    mWindowManager.updateViewLayout(mFloatingView, params);
                    return true;
            }
            return false;
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void print(LComunicateDebug.MsgFromActivity msgFromActivity) {
        if (msgFromActivity == null) {
            return;
        }
        String currentTime = LDateUtil.Companion.getDateCurrentTimeZoneMls(System.currentTimeMillis(), "HH:mm:ss");
        TextView textView = new TextView(this);
        LUIUtil.Companion.setTextSize(textView, LAppResource.INSTANCE.getDimenValue(R.dimen.txt_medium));

        if (msgFromActivity.getObject() == null) {
//            Log.d(logTag, "msgFromActivity.getObject() == null");
            textView.setText(currentTime + " : " + msgFromActivity.getMsg());
        } else {
//            Log.d(logTag, "msgFromActivity.getObject() != null");
            LUIUtil.Companion.printBeautyJson(msgFromActivity.getObject(), textView, currentTime);
        }

        LUIUtil.Companion.setTextSize(textView, getBaseContext().getResources().getDimension(R.dimen.text_small));
        if (msgFromActivity.getType() == LComunicateDebug.MsgFromActivity.TYPE_D) {
            textView.setTextColor(Color.WHITE);
        } else if (msgFromActivity.getType() == LComunicateDebug.MsgFromActivity.TYPE_E) {
            textView.setTextColor(Color.RED);
        } else if (msgFromActivity.getType() == LComunicateDebug.MsgFromActivity.TYPE_I) {
            textView.setTextColor(Color.GREEN);
        }
        llRootTv.addView(textView);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
        }
        super.onDestroy();
    }

    //listen msg from activity
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LComunicateDebug.MsgFromActivity msg) {
        print(msg);
    }
}