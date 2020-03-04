package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;

import vn.loitp.app.R;

public class MultiSelectActivity extends AppCompatActivity {
    private static final String LOG_TAG = MultiSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select);
        init();
    }

    private void init() {
        MultiSelectToggleGroup multi = findViewById(R.id.group_weekdays);
        multi.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                Log.v(LOG_TAG, "onCheckedStateChanged(): group.getCheckedIds() = " + group.getCheckedIds());
            }
        });
    }
}
