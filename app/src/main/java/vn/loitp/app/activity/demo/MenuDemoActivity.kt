package vn.loitp.app.activity.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.gallery.GalleryCoreSplashActivity
import com.loitpcore.core.helper.gallery.albumOnly.GalleryCorePhotosOnlyActivity
import com.loitpcore.core.helper.gallery.member.GalleryMemberActivity
import com.loitpcore.core.helper.ttt.ui.a.TTTSplashActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.game.findNumber.ui.SplashActivity
import kotlinx.android.synthetic.main.activity_menu_demo.*
import vn.loitp.app.BuildConfig
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architectureComponent.MenuAndroidArchitectureComponentActivity
import vn.loitp.app.activity.demo.epubReader.MenuEpubReaderActivity
import vn.loitp.app.activity.demo.floatingVideo.FloatingWidgetActivity
import vn.loitp.app.activity.demo.fragmentFlow.FragmentFlowActivity
import vn.loitp.app.activity.demo.fragmentNavigation.FragmentNavigationActivity
import vn.loitp.app.activity.demo.galleryCoreAlbumFrm.GalleryCoreAlbumFrmActivity
import vn.loitp.app.activity.demo.mapTracker.MapTrackerActivity
import vn.loitp.app.activity.demo.nfc.NFCActivity
import vn.loitp.app.activity.demo.pdf.PdfDemoActivity
import vn.loitp.app.activity.demo.piano.PianoActivity
import vn.loitp.app.activity.demo.rss.RSSActivity
import vn.loitp.app.activity.demo.sound.SoundActivity
import vn.loitp.app.activity.demo.textToSpeech.TextToSpeechActivity
import vn.loitp.app.activity.demo.trackingG1.TrackingG1Activity
import vn.loitp.app.activity.demo.twoInstanceActivity.Activity1

@LogTag("MenuDemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuDemoActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuDemoActivity::class.java.simpleName
        }
        if (BuildConfig.DEBUG) {
            btGalleryCore.visibility = View.VISIBLE
            btGalleryCoreAlbum.visibility = View.VISIBLE
            btGalleryMember.visibility = View.VISIBLE
            btGalleryCoreAlbumFrm.visibility = View.VISIBLE
            btEpubReader.visibility = View.VISIBLE
            btTTT.visibility = View.VISIBLE
        } else {
            btGalleryCore.visibility = View.GONE
            btGalleryCoreAlbum.visibility = View.GONE
            btGalleryMember.visibility = View.GONE
            btGalleryCoreAlbumFrm.visibility = View.GONE
            btEpubReader.visibility = View.GONE
            btTTT.visibility = View.GONE
        }

        btSound.setOnClickListener(this)
        btTextToSpeech.setOnClickListener(this)
        btFloatingWidget.setOnClickListener(this)
        btFloatingVideo.setOnClickListener(this)
        btGalleryCore.setOnClickListener(this)
        btGalleryCoreAlbum.setOnClickListener(this)
        btGalleryMember.setOnClickListener(this)
        btGalleryCoreAlbumFrm.setOnClickListener(this)
        btEpubReader.setOnClickListener(this)
        bt2InstanceActivity.setOnClickListener(this)
        btFragmentNavigation.setOnClickListener(this)
        btPdf.setOnClickListener(this)
        btPiano.setOnClickListener(this)
        btFragmentFlow.setOnClickListener(this)
        btArchitectureComponent.setOnClickListener(this)
        btNFC.setOnClickListener(this)
        btMapTracker.setOnClickListener(this)
        btFindNumber.setOnClickListener(this)
        btTTT.setOnClickListener(this)
        btRSS.setOnClickListener(this)
        btTrackingG1.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btSound -> intent = Intent(this, SoundActivity::class.java)
            btTextToSpeech -> intent = Intent(this, TextToSpeechActivity::class.java)
            btFloatingWidget -> intent = Intent(
                this,
                vn.loitp.app.activity.demo.floatingWidget.FloatingWidgetActivity::class.java
            )
            btFloatingVideo -> intent = Intent(this, FloatingWidgetActivity::class.java)
            btGalleryCore -> {
                intent = Intent(this, GalleryCoreSplashActivity::class.java)
                intent.putExtra(Constants.BKG_SPLASH_SCREEN, Constants.URL_IMG_11)
                // neu muon remove albumn nao thi cu pass id cua albumn do
                val removeAlbumFlickrList = ArrayList<String>()
                removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER)
                // removeAlbumFlickrList.add(Constants.FLICKR_ID_GIRL);
                // removeAlbumFlickrList.add(Constants.FLICKR_ID_VN_BANCOBIET);
                // removeAlbumFlickrList.add(Constants.FLICKR_ID_DONGVATKHAC);
                intent.putStringArrayListExtra(
                    Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
                    removeAlbumFlickrList
                )
            }
            btGalleryCoreAlbum -> {
                intent = Intent(this, GalleryCorePhotosOnlyActivity::class.java)
                // intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_FAMOUSMANGA);
                // intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_TRUYENBUA);
                // intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_VN_BANCOBIET);
                // intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_XE);
                // intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_PHONGCANH);
//                intent.putExtra(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_MANGA)
                intent.putExtra(
                    Constants.SK_PHOTOSET_ID,
                    Constants.FLICKR_ID_VN_CUNGHOANGDAOHEHEHORO
                )
            }
            btGalleryMember -> {
                intent = Intent(this, GalleryMemberActivity::class.java)
//                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.l_bkg_primary_black)
            }
            btEpubReader -> intent = Intent(this, MenuEpubReaderActivity::class.java)
            bt2InstanceActivity -> intent = Intent(this, Activity1::class.java)
            btFragmentNavigation -> intent = Intent(this, FragmentNavigationActivity::class.java)
            btPdf -> intent = Intent(this, PdfDemoActivity::class.java)
            btPiano -> {
                intent = Intent(this, PianoActivity::class.java)
            }
            btFragmentFlow -> intent = Intent(this, FragmentFlowActivity::class.java)
            btGalleryCoreAlbumFrm -> intent = Intent(this, GalleryCoreAlbumFrmActivity::class.java)
            btArchitectureComponent ->
                intent =
                    Intent(this, MenuAndroidArchitectureComponentActivity::class.java)
            btNFC -> intent = Intent(this, NFCActivity::class.java)
            btMapTracker -> intent = Intent(this, MapTrackerActivity::class.java)
            btFindNumber -> intent = Intent(this, SplashActivity::class.java)
            btTTT -> {
                intent = Intent(this, TTTSplashActivity::class.java)
            }
            btRSS -> {
                intent = Intent(this, RSSActivity::class.java)
            }
            btTrackingG1 -> {
                intent = Intent(this, TrackingG1Activity::class.java)
            }
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
