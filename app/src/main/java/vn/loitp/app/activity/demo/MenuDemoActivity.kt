package vn.loitp.app.activity.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.GalleryCoreSplashActivity
import com.core.helper.gallery.albumonly.GalleryCorePhotosOnlyActivity
import com.core.helper.gallery.member.GalleryMemberActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_demo.*
import loitp.basemaster.R
import vn.loitp.app.activity.demo.alarmdemoapp.activity.AlarmMeActivity
import vn.loitp.app.activity.demo.butterknife.ButterKnifeActivity
import vn.loitp.app.activity.demo.deeplinks.DeepLinksActivity
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity
import vn.loitp.app.activity.demo.epubreader.EpubReaderMenuActivity
import vn.loitp.app.activity.demo.film.FilmDemoActivity
import vn.loitp.app.activity.demo.firebase.MenuFirebaseActivity
import vn.loitp.app.activity.demo.floatingvideo.FloatingWidgetActivity
import vn.loitp.app.activity.demo.floatingview.FloatingViewActivity
import vn.loitp.app.activity.demo.fragmentflow.FragmentFlowActivity
import vn.loitp.app.activity.demo.fragmentnavigation.FragmentNavigationActivity
import vn.loitp.app.activity.demo.gallery.FlicrkFrmActivity
import vn.loitp.app.activity.demo.gallery.GalleryDemoSplashActivity
import vn.loitp.app.activity.demo.pdf.PdfDemoActivity
import vn.loitp.app.activity.demo.sound.SoundActivity
import vn.loitp.app.activity.demo.texttospeech.TextToSpeechActivity
import vn.loitp.app.activity.demo.twoinstanceactivity.Activity1
import vn.loitp.app.activity.demo.video.VideoActivity
import vn.loitp.app.activity.demo.youtubeparser.YoutubeParserChannelActivity
import java.util.*

class MenuDemoActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_alarm).setOnClickListener(this)
        findViewById<View>(R.id.bt_butter_knife).setOnClickListener(this)
        findViewById<View>(R.id.bt_ebook_with_realm).setOnClickListener(this)
        findViewById<View>(R.id.bt_gallery).setOnClickListener(this)
        findViewById<View>(R.id.bt_video).setOnClickListener(this)
        findViewById<View>(R.id.bt_sound).setOnClickListener(this)
        findViewById<View>(R.id.bt_text_to_speech).setOnClickListener(this)
        findViewById<View>(R.id.bt_floating_widget).setOnClickListener(this)
        findViewById<View>(R.id.bt_floating_video).setOnClickListener(this)
        findViewById<View>(R.id.bt_floating_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_firebase).setOnClickListener(this)
        findViewById<View>(R.id.bt_film).setOnClickListener(this)
        if (Constants.IS_DEBUG) {
            findViewById<View>(R.id.bt_gallery_core).visibility = View.VISIBLE
            findViewById<View>(R.id.bt_gallery_core).setOnClickListener(this)

            findViewById<View>(R.id.bt_gallery_core_album).visibility = View.VISIBLE
            findViewById<View>(R.id.bt_gallery_core_album).setOnClickListener(this)

            findViewById<View>(R.id.bt_gallery_member).visibility = View.VISIBLE
            findViewById<View>(R.id.bt_gallery_member).setOnClickListener(this)

            findViewById<View>(R.id.bt_gallery_core_album_frm).setOnClickListener(this)
            findViewById<View>(R.id.bt_gallery_core_album_frm).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.bt_gallery_core).visibility = View.GONE
            findViewById<View>(R.id.bt_gallery_core_album).visibility = View.GONE
            findViewById<View>(R.id.bt_gallery_member).visibility = View.GONE
            findViewById<View>(R.id.bt_gallery_core_album_frm).visibility = View.GONE
        }
        findViewById<View>(R.id.bt_epub_reader).setOnClickListener(this)
        findViewById<View>(R.id.bt_2_instance_activity).setOnClickListener(this)
        findViewById<View>(R.id.bt_youtube_parser).setOnClickListener(this)
        findViewById<View>(R.id.bt_fragment_navigation).setOnClickListener(this)
        btPdf.setOnClickListener(this)
        btFragmentFlow.setOnClickListener(this)
        btDeepLinks.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_demo
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_alarm -> intent = Intent(activity, AlarmMeActivity::class.java)
            R.id.bt_butter_knife -> intent = Intent(activity, ButterKnifeActivity::class.java)
            R.id.bt_ebook_with_realm -> intent = Intent(activity, EbookWithRealmActivity::class.java)
            R.id.bt_gallery -> intent = Intent(activity, GalleryDemoSplashActivity::class.java)
            R.id.bt_video -> intent = Intent(activity, VideoActivity::class.java)
            R.id.bt_sound -> intent = Intent(activity, SoundActivity::class.java)
            R.id.bt_text_to_speech -> intent = Intent(activity, TextToSpeechActivity::class.java)
            R.id.bt_floating_widget -> intent = Intent(activity, vn.loitp.app.activity.demo.floatingwidget.FloatingWidgetActivity::class.java)
            R.id.bt_floating_video -> intent = Intent(activity, FloatingWidgetActivity::class.java)
            R.id.bt_firebase -> intent = Intent(activity, MenuFirebaseActivity::class.java)
            R.id.bt_film -> intent = Intent(activity, FilmDemoActivity::class.java)
            R.id.bt_gallery_core -> {
                intent = Intent(activity, GalleryCoreSplashActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
                intent.putExtra(Constants.BKG_SPLASH_SCREEN, "https://c2.staticflickr.com/8/7764/29782311711_0882f5b347_b.jpg")
                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.l_bkg_gradient_man_of_steel)
                //neu muon remove albumn nao thi cu pass id cua albumn do
                val removeAlbumFlickrList = ArrayList<String>()
                removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER)
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_GIRL);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_VN_BANCOBIET);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_DONGVATKHAC);
                intent.putStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST, removeAlbumFlickrList)
            }
            R.id.bt_gallery_core_album -> {
                intent = Intent(activity, GalleryCorePhotosOnlyActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.l_bkg_gradient_man_of_steel)
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_FAMOUSMANGA);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_TRUYENBUA);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_BANCOBIET);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_XE);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_PHONGCANH);
                intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_MANGA)
            }
            R.id.bt_gallery_core_album_frm -> intent = Intent(activity, FlicrkFrmActivity::class.java)
            R.id.bt_gallery_member -> {
                intent = Intent(activity, GalleryMemberActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.l_bkg_gradient_man_of_steel)
            }
            R.id.bt_epub_reader -> intent = Intent(activity, EpubReaderMenuActivity::class.java)
            R.id.bt_floating_view -> intent = Intent(activity, FloatingViewActivity::class.java)
            R.id.bt_2_instance_activity -> intent = Intent(activity, Activity1::class.java)
            R.id.bt_youtube_parser -> intent = Intent(activity, YoutubeParserChannelActivity::class.java)
            R.id.bt_fragment_navigation -> intent = Intent(activity, FragmentNavigationActivity::class.java)
            R.id.btPdf -> intent = Intent(activity, PdfDemoActivity::class.java)
            R.id.btFragmentFlow -> intent = Intent(activity, FragmentFlowActivity::class.java)
            R.id.btDeepLinks -> intent = Intent(activity, DeepLinksActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
