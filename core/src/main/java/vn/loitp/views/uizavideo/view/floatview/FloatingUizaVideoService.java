package vn.loitp.views.uizavideo.view.floatview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import loitp.core.R;
import vn.loitp.views.uizavideo.view.rl.SimpleUizaIMAVideo;

/**
 * Created by LENOVO on 3/27/2018.
 */

public class FloatingUizaVideoService extends Service {
    private final String TAG = getClass().getSimpleName();
    private WindowManager mWindowManager;
    private View mFloatingView;
    private SimpleUizaIMAVideo simpleUizaIMAVideo;
    private ImageButton btExit;

    public FloatingUizaVideoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_uiza_video, null);
        //Add the view to the window.
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        simpleUizaIMAVideo = (SimpleUizaIMAVideo) mFloatingView.findViewById(R.id.uiza_video);
        String linkPlay = getString(loitp.core.R.string.url_dash);
        //String urlIMAAd = getString(loitp.core.R.string.ad_tag_url);
        String urlIMAAd = null;
        String urlThumnailsPreviewSeekbar = getString(loitp.core.R.string.url_thumbnails);
        simpleUizaIMAVideo.initData(linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
        simpleUizaIMAVideo.onResume();

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.move_view).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            private long lastTouchDown;
            private final int CLICK_ACTION_THRESHHOLD = 200;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastTouchDown = System.currentTimeMillis();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //int Xdiff = (int) (event.getRawX() - initialTouchX);
                        //int Ydiff = (int) (event.getRawY() - initialTouchY);

                        //on Click event
                        if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                            btExit.setVisibility(btExit.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
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
        });

        btExit = (ImageButton) mFloatingView.findViewById(R.id.bt_exit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
        if (simpleUizaIMAVideo != null) {
            simpleUizaIMAVideo.onDestroy();
        }
    }
}