package com.core.helper.mup.comic.ui.activity

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.mup.comic.model.Chap
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import com.views.layout.swipeback.SwipeBackLayout
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_comic_chap.swipeBackLayout
import kotlinx.android.synthetic.main.l_activity_comic_read.*

@LogTag("loitppComicActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
@IsSwipeActivity(true)
class ComicReadActivity : BaseFontActivity() {

    companion object {
        const val KEY_CHAP = "KEY_CHAP"
    }

    private var chap: Chap? = null
    private val color = if (LUIUtil.isDarkTheme()) {
        R.color.dark900
    } else {
        R.color.whiteSmoke
    }
    private var comicViewModel: ComicViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_comic_read
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupData()
        setupViews()
        setupViewModels()

        comicViewModel?.getChapterDetail(chapId = chap?.id)
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
        ivBack.setSafeOnClickListener {
            onBackPressed()
        }
        ivMenu.setSafeOnClickListener {
            //TODO loitpp iplm
        }
    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
            vm.chapterDetailActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                logD("<<<chapterDetailActionLiveData observe isDoing $isDoing, isSuccess $isSuccess")

                if (isDoing == false && isSuccess == true) {
                    val listComic = actionData.data
//                    if (listComic.isNullOrEmpty()) {
//                        if (currentPageIndex == 0) {
//                            tvNoData.visibility = View.VISIBLE
//                            recyclerView.visibility = View.GONE
//                        }
//                        comicProgressAdapter?.let {
//                            concatAdapter?.removeAdapter(it)
//                        }
//                    } else {
//                        totalPage = actionData.totalPages ?: 0
//
//                        tvNoData.visibility = View.GONE
//                        recyclerView.visibility = View.VISIBLE
//                        comicProgressAdapter?.let {
//                            concatAdapter?.removeAdapter(it)
//                        }
//                        comicHeaderAdapter?.setData(comic = listComic.random())
//                        comicAdapter?.setData(listComic = listComic, isSwipeToRefresh = isSwipeToRefresh)
//                    }
                }
            })
        }
    }
}
