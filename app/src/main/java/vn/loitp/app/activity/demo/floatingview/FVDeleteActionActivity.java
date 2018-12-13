package vn.loitp.app.activity.demo.floatingview;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.floatingview.fragment.FVDeleteActionFragment;

public class FVDeleteActionActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG_DELETE_ACTION = "delete_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_action);

        if (savedInstanceState == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, FVDeleteActionFragment.newInstance(), FRAGMENT_TAG_DELETE_ACTION);
            ft.commit();
        }

    }
}