package vn.loitp.app.activity.customviews.textview.selectabletextView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.core.base.BaseFontActivity;
import com.views.textview.selectable.SelectableListener;
import com.views.textview.selectable.LSelectableView;

import loitp.basemaster.R;

public class SelectableTextViewActivity extends BaseFontActivity {
    private TextView emptyBoxBtn;
    private LSelectableView LSelectableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LSelectableView = findViewById(R.id.selectableView);
        LSelectableView.setActivity(this);
        LSelectableView.setText(getString(R.string.i_love_you));
        LSelectableView.addOnSaveClickListener(text -> Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show());

        emptyBoxBtn = findViewById(R.id.emptyBoxBtn);
        emptyBoxBtn.setTag(0);
        emptyBoxBtn.setOnClickListener(view -> {
            int type = (int) view.getTag();
            if (type == 0) {
                emptyBoxBtn.setBackgroundResource(R.drawable.selector_cancel_btn);
                LSelectableView.selectAll();
                emptyBoxBtn.setTag(1);
            } else {
                emptyBoxBtn.setBackgroundResource(R.drawable.selector_emptybox);
                LSelectableView.hideCursor();
                emptyBoxBtn.setTag(0);
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
        return R.layout.activity_selectable_textview;
    }

}
