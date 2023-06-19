package com.loitp.core.helper.gallery.photos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.Keep
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.R
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.*
import com.loitp.core.helper.gallery.slide.GalleryCoreSlideActivity
import com.loitp.databinding.LAFlickrGalleryCorePhotosBinding
import com.loitp.restApi.flickr.FlickrConst
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitp.restApi.flickr.service.FlickrService
import com.loitp.restApi.restClient.RestClient
import com.loitp.views.layout.swipeBack.SwipeBackLayout
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
@Keep
@LogTag("GalleryCorePhotosActivity")
@IsSwipeActivity(true)
class GalleryCorePhotosActivity : BaseActivityFont() {
    private var currentPage = 0
    private var totalPage = 1
    private var isLoading = false
    private var photosAdapter: PhotosAdapter? = null
    private var photoSetID: String? = null
    private lateinit var binding: LAFlickrGalleryCorePhotosBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LAFlickrGalleryCorePhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RestClient.init(getString(R.string.flickr_URL))
        isValidPackageName()

        setupViews()
    }

    private fun setupViews() {
        PhotosDataCore.instance.clearData()

        photoSetID = intent.getStringExtra(SK_PHOTOSET_ID)
        val photosSize = intent.getStringExtra(SK_PHOTOSET_SIZE)

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
        totalPage = if (totalPhotos % PER_PAGE_SIZE == 0) {
            totalPhotos / PER_PAGE_SIZE
        } else {
            totalPhotos / PER_PAGE_SIZE + 1
        }
        currentPage = totalPage

        val column = 2
        binding.recyclerView.layoutManager = GridLayoutManager(this, column)
        photosAdapter = PhotosAdapter(callback = object : PhotosAdapter.Callback {
            override fun onClick(photo: Photo, pos: Int) {
                val intent =
                    Intent(this@GalleryCorePhotosActivity, GalleryCoreSlideActivity::class.java)
                intent.putExtra(SK_PHOTO_ID, photo.id)
                startActivity(intent)
                this@GalleryCorePhotosActivity.tranIn()
            }

            override fun onLongClick(photo: Photo, pos: Int) {
                this@GalleryCorePhotosActivity.share(msg = photo.urlO)
            }
        })

        photosAdapter?.let {
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
        // LUIUtil.setPullLikeIOSVertical(recyclerView)

        photosetsGetPhotos(photosetID = photoSetID)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        binding.btPage.setSafeOnClickListener {
            showListPage()
        }

        binding.swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?, swipeBackFraction: Float, swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()//correct
                    this@GalleryCorePhotosActivity.transActivityNoAnimation()
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
        this.showDialogList(title = "Select page", arr = arr, onClick = { position ->
            currentPage = totalPage - position
            PhotosDataCore.instance.clearData()
            updateAllViews()
            photosetsGetPhotos(photoSetID)
        })
    }

    private fun photosetsGetPhotos(photosetID: String?) {
        if (isLoading || photosetID.isNullOrEmpty()) {
            return
        }
        isLoading = true
        binding.progressBar.showProgress()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            currentPage = 0
            binding.progressBar.hideProgress()
            isLoading = false
            return
        }
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK
        compositeDisposable.add(service.getPhotosetPhotos(
            method = method,
            apiKey = apiKey,
            photosetId = photosetID,
            userId = userID,
            extras = primaryPhotoExtras,
            perPage = PER_PAGE_SIZE,
            page = currentPage,
            format = format,
            noJsonCallback = noJsonCallBack
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ wrapperPhotosetGetPhotos ->
                val s =
                    wrapperPhotosetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                binding.tvTitle.text = s
                wrapperPhotosetGetPhotos?.photoset?.photo?.let {
                    it.shuffle()
                    PhotosDataCore.instance.addPhoto(it)
                }
                updateAllViews()
                binding.progressBar.hideProgress()
                binding.btPage.visibility = View.VISIBLE
                isLoading = false
            }) { e: Throwable ->
                handleException(e)
                binding.progressBar.hideProgress()
                isLoading = true
            })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAllViews() {
        photosAdapter?.notifyDataSetChanged()
    }
}
