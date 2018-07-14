package vn.loitp.app.activity.demo;

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

public class MenuDemoActivity extends BaseFontActivity implements View.OnClickListener {

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
        findViewById(R.id.bt_firebase).setOnClickListener(this);
        if (Constants.IS_DEBUG) {
            findViewById(R.id.bt_gallery_core).setVisibility(View.VISIBLE);
            findViewById(R.id.bt_gallery_core).setOnClickListener(this);

            findViewById(R.id.bt_gallery_core_album).setVisibility(View.VISIBLE);
            findViewById(R.id.bt_gallery_core_album).setOnClickListener(this);
        } else {
            findViewById(R.id.bt_gallery_core).setVisibility(View.GONE);
            findViewById(R.id.bt_gallery_core_album).setVisibility(View.GONE);
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
            case R.id.bt_firebase:
                intent = new Intent(activity, MenuFirebaseActivity.class);
                break;
            case R.id.bt_gallery_core:
                intent = new Intent(activity, GalleryCoreSplashActivity.class);
                //neu muon remove albumn nao thi cu pass id cua albumn do
                ArrayList<String> removeAlbumFlickrList = new ArrayList<>();
                removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_GIRL);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_VN_BANCOBIET);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_DONGVATKHAC);
                intent.putStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST, removeAlbumFlickrList);
                break;
            case R.id.bt_gallery_core_album:
                intent = new Intent(activity, GalleryCorePhotosOnlyActivity.class);
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b));
                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.bkg_gradient_man_of_steel);
                //TODO
                intent.putExtra(Constants.SK_PHOTOSET_ID, "72157669352081793");
                //intent.putExtra(Constants.SK_PHOTOSET_SIZE, 676);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
