package com.core.helper.gallery.member

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.floatdraglayout.DisplayUtil
import com.wang.avi.AVLoadingIndicatorView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.l_activity_gallery_core_photos_only.*

class GalleryMemberActivity : BaseFontActivity() {
    private lateinit var avLoadingIndicatorView: AVLoadingIndicatorView
    private lateinit var tvTitle: TextView

    private var currentPage = 0
    private var totalPage = 1
    private val PER_PAGE_SIZE = 50

    private var isLoading: Boolean = false
    private var memberAdapter: MemberAdapter? = null
    private var adView: AdView? = null

    private var photosetID: String? = null
    private var photosSize: Int = 0

    private var isShowDialogCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        RestClient.init(getString(R.string.flickr_URL))
        setTransparentStatusNavigationBar()
        PhotosDataCore.getInstance().clearData()

        val resBkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, R.color.colorPrimary)
        rootView.setBackgroundResource(resBkgRootView)

        val adUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
        logD("adUnitId $adUnitId")
        val lnAdview = findViewById<LinearLayout>(R.id.ln_adview)
        if (adUnitId.isNullOrEmpty()) {
            lnAdview.visibility = View.GONE
        } else {
            adView = AdView(activity)
            adView?.let {
                it.adSize = AdSize.SMART_BANNER
                it.adUnitId = adUnitId
                LUIUtil.createAdBanner(it)
                lnAdview.addView(it)
                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
                LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4)
            }
        }

        tvTitle = findViewById(R.id.tv_title)
        LUIUtil.setTextShadow(tvTitle, Color.WHITE)
        avLoadingIndicatorView = findViewById(R.id.av)

        photosetID = Constants.FLICKR_ID_MEMBERS
        if (photosetID?.isEmpty() == true) {
            handleException(Exception(getString(R.string.err_unknow)))
            return
        }
        photosSize = intent.getIntExtra(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        /*SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);*/

        val numCount = 2

        recyclerView.layoutManager = GridLayoutManager(activity, numCount)
        recyclerView.setHasFixedSize(true)
        memberAdapter = MemberAdapter(activity, numCount, object : MemberAdapter.Callback {
            override fun onClick(photo: Photo, pos: Int, imageView: ImageView, textView: TextView) {
                val intent = Intent(activity, GalleryMemberDetailActivity::class.java)
                intent.putExtra(GalleryMemberDetailActivity.PHOTO, photo)

                val pair1 = Pair<View, String>(imageView, GalleryMemberDetailActivity.IV)
                val pair2 = Pair<View, String>(textView, GalleryMemberDetailActivity.TV)

                val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        pair1,
                        pair2)
                ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
            }

            override fun onLongClick(photo: Photo, pos: Int) {
                ////do nothing
                //LSocialUtil.share(activity, photo.getUrlO());
            }
        })
        recyclerView.adapter = memberAdapter
        /*ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(memberAdapter);
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
                        photosetsGetPhotos(photosetID!!)
                    }
                }
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
        avLoadingIndicatorView.smoothToShow()
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
                    logE("photosetsGetList onFail $e")
                    handleException(e)
                    avLoadingIndicatorView.smoothToHide()
                }))
    }

    private fun photosetsGetPhotos(photosetID: String) {
        if (isLoading) {
            logD("photosetsGetList isLoading true -> return")
            return
        }
        logD("is calling photosetsGetPhotos $currentPage/$totalPage")
        isLoading = true
        avLoadingIndicatorView.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            logD("currentPage <= 0 -> return")
            currentPage = 0
            avLoadingIndicatorView.smoothToHide()
            return
        }
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val nojsoncallback = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
                    logD("photosetsGetPhotos $currentPage/$totalPage")

                    val s = wrapperPhotosetGetPhotos.photoset.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle.text = s
                    val photoList = wrapperPhotosetGetPhotos.photoset.photo
                    PhotosDataCore.getInstance().addPhoto(photoList)
                    updateAllViews()

                    avLoadingIndicatorView.smoothToHide()
                    isLoading = false
                    currentPage--
                }, { e ->
                    logE("photosetsGetPhotos onFail $e")
                    handleException(e)
                    avLoadingIndicatorView.smoothToHide()
                    isLoading = true
                }))
    }

    private fun updateAllViews() {
        memberAdapter?.notifyDataSetChanged()
    }

    public override fun onPause() {
        adView?.let {
            it.pause()
            it.visibility = View.INVISIBLE
        }
        super.onPause()
    }

    public override fun onResume() {
        adView?.let {
            it.resume()
            it.visibility = View.VISIBLE
        }
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
                            logD("onPermissionsChecked do you work now")
                            goToHome()
                        } else {
                            logD("!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            logD("onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        logD("onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions",
                "This app needs permission to use this feature.", "Okay", "Cancel",
                object : LDialogUtil.Callback2 {
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
        val alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions",
                "This app needs permission to use this feature. You can grant them in app settings.",
                "GOTO SETTINGS", "Cancel", object : LDialogUtil.Callback2 {
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
