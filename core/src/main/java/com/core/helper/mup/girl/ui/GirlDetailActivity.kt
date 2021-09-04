package com.core.helper.mup.girl.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.mup.girl.adapter.GirlDetailAdapter
import com.core.helper.mup.girl.model.GirlPage
import com.core.helper.mup.girl.viewmodel.GirlViewModel
import com.core.utilities.LActivityUtil
import com.core.utilities.LAnimationUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LImageUtil
import com.daimajia.androidanimations.library.Techniques
import com.views.LAppBarLayout
import com.views.layout.swipeback.SwipeBackLayout
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_girl_detail.*

@LogTag("GirlDetailActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
@IsSwipeActivity(true)
class GirlDetailActivity : BaseFontActivity() {

    private var girlPage: GirlPage? = null
    private var girlViewModel: GirlViewModel? = null
    private var mergeAdapter: ConcatAdapter? = null
    private var girlDetailAdapter: GirlDetailAdapter? = null

    companion object {
        const val KEY_GIRL_PAGE = "KEY_GIRL_PAGE"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_girl_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()
        setupViews()
        setupViewModels()

        girlViewModel?.getDetail(id = girlPage?.id)
    }

    private fun getData() {
        val tmpGirlPage = intent.getSerializableExtra(KEY_GIRL_PAGE)
        if (tmpGirlPage is GirlPage) {
            girlPage = tmpGirlPage
        }
    }

    private fun setupViews() {
        if (BuildConfig.DEBUG) {
            LImageUtil.load(context = this, any = Constants.URL_IMG, imageView = ivCover, resError = R.color.black, resPlaceHolder = R.color.black, drawableRequestListener = null)
        } else {
            LImageUtil.load(context = this, any = girlPage?.src, imageView = ivCover, resError = R.color.black, resPlaceHolder = R.color.black, drawableRequestListener = null)
        }
        ivCover.setAspectRatio(16f / 9f)
        collapsingToolbarLayout.title = girlPage?.title
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK)
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowTitleEnabled(true)
        }
        toolbar.navigationIcon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.BLACK, BlendModeCompat.SRC_ATOP)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View?, swipeBackFraction: Float, swipeBackFactor: Float) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GirlDetailActivity)
                }
            }
        })
        girlDetailAdapter = GirlDetailAdapter()
        girlDetailAdapter?.let { gda ->
            gda.onClickRootListener = { _, position ->
                val list = girlDetailAdapter?.getData()
                list?.let {
                    val intent = Intent(this, GirlSlideActivity::class.java)
                    intent.putExtra(GirlSlideActivity.KEY_POSITION, position)
                    intent.putExtra(GirlSlideActivity.KEY_LIST_DATA, it)
                    startActivity(intent)
                    LActivityUtil.tranIn(this)
                }
            }

            val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gda)
            mergeAdapter = ConcatAdapter(listOfAdapters)
        }
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mergeAdapter

        layoutAppBar.setOnStateChangeListener(object : LAppBarLayout.OnStateChangeListener {
            override fun onStateChange(toolbarChange: LAppBarLayout.State) {
                when (toolbarChange) {
                    LAppBarLayout.State.COLLAPSED -> {
                        //COLLAPSED appBarLayout min
                        LAnimationUtil.play(view = btLike, techniques = Techniques.ZoomOut, duration = 500)
                    }
                    LAppBarLayout.State.EXPANDED -> {
                        //EXPANDED appBarLayout max
                        LAnimationUtil.play(view = btLike, techniques = Techniques.ZoomIn, duration = 500)
                    }
                    else -> {
                        //IDLE appBarLayout not min not max
                    }
                }
            }
        })

        btLike.setSafeOnClickListener {
            girlPage?.let {
                girlViewModel?.likeGirlPage(girlPage = it)
            }
        }
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { vm ->
            vm.pageDetailActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    val listGirlPageDetail = actionData.data
                    logD("<<<pageDetailActionLiveData observe " + BaseApplication.gson.toJson(listGirlPageDetail))
                    if (listGirlPageDetail.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlDetailAdapter?.setData(listGirlPageDetail = listGirlPageDetail)

                        val isFavorites = listGirlPageDetail.all {
                            it.isFavorites
                        }
                        girlPage?.isFavorites = isFavorites
                        if (btLike.visibility != View.VISIBLE) {
                            btLike.visibility = View.VISIBLE
                            btLike.isChecked = isFavorites
                        }
                    }
                }
            })
            vm.likeGirlPageActionLiveData.observe(this, { actionData ->
//                logD("<<<likeGirlPageActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                }
                if (isDoing == false && actionData.isSuccess == true) {
//                    logD("<<<likeGirlPageActionLiveData observe " + BaseApplication.gson.toJson(actionData.data))
                    girlPage = actionData.data
                    if (girlPage?.isFavorites == true) {
                        showLongInformation(getString(R.string.added_to_favorites))
                    } else {
                        showLongInformation(getString(R.string.removed_from_favorites))
                    }
                }
            })
        }
    }
}
