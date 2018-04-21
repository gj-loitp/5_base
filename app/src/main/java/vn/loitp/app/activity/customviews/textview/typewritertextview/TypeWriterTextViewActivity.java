package vn.loitp.app.activity.customviews.textview.typewritertextview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.textview.typewritertextview.lib.TypeWriterTextView;

public class TypeWriterTextViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = (Button) findViewById(R.id.btn);
        TypeWriterTextView tv = (TypeWriterTextView) findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                tv.setCharacterDelay(150);
                tv.animateText("Type Writer Effect");
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
        return R.layout.activity_type_writer_textview;
    }

}
