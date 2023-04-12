package com.loitp.core.helper.gallery.member

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
import com.loitp.R
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.*
import com.loitp.core.helper.gallery.photos.PhotosDataCore
import com.loitp.databinding.LAFlickrGalleryCorePhotosOnlyBinding
import com.loitp.restApi.flickr.FlickrConst
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitp.restApi.flickr.service.FlickrService
import com.loitp.restApi.restClient.RestClient
import com.loitp.views.layout.swipeBack.SwipeBackLayout
import com.permissionx.guolindev.PermissionX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GalleryMemberActivity")
@IsSwipeActivity(true)
class GalleryMemberActivity : BaseActivityFont() {
    private lateinit var binding: LAFlickrGalleryCorePhotosOnlyBinding

    private var currentPage = 0
    private var totalPage = 1
    private var isLoading: Boolean = false
    private var memberAdapter: MemberAdapter? = null
    private var photoSetID: String? = null
    private var photosSize: Int = 0

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LAFlickrGalleryCorePhotosOnlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        checkPermission()
    }

    private fun setupViews() {
        RestClient.init(getString(R.string.flickr_URL))
        PhotosDataCore.instance.clearData()

        val resBkgRootView = intent.getIntExtra(BKG_ROOT_VIEW, NOT_FOUND)
        if (resBkgRootView != NOT_FOUND) {
            binding.rootView.setBackgroundResource(resBkgRootView)
        }

        photoSetID = FLICKR_ID_MEMBERS
        if (photoSetID?.isEmpty() == true) {
            handleException(Exception(getString(R.string.err_unknown)))
            return
        }
        photosSize = intent.getIntExtra(SK_PHOTOSET_SIZE, NOT_FOUND)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
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
                    this@GalleryMemberActivity.tranIn()
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
            binding.recyclerView.adapter = animAdapter
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        photosetsGetPhotos(photosetID = photoSetID)
                    }
                }
            }
        })

        binding.swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()//correct
                    this@GalleryMemberActivity.transActivityNoAnimation()
                }
            }
        })
    }

    private fun goToHome() {
        if (photosSize == NOT_FOUND) {
            getPhotoSets()
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

        photosetsGetPhotos(photosetID = photoSetID)
    }

    private fun getPhotoSets() {
        binding.progressBar.showProgress()
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
                .subscribe({ wrapperPhotoSetGetList ->
                    wrapperPhotoSetGetList?.photosets?.photoset?.let { list ->
                        for (photoSet in list) {
                            if (photoSet.id == photoSetID) {
                                photosSize = Integer.parseInt(photoSet.photos ?: "0")
                                init()
                                return@subscribe
                            }
                        }
                    }
                }, { e ->
                    e.printStackTrace()
                    handleException(e)
                    binding.progressBar.hideProgress()
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
        binding.progressBar.showProgress()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
//            logD("currentPage <= 0 -> return")
            currentPage = 0
            binding.progressBar.hideProgress()
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
                .subscribe({ wrapperPhotoSetGetPhotos ->
                    val s =
                        wrapperPhotoSetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                    binding.tvTitle.text = s
                    wrapperPhotoSetGetPhotos?.photoset?.photo?.let {
                        it.shuffle()
                        PhotosDataCore.instance.addPhoto(it)
                    }
                    updateAllViews()

                    binding.progressBar.hideProgress()
                    isLoading = false
                    currentPage--
                }, { e ->
                    e.printStackTrace()
                    handleException(e)
                    binding.progressBar.hideProgress()
                    isLoading = true
                })
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAllViews() {
        memberAdapter?.notifyDataSetChanged()
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
                    onBaseBackPressed()
                }
            }
    }
}
