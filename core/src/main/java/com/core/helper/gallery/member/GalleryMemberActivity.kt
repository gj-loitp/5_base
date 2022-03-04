package com.core.helper.gallery.member

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LActivityUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.permissionx.guolindev.PermissionX
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.swipeback.SwipeBackLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_photos_only.*

@LogTag("GalleryMemberActivity")
@IsSwipeActivity(true)
class GalleryMemberActivity : BaseFontActivity() {
    private var currentPage = 0
    private var totalPage = 1
    private var isLoading: Boolean = false
    private var memberAdapter: MemberAdapter? = null
    private var adView: AdView? = null
    private var photosetID: String? = null
    private var photosSize: Int = 0

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_photos_only
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RestClient.init(getString(R.string.flickr_URL))
        PhotosDataCore.instance.clearData()

        val resBkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND)
        if (resBkgRootView != Constants.NOT_FOUND) {
            rootView.setBackgroundResource(resBkgRootView)
        }

        val adUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
//        logD("adUnitId $adUnitId")
        if (adUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.adSize = AdSize.BANNER
                it.adUnitId = adUnitId
                LUIUtil.createAdBanner(adView = it)
                lnAdView.addView(it)
            }
        }

//        LUIUtil.setTextShadow(textView = tvTitle, color = Color.BLACK)

        photosetID = Constants.FLICKR_ID_MEMBERS
        if (photosetID?.isEmpty() == true) {
            handleException(Exception(getString(R.string.err_unknow)))
            return
        }
        photosSize = intent.getIntExtra(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        memberAdapter = MemberAdapter(
            callback = object : MemberAdapter.Callback {
                override fun onClick(
                    photo: Photo,
                    pos: Int,
                    imageView: ImageView,
                    textView: TextView
                ) {
                    val intent =
                        Intent(this@GalleryMemberActivity, GalleryMemberDetailActivity::class.java)
                    intent.putExtra(GalleryMemberDetailActivity.PHOTO, photo)
                    startActivity(intent)
                    LActivityUtil.tranIn(this@GalleryMemberActivity)
                }

                override fun onLongClick(photo: Photo, pos: Int) {
                }
            }
        )
        memberAdapter?.let {
//            val animAdapter = AlphaInAnimationAdapter(it)
//            val animAdapter = ScaleInAnimationAdapter(it)
            val animAdapter = SlideInBottomAnimationAdapter(it)
//            val animAdapter = SlideInLeftAnimationAdapter(it)
//            val animAdapter = SlideInRightAnimationAdapter(it)

            animAdapter.setDuration(1000)
//            animAdapter.setInterpolator(OvershootInterpolator())
            animAdapter.setFirstOnly(true)
            recyclerView.adapter = animAdapter
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        photosetsGetPhotos(photosetID = photosetID)
                    }
                }
            }
        })

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GalleryMemberActivity)
                }
            }
        })
        checkPermission()
    }

    private fun goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            getPhotosets()
        } else {
            init()
        }
    }

    private fun init() {
        totalPage = if (photosSize % Constants.PER_PAGE_SIZE == 0) {
            photosSize / Constants.PER_PAGE_SIZE
        } else {
            photosSize / Constants.PER_PAGE_SIZE + 1
        }

        currentPage = totalPage

        photosetsGetPhotos(photosetID = photosetID)
    }

    private fun getPhotosets() {
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        val primaryPhotoExtras = ""
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(
            service.getListPhotoset(
                method,
                apiKey,
                userID,
                page,
                perPage,
                primaryPhotoExtras,
                format,
                noJsonCallBack
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetlist ->
                    wrapperPhotosetGetlist?.photosets?.photoset?.let { list ->
                        for (photoset in list) {
                            if (photoset.id == photosetID) {
                                photosSize = Integer.parseInt(photoset.photos ?: "0")
                                init()
                                return@subscribe
                            }
                        }
                    }
                }, { e ->
                    e.printStackTrace()
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                })
        )
    }

    private fun photosetsGetPhotos(photosetID: String?) {
        if (photosetID.isNullOrEmpty()) {
//            logD("photosetID isNullOrEmpty -> return")
            return
        }
        if (isLoading) {
//            logD("photosetsGetList isLoading true -> return")
            return
        }
//        logD("is calling photosetsGetPhotos $currentPage/$totalPage")
        isLoading = true
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
//            logD("currentPage <= 0 -> return")
            currentPage = 0
            LDialogUtil.hideProgress(progressBar)
            return
        }
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(
            service.getPhotosetPhotos(
                method = method,
                apiKey = apiKey,
                photosetId = photosetID,
                userId = userID,
                extras = primaryPhotoExtras,
                perPage = Constants.PER_PAGE_SIZE,
                page = currentPage,
                format = format,
                noJsonCallback = noJsonCallBack
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
//                    logD("photosetsGetPhotos $currentPage/$totalPage")

                    val s =
                        wrapperPhotosetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle.text = s
                    wrapperPhotosetGetPhotos?.photoset?.photo?.let {
                        it.shuffle()
                        PhotosDataCore.instance.addPhoto(it)
                    }
                    updateAllViews()

                    LDialogUtil.hideProgress(progressBar)
                    isLoading = false
                    currentPage--
                }, { e ->
                    e.printStackTrace()
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                    isLoading = true
                })
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAllViews() {
        memberAdapter?.notifyDataSetChanged()
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    private fun checkPermission() {
        val color = if (LUIUtil.isDarkTheme()) {
            Color.WHITE
        } else {
            Color.BLACK
        }
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .setDialogTintColor(color, color)
            .onExplainRequestReason { scope, deniedList, _ ->
                val message = getString(R.string.app_name) + getString(R.string.needs_per)
                scope.showRequestReasonDialog(
                    permissions = deniedList,
                    message = message,
                    positiveText = getString(R.string.allow),
                    negativeText = getString(R.string.deny)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    permissions = deniedList,
                    message = getString(R.string.per_manually_msg),
                    positiveText = getString(R.string.ok),
                    negativeText = getString(R.string.cancel)
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    goToHome()
                } else {
                    finish()
                    LActivityUtil.tranOut(this)
                }
            }
    }
}
