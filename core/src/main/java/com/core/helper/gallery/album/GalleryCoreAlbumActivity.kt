package com.core.helper.gallery.album

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.GalleryCorePhotosActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import com.core.utilities.LValidateUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetlist.Photoset
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.swipeback.SwipeBackLayout
import com.views.recyclerview.animator.adapters.ScaleInAnimationAdapter
import com.views.recyclerview.animator.animators.SlideInRightAnimator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_album.*
import java.util.*

@LogTag("GalleryCoreAlbumActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class GalleryCoreAlbumActivity : BaseFontActivity() {
    private var albumAdapter: AlbumAdapter? = null
    private val listPhotoSet = ArrayList<Photoset>()
    private var listRemoveAlbum = ArrayList<String>()
    private var adView: AdView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_album
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)?.let {
            listRemoveAlbum.addAll(it)
        }
        val admobBannerUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
//        logD("admobBannerUnitId $admobBannerUnitId")

        if (admobBannerUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.adSize = AdSize.SMART_BANNER
                it.adUnitId = admobBannerUnitId
                LUIUtil.createAdBanner(adView = it)
                lnAdView.addView(it)
//                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
//                LUIUtil.setMargins(view = lnAdView, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = navigationHeight + navigationHeight / 4)
            }
        }

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 1000

        recyclerView.apply {
            itemAnimator = animator
            layoutManager = LinearLayoutManager(this@GalleryCoreAlbumActivity)
            setHasFixedSize(true)
        }

        albumAdapter = AlbumAdapter(context = this,
                photosetList = listPhotoSet,
                callback = object : AlbumAdapter.Callback {
                    override fun onClick(pos: Int) {
                        val intent = Intent(this@GalleryCoreAlbumActivity, GalleryCorePhotosActivity::class.java)
                        intent.apply {
                            putExtra(Constants.SK_PHOTOSET_ID, listPhotoSet[pos].id)
                            putExtra(Constants.SK_PHOTOSET_SIZE, listPhotoSet[pos].photos)
                            startActivity(this)
                            LActivityUtil.tranIn(this@GalleryCoreAlbumActivity)
                        }
                    }

                    override fun onLongClick(pos: Int) {
                    }
                })

        val scaleAdapter = ScaleInAnimationAdapter(albumAdapter)
        scaleAdapter.apply {
            setDuration(1000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(true)
        }

        recyclerView.apply {
            //this.adapter = scaleAdapter
            this.adapter = albumAdapter
            //LUIUtil.setPullLikeIOSVertical(this)
        }

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GalleryCoreAlbumActivity)
                }
            }
        })
        LValidateUtil.isValidCoreGalleryFlickr()
        getListPhotosets()
    }

    private fun getListPhotosets() {
        indicatorView.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.getListPhotoset(method = method,
                apiKey = apiKey,
                userId = userID,
                page = page,
                perPage = perPage,
                primaryPhotoExtras = primaryPhotoExtras,
                format = format,
                noJsonCallback = noJsonCallBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotoSetGetlist ->
                    wrapperPhotoSetGetlist?.photosets?.photoset?.let {
                        listPhotoSet.addAll(it)
                    }
                    listRemoveAlbum.let {
                        for (i in it.indices.reversed()) {
                            for (j in listPhotoSet.indices.reversed()) {
                                if (it[i] == listPhotoSet[j].id) {
                                    listPhotoSet.removeAt(j)
                                }
                            }
                        }
                    }

                    listPhotoSet.sortWith { o1, o2 ->
                        java.lang.Long.valueOf(o2.dateUpdate).compareTo(java.lang.Long.valueOf(o1.dateUpdate))
                    }
                    updateAllViews()
                    indicatorView.smoothToHide()
                }, { throwable ->
                    handleException(throwable)
                    indicatorView.smoothToHide()
                }))
    }

    private fun updateAllViews() {
        albumAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
