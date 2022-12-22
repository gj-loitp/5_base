package vn.loitp.activity.api.galleryAPI

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.restApi.flickr.FlickrConst
import com.loitp.restApi.flickr.model.photoSetGetList.Photoset
import com.loitp.restApi.flickr.model.photoSetGetList.WrapperPhotosetGetlist
import com.loitp.restApi.flickr.model.photoSetGetPhotos.WrapperPhotosetGetPhotos
import com.loitp.restApi.flickr.service.FlickrService
import com.loitp.restApi.restClient.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_api_gallery.*
import vn.loitp.R

@LogTag("GalleryAPIActivity")
@IsFullScreen(false)
class GalleryAPIActivity : BaseFontActivity() {
    private var mWrapperPhotoSetGetList: WrapperPhotosetGetlist? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_gallery
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = GalleryAPIActivity::class.java.simpleName
        }
        LDialogUtil.hideProgress(progressBar)
        bt1.setSafeOnClickListener {
            getPhotoSets()
        }
        bt2.setSafeOnClickListener {
            showDialogSelectPhotoset()
        }
    }

    private fun getPhotoSets() {
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0
        val format = FlickrConst.FORMAT
        val noJsonCallback = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(
            service.getListPhotoset(
                method,
                apiKey,
                userID,
                page,
                perPage,
                primaryPhotoExtras,
                format,
                noJsonCallback
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetlist: WrapperPhotosetGetlist? ->
                    mWrapperPhotoSetGetList = wrapperPhotosetGetlist
                    wrapperPhotosetGetlist?.let {
                        LUIUtil.printBeautyJson(it, textView)
                    }
                    LDialogUtil.hideProgress(progressBar)
                    bt2.visibility = View.VISIBLE
                }) { e: Throwable ->
                    e.printStackTrace()
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                }
        )
    }

    private fun showDialogSelectPhotoset() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose:")
        val listPhotoset = ArrayList<Photoset>()
        mWrapperPhotoSetGetList?.photosets?.photoset?.let {
            listPhotoset.addAll(it)
        }
        val items = arrayOfNulls<String>(listPhotoset.size)
        for (i in listPhotoset.indices) {
            items[i] = listPhotoset[i].title?.content
        }
        builder.setItems(items) { _: DialogInterface?, position: Int ->
            photosetsGetPhotos(listPhotoset[position].id)
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun photosetsGetPhotos(photosetID: String?) {
        if (photosetID.isNullOrEmpty()) {
            return
        }
        textView.text = ""
        LDialogUtil.showProgress(progressBar)
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val noJsonCallback = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(
            service.getPhotosetPhotos(
                method,
                apiKey,
                photosetID,
                userID,
                primaryPhotoExtras,
                perPage,
                page,
                format,
                noJsonCallback
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetlist: WrapperPhotosetGetPhotos? ->
                    wrapperPhotosetGetlist?.let {
                        LUIUtil.printBeautyJson(it, textView)
                    }
                    LDialogUtil.hideProgress(progressBar)
                    bt2.visibility = View.VISIBLE
                }) { e: Throwable ->
                    handleException(e)
                    LDialogUtil.hideProgress(progressBar)
                }
        )
    }
}
