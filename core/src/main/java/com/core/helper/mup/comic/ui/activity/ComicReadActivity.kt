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
import com.bumptech.glide.Glide
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.helper.mup.comic.model.Chap
import com.core.helper.mup.comic.model.ChapterDetail
import com.core.helper.mup.comic.ui.popup.PopupComicChapterDetail
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.*
import com.daimajia.androidanimations.library.Techniques
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.views.layout.swipeback.SwipeBackLayout
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_comic_read.*
import java.util.*

@LogTag("loitppComicActivity")
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
        LValidateUtil.isValidCoreComicMup()
    }

    private fun setupData() {
        intent?.getSerializableExtra(KEY_CHAP)?.let {
            if (it is Chap) {
                chap = it
            }
        }
    }

    private var isHideView = false

    private fun setupViews() {
        comicAdapter = ComicAdapter()
        //TODO
//        comicAdapter.onClickRoot = { _, _ ->
//            logD("comicAdapter onClickRoot")
//            isHideView = if (isHideView) {
//                LAnimationUtil.play(view = fabPrevious, techniques = Techniques.SlideInUp)
//                LAnimationUtil.play(view = fabNext, techniques = Techniques.SlideInUp)
//                LAnimationUtil.play(view = layoutControl, techniques = Techniques.SlideInDown)
//                false
//            } else {
//                LAnimationUtil.play(view = fabPrevious, techniques = Techniques.SlideOutDown)
//                LAnimationUtil.play(view = fabNext, techniques = Techniques.SlideOutDown)
//                LAnimationUtil.play(view = layoutControl, techniques = Techniques.SlideOutUp)
//                true
//            }
//        }
        comicView.adapter = comicAdapter

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
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

                        initData(chapterDetail)
                    }
                }
            })
        }
    }

    private fun goToPreviousChap() {
        //TODO
//        val prevChap = chapterDetailAdapter.getChapterDetail()?.prevChap
//        logD("goToPreviousChap prevChap " + BaseApplication.gson.toJson(prevChap))
//        if (prevChap == null || prevChap.id.isNullOrEmpty()) {
//            showLongInformation(getString(R.string.no_data))
//        } else {
//            comicViewModel?.getChapterDetail(chapId = prevChap.id)
//        }
    }

    private fun goToNextChap() {
        //TODO
//        val nextChap = chapterDetailAdapter.getChapterDetail()?.nextChap
//        logD("goToNextChap nextChap " + BaseApplication.gson.toJson(nextChap))
//        if (nextChap == null || nextChap.id.isNullOrEmpty()) {
//            showLongInformation(getString(R.string.no_data))
//        } else {
//            comicViewModel?.getChapterDetail(chapId = nextChap.id)
//        }
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
            //TODO loitpp iplm
            showLongInformation(getString(R.string.coming_soon))
        }
        popup.showOnAnchor(anchorView, verticalPos, horizontalPos, false)
    }

    private fun initData(chapterDetail: ChapterDetail) {
        val list = ArrayList<String>()

        val sizeWidth = LScreenUtil.screenWidth
        chapterDetail.chapterComicsDetails.forEach {
            it.imageSrc?.let { url ->
                list.add(element = "$url=w$sizeWidth")
            }
        }
//        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00001.jpg")
//        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00002.jpg")
//        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00003.jpg")
//        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00004.jpg")
//        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00005.jpg")

        comicAdapter.setData(data = list)
    }

    private inner class ComicAdapter : BaseAdapter() {

        private var listData = ArrayList<String>()

        fun setData(data: List<String>) {
            listData.clear()
            listData.addAll(data)
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

            //wont work
//            LImageUtil.load(
//                    context = parent.context,
//                    any = listData[position],
//                    imageView = holder.ivComic
//            )

            val url = listData[position]
            logD("$position -> $url")

            holder.ivComic?.let { iv ->
                Glide.with(parent.context)
                        .load(url)
                        .dontAnimate()
                        .into(iv)

            }

            return mConvertView
        }
    }

    internal class ComicHolder {
        var ivComic: ImageView? = null
    }
}
