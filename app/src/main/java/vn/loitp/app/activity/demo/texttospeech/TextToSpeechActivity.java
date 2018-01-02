package vn.loitp.app.activity.demo.texttospeech;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LTextToSpeechUtil;

public class TextToSpeechActivity extends BaseActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LTextToSpeechUtil.getInstance().setupTTS(this);
        findViewById(R.id.bt_i_love_you).setOnClickListener(this);
        findViewById(R.id.bt_i_you_love_me).setOnClickListener(this);
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
        return R.layout.activity_text_to_speech;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_i_love_you:
                LTextToSpeechUtil.getInstance().speakOut("I love you");
                break;
            case R.id.bt_i_you_love_me:
                LTextToSpeechUtil.getInstance().speakOut("You love me");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        LTextToSpeechUtil.getInstance().destroy();
        super.onDestroy();
    }
}
