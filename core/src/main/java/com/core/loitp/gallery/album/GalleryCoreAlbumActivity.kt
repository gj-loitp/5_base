package com.core.loitp.gallery.album

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.loitp.gallery.photos.GalleryCorePhotosActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetlist.Photoset
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.floatdraglayout.DisplayUtil
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView
import com.views.recyclerview.animator.adapters.ScaleInAnimationAdapter
import com.views.recyclerview.animator.animators.SlideInRightAnimator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import loitp.core.R
import java.util.*

class GalleryCoreAlbumActivity : BaseFontActivity() {
    private var albumAdapter: AlbumAdapter? = null
    private val photosetList = ArrayList<Photoset>()
    private var removeAlbumList: ArrayList<String>? = null
    private var bkgRootView: Int = 0
    private var avLoadingIndicatorView: AVLoadingIndicatorView? = null
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        setTransparentStatusNavigationBar()
        removeAlbumList = intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)
        avLoadingIndicatorView = findViewById(R.id.av)
        val admobBannerUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
        LLog.d(TAG, "admobBannerUnitId $admobBannerUnitId")
        val lnAdview = findViewById<LinearLayout>(R.id.ln_adview)
        if (admobBannerUnitId.isNullOrEmpty()) {
            lnAdview.visibility = View.GONE
        } else {
            adView = AdView(activity)
            adView?.let {
                it.adSize = AdSize.BANNER
                it.adUnitId = admobBannerUnitId
                LUIUtil.createAdBanner(it)
                lnAdview.addView(it)
                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
                LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        bkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND)
        LLog.d(TAG, "bkgRootView $bkgRootView")
        if (bkgRootView == Constants.NOT_FOUND) {
            rootView?.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        } else {
            rootView?.setBackgroundResource(bkgRootView)
        }

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 1000

        recyclerView?.let {
            it.itemAnimator = animator
            it.layoutManager = LinearLayoutManager(activity)
            it.setHasFixedSize(true)
        }

        albumAdapter = AlbumAdapter(activity, photosetList, object : AlbumAdapter.Callback {
            override fun onClick(pos: Int) {
                val intent = Intent(activity, GalleryCorePhotosActivity::class.java)
                intent.apply {
                    putExtra(Constants.BKG_ROOT_VIEW, bkgRootView)
                    putExtra(Constants.SK_PHOTOSET_ID, photosetList[pos].id)
                    putExtra(Constants.SK_PHOTOSET_SIZE, photosetList[pos].photos)
                    startActivity(this)
                    LActivityUtil.tranIn(activity)
                }
            }

            override fun onLongClick(pos: Int) {
                /*photosetList.remove(pos);
                albumAdapter.notifyItemRemoved(pos);
                albumAdapter.notifyItemRangeChanged(pos, photosetList.size());*/
            }
        })
        val scaleAdapter = ScaleInAnimationAdapter(albumAdapter)
        scaleAdapter.apply {
            setDuration(1000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(true)
        }

        recyclerView?.let {
            it.adapter = scaleAdapter
            LUIUtil.setPullLikeIOSVertical(it)
        }

        photosetsGetList()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return "TAG" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_gallery_core_album
    }

    private fun photosetsGetList() {
        avLoadingIndicatorView?.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0
        val format = FlickrConst.FORMAT
        val nojsoncallback = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras,
                format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetlist ->
                    //LLog.d(TAG, "onSuccess " + new Gson().toJson(wrapperPhotosetGetlist));
                    photosetList.addAll(wrapperPhotosetGetlist.photosets.photoset)

                    /*String x = "";
                    for (int i = 0; i < photosetList.size(); i++) {
                        x += photosetList.get(i).getTitle().getContent() + " - " + photosetList.get(i).getId() + "\n";
                    }
                    LLog.d(TAG, "" + x);*/

                    //LLog.d(TAG, "orginal size: " + photosetList.size());
                    //LLog.d(TAG, "removeAlbumList size: " + removeAlbumList.size());

                    removeAlbumList?.let {
                        for (i in it.indices.reversed()) {
                            for (j in photosetList.indices.reversed()) {
                                if (it[i] == photosetList[j].id) {
                                    photosetList.removeAt(j)
                                }
                            }
                        }
                    }

                    //LLog.d(TAG, "after size: " + photosetList.size());
                    photosetList.sortWith(Comparator { o1, o2 ->
                        java.lang.Long.valueOf(o2.dateUpdate).compareTo(java.lang.Long.valueOf(o1.dateUpdate))
                    })
                    updateAllViews()
                    avLoadingIndicatorView?.smoothToHide()
                }, { throwable ->
                    handleException(throwable)
                    avLoadingIndicatorView?.smoothToHide()
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
