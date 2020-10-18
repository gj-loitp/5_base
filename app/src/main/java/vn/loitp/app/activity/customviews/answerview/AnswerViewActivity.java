package vn.loitp.app.activity.customviews.answerview;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.views.LToast;
import com.views.answerview.LAnswerView;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_answer_view)
@LogTag("AnswerViewActivity")
@IsFullScreen(false)
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
        LAnswerView LAnswerView1 = findViewById(R.id.av_1);
        LAnswerView1.setNumber(1);
        LAnswerView1.setOnAnswerChange((view, index) -> LToast.INSTANCE.showShortInformation("Click: " + index, true));
        LAnswerView1.setActiveChar('A');
        //answerView1.resize(2);

        LAnswerView LAnswerView2 = findViewById(R.id.av_2);
        LAnswerView2.setNumber(2);
        LAnswerView2.setOnAnswerChange((view, index) -> LToast.INSTANCE.showShortInformation("Click: " + index, true));
    }

    private void useJava() {
        LinearLayout ll = findViewById(R.id.ll);
        for (int i = 0; i < 10; i++) {
            LAnswerView LAnswerView = new LAnswerView(this);
            LAnswerView.init(i + 3, 6, true, true, true, true);
            LAnswerView.setOnAnswerChange((view, index) -> LToast.INSTANCE.showShortInformation("Click: " + index, true));
            ll.addView(LAnswerView);
        }
    }

}
