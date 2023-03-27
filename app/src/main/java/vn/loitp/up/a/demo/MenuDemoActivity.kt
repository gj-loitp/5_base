package vn.loitp.up.a.demo

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.gallery.GalleryCoreSplashActivity
import com.loitp.core.helper.gallery.albumOnly.GalleryCorePhotosOnlyActivity
import com.loitp.core.helper.gallery.member.GalleryMemberActivity
import com.loitp.core.helper.ttt.ui.a.TTTSplashActivity
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.databinding.ADemoMenuBinding
import vn.loitp.up.a.demo.ad.MenuAdActivity
import vn.loitp.up.a.demo.alarm.a.AlarmMeActivity
import vn.loitp.up.a.demo.architectureComponent.MenuArchitectureComponentActivity
import vn.loitp.up.a.demo.epubReader.MenuEpubReaderActivity
import vn.loitp.up.a.demo.firebase.FirebaseActivity
import vn.loitp.up.a.demo.floatingVideo.FloatingWidgetVideoActivity
import vn.loitp.up.a.demo.floatingWidget.FloatingWidgetActivity
import vn.loitp.up.a.demo.fragmentFlow.FragmentFlowActivity
import vn.loitp.up.a.demo.fragmentNavigation.FragmentNavigationActivity
import vn.loitp.up.a.demo.galleryCoreAlbumFrm.GalleryCoreAlbumFrmActivity
import vn.loitp.up.a.demo.mapTracker.MapTrackerActivity
import vn.loitp.up.a.demo.nfc.NFCActivity
import vn.loitp.up.a.demo.pdf.PdfDemoActivity
import vn.loitp.up.a.demo.piano.PianoActivity
import vn.loitp.up.a.demo.rss.RSSActivity
import vn.loitp.up.a.demo.sound.SoundActivity
import vn.loitp.up.a.demo.trackingG1.TrackingG1Activity
import vn.loitp.up.a.demo.tts.TextToSpeechActivity
import vn.loitp.up.a.demo.twoInstanceActivity.Activity1

@LogTag("MenuDemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuDemoActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ADemoMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADemoMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuDemoActivity::class.java.simpleName
        }
        if (BuildConfig.DEBUG) {
            binding.btGalleryCore.visibility = View.VISIBLE
            binding.btGalleryCoreAlbum.visibility = View.VISIBLE
            binding.btGalleryMember.visibility = View.VISIBLE
            binding.btGalleryCoreAlbumFrm.visibility = View.VISIBLE
            binding.btEpubReader.visibility = View.VISIBLE
            binding.btTTT.visibility = View.VISIBLE
        } else {
            binding.btGalleryCore.visibility = View.GONE
            binding.btGalleryCoreAlbum.visibility = View.GONE
            binding.btGalleryMember.visibility = View.GONE
            binding.btGalleryCoreAlbumFrm.visibility = View.GONE
            binding.btEpubReader.visibility = View.GONE
            binding.btTTT.visibility = View.GONE
        }

        binding.btSound.setOnClickListener(this)
        binding.btTextToSpeech.setOnClickListener(this)
        binding.btFloatingWidget.setOnClickListener(this)
        binding.btFloatingVideo.setOnClickListener(this)
        binding.btGalleryCore.setOnClickListener(this)
        binding.btGalleryCoreAlbum.setOnClickListener(this)
        binding.btGalleryMember.setOnClickListener(this)
        binding.btGalleryCoreAlbumFrm.setOnClickListener(this)
        binding.btEpubReader.setOnClickListener(this)
        binding.bt2InstanceActivity.setOnClickListener(this)
        binding.btFragmentNavigation.setOnClickListener(this)
        binding.btPdf.setOnClickListener(this)
        binding.btPiano.setOnClickListener(this)
        binding.btFragmentFlow.setOnClickListener(this)
        binding.btArchitectureComponent.setOnClickListener(this)
        binding.btNFC.setOnClickListener(this)
        binding.btMapTracker.setOnClickListener(this)
        binding.btTTT.setOnClickListener(this)
        binding.btRSS.setOnClickListener(this)
        binding.btTrackingG1.setOnClickListener(this)
        binding.btFirebase.setOnClickListener(this)
        binding.btAlarm.setOnClickListener(this)
        binding.btApplovin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSound -> launchActivity(SoundActivity::class.java)
            binding.btTextToSpeech -> launchActivity(TextToSpeechActivity::class.java)
            binding.btFloatingWidget -> launchActivity(FloatingWidgetActivity::class.java)
            binding.btFloatingVideo -> launchActivity(FloatingWidgetVideoActivity::class.java)
            binding.btGalleryCore -> {
                launchActivity(
                    cls = GalleryCoreSplashActivity::class.java,
                    withAnim = true,
                    data = {
                        it.putExtra(BKG_SPLASH_SCREEN, URL_IMG_11)

                        // neu muon remove albumn nao thi cu pass id cua album do
                        val removeAlbumFlickrList = ArrayList<String>()
                        removeAlbumFlickrList.add(FLICKR_ID_STICKER)
                        // removeAlbumFlickrList.add(FLICKR_ID_GIRL)

                        it.putStringArrayListExtra(
                            KEY_REMOVE_ALBUM_FLICKR_LIST,
                            removeAlbumFlickrList
                        )
                    })
            }
            binding.btGalleryCoreAlbum -> {
                launchActivity(
                    cls = GalleryCorePhotosOnlyActivity::class.java,
                    withAnim = true,
                    data = {
                        it.putExtra(
                            SK_PHOTOSET_ID,
                            FLICKR_ID_VN_CUNGHOANGDAOHEHEHORO
                        )
                    })
            }
            binding.btGalleryMember -> launchActivity(GalleryMemberActivity::class.java)
            binding.btEpubReader -> launchActivity(MenuEpubReaderActivity::class.java)
            binding.bt2InstanceActivity -> launchActivity(Activity1::class.java)
            binding.btFragmentNavigation -> launchActivity(FragmentNavigationActivity::class.java)
            binding.btPdf -> launchActivity(PdfDemoActivity::class.java)
            binding.btPiano -> launchActivity(PianoActivity::class.java)
            binding.btFragmentFlow -> launchActivity(FragmentFlowActivity::class.java)
            binding.btGalleryCoreAlbumFrm -> launchActivity(GalleryCoreAlbumFrmActivity::class.java)
            binding.btArchitectureComponent -> launchActivity(MenuArchitectureComponentActivity::class.java)
            binding.btNFC -> launchActivity(NFCActivity::class.java)
            binding.btMapTracker -> launchActivity(MapTrackerActivity::class.java)
            binding.btTTT -> launchActivity(TTTSplashActivity::class.java)
            binding.btRSS -> launchActivity(RSSActivity::class.java)
            binding.btTrackingG1 -> launchActivity(TrackingG1Activity::class.java)
            binding.btFirebase -> launchActivity(FirebaseActivity::class.java)
            binding.btAlarm -> launchActivity(AlarmMeActivity::class.java)
            binding.btApplovin -> launchActivity(MenuAdActivity::class.java)
        }
    }
}
