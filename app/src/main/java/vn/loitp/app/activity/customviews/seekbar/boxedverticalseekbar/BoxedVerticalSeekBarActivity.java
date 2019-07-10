package vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.views.seekbar.boxedverticalseekbar.BoxedVertical;

//https://github.com/alpbak/BoxedVerticalSeekBar?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6291
public class BoxedVerticalSeekBarActivity extends BaseFontActivity {
    private TextView tv;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoxedVertical bv = (BoxedVertical) findViewById(R.id.boxed_vertical);
        tv = (TextView) findViewById(R.id.tv);

        bv.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
            @Override
            public void onPointsChanged(BoxedVertical boxedPoints, final int value) {
                LLog.INSTANCE.d(TAG, "onPointsChanged " + value);
                stringList.add(0, "onPointsChanged " + value);
                print();
            }

            @Override
            public void onStartTrackingTouch(BoxedVertical boxedPoints) {
                LLog.INSTANCE.d(TAG, "onStartTrackingTouch");
                stringList.add(0, "onStartTrackingTouch");
                print();
            }

            @Override
            public void onStopTrackingTouch(BoxedVertical boxedPoints) {
                LLog.INSTANCE.d(TAG, "onStopTrackingTouch");
                stringList.add(0, "onStopTrackingTouch");
                print();
                bv.setBackgroundColor(Color.RED);
                bv.setProgressColor(Color.GREEN);
            }
        });
    }

    private String x;

    private void print() {
        if (stringList.size() > 30) {
            stringList.remove(stringList.size() - 1);
        }
        String x = "";
        for (String s : stringList) {
            x += "\n" + s;
        }
        tv.setText(x);
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
        return R.layout.activity_boxed_vertical_seekbar;
    }
}
