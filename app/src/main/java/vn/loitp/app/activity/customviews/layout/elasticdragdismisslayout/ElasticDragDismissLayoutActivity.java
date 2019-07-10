package vn.loitp.app.activity.customviews.layout.elasticdragdismisslayout;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;
import vn.loitp.views.layout.elasticdragdismisslayout.ElasticDragDismissCallback;
import vn.loitp.views.layout.elasticdragdismisslayout.ElasticDragDismissLinearLayout;

//https://github.com/Commit451/ElasticDragDismissLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3098
public class ElasticDragDismissLayoutActivity extends BaseFontActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        ElasticDragDismissLinearLayout elasticDragDismissLinearLayout = (ElasticDragDismissLinearLayout) findViewById(R.id.draggable_frame);
        elasticDragDismissLinearLayout.addListener(new ElasticDragDismissCallback() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
                tv.setText("onDrag " + elasticOffset + " - " + elasticOffsetPixels + " - " + rawOffset + " - " + rawOffsetPixels);
            }

            @Override
            public void onDragDismissed() {
                tv.setText("onDragDismissed");

                //if you are targeting 21+ you might want to finish after transition
                finish();
            }
        });
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
        return R.layout.activity_elasticdragdismisslayout;
    }
}
