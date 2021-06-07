package vn.loitp.app.activity.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.BuildConfig
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.GalleryCoreSplashActivity
import com.core.helper.gallery.albumonly.GalleryCorePhotosOnlyActivity
import com.core.helper.gallery.member.GalleryMemberActivity
import com.core.helper.mup.comic.ui.activity.ComicSplashActivity
import com.core.helper.mup.girl.ui.GirlSplashActivity
import com.core.helper.ttt.ui.a.TTTSplashActivity
import com.core.utilities.LActivityUtil
import com.game.findnumber.ui.SplashActivity
import kotlinx.android.synthetic.main.activity_demo_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architecturecomponent.MenuAndroidArchitectureComponentActivity
import vn.loitp.app.activity.demo.deeplinks.DeepLinksActivity
import vn.loitp.app.activity.demo.epubreader.EpubReaderMenuActivity
import vn.loitp.app.activity.demo.firebase.MenuFirebaseActivity
import vn.loitp.app.activity.demo.floatingvideo.FloatingWidgetActivity
import vn.loitp.app.activity.demo.fragmentflow.FragmentFlowActivity
import vn.loitp.app.activity.demo.fragmentnavigation.FragmentNavigationActivity
import vn.loitp.app.activity.demo.gallerycorealbumfrm.GalleryCoreAlbumFrmActivity
import vn.loitp.app.activity.demo.maptracker.MapTrackerActivity
import vn.loitp.app.activity.demo.nfc.NFCActivity
import vn.loitp.app.activity.demo.pdf.PdfDemoActivity
import vn.loitp.app.activity.demo.rss.RSSActivity
import vn.loitp.app.activity.demo.sound.SoundActivity
import vn.loitp.app.activity.demo.texttospeech.TextToSpeechActivity
import vn.loitp.app.activity.demo.twoinstanceactivity.Activity1
import vn.loitp.app.activity.demo.video.VideoActivity

@LogTag("MenuDemoActivity")
@IsFullScreen(false)
class MenuDemoActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        if (BuildConfig.DEBUG) {
            btGalleryCore.visibility = View.VISIBLE
            btGalleryCoreAlbum.visibility = View.VISIBLE
            btGalleryMember.visibility = View.VISIBLE
            btGalleryCoreAlbumFrm.visibility = View.VISIBLE
            btEpubReader.visibility = View.VISIBLE
            btGirl.visibility = View.VISIBLE
            btComic.visibility = View.VISIBLE
            btTTT.visibility = View.VISIBLE
        } else {
            btGalleryCore.visibility = View.GONE
            btGalleryCoreAlbum.visibility = View.GONE
            btGalleryMember.visibility = View.GONE
            btGalleryCoreAlbumFrm.visibility = View.GONE
            btEpubReader.visibility = View.GONE
            btGirl.visibility = View.GONE
            btComic.visibility = View.GONE
            btTTT.visibility = View.GONE
        }

        btVideo.setOnClickListener(this)
        btSound.setOnClickListener(this)
        btTextToSpeech.setOnClickListener(this)
        btFloatingWidget.setOnClickListener(this)
        btFloatingVideo.setOnClickListener(this)
        btFirebase.setOnClickListener(this)
        btGalleryCore.setOnClickListener(this)
        btGalleryCoreAlbum.setOnClickListener(this)
        btGalleryMember.setOnClickListener(this)
        btGalleryCoreAlbumFrm.setOnClickListener(this)
        btEpubReader.setOnClickListener(this)
        bt2InstanceActivity.setOnClickListener(this)
        btFragmentNavigation.setOnClickListener(this)
        btPdf.setOnClickListener(this)
        btFragmentFlow.setOnClickListener(this)
        btDeepLinks.setOnClickListener(this)
        btArchitectureComponent.setOnClickListener(this)
        btNFC.setOnClickListener(this)
        btGirl.setOnClickListener(this)
        btMapTracker.setOnClickListener(this)
        btComic.setOnClickListener(this)
        btFindNumber.setOnClickListener(this)
        btTTT.setOnClickListener(this)
        btRSS.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btVideo -> intent = Intent(this, VideoActivity::class.java)
            btSound -> intent = Intent(this, SoundActivity::class.java)
            btTextToSpeech -> intent = Intent(this, TextToSpeechActivity::class.java)
            btFloatingWidget -> intent = Intent(
                this,
                vn.loitp.app.activity.demo.floatingwidget.FloatingWidgetActivity::class.java
            )
            btFloatingVideo -> intent = Intent(this, FloatingWidgetActivity::class.java)
            btFirebase -> intent = Intent(this, MenuFirebaseActivity::class.java)
            btGalleryCore -> {
                intent = Intent(this, GalleryCoreSplashActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
                intent.putExtra(Constants.BKG_SPLASH_SCREEN, Constants.URL_IMG_11)
                //neu muon remove albumn nao thi cu pass id cua albumn do
                val removeAlbumFlickrList = ArrayList<String>()
                removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER)
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_GIRL);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_VN_BANCOBIET);
                //removeAlbumFlickrList.add(Constants.FLICKR_ID_DONGVATKHAC);
                intent.putStringArrayListExtra(
                    Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
                    removeAlbumFlickrList
                )
            }
            btGalleryCoreAlbum -> {
                intent = Intent(this, GalleryCorePhotosOnlyActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_FAMOUSMANGA);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_TRUYENBUA);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_BANCOBIET);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_XE);
                //intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_PHONGCANH);
//                intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_MANGA)
                intent.putExtra(
                    Constants.SK_PHOTOSET_ID,
                    Constants.FLICKR_ID_VN_CUNGHOANGDAOHEHEHORO
                )
            }
            btGalleryMember -> {
                intent = Intent(this, GalleryMemberActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
//                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.l_bkg_primary_black)
            }
            btEpubReader -> intent = Intent(this, EpubReaderMenuActivity::class.java)
            bt2InstanceActivity -> intent = Intent(this, Activity1::class.java)
            btFragmentNavigation -> intent = Intent(this, FragmentNavigationActivity::class.java)
            btPdf -> intent = Intent(this, PdfDemoActivity::class.java)
            btFragmentFlow -> intent = Intent(this, FragmentFlowActivity::class.java)
            btDeepLinks -> intent = Intent(this, DeepLinksActivity::class.java)
            btGalleryCoreAlbumFrm -> intent = Intent(this, GalleryCoreAlbumFrmActivity::class.java)
            btArchitectureComponent -> intent =
                Intent(this, MenuAndroidArchitectureComponentActivity::class.java)
            btNFC -> intent = Intent(this, NFCActivity::class.java)
            btGirl -> {
                intent = Intent(this, GirlSplashActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))

                val listBkg = ArrayList<String>()
                listBkg.add("https://live.staticflickr.com/4657/26146170428_894243ab3c_b.jpg")
                listBkg.add("https://live.staticflickr.com/4782/26128440717_a00e7cbec1_h.jpg")
                listBkg.add("https://live.staticflickr.com/817/26128440867_1a90f7f8ec_h.jpg")
                listBkg.add("https://live.staticflickr.com/789/26128436937_84ecab7cdf_h.jpg")
                listBkg.add("https://live.staticflickr.com/4794/26128436737_69e5dfca7b_h.jpg")
                intent.putExtra(Constants.BKG_SPLASH_SCREEN, listBkg.random())
            }
            btMapTracker -> intent = Intent(this, MapTrackerActivity::class.java)
            btComic -> {
                intent = Intent(this, ComicSplashActivity::class.java)
                intent.putExtra(Constants.COMIC_ADMOB_ID_BANNER, getString(R.string.str_b))
                intent.putExtra(Constants.COMIC_SHOW_DONATION, true)
            }
            btFindNumber -> intent = Intent(this, SplashActivity::class.java)
            btTTT -> {
                intent = Intent(this, TTTSplashActivity::class.java)
                intent.putExtra(Constants.COMIC_ADMOB_ID_BANNER, getString(R.string.str_b))
            }
            btRSS -> {
                intent = Intent(this, RSSActivity::class.java)
            }
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
