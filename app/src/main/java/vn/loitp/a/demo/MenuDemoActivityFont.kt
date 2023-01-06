package vn.loitp.a.demo

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.helper.gallery.GalleryCoreSplashActivity
import com.loitp.core.helper.gallery.albumOnly.GalleryCorePhotosOnlyActivity
import com.loitp.core.helper.gallery.member.GalleryMemberActivity
import com.loitp.core.helper.ttt.ui.a.TTTSplashActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_demo_menu.*
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.a.demo.alarm.a.AlarmMeActivityFont
import vn.loitp.a.demo.architectureComponent.MenuArchitectureComponentActivityFont
import vn.loitp.a.demo.epubReader.MenuEpubReaderActivityFont
import vn.loitp.a.demo.firebase.FirebaseActivityFont
import vn.loitp.a.demo.floatingVideo.FloatingWidgetVideoActivityFont
import vn.loitp.a.demo.floatingWidget.FloatingWidgetActivityFont
import vn.loitp.a.demo.fragmentFlow.FragmentFlowActivityFont
import vn.loitp.a.demo.fragmentNavigation.FragmentNavigationActivityFont
import vn.loitp.a.demo.galleryCoreAlbumFrm.GalleryCoreAlbumFrmActivityFont
import vn.loitp.a.demo.mapTracker.MapTrackerActivityFont
import vn.loitp.a.demo.nfc.NFCActivityFont
import vn.loitp.a.demo.pdf.PdfDemoActivityFont
import vn.loitp.a.demo.piano.PianoActivityFont
import vn.loitp.a.demo.rss.RSSActivityFont
import vn.loitp.a.demo.sound.SoundActivityFont
import vn.loitp.a.demo.trackingG1.TrackingG1ActivityFont
import vn.loitp.a.demo.tts.TextToSpeechActivityFont
import vn.loitp.a.demo.twoInstanceActivity.Activity1Font

@LogTag("MenuDemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuDemoActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_demo_menu
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
            this.tvTitle?.text = MenuDemoActivityFont::class.java.simpleName
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
        btTTT.setOnClickListener(this)
        btRSS.setOnClickListener(this)
        btTrackingG1.setOnClickListener(this)
        btFirebase.setOnClickListener(this)
        btAlarm.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btSound -> launchActivity(SoundActivityFont::class.java)
            btTextToSpeech -> launchActivity(TextToSpeechActivityFont::class.java)
            btFloatingWidget -> launchActivity(FloatingWidgetActivityFont::class.java)
            btFloatingVideo -> launchActivity(FloatingWidgetVideoActivityFont::class.java)
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
            btEpubReader -> launchActivity(MenuEpubReaderActivityFont::class.java)
            bt2InstanceActivity -> launchActivity(Activity1Font::class.java)
            btFragmentNavigation -> launchActivity(FragmentNavigationActivityFont::class.java)
            btPdf -> launchActivity(PdfDemoActivityFont::class.java)
            btPiano -> launchActivity(PianoActivityFont::class.java)
            btFragmentFlow -> launchActivity(FragmentFlowActivityFont::class.java)
            btGalleryCoreAlbumFrm -> launchActivity(GalleryCoreAlbumFrmActivityFont::class.java)
            btArchitectureComponent -> launchActivity(MenuArchitectureComponentActivityFont::class.java)
            btNFC -> launchActivity(NFCActivityFont::class.java)
            btMapTracker -> launchActivity(MapTrackerActivityFont::class.java)
            btTTT -> launchActivity(TTTSplashActivity::class.java)
            btRSS -> launchActivity(RSSActivityFont::class.java)
            btTrackingG1 -> launchActivity(TrackingG1ActivityFont::class.java)
            btFirebase -> launchActivity(FirebaseActivityFont::class.java)
            btAlarm -> launchActivity(AlarmMeActivityFont::class.java)
        }
    }
}
