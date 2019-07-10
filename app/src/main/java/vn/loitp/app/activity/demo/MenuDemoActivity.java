package vn.loitp.app.activity.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.GalleryCoreSplashActivity;
import com.core.loitp.gallery.albumonly.GalleryCorePhotosOnlyActivity;
import com.core.loitp.gallery.member.GalleryMemberActivity;
import com.core.utilities.LActivityUtil;

import java.util.ArrayList;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.alarmdemoapp.activity.AlarmMeActivity;
import vn.loitp.app.activity.demo.butterknife.ButterKnifeActivity;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.app.activity.demo.epubreader.EpubReaderMenuActivity;
import vn.loitp.app.activity.demo.film.FilmDemoActivity;
import vn.loitp.app.activity.demo.firebase.MenuFirebaseActivity;
import vn.loitp.app.activity.demo.floatingvideo.FloatingWidgetActivity;
import vn.loitp.app.activity.demo.floatingview.FloatingViewActivity;
import vn.loitp.app.activity.demo.fragmentnavigation.FragmentNavigationActivity;
import vn.loitp.app.activity.demo.gallery.FlicrkFrmActivity;
import vn.loitp.app.activity.demo.gallery.GalleryDemoSplashActivity;
import vn.loitp.app.activity.demo.sound.SoundActivity;
import vn.loitp.app.activity.demo.texttospeech.TextToSpeechActivity;
import vn.loitp.app.activity.demo.twoinstanceactivity.Activity1;
import vn.loitp.app.activity.demo.video.VideoActivity;
import vn.loitp.app.activity.demo.youtubeparser.YoutubeParserChannelActivity;

public class MenuDemoActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_alarm).setOnClickListener(this);
        findViewById(R.id.bt_butter_knife).setOnClickListener(this);
        findViewById(R.id.bt_ebook_with_realm).setOnClickListener(this);
        findViewById(R.id.bt_gallery).setOnClickListener(this);
        findViewById(R.id.bt_video).setOnClickListener(this);
        findViewById(R.id.bt_sound).setOnClickListener(this);
        findViewById(R.id.bt_text_to_speech).setOnClickListener(this);
        findViewById(R.id.bt_floating_widget).setOnClickListener(this);
        findViewById(R.id.bt_floating_video).setOnClickListener(this);
        findViewById(R.id.bt_floating_view).setOnClickListener(this);
        findViewById(R.id.bt_firebase).setOnClickListener(this);
        findViewById(R.id.bt_film).setOnClickListener(this);
        if (Constants.INSTANCE.getIS_DEBUG()) {
            findViewById(R.id.bt_gallery_core).setVisibility(View.VISIBLE);
            findViewById(R.id.bt_gallery_core).setOnClickListener(this);

            findViewById(R.id.bt_gallery_core_album).setVisibility(View.VISIBLE);
            findViewById(R.id.bt_gallery_core_album).setOnClickListener(this);

            findViewById(R.id.bt_gallery_member).setVisibility(View.VISIBLE);
            findViewById(R.id.bt_gallery_member).setOnClickListener(this);

            findViewById(R.id.bt_gallery_core_album_frm).setOnClickListener(this);
            findViewById(R.id.bt_gallery_core_album_frm).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.bt_gallery_core).setVisibility(View.GONE);
            findViewById(R.id.bt_gallery_core_album).setVisibility(View.GONE);
            findViewById(R.id.bt_gallery_member).setVisibility(View.GONE);
            findViewById(R.id.bt_gallery_core_album_frm).setVisibility(View.GONE);
        }
        findViewById(R.id.bt_epub_reader).setOnClickListener(this);
        findViewById(R.id.bt_2_instance_activity).setOnClickListener(this);
        findViewById(R.id.bt_youtube_parser).setOnClickListener(this);
        findViewById(R.id.bt_fragment_navigation).setOnClickListener(this);
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
            case R.id.bt_butter_knife:
                intent = new Intent(activity, ButterKnifeActivity.class);
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
                intent = new Intent(activity, vn.loitp.app.activity.demo.floatingwidget.FloatingWidgetActivity.class);
                break;
            case R.id.bt_floating_video:
                intent = new Intent(activity, FloatingWidgetActivity.class);
                break;
            case R.id.bt_firebase:
                intent = new Intent(activity, MenuFirebaseActivity.class);
                break;
            case R.id.bt_film:
                intent = new Intent(activity, FilmDemoActivity.class);
                break;
            case R.id.bt_gallery_core:
                intent = new Intent(activity, GalleryCoreSplashActivity.class);
                intent.putExtra(Constants.getAD_UNIT_ID_BANNER(), getString(R.string.str_b));
                intent.putExtra(Constants.INSTANCE.getBKG_SPLASH_SCREEN(), "https://c2.staticflickr.com/8/7764/29782311711_0882f5b347_b.jpg");
                intent.putExtra(Constants.getBKG_ROOT_VIEW(), R.drawable.bkg_gradient_man_of_steel);
                //neu muon remove albumn nao thi cu pass id cua albumn do
                ArrayList<String> removeAlbumFlickrList = new ArrayList<>();
                removeAlbumFlickrList.add(Constants.INSTANCE.getFLICKR_ID_STICKER());
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_GIRL);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_VN_BANCOBIET);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_DONGVATKHAC);
                intent.putStringArrayListExtra(Constants.getKEY_REMOVE_ALBUM_FLICKR_LIST(), removeAlbumFlickrList);
                break;
            case R.id.bt_gallery_core_album:
                intent = new Intent(activity, GalleryCorePhotosOnlyActivity.class);
                intent.putExtra(Constants.getAD_UNIT_ID_BANNER(), getString(R.string.str_b));
                intent.putExtra(Constants.getBKG_ROOT_VIEW(), R.drawable.bkg_gradient_man_of_steel);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_FAMOUSMANGA);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_TRUYENBUA);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_BANCOBIET);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_XE);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_PHONGCANH);
                intent.putExtra(Constants.getSK_PHOTOSET_ID(), Constants.INSTANCE.getFLICKR_ID_MANGA());
                break;
            case R.id.bt_gallery_core_album_frm:
                intent = new Intent(activity, FlicrkFrmActivity.class);
                break;
            case R.id.bt_gallery_member:
                intent = new Intent(activity, GalleryMemberActivity.class);
                intent.putExtra(Constants.getAD_UNIT_ID_BANNER(), getString(R.string.str_b));
                intent.putExtra(Constants.getBKG_ROOT_VIEW(), R.drawable.bkg_gradient_man_of_steel);
                break;
            case R.id.bt_epub_reader:
                intent = new Intent(activity, EpubReaderMenuActivity.class);
                break;
            case R.id.bt_floating_view:
                intent = new Intent(activity, FloatingViewActivity.class);
                break;
            case R.id.bt_2_instance_activity:
                intent = new Intent(activity, Activity1.class);
                break;
            case R.id.bt_youtube_parser:
                intent = new Intent(activity, YoutubeParserChannelActivity.class);
                break;
            case R.id.bt_fragment_navigation:
                intent = new Intent(activity, FragmentNavigationActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
