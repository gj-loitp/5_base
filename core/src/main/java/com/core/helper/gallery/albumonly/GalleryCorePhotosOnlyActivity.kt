package com.core.helper.gallery.albumonly

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LDialogUtil
import com.core.utilities.LLog
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.task.AsyncTaskDownloadImage
import com.views.layout.floatdraglayout.DisplayUtil
import com.views.progressloadingview.avl.LAVLoadingIndicatorView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.l_activity_gallery_core_photos_only.*

class GalleryCorePhotosOnlyActivity : BaseFontActivity() {
    private var tvTitle: TextView? = null
    private var LAVLoadingIndicatorView: LAVLoadingIndicatorView? = null
    private var currentPage = 0
    private var totalPage = 1
    private val PER_PAGE_SIZE = 100
    private var isLoading: Boolean = false
    private var photosOnlyAdapter: PhotosOnlyAdapter? = null
    private var adView: AdView? = null
    private var photosetID: String? = null
    private var photosSize: Int = 0
    private var btPage: FloatingActionButton? = null
    private var isShowDialogCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RestClient.init(getString(R.string.flickr_URL))
        setTransparentStatusNavigationBar()
        PhotosDataCore.getInstance().clearData()

        val resBkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, R.color.colorPrimary)
        rootView.setBackgroundResource(resBkgRootView)

        val adUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
        LLog.d(TAG, "adUnitId $adUnitId")
        val lnAdview = findViewById<LinearLayout>(R.id.ln_adview)
        if (adUnitId.isNullOrEmpty()) {
            lnAdview.visibility = View.GONE
        } else {
            adView = AdView(activity)
            adView?.let {
                it.adSize = AdSize.BANNER
                it.adUnitId = adUnitId
                LUIUtil.createAdBanner(it)
                lnAdview.addView(it)
                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
                LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4)
            }
        }

        tvTitle = findViewById(R.id.tv_title)
        tvTitle?.let {
            LUIUtil.setTextShadow(it)
        }
        LAVLoadingIndicatorView = findViewById(R.id.av)
        btPage = findViewById(R.id.bt_page)

        photosetID = intent.getStringExtra(Constants.SK_PHOTOSET_ID)
        if (photosetID.isNullOrEmpty()) {
            handleException(Exception(getString(R.string.err_unknow)))
            return
        }
        LLog.d(TAG, "photosetID $photosetID")
        photosSize = intent.getIntExtra(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)
        LLog.d(TAG, "photosSize $photosSize")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        /*SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);*/

        recyclerView.layoutManager = LinearLayoutManager(activity)
        //recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));

        recyclerView.setHasFixedSize(true)
        photosOnlyAdapter = PhotosOnlyAdapter(activity, object : PhotosOnlyAdapter.Callback {
            override fun onClick(photo: Photo, pos: Int) {
                //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                /*Intent intent = new Intent(activity, GalleryCoreSlideActivity.class);
                intent.putExtra(Constants.SK_PHOTO_ID, photo.getId());
                startActivity(intent);
                LActivityUtil.tranIn(activity);*/
            }

            override fun onLongClick(photo: Photo, pos: Int) {
                //LSocialUtil.share(activity, photo.getUrlO());
            }

            override fun onClickDownload(photo: Photo, pos: Int) {
                //LLog.d(TAG, "onClick " + PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO());
                AsyncTaskDownloadImage(applicationContext, photo.urlO).execute()
            }

            override fun onClickShare(photo: Photo, pos: Int) {
                LSocialUtil.share(activity, photo.urlO)
            }

            override fun onClickReport(photo: Photo, pos: Int) {
                LSocialUtil.sendEmail(activity)
            }

            override fun onClickCmt(photo: Photo, pos: Int) {
                LSocialUtil.openFacebookComment(context = activity, url = photo.urlO, adUnitId = adUnitId)
            }
        })
        recyclerView.adapter = photosOnlyAdapter
        /*ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(photosOnlyAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);*/

        LUIUtil.setPullLikeIOSVertical(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        //LLog.d(TAG, "last item");
                        photosetsGetPhotos(photosetID!!)
                    }
                }
            }
        })

        btPage?.setOnClickListener { _ ->
            //LLog.d(TAG, "onClick " + currentPage + "/" + totalPage);
            showListPage()
        }
    }

    private fun showListPage() {
        val size = totalPage
        val arr = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            arr[i] = "Page " + (totalPage - i)
        }
        LDialogUtil.showDialogList(activity, "Select page", arr, object : LDialogUtil.CallbackList {
            override fun onClick(position: Int) {
                currentPage = totalPage - position
                LLog.d(TAG, "showDialogList onClick position $position, -> currentPage: $currentPage")
                PhotosDataCore.getInstance().clearData()
                updateAllViews()
                photosetsGetPhotos(photosetID!!)
            }

        })
    }

    private fun goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            photosetsGetList()
        } else {
            init()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_gallery_core_photos_only
    }

    private fun init() {
        LLog.d(TAG, "init photosSize $photosSize")

        totalPage = if (photosSize % PER_PAGE_SIZE == 0) {
            photosSize / PER_PAGE_SIZE
        } else {
            photosSize / PER_PAGE_SIZE + 1
        }

        currentPage = totalPage
        //LLog.d(TAG, "total page " + totalPage);
        //LLog.d(TAG, "currentPage " + currentPage);

        photosetsGetPhotos(photosetID!!)
    }

    private fun photosetsGetList() {
        LAVLoadingIndicatorView?.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        //String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        val primaryPhotoExtras = ""
        val format = FlickrConst.FORMAT
        val nojsoncallback = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetlist ->
                    for (photoset in wrapperPhotosetGetlist.photosets.photoset) {
                        if (photoset.id == photosetID) {
                            photosSize = Integer.parseInt(photoset.photos)
                            init()
                            return@subscribe
                        }
                    }
                }, { e ->
                    LLog.e(TAG, "photosetsGetList onFail $e")
                    handleException(e)
                    LAVLoadingIndicatorView?.smoothToHide()
                }))
    }

    private fun photosetsGetPhotos(photosetID: String) {
        if (isLoading) {
            LLog.d(TAG, "photosetsGetList isLoading true -> return")
            return
        }
        LLog.d(TAG, "is calling photosetsGetPhotos $currentPage/$totalPage")
        isLoading = true
        LAVLoadingIndicatorView?.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            LLog.d(TAG, "currentPage <= 0 -> return")
            currentPage = 0
            LAVLoadingIndicatorView?.smoothToHide()
            return
        }
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val nojsoncallback = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
                    LLog.d(TAG, "photosetsGetPhotos onSuccess " + Gson().toJson(wrapperPhotosetGetPhotos))
                    //LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

                    val s = wrapperPhotosetGetPhotos.photoset.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle?.text = s
                    val photoList = wrapperPhotosetGetPhotos.photoset.photo
                    PhotosDataCore.getInstance().addPhoto(photoList)
                    updateAllViews()

                    LAVLoadingIndicatorView?.smoothToHide()
                    btPage?.visibility = View.VISIBLE
                    isLoading = false
                    currentPage--
                }, { e ->
                    LLog.e(TAG, "photosetsGetPhotos onFail $e")
                    handleException(e)
                    LAVLoadingIndicatorView?.smoothToHide()
                    isLoading = true
                }))
    }

    private fun updateAllViews() {
        photosOnlyAdapter?.notifyDataSetChanged()
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView?.resume()
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    private fun checkPermission() {
        isShowDialogCheck = true
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            LLog.d(TAG, "onPermissionsChecked do you work now")
                            goToHome()
                        } else {
                            LLog.d(TAG, "!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            LLog.d(TAG, "onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        LLog.d(TAG, "onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", object : LDialogUtil.Callback2 {
            override fun onClick1() {
                checkPermission()
            }

            override fun onClick2() {
                onBackPressed()
            }
        })
        alertDialog.setCancelable(false)
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", object : LDialogUtil.Callback2 {
            override fun onClick1() {
                isShowDialogCheck = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, 101)
            }

            override fun onClick2() {
                onBackPressed()
            }
        })
        alertDialog.setCancelable(false)
    }
}
