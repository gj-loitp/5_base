package vn.loitp.app.activity.animation.basictransition;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LImageUtil;

public class BasicTransition0Activity extends BaseFontActivity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = (ImageView) findViewById(R.id.imageview_item);
        tv = (TextView) findViewById(R.id.tv);
        LImageUtil.load(activity, Constants.URL_IMG_2, iv);
        findViewById(R.id.imageview_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickIv();
            }
        });
    }

    private void onClickIv() {
        Intent intent = new Intent(activity, BasicTransition1Activity.class);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                new Pair<View, String>(iv, BasicTransition1Activity.IV),
                new Pair<View, String>(tv, BasicTransition1Activity.TV));
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
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
        return R.layout.activity_basic_transition_0;
    }
}
