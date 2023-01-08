package com.loitp.core.helper.gallery.albumOnly

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huxq17.download.Pump
import com.huxq17.download.core.DownloadListener
import com.loitp.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.*
import com.loitp.core.helper.gallery.photos.PhotosDataCore
import com.loitp.core.utilities.LDialogUtil
import com.loitp.restApi.flickr.FlickrConst
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitp.restApi.flickr.service.FlickrService
import com.loitp.restApi.restClient.RestClient
import com.loitp.views.layout.swipeBack.SwipeBackLayout
import com.permissionx.guolindev.PermissionX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import kotlinx.android.synthetic.main.l_a_flickr_gallery_core_photos_only.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GalleryCorePhotosOnlyActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class GalleryCorePhotosOnlyActivity : BaseActivityFont() {
    companion object {
        private const val PER_PAGE_SIZE = 100
    }

    private var currentPage = 0
    private var totalPage = 1
    private var isLoading: Boolean = false
    private var photosOnlyAdapter: PhotosOnlyAdapter? = null
    private var photoSetID: String? = null
    private var photosSize: Int = 0

    override fun setLayoutResourceId(): Int {
        return R.layout.l_a_flickr_gallery_core_photos_only
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        checkPermission()
    }

    private fun setupViews() {
        RestClient.init(getString(R.string.flickr_URL))
        PhotosDataCore.instance.clearData()

        photoSetID = intent.getStringExtra(Constants.SK_PHOTOSET_ID)
        if (photoSetID.isNullOrEmpty()) {
            handleException(Exception(getString(R.string.err_unknown)))
            return
        }
        photosSize = intent.getIntExtra(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)
        recyclerView.layoutManager = LinearLayoutManager(this)
        photosOnlyAdapter = PhotosOnlyAdapter(
            callback = object : PhotosOnlyAdapter.Callback {
                override fun onClick(photo: Photo, pos: Int) {
                }

                override fun onLongClick(photo: Photo, pos: Int) {
                }

                override fun onClickDownload(photo: Photo, pos: Int) {
                    save(url = photo.urlO)
                }

                override fun onClickShare(photo: Photo, pos: Int) {
                    this@GalleryCorePhotosOnlyActivity.share(
                        msg = photo.urlO
                    )
                }

                override fun onClickSetWallpaper(photo: Photo, pos: Int, imageView: ImageView) {
                    imageView.setWallpaperAndLockScreen(
                        isSetWallpaper = true,
                        isSetLockScreen = true,
                    )
                }

                override fun onClickReport(photo: Photo, pos: Int) {
                    this@GalleryCorePhotosOnlyActivity.sendEmail()
                }

                override fun onClickCmt(photo: Photo, pos: Int) {
                    this@GalleryCorePhotosOnlyActivity.openFacebookComment(
                        url = photo.urlO,
                    )
                }
            }
        )
        photosOnlyAdapter?.let {
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

        // LUIUtil.setPullLikeIOSVertical(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        photoSetID?.let {
                            photoSetsGetPhotos(photoSetID = it)
                        }
                    }
                }
            }
        })

        btPage.setOnClickListener {
            showListPage()
        }

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(
                mView: View?,
                isEnd: Boolean
            ) {
                if (isEnd) {
                    finish()//correct
                    this@GalleryCorePhotosOnlyActivity.transActivityNoAnimation()
                }
            }
        })
    }

    private fun showListPage() {
        val size = totalPage
        val arr = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            arr[i] = "Page " + (totalPage - i)
        }
        LDialogUtil.showDialogList(
            context = this,
            title = "Select page",
            arr = arr,
            onClick = { position ->
                currentPage = totalPage - position
                PhotosDataCore.instance.clearData()
                updateAllViews()
                photoSetID?.let {
                    photoSetsGetPhotos(it)
                }
            }
        )
    }

    private fun goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            getListPhotoSets()
        } else {
            init()
        }
    }

    private fun init() {
        totalPage = if (photosSize % PER_PAGE_SIZE == 0) {
            photosSize / PER_PAGE_SIZE
        } else {
            photosSize / PER_PAGE_SIZE + 1
        }

        currentPage = totalPage

        photoSetID?.let {
            photoSetsGetPhotos(it)
        }
    }

    private fun getListPhotoSets() {
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        // String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        val primaryPhotoExtras = ""
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(
            service.getListPhotoset(
                method = method,
                apiKey = apiKey,
                userId = userID,
                page = page,
                perPage = perPage,
                primaryPhotoExtras = primaryPhotoExtras,
                format = format, noJsonCallback = noJsonCallBack
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotoSetGetlist ->
                    wrapperPhotoSetGetlist?.photosets?.photoset?.let { list ->
                        for (photoSet in list) {
                            if (photoSet.id == photoSetID) {
                                photosSize = Integer.parseInt(photoSet.photos ?: "0")
                                init()
                                return@subscribe
                            }
                        }
                    }
                }, { e ->
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                })
        )
    }

    private fun photoSetsGetPhotos(photoSetID: String) {
        if (isLoading) {
            return
        }
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
                photosetId = photoSetID,
                userId = userID,
                extras = primaryPhotoExtras,
                perPage = PER_PAGE_SIZE,
                page = currentPage,
                format = format,
                noJsonCallback = noJsonCallBack
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
                    val s =
                        wrapperPhotosetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle.text = s
                    wrapperPhotosetGetPhotos?.photoset?.photo?.let {
                        it.shuffle()
                        PhotosDataCore.instance.addPhoto(it)
                    }
                    updateAllViews()

                    LDialogUtil.hideProgress(progressBar)
                    btPage.visibility = View.VISIBLE
                    isLoading = false
                    currentPage--
                }, { e ->
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                    isLoading = true
                })
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAllViews() {
        photosOnlyAdapter?.notifyDataSetChanged()
    }

    private fun checkPermission() {
        val color = if (isDarkTheme()) {
            Color.WHITE
        } else {
            Color.BLACK
        }
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .setDialogTintColor(lightColor = color, darkColor = color)
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
                    onBaseBackPressed()
                }
            }
    }

    private fun save(url: String) {
        Pump.newRequestToPicture(/* url = */ url, /* directory = */ "/loitp/picture")
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.filePath
                    showShortInformation("Download Finished $filePath")
                }

                override fun onFailed() {
                    showShortError("Download failed")
                }
            })
            // Optionally,Set whether to repeatedly download the downloaded file,default false.
            .forceReDownload(true)
            // Optionally,Set how many threads are used when downloading,default 3.
            .threadNum(3)
            .setRetry(3, 200)
            .submit()
    }
}
