package vn.loitp.app.activity.customviews.textview.selectabletextView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.core.base.BaseFontActivity;
import com.views.textview.selectabletextview.SelectableListener;
import com.views.textview.selectabletextview.SelectableView;

import loitp.basemaster.R;

public class SelectableTextViewActivity extends BaseFontActivity {
    private TextView emptyBoxBtn;
    private SelectableView selectableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectableView = (SelectableView) findViewById(R.id.selectableView);
        selectableView.setActivity(this);
        selectableView.setText(getString(R.string.i_love_you));
        selectableView.addOnSaveClickListener(new SelectableListener() {
            @Override
            public void selectedText(String text) {
                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
            }
        });

        emptyBoxBtn = (TextView) findViewById(R.id.emptyBoxBtn);
        emptyBoxBtn.setTag(0);
        emptyBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type = (int) view.getTag();
                if (type == 0) {
                    emptyBoxBtn.setBackgroundResource(R.drawable.selector_cancel_btn);
                    selectableView.selectAll();
                    emptyBoxBtn.setTag(1);
                } else {
                    emptyBoxBtn.setBackgroundResource(R.drawable.selector_emptybox);
                    selectableView.hideCursor();
                    emptyBoxBtn.setTag(0);
                }
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
