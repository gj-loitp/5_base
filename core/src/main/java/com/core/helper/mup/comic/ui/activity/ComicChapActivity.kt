package com.core.helper.mup.comic.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.base.BaseFontActivity
import com.core.helper.mup.comic.adapter.ChapAdapter
import com.core.helper.mup.comic.adapter.ComicProgressAdapter
import com.core.helper.mup.comic.model.Comic
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.*
import com.interfaces.CallbackRecyclerView
import com.views.layout.swipeback.SwipeBackLayout
import com.views.setSafeOnClickListener
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.l_activity_comic_chap.*

@LogTag("ComicActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
@IsSwipeActivity(true)
class ComicChapActivity : BaseFontActivity() {

    companion object {
        const val KEY_COMIC = "KEY_COMIC"
    }

    private var comic: Comic? = null
    private val color = if (LUIUtil.isDarkTheme()) {
        R.color.dark900
    } else {
        R.color.whiteSmoke
    }
    private var concatAdapter = ConcatAdapter()
    private var chapAdapter: ChapAdapter? = null
    private var comicProgressAdapter = ComicProgressAdapter(150)
    private var comicViewModel: ComicViewModel? = null
    private var currentPageIndex = 0
    private var totalPage = Int.MAX_VALUE

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_comic_chap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupData()
        setupViews()
        setupViewModels()

        comicViewModel?.getChapterByComicId(comicId = comic?.id, pageIndex = currentPageIndex)
        LValidateUtil.isValidCoreComicMup()
    }

    private fun setupData() {
        intent?.getSerializableExtra(KEY_COMIC)?.let {
            if (it is Comic) {
                comic = it
            }
        }
    }

    private fun setupViews() {
        toolbar.title = comic?.title
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val transform = MultiTransformation(
                BlurTransformation(25),
                ColorFilterTransformation(LAppResource.getColor(R.color.black50))
        )
        LImageUtil.load(
                context = this,
                any = comic?.imageSrc,
                imageView = imgCover,
                resPlaceHolder = color,
                resError = color,
                transformation = transform,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
        LImageUtil.load(
                context = this,
                any = comic?.imageSrc,
                imageView = ivAvatar,
                resPlaceHolder = color,
                resError = color,
                transformation = CropCircleWithBorderTransformation(0, Color.TRANSPARENT),
                drawableRequestListener = null
        )
        chapAdapter = ChapAdapter()
        chapAdapter?.let {
            it.onClickRoot = { chap ->
                val intent = Intent(this, ComicReadActivity::class.java)
                intent.putExtra(ComicReadActivity.KEY_CHAP, chap)
                startActivity(intent)
                LActivityUtil.tranIn(this)
            }
            concatAdapter.addAdapter(it)
        }
        concatAdapter.addAdapter(comicProgressAdapter)

        rvComicChap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvComicChap.adapter = concatAdapter
        LUIUtil.setScrollChange(
                recyclerView = rvComicChap,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                    }

                    override fun onBottom() {
                        val isExistComicProgressAdapter = concatAdapter.adapters.firstOrNull { adapter ->
                            adapter.javaClass.simpleName == ComicProgressAdapter::class.java.simpleName
                        }
//                        logD("onBottom isExistComicProgressAdapter $isExistComicProgressAdapter")
                        if (isExistComicProgressAdapter == null) {
                            logD("onBottom $currentPageIndex/$totalPage")
                            if (currentPageIndex < totalPage) {
                                currentPageIndex++
                                concatAdapter.addAdapter(comicProgressAdapter)
                                rvComicChap.smoothScrollToPosition(concatAdapter.itemCount - 1)

                                comicViewModel?.getChapterByComicId(comicId = comic?.id, pageIndex = currentPageIndex)
                            }
                        }
                    }

                    override fun onScrolled(isScrollDown: Boolean) {
                    }
                })

        fabLike.setSafeOnClickListener {
            //TODO loitpp iplm
            showLongInformation(getString(R.string.coming_soon))
        }
        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@ComicChapActivity)
                }
            }
        })
    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
            vm.listChapActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == false && isSuccess == true) {
                    val listChap = actionData.data
                    if (listChap.isNullOrEmpty()) {
                        if (currentPageIndex == 0) {
                            lottieAnimationViewNoData.visibility = View.VISIBLE
                            rvComicChap.visibility = View.GONE
                        }
                        concatAdapter.removeAdapter(comicProgressAdapter)
                    } else {
                        totalPage = actionData.totalPages ?: 0

                        lottieAnimationViewNoData.visibility = View.GONE
                        rvComicChap.visibility = View.VISIBLE
                        concatAdapter.removeAdapter(comicProgressAdapter)
                        chapAdapter?.setListData(listChap = listChap)
                    }
                }
            })
        }
    }
}
