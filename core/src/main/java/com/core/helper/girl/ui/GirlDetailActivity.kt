package com.core.helper.girl.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.girl.adapter.GirlDetailAdapter
import com.core.helper.girl.model.GirlPage
import com.core.helper.girl.viewmodel.GirlViewModel
import com.core.utilities.LActivityUtil
import com.core.utilities.LImageUtil
import com.views.layout.swipeback.SwipeBackLayout
import kotlinx.android.synthetic.main.l_activity_girl_detail.*

@LogTag("loitppGirlActivity")
@IsFullScreen(false)
class GirlDetailActivity : BaseFontActivity() {

    private var girlPage: GirlPage? = null
    private var girlViewModel: GirlViewModel? = null
    private var mergeAdapter: MergeAdapter? = null
    private var girlDetailAdapter: GirlDetailAdapter? = null

    companion object {
        const val KEY_GIRL_PAGE = "KEY_GIRL_PAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl_detail)

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
        LImageUtil.load(context = this, url = girlPage?.src, imageView = ivCover)
        collapsingToolbarLayout.title = girlPage?.title
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowTitleEnabled(true)
        }
        toolbar.navigationIcon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.WHITE, BlendModeCompat.SRC_ATOP)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAniamtion(this@GirlDetailActivity)
                }
            }
        })
        girlDetailAdapter = GirlDetailAdapter()
        girlDetailAdapter?.let { gda ->
            gda.onClickRootListener = { girlPageDetail, position ->
                //TODO
            }

            val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gda)
            mergeAdapter = MergeAdapter(listOfAdapters)
        }
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mergeAdapter
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { vm ->
            vm.pageDetailActionLiveData.observe(this, Observer { actionData ->
//                logD("pageDetailActionLiveData " + Gson().toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    val listGirlPageDetail = actionData.data
                    if (listGirlPageDetail.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlDetailAdapter?.setData(listGirlPageDetail = listGirlPageDetail)
                    }
                }
            })
        }
    }
}
