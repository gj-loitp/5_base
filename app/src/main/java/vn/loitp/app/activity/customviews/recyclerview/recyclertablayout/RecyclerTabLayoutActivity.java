package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.autoselect.DemoAutoSelectActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.basic.DemoBasicActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview01.DemoCustomView01Activity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02.DemoCustomView02Activity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.imitationloop.DemoImitationLoopActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.rtl.DemoRtlActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabonscreenlimit.DemoTabOnScreenLimitActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabscrolldisabled.DemoTabScrollDisabledActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years.DemoYearsActivity;

public class RecyclerTabLayoutActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tablayout_menu);

        ListView listView = findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        for (Demo demo : Demo.values()) {
            adapter.add(getString(demo.titleResId));
        }

        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Demo demo = Demo.values()[position];
        switch (demo) {
            case BASIC:
                DemoBasicActivity.startActivity(this, demo);
                break;

            case AUTO_SELECT:
                DemoAutoSelectActivity.startActivity(this, demo);
                break;

            case CUSTOM_VIEW01:
                DemoCustomView01Activity.startActivity(this, demo);
                break;

            case CUSTOM_VIEW02:
                DemoCustomView02Activity.startActivity(this, demo);
                break;

            case YEARS:
                DemoYearsActivity.startActivity(this, demo);
                break;

            case IMITATION_LOOP:
                DemoImitationLoopActivity.startActivity(this, demo);
                break;

            case RTL:
                DemoRtlActivity.startActivity(this, demo);
                break;

            case TAB_SCROLL_DISABLED:
                DemoTabScrollDisabledActivity.startActivity(this, demo);
                break;

            case TAB_ON_SCREEN_LIMIT:
                DemoTabOnScreenLimitActivity.startActivity(this, demo);
                break;
        }
    }
}
