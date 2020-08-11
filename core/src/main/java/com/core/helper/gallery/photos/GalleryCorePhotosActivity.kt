package com.core.helper.gallery.photos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.slide.GalleryCoreSlideActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.interfaces.CallbackList
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.swipeback.SwipeBackLayout
import com.views.recyclerview.animator.animators.SlideInRightAnimator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_photos.*

class GalleryCorePhotosActivity : BaseFontActivity() {
    private var currentPage = 0
    private var totalPage = 1
    private val PER_PAGE_SIZE = 100
    private var isLoading = false
    private var photosAdapter: PhotosAdapter? = null
    private var photosetID: String? = null
    private var bkgRootView = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setTransparentStatusNavigationBar()
        PhotosDataCore.instance.clearData()

        LUIUtil.setTextShadow(textView = tvTitle)

        photosetID = intent.getStringExtra(Constants.SK_PHOTOSET_ID)
        val photosSize = intent.getStringExtra(Constants.SK_PHOTOSET_SIZE)
        bkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND)

        if (bkgRootView == Constants.NOT_FOUND) {
            rootView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        } else {
            rootView.setBackgroundResource(bkgRootView)
        }
        val totalPhotos = try {
            photosSize?.toInt() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            showDialogError(getString(R.string.err_unknow))
            return
        }
        if (totalPhotos == 0) {
            showDialogError(getString(R.string.err_unknow))
            return
        }
        totalPage = if (totalPhotos % PER_PAGE_SIZE == 0) {
            totalPhotos / PER_PAGE_SIZE
        } else {
            totalPhotos / PER_PAGE_SIZE + 1
        }
        currentPage = totalPage

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 1000
        recyclerView.itemAnimator = animator
        val column = 2
        recyclerView.layoutManager = GridLayoutManager(activity, column)
        recyclerView.setHasFixedSize(true)
        photosAdapter = PhotosAdapter(context = activity, callback = object : PhotosAdapter.Callback {
            override fun onClick(photo: Photo, pos: Int) {
                val intent = Intent(activity, GalleryCoreSlideActivity::class.java)
                intent.putExtra(Constants.SK_PHOTO_ID, photo.id)
                intent.putExtra(Constants.BKG_ROOT_VIEW, bkgRootView)
                startActivity(intent)
                LActivityUtil.tranIn(activity)
            }

            override fun onLongClick(photo: Photo, pos: Int) {
                LSocialUtil.share(activity = activity, msg = photo.urlO)
            }
        })

//        val scaleAdapter = ScaleInAnimationAdapter(photosAdapter)
//        scaleAdapter.setDuration(1000)
//        scaleAdapter.setInterpolator(OvershootInterpolator())
//        scaleAdapter.setFirstOnly(true)
//        recyclerView.adapter = scaleAdapter

        recyclerView.adapter = photosAdapter
        //LUIUtil.setPullLikeIOSVertical(recyclerView)

        photosetsGetPhotos(photosetID)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        currentPage--
                        photosetsGetPhotos(photosetID = photosetID)
                    }
                }
            }
        })
        btPage.setOnClickListener {
            showListPage()
        }

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAniamtion(activity)
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
        LDialogUtil.showDialogList(context = activity,
                title = "Select page",
                arr = arr,
                callbackList = object : CallbackList {
                    override fun onClick(position: Int) {
                        currentPage = totalPage - position
                        PhotosDataCore.instance.clearData()
                        updateAllViews()
                        photosetsGetPhotos(photosetID)
                    }
                })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_photos
    }

    private fun photosetsGetPhotos(photosetID: String?) {
        if (isLoading || photosetID.isNullOrEmpty()) {
            return
        }
        isLoading = true
        indicatorView.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            currentPage = 0
            indicatorView.smoothToHide()
            isLoading = false
            return
        }
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK
        compositeDisposable.add(service.getPhotosetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, noJsonCallBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
                    val s = wrapperPhotosetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle.text = s
                    wrapperPhotosetGetPhotos?.photoset?.photo?.let {
                        PhotosDataCore.instance.addPhoto(it)
                    }
                    updateAllViews()
                    indicatorView.smoothToHide()
                    btPage.visibility = View.VISIBLE
                    isLoading = false
                }) { e: Throwable ->
                    handleException(e)
                    indicatorView.smoothToHide()
                    isLoading = true
                })
    }

    private fun updateAllViews() {
        photosAdapter?.notifyDataSetChanged()
    }
}
