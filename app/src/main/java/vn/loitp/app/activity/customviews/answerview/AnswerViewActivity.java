package vn.loitp.app.activity.customviews.answerview;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.answerview.AnswerView;

public class AnswerViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_answer_view;
    }
}
