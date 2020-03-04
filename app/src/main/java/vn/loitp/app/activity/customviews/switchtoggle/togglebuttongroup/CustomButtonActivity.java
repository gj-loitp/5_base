package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import vn.loitp.app.R;

public class CustomButtonActivity extends AppCompatActivity {
    private static final String LOG_TAG = CustomButtonActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_button);
        init();
    }

    private void init() {
        SingleSelectToggleGroup singleRadioButton = findViewById(R.id.group_single_radiobutton);
        singleRadioButton.setOnCheckedChangeListener(new SingleSelectListener());

        MultiSelectToggleGroup multiCustomCompoundButton =
                findViewById(R.id.group_multi_custom_compoundbutton);
        multiCustomCompoundButton.setOnCheckedChangeListener(new MultiSelectListener());

        MultiSelectToggleGroup multiCustomToggleButton =
                findViewById(R.id.group_multi_custom_togglebutton);
        multiCustomToggleButton.setOnCheckedChangeListener(new MultiSelectListener());

        SingleSelectToggleGroup singleCompoundToggleButton =
                findViewById(R.id.group_single_custom_compoundtogglebutton);
        singleCompoundToggleButton.setOnCheckedChangeListener(new SingleSelectListener());
    }

    private static class SingleSelectListener implements SingleSelectToggleGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
            Log.v(LOG_TAG, "onCheckedChanged(): " + checkedId);
        }
    }

    private static class MultiSelectListener implements MultiSelectToggleGroup.OnCheckedStateChangeListener {
        @Override
        public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
            Log.v(LOG_TAG, "onCheckedStateChanged(): " + checkedId + ", isChecked = " + isChecked);
        }
    }

}
