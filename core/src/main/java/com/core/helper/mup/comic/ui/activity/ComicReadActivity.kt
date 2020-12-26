package com.core.helper.mup.comic.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.helper.mup.comic.model.Chap
import com.core.helper.mup.comic.model.ChapterDetail
import com.core.helper.mup.comic.ui.popup.PopupComicChapterDetail
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.*
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.views.layout.swipeback.SwipeBackLayout
import com.views.listview.OnDetectScrollListener
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_comic_read.*
import java.util.*

@LogTag("ComicActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
@IsSwipeActivity(true)
class ComicReadActivity : BaseFontActivity() {

    companion object {
        const val KEY_CHAP = "KEY_CHAP"
    }

    private var chap: Chap? = null
    private var comicViewModel: ComicViewModel? = null
    private var comicAdapter = ComicAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_comic_read
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupData()
        setupViews()
        setupViewModels()

        comicViewModel?.getChapterDetail(chapId = chap?.id)
        LValidateUtil.isValidPackageName()
    }

    private fun setupData() {
        intent?.getSerializableExtra(KEY_CHAP)?.let {
            if (it is Chap) {
                chap = it
            }
        }
    }

    private fun setupViews() {
        comicAdapter = ComicAdapter()
        comicView.setOnDetectScrollListener(object : OnDetectScrollListener {
            override fun onUpScrolling() {
//                logD("setOnDetectScrollListener up")
                if (fabPrevious.visibility != View.VISIBLE) {
                    fabPrevious.visibility = View.VISIBLE
                    fabNext.visibility = View.VISIBLE
                    layoutControl.visibility = View.VISIBLE
                }
            }

            override fun onDownScrolling() {
//                logD("setOnDetectScrollListener down")
                if (fabPrevious.visibility != View.GONE) {
                    fabPrevious.visibility = View.GONE
                    fabNext.visibility = View.GONE
                    layoutControl.visibility = View.GONE
                }
            }

        })
        comicView.adapter = comicAdapter

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View?, swipeBackFraction: Float, swipeBackFactor: Float) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@ComicReadActivity)
                }
            }
        })

        ivBack.setSafeOnClickListener {
            onBackPressed()
        }
        ivMenu.setSafeOnClickListener {
            handleClickMenu(anchorView = it)
        }
        fabNext.setSafeOnClickListener {
            goToNextChap()
        }
        fabPrevious.setSafeOnClickListener {
            goToPreviousChap()
        }
    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
            vm.chapterDetailActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                logD("<<<chapterDetailActionLiveData observe isDoing $isDoing, isSuccess $isSuccess")

                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }

                if (isDoing == false && isSuccess == true) {
                    val chapterDetail = actionData.data
                    logD("<<<<observe chapterDetail " + BaseApplication.gson.toJson(chapterDetail))
                    if (chapterDetail == null || chapterDetail.chapterComicsDetails.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        comicView.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        comicView.visibility = View.VISIBLE
                        tvTitle.text = actionData.data?.title

                        comicAdapter.setChapterDetail(chapterDetail = chapterDetail)
                        comicView.smoothScrollToPosition(0)
                    }
                }
            })
        }
    }

    private fun goToPreviousChap() {
        val prevChap = comicAdapter.getChapterDetail()?.prevChap
        logD("goToPreviousChap prevChap " + BaseApplication.gson.toJson(prevChap))
        if (prevChap == null || prevChap.id.isNullOrEmpty()) {
            showLongInformation(getString(R.string.no_data))
        } else {
            comicViewModel?.getChapterDetail(chapId = prevChap.id)
        }
    }

    private fun goToNextChap() {
        val nextChap = comicAdapter.getChapterDetail()?.nextChap
        logD("goToNextChap nextChap " + BaseApplication.gson.toJson(nextChap))
        if (nextChap == null || nextChap.id.isNullOrEmpty()) {
            showLongInformation(getString(R.string.no_data))
        } else {
            comicViewModel?.getChapterDetail(chapId = nextChap.id)
        }
    }

    private fun handleClickMenu(anchorView: View) {
        val popup = PopupComicChapterDetail(anchorView.context)
        popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val verticalPos = RelativePopupWindow.VerticalPosition.BELOW
        val horizontalPos = RelativePopupWindow.HorizontalPosition.ALIGN_RIGHT
        popup.onClickShare = {
            LSocialUtil.shareApp(activity = this)
        }
        popup.onClickDownload = {
            //TODO iplm download
            showLongInformation(getString(R.string.coming_soon))
        }
        popup.showOnAnchor(anchorView, verticalPos, horizontalPos, false)
    }

    private inner class ComicAdapter : BaseAdapter() {
        private val sizeWidth = LScreenUtil.screenWidth
        private var listData = ArrayList<String>()
        private var chapterDetail: ChapterDetail? = null

        fun getChapterDetail(): ChapterDetail? {
            return chapterDetail
        }

        fun setChapterDetail(chapterDetail: ChapterDetail?) {

            this.chapterDetail = chapterDetail
            this.listData.clear()
            this.chapterDetail?.chapterComicsDetails?.forEach {
                it.imageSrc?.let { url ->
                    listData.add(element = "$url=w$sizeWidth")
                }
            }
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return listData.size
        }

        override fun getItem(position: Int): Any {
            return listData[position]
        }

        override fun getItemId(position: Int): Long {
            return (position + 1000).toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var mConvertView = convertView
            val holder: ComicHolder

            if (mConvertView == null) {
                mConvertView = LayoutInflater.from(parent.context).inflate(R.layout.row_comic_view, parent, false)

                holder = ComicHolder()
                holder.ivComic = mConvertView!!.findViewById(R.id.ivComic)
                mConvertView.tag = holder
            } else {
                holder = mConvertView.tag as ComicHolder
            }

            val url = listData[position]
            logD("$position -> $url")

            holder.ivComic?.let { iv ->
//                Glide.with(parent.context)
//                        .load(url)
//                        .dontAnimate()
//                        .into(iv)
                LImageUtil.loadHighQuality(
                        any = url,
                        imageView = iv
                )
            }

            return mConvertView
        }
    }

    internal class ComicHolder {
        var ivComic: ImageView? = null
    }
}
