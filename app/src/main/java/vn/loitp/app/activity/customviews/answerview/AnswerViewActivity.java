package vn.loitp.app.activity.customviews.answerview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.answerview.AnswerView;

public class AnswerViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //use xml
        useXML();

        //use JAVA codes
        useJava();
    }

    private void useXML() {
        AnswerView answerView1 = (AnswerView) findViewById(R.id.av_1);
        answerView1.setNumber(1);
        answerView1.setOnAnswerChange(new AnswerView.OnAnswerChange() {
            @Override
            public void onAnswerChange(AnswerView view, int index) {
                LToast.show(activity, "Click: " + index);
            }
        });
        answerView1.setActiveChar('A');
        //answerView1.resize(2);

        AnswerView answerView2 = (AnswerView) findViewById(R.id.av_2);
        answerView2.setNumber(2);
        answerView2.setOnAnswerChange(new AnswerView.OnAnswerChange() {
            @Override
            public void onAnswerChange(AnswerView view, int index) {
                LToast.show(activity, "Click: " + index);
            }
        });
    }

    private void useJava() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        for (int i = 0; i < 10; i++) {
            AnswerView answerView = new AnswerView(activity);
            answerView.init(i + 3, 6, true, true, true, true);
            answerView.setOnAnswerChange(new AnswerView.OnAnswerChange() {
                @Override
                public void onAnswerChange(AnswerView view, int index) {
                    LToast.show(activity, "Click: " + index);
                }
            });
            ll.addView(answerView);
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
