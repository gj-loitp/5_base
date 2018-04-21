package vn.loitp.app.activity.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.alarmdemoapp.activity.AlarmMeActivity;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.app.activity.demo.floatingvideo.FloatingWidgetVideoActivity;
import vn.loitp.app.activity.demo.floatingwidget.FloatingWidgetActivity;
import vn.loitp.app.activity.demo.gallery.GalleryDemoSplashActivity;
import vn.loitp.app.activity.demo.sound.SoundActivity;
import vn.loitp.app.activity.demo.texttospeech.TextToSpeechActivity;
import vn.loitp.app.activity.demo.video.VideoActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class MenuDemoActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_alarm).setOnClickListener(this);
        findViewById(R.id.bt_ebook_with_realm).setOnClickListener(this);
        findViewById(R.id.bt_gallery).setOnClickListener(this);
        findViewById(R.id.bt_video).setOnClickListener(this);
        findViewById(R.id.bt_sound).setOnClickListener(this);
        findViewById(R.id.bt_text_to_speech).setOnClickListener(this);
        findViewById(R.id.bt_floating_widget).setOnClickListener(this);
        findViewById(R.id.bt_floating_video).setOnClickListener(this);
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
        return R.layout.activity_menu_demo;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_alarm:
                intent = new Intent(activity, AlarmMeActivity.class);
                break;
            case R.id.bt_ebook_with_realm:
                intent = new Intent(activity, EbookWithRealmActivity.class);
                break;
            case R.id.bt_gallery:
                intent = new Intent(activity, GalleryDemoSplashActivity.class);
                break;
            case R.id.bt_video:
                intent = new Intent(activity, VideoActivity.class);
                break;
            case R.id.bt_sound:
                intent = new Intent(activity, SoundActivity.class);
                break;
            case R.id.bt_text_to_speech:
                intent = new Intent(activity, TextToSpeechActivity.class);
                break;
            case R.id.bt_floating_widget:
                intent = new Intent(activity, FloatingWidgetActivity.class);
                break;
            case R.id.bt_floating_video:
                intent = new Intent(activity, FloatingWidgetVideoActivity.class);
                break;

        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
