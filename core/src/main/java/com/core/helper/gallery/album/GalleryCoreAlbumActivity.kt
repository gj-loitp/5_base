package com.core.helper.gallery.album

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.GalleryCorePhotosActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetlist.Photoset
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.floatdraglayout.DisplayUtil
import com.views.recyclerview.animator.adapters.ScaleInAnimationAdapter
import com.views.recyclerview.animator.animators.SlideInRightAnimator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_album.*
import java.util.*

class GalleryCoreAlbumActivity : BaseFontActivity() {
    private var albumAdapter: AlbumAdapter? = null
    private val listPhotoSet = ArrayList<Photoset>()
    private var listRemoveAlbum = ArrayList<String>()
    private var bkgRootView: Int = 0
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isShowAdWhenExit = false
        setTransparentStatusNavigationBar()
        intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)?.let {
            listRemoveAlbum.addAll(it)
        }
        val admobBannerUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
        logD("admobBannerUnitId $admobBannerUnitId")

        if (admobBannerUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(activity)
            adView?.let {
                it.adSize = AdSize.BANNER
                it.adUnitId = admobBannerUnitId
                LUIUtil.createAdBanner(adView = it)
                lnAdView.addView(it)
                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
                LUIUtil.setMargins(view = lnAdView, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = navigationHeight + navigationHeight / 4)
            }
        }

        bkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND)
        logD("bkgRootView $bkgRootView")
        if (bkgRootView == Constants.NOT_FOUND) {
            rootView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        } else {
            rootView.setBackgroundResource(bkgRootView)
        }

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 1000

        recyclerView.apply {
            itemAnimator = animator
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        albumAdapter = AlbumAdapter(context = activity,
                photosetList = listPhotoSet,
                callback = object : AlbumAdapter.Callback {
                    override fun onClick(pos: Int) {
                        val intent = Intent(activity, GalleryCorePhotosActivity::class.java)
                        intent.apply {
                            putExtra(Constants.BKG_ROOT_VIEW, bkgRootView)
                            putExtra(Constants.SK_PHOTOSET_ID, listPhotoSet[pos].id)
                            putExtra(Constants.SK_PHOTOSET_SIZE, listPhotoSet[pos].photos)
                            startActivity(this)
                            LActivityUtil.tranIn(activity)
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
            this.adapter = scaleAdapter
            //LUIUtil.setPullLikeIOSVertical(this)
        }

        getListPhotosets()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_album
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

        compositeDisposable.add(service.getListPhotoset(method, apiKey, userID, page, perPage, primaryPhotoExtras,
                format, noJsonCallBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotoSetGetlist ->
                    wrapperPhotoSetGetlist.photosets?.photoset?.let {
                        listPhotoSet.addAll(it)
                    }

                    /*String x = "";
                    for (int i = 0; i < photosetList.size(); i++) {
                        x += photosetList.get(i).getTitle().getContent() + " - " + photosetList.get(i).getId() + "\n";
                    }
                    LLog.d(TAG, "" + x);*/

                    //LLog.d(TAG, "orginal size: " + photosetList.size());
                    //LLog.d(TAG, "removeAlbumList size: " + removeAlbumList.size());

                    listRemoveAlbum.let {
                        for (i in it.indices.reversed()) {
                            for (j in listPhotoSet.indices.reversed()) {
                                if (it[i] == listPhotoSet[j].id) {
                                    listPhotoSet.removeAt(j)
                                }
                            }
                        }
                    }

                    //LLog.d(TAG, "after size: " + photosetList.size());
                    listPhotoSet.sortWith(Comparator { o1, o2 ->
                        java.lang.Long.valueOf(o2.dateUpdate).compareTo(java.lang.Long.valueOf(o1.dateUpdate))
                    })
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
