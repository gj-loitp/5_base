package com.function.simplefingergestures;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SimpleFingerGestures implements View.OnTouchListener {

    private boolean debug = true;
    private boolean consumeTouchEvents = false;

    // Will see if these need to be used. For now just returning duration in milliS
    public static final long GESTURE_SPEED_SLOW = 1500;
    public static final long GESTURE_SPEED_MEDIUM = 1000;
    public static final long GESTURE_SPEED_FAST = 500;
    private static final String TAG = "SimpleFingerGestures";
    protected boolean tracking[] = {false, false, false, false, false};
    private GestureAnalyser ga;
    private OnFingerGestureListener onFingerGestureListener;

    public SimpleFingerGestures() {
        ga = new GestureAnalyser();
    }

    public SimpleFingerGestures(int swipeSlopeIntolerance, int doubleTapMaxDelayMillis, int doubleTapMaxDownMillis) {
        ga = new GestureAnalyser(swipeSlopeIntolerance, doubleTapMaxDelayMillis, doubleTapMaxDownMillis);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean getConsumeTouchEvents() {
        return consumeTouchEvents;
    }

    public void setConsumeTouchEvents(boolean consumeTouchEvents) {
        this.consumeTouchEvents = consumeTouchEvents;
    }

    public void setOnFingerGestureListener(OnFingerGestureListener omfgl) {
        onFingerGestureListener = omfgl;
    }

    @Override
    public boolean onTouch(View view, MotionEvent ev) {
        if (debug) Log.d(TAG, "onTouch");
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (debug) Log.d(TAG, "ACTION_DOWN");
                startTracking(0);
                ga.trackGesture(ev);
                return consumeTouchEvents;
            case MotionEvent.ACTION_UP:
                if (debug) Log.d(TAG, "ACTION_UP");
                if (tracking[0]) {
                    doCallBack(ga.getGesture(ev));
                }
                stopTracking(0);
                ga.untrackGesture();
                return consumeTouchEvents;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (debug) Log.d(TAG, "ACTION_POINTER_DOWN" + " " + "num" + ev.getPointerCount());
                startTracking(ev.getPointerCount() - 1);
                ga.trackGesture(ev);
                return consumeTouchEvents;
            case MotionEvent.ACTION_POINTER_UP:
                if (debug) Log.d(TAG, "ACTION_POINTER_UP" + " " + "num" + ev.getPointerCount());
                if (tracking[1]) {
                    doCallBack(ga.getGesture(ev));
                }
                stopTracking(ev.getPointerCount() - 1);
                ga.untrackGesture();
                return consumeTouchEvents;
            case MotionEvent.ACTION_CANCEL:
                if (debug) Log.d(TAG, "ACTION_CANCEL");
                return true;
            case MotionEvent.ACTION_MOVE:
                if (debug) Log.d(TAG, "ACTION_MOVE");
                return consumeTouchEvents;
        }
        return consumeTouchEvents;
    }

    private void doCallBack(GestureAnalyser.GestureType mGt) {
        switch (mGt.getGestureFlag()) {
            case GestureAnalyser.SWIPE_1_UP:
                onFingerGestureListener.onSwipeUp(1, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_1_DOWN:
                onFingerGestureListener.onSwipeDown(1, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_1_LEFT:
                onFingerGestureListener.onSwipeLeft(1, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_1_RIGHT:
                onFingerGestureListener.onSwipeRight(1, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;

            case GestureAnalyser.SWIPE_2_UP:
                onFingerGestureListener.onSwipeUp(2, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_2_DOWN:
                onFingerGestureListener.onSwipeDown(2, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_2_LEFT:
                onFingerGestureListener.onSwipeLeft(2, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_2_RIGHT:
                onFingerGestureListener.onSwipeRight(2, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.PINCH_2:
                onFingerGestureListener.onPinch(2, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.UNPINCH_2:
                onFingerGestureListener.onUnpinch(2, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;

            case GestureAnalyser.SWIPE_3_UP:
                onFingerGestureListener.onSwipeUp(3, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_3_DOWN:
                onFingerGestureListener.onSwipeDown(3, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_3_LEFT:
                onFingerGestureListener.onSwipeLeft(3, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_3_RIGHT:
                onFingerGestureListener.onSwipeRight(3, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.PINCH_3:
                onFingerGestureListener.onPinch(3, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.UNPINCH_3:
                onFingerGestureListener.onUnpinch(3, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;

            case GestureAnalyser.SWIPE_4_UP:
                onFingerGestureListener.onSwipeUp(4, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_4_DOWN:
                onFingerGestureListener.onSwipeDown(4, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_4_LEFT:
                onFingerGestureListener.onSwipeLeft(4, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.SWIPE_4_RIGHT:
                onFingerGestureListener.onSwipeRight(4, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.PINCH_4:
                onFingerGestureListener.onPinch(4, mGt.getGestureDuration(), mGt.getGestureDistance());
                break;
            case GestureAnalyser.UNPINCH_4:
                onFingerGestureListener.onUnpinch(4, mGt.getGestureDuration(), mGt.getGestureDistance());
            case GestureAnalyser.DOUBLE_TAP_1:
                onFingerGestureListener.onDoubleTap(1);
                break;
        }
    }

    private void startTracking(int nthPointer) {
        for (int i = 0; i <= nthPointer; i++) {
            tracking[i] = true;
        }
    }

    private void stopTracking(int nthPointer) {
        for (int i = nthPointer; i < tracking.length; i++) {
            tracking[i] = false;
        }
    }

    public interface OnFingerGestureListener {

        public boolean onSwipeUp(int fingers, long gestureDuration, double gestureDistance);

        public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance);

        public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance);

        public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance);

        public boolean onPinch(int fingers, long gestureDuration, double gestureDistance);

        public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance);

        public boolean onDoubleTap(int fingers);
    }
}