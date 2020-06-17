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
import vn.loitp.app.R
import vn.loitp.app.activity.demo.alarmdemoapp.activity.AlarmMeActivity
import vn.loitp.app.activity.demo.architecturecomponent.MenuAndroidArchitectureComponentActivity
import vn.loitp.app.activity.demo.deeplinks.DeepLinksActivity
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity
import vn.loitp.app.activity.demo.epubreader.EpubReaderMenuActivity
import vn.loitp.app.activity.demo.firebase.MenuFirebaseActivity
import vn.loitp.app.activity.demo.floatingvideo.FloatingWidgetActivity
import vn.loitp.app.activity.demo.floatingview.FloatingViewActivity
import vn.loitp.app.activity.demo.fragmentflow.FragmentFlowActivity
import vn.loitp.app.activity.demo.fragmentnavigation.FragmentNavigationActivity
import vn.loitp.app.activity.demo.gallerycorealbumfrm.GalleryCoreAlbumFrmActivity
import vn.loitp.app.activity.demo.pdf.PdfDemoActivity
import vn.loitp.app.activity.demo.sound.SoundActivity
import vn.loitp.app.activity.demo.texttospeech.TextToSpeechActivity
import vn.loitp.app.activity.demo.twoinstanceactivity.Activity1
import vn.loitp.app.activity.demo.video.VideoActivity
import java.util.*

class MenuDemoActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAlarm.setOnClickListener(this)
        btEbookWithRealm.setOnClickListener(this)
        btVideo.setOnClickListener(this)
        btSound.setOnClickListener(this)
        btTextToSpeech.setOnClickListener(this)
        btFloatingWidget.setOnClickListener(this)
        btFloatingVideo.setOnClickListener(this)
        btFloatingView.setOnClickListener(this)
        btFirebase.setOnClickListener(this)
        if (Constants.IS_DEBUG) {
            btGalleryCore.visibility = View.VISIBLE
            btGalleryCore.setOnClickListener(this)

            btGalleryCoreAlbum.visibility = View.VISIBLE
            btGalleryCoreAlbum.setOnClickListener(this)

            btGalleryMember.visibility = View.VISIBLE
            btGalleryMember.setOnClickListener(this)

            btGalleryCoreAlbumFrm.setOnClickListener(this)
            btGalleryCoreAlbumFrm.visibility = View.VISIBLE
        } else {
            btGalleryCore.visibility = View.GONE
            btGalleryCoreAlbum.visibility = View.GONE
            btGalleryMember.visibility = View.GONE
            btGalleryCoreAlbumFrm.visibility = View.GONE
        }
        btEpubReader.setOnClickListener(this)
        bt2InstanceActivity.setOnClickListener(this)
        btFragmentNavigation.setOnClickListener(this)
        btPdf.setOnClickListener(this)
        btFragmentFlow.setOnClickListener(this)
        btDeepLinks.setOnClickListener(this)
        btArchitectureComponent.setOnClickListener(this)
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
        when (v) {
            btAlarm -> intent = Intent(activity, AlarmMeActivity::class.java)
            btEbookWithRealm -> intent = Intent(activity, EbookWithRealmActivity::class.java)
            btVideo -> intent = Intent(activity, VideoActivity::class.java)
            btSound -> intent = Intent(activity, SoundActivity::class.java)
            btTextToSpeech -> intent = Intent(activity, TextToSpeechActivity::class.java)
            btFloatingWidget -> intent = Intent(activity, vn.loitp.app.activity.demo.floatingwidget.FloatingWidgetActivity::class.java)
            btFloatingVideo -> intent = Intent(activity, FloatingWidgetActivity::class.java)
            btFirebase -> intent = Intent(activity, MenuFirebaseActivity::class.java)
            btGalleryCore -> {
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
            btGalleryCoreAlbum -> {
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
            btGalleryMember -> {
                intent = Intent(activity, GalleryMemberActivity::class.java)
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, getString(R.string.str_b))
                intent.putExtra(Constants.BKG_ROOT_VIEW, R.drawable.l_bkg_gradient_man_of_steel)
            }
            btEpubReader -> intent = Intent(activity, EpubReaderMenuActivity::class.java)
            btFloatingView -> intent = Intent(activity, FloatingViewActivity::class.java)
            bt2InstanceActivity -> intent = Intent(activity, Activity1::class.java)
            btFragmentNavigation -> intent = Intent(activity, FragmentNavigationActivity::class.java)
            btPdf -> intent = Intent(activity, PdfDemoActivity::class.java)
            btFragmentFlow -> intent = Intent(activity, FragmentFlowActivity::class.java)
            btDeepLinks -> intent = Intent(activity, DeepLinksActivity::class.java)
            btGalleryCoreAlbumFrm -> intent = Intent(activity, GalleryCoreAlbumFrmActivity::class.java)
            btArchitectureComponent -> intent = Intent(activity, MenuAndroidArchitectureComponentActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
