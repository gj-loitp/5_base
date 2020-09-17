package vn.loitp.app.activity.demo.firebase.admob;

import android.os.Bundle;

import com.annotation.LayoutId;
import com.core.base.BaseFontActivity;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_firebase_admob2)
public class FirebaseAdmobActivity2 extends BaseFontActivity {

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

}
