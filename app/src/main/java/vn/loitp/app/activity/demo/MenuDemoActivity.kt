package vn.loitp.app.activity.demo

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.helper.gallery.GalleryCoreSplashActivity
import com.loitp.core.helper.gallery.albumOnly.GalleryCorePhotosOnlyActivity
import com.loitp.core.helper.gallery.member.GalleryMemberActivity
import com.loitp.core.helper.ttt.ui.a.TTTSplashActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.game.findNumber.ui.SplashActivity
import kotlinx.android.synthetic.main.activity_menu_demo.*
import vn.loitp.app.BuildConfig
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architectureComponent.MenuAndroidArchitectureComponentActivity
import vn.loitp.app.activity.demo.epubReader.MenuEpubReaderActivity
import vn.loitp.app.activity.demo.firebase.FirebaseActivity
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
        btFirebase.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btSound -> launchActivity(SoundActivity::class.java)
            btTextToSpeech -> launchActivity(TextToSpeechActivity::class.java)
            btFloatingWidget -> launchActivity(FloatingWidgetActivity::class.java)
            btFloatingVideo -> launchActivity(FloatingWidgetActivity::class.java)
            btGalleryCore -> {
                launchActivity(
                    cls = GalleryCoreSplashActivity::class.java,
                    withAnim = true,
                    data = {
                        it.putExtra(Constants.BKG_SPLASH_SCREEN, Constants.URL_IMG_11)

                        // neu muon remove albumn nao thi cu pass id cua album do
                        val removeAlbumFlickrList = ArrayList<String>()
                        removeAlbumFlickrList.add(Constants.FLICKR_ID_STICKER)
                        // removeAlbumFlickrList.add(Constants.FLICKR_ID_GIRL)

                        it.putStringArrayListExtra(
                            Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
                            removeAlbumFlickrList
                        )
                    })
            }
            btGalleryCoreAlbum -> {
                launchActivity(
                    cls = GalleryCorePhotosOnlyActivity::class.java,
                    withAnim = true,
                    data = {
                        it.putExtra(
                            Constants.SK_PHOTOSET_ID,
                            Constants.FLICKR_ID_VN_CUNGHOANGDAOHEHEHORO
                        )
                    })
            }
            btGalleryMember -> launchActivity(GalleryMemberActivity::class.java)
            btEpubReader -> launchActivity(MenuEpubReaderActivity::class.java)
            bt2InstanceActivity -> launchActivity(Activity1::class.java)
            btFragmentNavigation -> launchActivity(FragmentNavigationActivity::class.java)
            btPdf -> launchActivity(PdfDemoActivity::class.java)
            btPiano -> launchActivity(PianoActivity::class.java)
            btFragmentFlow -> launchActivity(FragmentFlowActivity::class.java)
            btGalleryCoreAlbumFrm -> launchActivity(GalleryCoreAlbumFrmActivity::class.java)
            btArchitectureComponent -> launchActivity(MenuAndroidArchitectureComponentActivity::class.java)
            btNFC -> launchActivity(NFCActivity::class.java)
            btMapTracker -> launchActivity(MapTrackerActivity::class.java)
            btFindNumber -> launchActivity(SplashActivity::class.java)
            btTTT -> launchActivity(TTTSplashActivity::class.java)
            btRSS -> launchActivity(RSSActivity::class.java)
            btTrackingG1 -> launchActivity(TrackingG1Activity::class.java)
            btFirebase -> launchActivity(FirebaseActivity::class.java)
        }
    }
}
