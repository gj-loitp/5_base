package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.util.AttributeSet;

import com.dd.CircularProgressButton;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 1/5/2016
 */
public class LSProgressButton extends CircularProgressButton {

    public static final int ERROR = -1;
    public static final int IDLE = 0;
    public static final int LOADING = 1;

    public static final int ERROR_TIMEOUT = 3000;

    private int currentState;

    public LSProgressButton(Context context) {
        super(context);
        init();
    }

    public LSProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LSProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setIndeterminateProgressMode(true);
    }

    public void setState(int state) {
        switch (state) {
            case ERROR:
                setProgress(-1);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetError();
                    }
                }, ERROR_TIMEOUT);
                break;

            case IDLE:
                setProgress(0);
                break;

            case LOADING:
                setProgress(50);
                break;
        }
        currentState = state;
    }

    public int gettState() {
        return currentState;
    }

    private void resetError() {
        if (currentState == ERROR) {
            setState(IDLE);
        }
    }
}
