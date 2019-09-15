package vn.loitp.app.activity.customviews.answerview;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.core.base.BaseFontActivity;
import com.views.LToast;
import com.views.answerview.LAnswerView;

import loitp.basemaster.R;

public class AnswerViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //use xml
        useXML();

        //use JAVA codes
        useJava();
    }

    private void useXML() {
        LAnswerView LAnswerView1 = (LAnswerView) findViewById(R.id.av_1);
        LAnswerView1.setNumber(1);
        LAnswerView1.setOnAnswerChange(new LAnswerView.OnAnswerChange() {
            @Override
            public void onAnswerChange(LAnswerView view, int index) {
                LToast.INSTANCE.show(getActivity(), "Click: " + index);
            }
        });
        LAnswerView1.setActiveChar('A');
        //answerView1.resize(2);

        LAnswerView LAnswerView2 = (LAnswerView) findViewById(R.id.av_2);
        LAnswerView2.setNumber(2);
        LAnswerView2.setOnAnswerChange(new LAnswerView.OnAnswerChange() {
            @Override
            public void onAnswerChange(LAnswerView view, int index) {
                LToast.INSTANCE.show(getActivity(), "Click: " + index);
            }
        });
    }

    private void useJava() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        for (int i = 0; i < 10; i++) {
            LAnswerView LAnswerView = new LAnswerView(getActivity());
            LAnswerView.init(i + 3, 6, true, true, true, true);
            LAnswerView.setOnAnswerChange(new LAnswerView.OnAnswerChange() {
                @Override
                public void onAnswerChange(LAnswerView view, int index) {
                    LToast.INSTANCE.show(getActivity(), "Click: " + index);
                }
            });
            ll.addView(LAnswerView);
        }
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
        return R.layout.activity_answer_view;
    }
}
