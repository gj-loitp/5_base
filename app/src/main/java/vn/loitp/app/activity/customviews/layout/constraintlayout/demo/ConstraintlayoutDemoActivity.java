package vn.loitp.app.activity.customviews.layout.constraintlayout.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;

public class ConstraintlayoutDemoActivity extends BaseFontActivity {
    private Button button;
    private Button bt0;
    private Button bt1;
    private Button bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);button = (Button) findViewById(R.id.button);
        bt0 = (Button) findViewById(R.id.bt_0);
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.GONE);
            }
        });
        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt2.setVisibility(View.GONE);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt2.setVisibility(View.VISIBLE);
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
        return R.layout.activity_constraintlayout_demo;
    }
}
