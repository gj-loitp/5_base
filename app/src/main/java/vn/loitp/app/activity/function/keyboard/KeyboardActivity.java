package vn.loitp.app.activity.function.keyboard;

import android.os.Bundle;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;

//https://github.com/ParkSangGwon/TedKeyboardObserver
public class KeyboardActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_keyboard;
    }

}
