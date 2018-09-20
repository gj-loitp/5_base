package vn.loitp.app.activity.animation.basictransition;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

//https://github.com/googlesamples/android-BasicTransition/#readme
public class BasicTransitionActivity extends BaseFontActivity {
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BasicTransitionFragment fragment = new BasicTransitionFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
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
        return R.layout.activity_basic_transition;
    }
}
