package vn.loitp.app.activity.demo.floatingvideo;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.core.utilities.LDeviceUtil;
import com.core.utilities.LScreenUtil;

import vn.loitp.app.R;

/**
 * Created by loitp on 3/27/2018.
 */

public class FloatingViewEdgeService extends Service {
    private final String TAG = getClass().getSimpleName();
    private WindowManager mWindowManager;
    private View mFloatingView;
    private View vBkgDestroy;
    private int screenWidth;
    private int screenHeight;
    private int statusBarHeight;
    private WindowManager.LayoutParams params;
    private RelativeLayout moveView;

    public FloatingViewEdgeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenWidth = LScreenUtil.Companion.getScreenWidth();
        screenHeight = LScreenUtil.Companion.getScreenHeight();
        statusBarHeight = LScreenUtil.Companion.getStatusBarHeight(getApplicationContext());
        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_view_edge, null);
        moveView = mFloatingView.findViewById(R.id.rlMove);
        vBkgDestroy = mFloatingView.findViewById(R.id.v_bkg_destroy);
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
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        setSizeMoveView(true, false);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = screenWidth - getMoveViewWidth();
        params.y = screenHeight - getMoveViewHeight() - statusBarHeight;//dell hieu sao phai tru getBottomBarHeight thi moi dung position :(

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        //Drag and move floating view using user's touch action.
        dragAndMove();
    }

    //only update 1 one time
    private boolean isUpdatedUIVideoSize;

    private void updateUIVideoSizeOneTime(int videoW, int videoH) {
        if (!isUpdatedUIVideoSize) {
            //LLog.d(TAG, "updateUIVideoSizeOneTime " + videoW + "x" + videoH);
            int vW = screenWidth / 2;
            int vH = vW * videoH / videoW;
            //LLog.d(TAG, "-> " + vW + "x" + vH);
            int newPosX = params.x;
            int newPosY = screenHeight - vH - statusBarHeight;//dell hieu sao phai tru getBottomBarHeight thi moi dung position :(
            updateUISlide(newPosX, newPosY);
            isUpdatedUIVideoSize = true;
        }
    }

    private CountDownTimer countDownTimer;

    private void slideToPosition(final int goToPosX, final int goToPosY) {
        final int currentPosX = params.x;
        final int currentPosY = params.y;
        //LLog.d(TAG, "slideToPosition current Point: " + currentPosX + " x " + currentPosY);
        final int a = Math.abs(goToPosX - currentPosX);
        final int b = Math.abs(goToPosY - currentPosY);
        //LLog.d(TAG, "slideToPosition " + goToPosX + " x " + goToPosY + " -> a x b: " + a + " x " + b);

        countDownTimer = new CountDownTimer(300, 3) {
            public void onTick(long t) {
                float step = (300 - t) / 3;
                int tmpX;
                int tmpY;
                if (currentPosX > goToPosX) {
                    if (currentPosY > goToPosY) {
                        tmpX = currentPosX - (int) (a * step / 100);
                        tmpY = currentPosY - (int) (b * step / 100);
                    } else {
                        tmpX = currentPosX - (int) (a * step / 100);
                        tmpY = currentPosY + (int) (b * step / 100);
                    }
                } else {
                    if (currentPosY > goToPosY) {
                        tmpX = currentPosX + (int) (a * step / 100);
                        tmpY = currentPosY - (int) (b * step / 100);
                    } else {
                        tmpX = currentPosX + (int) (a * step / 100);
                        tmpY = currentPosY + (int) (b * step / 100);
                    }
                }
                //LLog.d(TAG, "slideToLeft onTick step: " + step + ", " + tmpX + " x " + tmpY);
                updateUISlide(tmpX, tmpY);
            }

            public void onFinish() {
                //LLog.d(TAG, "slideToLeft onFinish " + goToPosX + " x " + goToPosY);
                updateUISlide(goToPosX, goToPosY);
            }
        }.start();
    }

    private void updateUISlide(int x, int y) {
        params.x = x;
        params.y = y;
        mWindowManager.updateViewLayout(mFloatingView, params);
    }

    private GestureDetector mTapDetector;

    private class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            openApp();
            return true;
        }
    }

    private void dragAndMove() {
        mTapDetector = new GestureDetector(getBaseContext(), new GestureTap());
        moveView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTapDetector.onTouchEvent(event);
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
                        onMoveUp();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //LLog.d(TAG, "ACTION_MOVE " + (int) event.getRawX() + " - " + (int) event.getRawY() + "___" + (initialX + (int) (event.getRawX() - initialTouchX) + " - " + (initialY + (int) (event.getRawY() - initialTouchY))));
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        getLocationOnScreen(mFloatingView);
                        return true;
                }
                return false;
            }
        });
    }

    private enum POS {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER_LEFT, CENTER_RIGHT, CENTER_TOP, CENTER_BOTTOM, LEFT, RIGHT, TOP, BOTTOM, CENTER}

    private POS pos;

    private void notiPos(POS tmpPos) {
        if (pos != tmpPos) {
            pos = tmpPos;
            //LLog.d(TAG, "notiPos: " + pos);
            switch (pos) {
                case TOP_LEFT:
                case TOP_RIGHT:
                case BOTTOM_LEFT:
                case BOTTOM_RIGHT:
                    LDeviceUtil.vibrate(getBaseContext());
                    vBkgDestroy.setVisibility(View.VISIBLE);
                    break;
                default:
                    if (vBkgDestroy.getVisibility() != View.GONE) {
                        vBkgDestroy.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    private void getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int posLeft = location[0];
        int posTop = location[1];
        int posRight = posLeft + view.getWidth();
        int posBottom = posTop + view.getHeight();
        int centerX = (posLeft + posRight) / 2;
        int centerY = (posTop + posBottom) / 2;
        //LLog.d(TAG, "getLocationOnScreen " + posLeft + " - " + posTop + " - " + posRight + " - " + posBottom);
        if (centerX < 0) {
            if (centerY < 0) {
                //LLog.d(TAG, "TOP_LEFT");
                notiPos(POS.TOP_LEFT);
            } else if (centerY > screenHeight) {
                //LLog.d(TAG, "BOTTOM_LEFT");
                notiPos(POS.BOTTOM_LEFT);
            } else {
                //LLog.d(TAG, "CENTER_LEFT");
                notiPos(POS.CENTER_LEFT);
            }
        } else if (centerX > screenWidth) {
            if (centerY < 0) {
                //LLog.d(TAG, "TOP_RIGHT");
                notiPos(POS.TOP_RIGHT);
            } else if (centerY > screenHeight) {
                //LLog.d(TAG, "BOTTOM_RIGHT");
                notiPos(POS.BOTTOM_RIGHT);
            } else {
                //LLog.d(TAG, "CENTER_RIGHT");
                notiPos(POS.CENTER_RIGHT);
            }
        } else {
            if (centerY < 0) {
                //LLog.d(TAG, "CENTER_TOP");
                notiPos(POS.CENTER_TOP);
            } else if (centerY > screenHeight) {
                //LLog.d(TAG, "CENTER_BOTTOM");
                notiPos(POS.CENTER_BOTTOM);
            } else {
                if (posLeft < 0) {
                    //LLog.d(TAG, "LEFT");
                    notiPos(POS.LEFT);
                } else if (posRight > screenWidth) {
                    //LLog.d(TAG, "RIGHT");
                    notiPos(POS.RIGHT);
                } else {
                    if (posTop < 0) {
                        //LLog.d(TAG, "TOP");
                        notiPos(POS.TOP);
                    } else if (posBottom > screenHeight) {
                        //LLog.d(TAG, "BOTTOM");
                        notiPos(POS.BOTTOM);
                    } else {
                        //LLog.d(TAG, "CENTER");
                        notiPos(POS.CENTER);
                    }
                }
            }
        }

    }

    private void onMoveUp() {
        //LLog.d(TAG, "onMoveUp " + pos.name());
        if (pos == null) {
            return;
        }
        int posX;
        int posY;
        int centerPosX;
        int centerPosY;
        switch (pos) {
            case TOP_LEFT:
            case TOP_RIGHT:
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
                stopSelf();
                break;
            case TOP:
            case CENTER_TOP:
                posX = params.x;
                centerPosX = posX + getMoveViewWidth() / 2;
                if (centerPosX < screenWidth / 2) {
                    slideToPosition(0, 0);
                } else {
                    slideToPosition(screenWidth - getMoveViewWidth(), 0);
                }
                break;
            case BOTTOM:
            case CENTER_BOTTOM:
                posX = params.x;
                centerPosX = posX + getMoveViewWidth() / 2;
                if (centerPosX < screenWidth / 2) {
                    slideToPosition(0, screenHeight - getMoveViewHeight() - statusBarHeight);
                } else {
                    slideToPosition(screenWidth - getMoveViewWidth(), screenHeight - getMoveViewHeight() - statusBarHeight);
                }
                break;
            case LEFT:
            case CENTER_LEFT:
                posY = params.y;
                centerPosY = posY + getMoveViewHeight() / 2;
                if (centerPosY < screenHeight / 2) {
                    slideToPosition(0, 0);
                } else {
                    slideToPosition(0, screenHeight - getMoveViewHeight() - statusBarHeight);
                }
                break;
            case RIGHT:
            case CENTER_RIGHT:
                posY = params.y;
                centerPosY = posY + getMoveViewHeight() / 2;
                if (centerPosY < screenHeight / 2) {
                    slideToPosition(screenWidth - getMoveViewWidth(), 0);
                } else {
                    slideToPosition(screenWidth - getMoveViewWidth(), screenHeight - getMoveViewHeight() - statusBarHeight);
                }
                break;
            case CENTER:
                posX = params.x;
                posY = params.y;
                centerPosX = posX + getMoveViewWidth() / 2;
                centerPosY = posY + getMoveViewHeight() / 2;
                //LLog.d(TAG, "CENTER " + centerPosX + "x" + centerPosY);
                if (centerPosX < screenWidth / 2) {
                    if (centerPosY < screenHeight / 2) {
                        //LLog.d(TAG, "top left part");
                        slideToPosition(0, 0);
                    } else {
                        //LLog.d(TAG, "bottom left part");
                        slideToPosition(0, screenHeight - getMoveViewHeight() - statusBarHeight);
                    }
                } else {
                    if (centerPosY < screenHeight / 2) {
                        //LLog.d(TAG, "top right part");
                        slideToPosition(screenWidth - getMoveViewWidth(), 0);
                    } else {
                        //LLog.d(TAG, "bottom right part");
                        slideToPosition(screenWidth - getMoveViewWidth(), screenHeight - getMoveViewHeight() - statusBarHeight);
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    private void openApp() {
        //Open the application  click.
        Intent intent = new Intent(FloatingViewEdgeService.this, FloatingWidgetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        //close the service and remove view from the view hierarchy
        stopSelf();
    }

    //click vo se larger, click lan nua de smaller
    private void setSizeMoveView(boolean isFirstSizeInit, boolean isLarger) {
        if (moveView == null) {
            return;
        }
        int w = 0;
        int h = 0;
        if (isFirstSizeInit) {
            w = screenWidth / 2;
            h = w * 9 / 16;
        } else {
            //works fine
            //OPTION 1: isLarger->mini player se to hon 1 chut
            //!isLarger->ve trang thai ban dau
            /*if (isLarger) {
                w = getMoveViewWidth() * 120 / 100;
                h = getMoveViewHeight() * 120 / 100;
            } else {
                int videoW = fuzVideo.getVideoW();
                int videoH = fuzVideo.getVideoH();
                w = screenWidth / 2;
                h = w * videoH / videoW;
            }*/
        }
        if (w != 0 && h != 0) {
            moveView.getLayoutParams().width = w;
            moveView.getLayoutParams().height = h;
            //LLog.d(TAG, "setSizeMoveView isFirstSizeInit:" + isFirstSizeInit + ",isLarger: " + isLarger + ", " + w + "x" + h);
            moveView.requestLayout();
        }
    }

    private int getMoveViewWidth() {
        if (moveView == null) {
            return 0;
        }
        return moveView.getLayoutParams().width;
    }

    private int getMoveViewHeight() {
        if (moveView == null) {
            return 0;
        }
        return moveView.getLayoutParams().height;
    }
}