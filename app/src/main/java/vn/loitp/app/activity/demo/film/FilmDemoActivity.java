package vn.loitp.app.activity.demo.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.alarmdemoapp.activity.AlarmMeActivity;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.app.activity.demo.firebase.MenuFirebaseActivity;
import vn.loitp.app.activity.demo.floatingvideo.FloatingWidgetVideoActivity;
import vn.loitp.app.activity.demo.floatingwidget.FloatingWidgetActivity;
import vn.loitp.app.activity.demo.gallery.GalleryDemoSplashActivity;
import vn.loitp.app.activity.demo.sound.SoundActivity;
import vn.loitp.app.activity.demo.texttospeech.TextToSpeechActivity;
import vn.loitp.app.activity.demo.video.VideoActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.GalleryCoreSplashActivity;
import vn.loitp.core.loitp.gallery.albumonly.GalleryCorePhotosOnlyActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class FilmDemoActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_film_demo;
    }
}
