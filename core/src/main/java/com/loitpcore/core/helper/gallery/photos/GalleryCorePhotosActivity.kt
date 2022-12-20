package com.loitpcore.core.helper.gallery.photos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.R
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.helper.gallery.slide.GalleryCoreSlideActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.restApi.flickr.FlickrConst
import com.loitpcore.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitpcore.restApi.flickr.service.FlickrService
import com.loitpcore.restApi.restClient.RestClient
import com.loitpcore.views.layout.swipeBack.SwipeBackLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_photos.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GalleryCorePhotosActivity")
@IsSwipeActivity(true)
class GalleryCorePhotosActivity : BaseFontActivity() {
    private var currentPage = 0
    private var totalPage = 1
    private var isLoading = false
    private var photosAdapter: PhotosAdapter? = null
    private var photoSetID: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_photos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        PhotosDataCore.instance.clearData()

        photoSetID = intent.getStringExtra(Constants.SK_PHOTOSET_ID)
        val photosSize = intent.getStringExtra(Constants.SK_PHOTOSET_SIZE)

        val totalPhotos = try {
            photosSize?.toInt() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            showDialogError(getString(R.string.err_unknown))
            return
        }
        if (totalPhotos == 0) {
            showDialogError(getString(R.string.err_unknown))
            return
        }
        totalPage = if (totalPhotos % Constants.PER_PAGE_SIZE == 0) {
            totalPhotos / Constants.PER_PAGE_SIZE
        } else {
            totalPhotos / Constants.PER_PAGE_SIZE + 1
        }
        currentPage = totalPage

        val column = 2
        recyclerView.layoutManager = GridLayoutManager(this, column)
        photosAdapter = PhotosAdapter(
            callback = object : PhotosAdapter.Callback {
                override fun onClick(photo: Photo, pos: Int) {
                    val intent =
                        Intent(this@GalleryCorePhotosActivity, GalleryCoreSlideActivity::class.java)
                    intent.putExtra(Constants.SK_PHOTO_ID, photo.id)
                    startActivity(intent)
                    LActivityUtil.tranIn(this@GalleryCorePhotosActivity)
                }

                override fun onLongClick(photo: Photo, pos: Int) {
                    LSocialUtil.share(activity = this@GalleryCorePhotosActivity, msg = photo.urlO)
                }
            }
        )

        photosAdapter?.let {
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

        photosetsGetPhotos(photosetID = photoSetID)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        currentPage--
                        photosetsGetPhotos(photosetID = photoSetID)
                    }
                }
            }
        })
        btPage.setSafeOnClickListener {
            showListPage()
        }

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()//correct
                    LActivityUtil.transActivityNoAnimation(this@GalleryCorePhotosActivity)
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
                photosetsGetPhotos(photoSetID)
            }
        )
    }

    private fun photosetsGetPhotos(photosetID: String?) {
        if (isLoading || photosetID.isNullOrEmpty()) {
            return
        }
        isLoading = true
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            currentPage = 0
            LDialogUtil.hideProgress(progressBar)
            isLoading = false
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
                }) { e: Throwable ->
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                    isLoading = true
                }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAllViews() {
        photosAdapter?.notifyDataSetChanged()
    }
}
