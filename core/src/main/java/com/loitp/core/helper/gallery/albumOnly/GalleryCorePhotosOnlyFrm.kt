package com.loitp.core.helper.gallery.albumOnly

// ktlint-disable no-wildcard-imports
import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.huxq17.download.Pump
import com.huxq17.download.core.DownloadListener
import com.loitp.R
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFragment
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.helper.gallery.photos.PhotosDataCore
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.restApi.flickr.FlickrConst
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitp.restApi.flickr.service.FlickrService
import com.loitp.restApi.restClient.RestClient
import com.permissionx.guolindev.PermissionX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import kotlinx.android.synthetic.main.l_f_flickr_gallery_core_photos_only.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GalleryCorePhotosOnlyFrm")
class GalleryCorePhotosOnlyFrm(
    private val onTop: ((Unit) -> Unit)? = null,
    private val onBottom: ((Unit) -> Unit)? = null,
    private val onScrolled: ((isScrollDown: Boolean) -> Unit)? = null
) : BaseFragment() {

    companion object {
        private const val PER_PAGE_SIZE = 100
        const val IS_SHOW_TITLE = "IS_SHOW_TITLE"
    }

    private var currentPage = 0
    private var totalPage = 1
    private var isLoading: Boolean = false
    private var photosOnlyAdapter: PhotosOnlyAdapter? = null
    private var photosetID: String? = null
    private var photosSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RestClient.init(getString(R.string.flickr_URL))
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_f_flickr_gallery_core_photos_only
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        checkPermission()
    }

    private fun setupViews() {
        val bundle = arguments ?: return
        PhotosDataCore.instance.clearData()
        photosetID = bundle.getString(Constants.SK_PHOTOSET_ID)
        if (photosetID.isNullOrEmpty()) {
            handleException(Exception(getString(R.string.err_unknown)))
            return
        }
//        logD("photosetID $photosetID")
        photosSize = bundle.getInt(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)
//        logD("photosSize $photosSize")

        val isShowTitle = bundle.getBoolean(IS_SHOW_TITLE)
        if (isShowTitle) {
            tvTitle.visibility = View.VISIBLE
        } else {
            tvTitle.visibility = View.GONE
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        activity?.let { a ->
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
                        LSocialUtil.share(activity = a, msg = photo.urlO)
                    }

                    override fun onClickSetWallpaper(photo: Photo, pos: Int, imageView: ImageView) {
                        LUIUtil.setWallpaperAndLockScreen(
                            activity = requireActivity(),
                            imageView = imageView,
                            isSetWallpaper = true,
                            isSetLockScreen = true,
                        )
                    }

                    override fun onClickReport(photo: Photo, pos: Int) {
                        LSocialUtil.sendEmail(context = a)
                    }

                    override fun onClickCmt(photo: Photo, pos: Int) {
                        LSocialUtil.openFacebookComment(context = a, url = photo.urlO)
                    }
                }
            )
        }
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

        LUIUtil.setScrollChange(
            recyclerView = recyclerView,
            onTop = {
                onTop?.invoke(Unit)
            },
            onBottom = {
                if (!isLoading) {
                    photosetsGetPhotos(photosetID)
                }
                onBottom?.invoke(Unit)
            },
            onScrolled = {
                onScrolled?.invoke(it)
            }
        )

        btPage.setSafeOnClickListener {
            showListPage()
        }
    }

    private fun showListPage() {
        val size = totalPage
        val arr = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            arr[i] = "Page " + (totalPage - i)
        }
        activity?.let {
            LDialogUtil.showDialogList(
                context = it,
                title = "Select page",
                arr = arr,
                onClick = { position ->
                    currentPage = totalPage - position
                    logD("showDialogList onClick position $position, -> currentPage: $currentPage")
                    PhotosDataCore.instance.clearData()
                    updateAllViews()
                    photosetsGetPhotos(photosetID)
                }
            )
        }
    }

    private fun goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            getPhotosets()
        } else {
            init()
        }
    }

    private fun init() {
        logD("init photosSize $photosSize")

        totalPage = if (photosSize % PER_PAGE_SIZE == 0) {
            photosSize / PER_PAGE_SIZE
        } else {
            photosSize / PER_PAGE_SIZE + 1
        }

        currentPage = totalPage

        photosetsGetPhotos(photosetID)
    }

    private fun getPhotosets() {
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
            logD("photosetID isNullOrEmpty -> return")
            return
        }
        if (isLoading) {
            logD("photosetsGetList isLoading true -> return")
            return
        }
        logD("is calling photosetsGetPhotos $currentPage/$totalPage")
        isLoading = true
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            logD("currentPage <= 0 -> return")
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
                perPage = PER_PAGE_SIZE,
                page = currentPage,
                format = format,
                noJsonCallback = noJsonCallBack
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
                    logD(
                        "photosetsGetPhotos onSuccess " + BaseApplication.gson.toJson(
                            wrapperPhotosetGetPhotos
                        )
                    )

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
        val color = if (LUIUtil.isDarkTheme()) {
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
                    activity?.let {
                        it.finish()//correct
                        LActivityUtil.tranOut(it)
                    }
                }
            }
    }

    private fun save(url: String) {
        Pump.newRequestToPicture(url, "/loitp/picture")
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
