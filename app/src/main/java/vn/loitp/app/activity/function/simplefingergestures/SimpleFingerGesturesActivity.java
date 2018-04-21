package vn.loitp.app.activity.function.simplefingergestures;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.function.simplefingergestures.SimpleFingerGestures;

public class SimpleFingerGesturesActivity extends BaseActivity {
    private SimpleFingerGestures mySfg = new SimpleFingerGestures();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView mv = (ImageView) findViewById(R.id.myview);
        final TextView grtv = (TextView) findViewById(R.id.tv);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        SimpleFingerGestures sfg = new SimpleFingerGestures();
        sfg.setDebug(true);
        sfg.setConsumeTouchEvents(true);

        sfg.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int fingers, long gestureDuration, double gestureDistance) {
                grtv.setText("You swiped " + fingers + " fingers  up " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance) {
                grtv.setText("You swiped " + fingers + " fingers  down " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance) {
                grtv.setText("You swiped " + fingers + " fingers  left " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance) {
                grtv.setText("You swiped " + fingers + " fingers  right " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onPinch(int fingers, long gestureDuration, double gestureDistance) {
                grtv.setText("You pinched " + fingers + " fingers " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance) {
                grtv.setText("You unpinched " + fingers + "fingers" + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onDoubleTap(int fingers) {
                grtv.setText("You double tapped");
                return false;
            }
        });
        mv.setOnTouchListener(sfg);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_simple_finger_gestures;
    }
}
