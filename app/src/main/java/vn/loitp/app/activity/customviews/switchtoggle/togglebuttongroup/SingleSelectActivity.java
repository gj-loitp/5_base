package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import vn.loitp.app.R;

public class SingleSelectActivity extends AppCompatActivity {
    private static final String LOG_TAG = SingleSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select);
        init();
    }

    private void init() {
        SingleSelectToggleGroup single = findViewById(R.id.group_choices);
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                Log.v(LOG_TAG, "onCheckedChanged(): checkedId = " + checkedId);
            }
        });
    }
}
