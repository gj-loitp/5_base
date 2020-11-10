package com.core.helper.mup.comic.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.helper.mup.comic.adapter.ChapterDetailAdapter
import com.core.helper.mup.comic.model.Chap
import com.core.helper.mup.comic.ui.popup.PopupComicChapterDetail
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.*
import com.daimajia.androidanimations.library.Techniques
import com.interfaces.CallbackRecyclerView
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.views.layout.swipeback.SwipeBackLayout
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_comic_read.*

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

    private var concatAdapter = ConcatAdapter()
    private var chapterDetailAdapter = ChapterDetailAdapter()

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

    private fun setupViews() {
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
        chapterDetailAdapter = ChapterDetailAdapter()
        chapterDetailAdapter.onClickRoot = { chapterComicsDetail ->
            logD("onClickRoot chapterComicsDetail " + BaseApplication.gson.toJson(chapterComicsDetail))
            //TODO zoom
        }
        concatAdapter.addAdapter(chapterDetailAdapter)

        rvComicRead.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvComicRead.adapter = concatAdapter

        LUIUtil.setScrollChange(
                recyclerView = rvComicRead,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                        fabPrevious.visibility = View.VISIBLE
                        LAnimationUtil.play(view = fabPrevious, techniques = Techniques.SlideInUp)
                    }

                    override fun onBottom() {
                        fabNext.visibility = View.VISIBLE
                        LAnimationUtil.play(view = fabNext, techniques = Techniques.SlideInUp)
                    }

                    override fun onScrolled(isScrollDown: Boolean) {
                        if (isScrollDown) {
                            LAnimationUtil.play(view = layoutControl, techniques = Techniques.SlideOutUp)
                            LAnimationUtil.play(view = fabPrevious, techniques = Techniques.SlideOutDown)
                        } else {
                            LAnimationUtil.play(view = layoutControl, techniques = Techniques.SlideInDown)
                            LAnimationUtil.play(view = fabNext, techniques = Techniques.SlideOutDown)
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
                        rvComicRead.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        rvComicRead.visibility = View.VISIBLE
                        tvTitle.text = actionData.data?.title

                        chapterDetailAdapter.setData(chapterDetail = chapterDetail)
                    }
                }
            })
        }
    }

    private fun goToPreviousChap() {
        val prevChap = chapterDetailAdapter.getChapterDetail()?.prevChap
        logD("goToPreviousChap prevChap " + BaseApplication.gson.toJson(prevChap))
        if (prevChap == null || prevChap.id.isNullOrEmpty()) {
            showLongInformation(getString(R.string.no_data))
        } else {
            comicViewModel?.getChapterDetail(chapId = prevChap.id)
        }
    }

    private fun goToNextChap() {
        val nextChap = chapterDetailAdapter.getChapterDetail()?.nextChap
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
        val horizontalPos = RelativePopupWindow.HorizontalPosition.RIGHT
        popup.onClickShare = {
            LSocialUtil.shareApp(activity = this)
        }
        popup.onClickDownload = {
            //TODO loitpp iplm
            showLongInformation(getString(R.string.coming_soon))
        }
        popup.showOnAnchor(anchorView, verticalPos, horizontalPos, false)
    }
}
