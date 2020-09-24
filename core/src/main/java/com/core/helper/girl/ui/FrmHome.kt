package com.core.helper.girl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.girl.adapter.*
import com.core.helper.girl.model.GirlTopUser
import com.core.helper.girl.model.GirlTopVideo
import com.core.helper.girl.viewmodel.GirlViewModel
import com.core.utilities.LUIUtil
import com.google.gson.Gson
import com.interfaces.CallbackRecyclerView
import kotlinx.android.synthetic.main.l_frm_girl_home.*

@LogTag("loitppFrmHome")
class FrmHome : BaseFragment() {

    private var girlViewModel: GirlViewModel? = null
    private var mergeAdapter: MergeAdapter? = null
    private var girlHeaderAdapter: GirlHeaderAdapter? = null
    private var girlTopUserAdapter: GirlTopUserAdapter? = null
    private var girlTopVideoAdapter: GirlTopVideoAdapter? = null
    private var girlAlbumAdapter: GirlAlbumAdapter? = null
    private var girlProgressAdapter: GirlProgressAdapter? = null
    private var currentPageIndex = 0
    private var totalPage = Int.MAX_VALUE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_girl_home, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
        getPage(isSwipeToRefresh = false)
        getListGirlTopUser()
        getListGirlTopVideo()
    }

    private fun getPage(isSwipeToRefresh: Boolean) {
        val keyword = ""//TODO
        girlViewModel?.getPage(pageIndex = currentPageIndex, keyWord = keyword, isSwipeToRefresh = isSwipeToRefresh)
    }

    private fun getListGirlTopUser() {
        val listGirlTopUser = ArrayList<GirlTopUser>()

        val girlTopUserLoi = GirlTopUser(
                avatar = "https://live.staticflickr.com/8612/28213046065_796c678f46_q.jpg",
                name = "Lợi Dubai"
        )
        listGirlTopUser.add(element = girlTopUserLoi)

        val girlTopUserToai = GirlTopUser(
                avatar = "https://live.staticflickr.com/5712/30595575736_2f7825cdec_q.jpg",
                name = "Toại Titu"
        )
        listGirlTopUser.add(element = girlTopUserToai)

        val girlTopUserHung = GirlTopUser(
                avatar = "https://live.staticflickr.com/65535/49424976107_5e9e680df8_q.jpg",
                name = "Hưng Cu Đen"
        )
        listGirlTopUser.add(element = girlTopUserHung)

        val girlTopUserTuan = GirlTopUser(
                avatar = "https://live.staticflickr.com/65535/49424972522_254064a97d_q.jpg",
                name = "Tuấn Liệt"
        )
        listGirlTopUser.add(element = girlTopUserTuan)

        val girlTopUserTien = GirlTopUser(
                avatar = "https://live.staticflickr.com/65535/48111902461_f316050394_q.jpg",
                name = "Tiên Giả"
        )
        listGirlTopUser.add(element = girlTopUserTien)

        girlTopUserAdapter?.setListGirlTopUser(listGirlTopUser)
    }

    private fun getListGirlTopVideo() {
        val listGirlTopVideo = ArrayList<GirlTopVideo>()

        var girlTopVideo = GirlTopVideo(
                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
                title = "'24/365 with BLACKPINK' EP.12",
                link = "https://www.youtube.com/watch?v=Is0iob8lz4w"
        )
        listGirlTopVideo.add(element = girlTopVideo)

        girlTopVideo = GirlTopVideo(
                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
                title = "'How You Like That' M/V",
                link = "https://www.youtube.com/watch?v=ioNng23DkIM"
        )
        listGirlTopVideo.add(element = girlTopVideo)

        girlTopVideo = GirlTopVideo(
                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
                title = "LIKEY M/V",
                link = "https://www.youtube.com/watch?v=V2hlQkVJZhE"
        )
        listGirlTopVideo.add(element = girlTopVideo)

        girlTopVideo = GirlTopVideo(
                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
                title = "'붐바야'(BOOMBAYAH) M/V",
                link = "https://www.youtube.com/watch?v=bwmSjveL3Lc"
        )
        listGirlTopVideo.add(element = girlTopVideo)

        girlTopVideoAdapter?.setListGirlTopUser(listGirlTopVideo)
    }

    private fun setupViews() {
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        LUIUtil.setProgressViewOffset(swipeRefreshLayout = swipeRefreshLayout, topMargin = 120)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            currentPageIndex = 0
            getPage(isSwipeToRefresh = true)
        }
        girlHeaderAdapter = GirlHeaderAdapter()
        val girlTitleAdapterTopUser = GirlTitleAdapter()
        girlTitleAdapterTopUser.setTitle(getString(R.string.top_user))
        girlTopUserAdapter = GirlTopUserAdapter()
        val girlTitleAdapterVideo = GirlTitleAdapter()
        girlTitleAdapterVideo.setTitle(getString(R.string.video))
        girlTopVideoAdapter = GirlTopVideoAdapter()
        val girlTitleAdapterAlbum = GirlTitleAdapter()
        girlTitleAdapterAlbum.setTitle(getString(R.string.album))
        girlAlbumAdapter = GirlAlbumAdapter()
        girlProgressAdapter = GirlProgressAdapter()

        girlAlbumAdapter?.onClickRootListener = { girlPage, position ->
            logD("onClickRootListener girlAlbumAdapter $position -> " + Gson().toJson(girlPage))
            //TODO
        }
        girlTopUserAdapter?.onClickRootView = { girlTopUser ->
            //TODO
        }
        girlTopVideoAdapter?.onClickRootView = { girlTopVideo ->
            LUIUtil.playYoutube(activity = activity, url = girlTopVideo.link)
        }

        girlHeaderAdapter?.let { gha ->
            girlTitleAdapterTopUser.let { gtatu ->
                girlTopUserAdapter?.let { gtua ->
                    girlTitleAdapterVideo.let { gtav ->
                        girlTopVideoAdapter?.let { gtva ->
                            girlTitleAdapterAlbum.let { gtaa ->
                                girlAlbumAdapter?.let { gaa ->
                                    val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gha, gtatu, gtua, gtav, gtva, gtaa, gaa)
                                    mergeAdapter = MergeAdapter(listOfAdapters)
                                }
                            }
                        }
                    }
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mergeAdapter

        LUIUtil.setScrollChange(
                recyclerView = recyclerView,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                        logD("onTop")
                    }

                    override fun onBottom() {
                        logD("onBottom $currentPageIndex/$totalPage")
                        if (currentPageIndex < totalPage) {
                            currentPageIndex++
                            girlProgressAdapter?.let { gpa ->
                                mergeAdapter?.let { ma ->
                                    ma.addAdapter(gpa)
                                    recyclerView.smoothScrollToPosition(ma.itemCount - 1)
                                }
                            }
                            getPage(isSwipeToRefresh = false)
                        }
                    }
                })

        ivSearch.setOnClickListener {
            if (cardViewSearch.visibility == View.VISIBLE) {
                cardViewSearch.visibility = View.INVISIBLE
                tvToday.visibility = View.VISIBLE
                tvHottestShot.visibility = View.VISIBLE
            } else {
                cardViewSearch.visibility = View.VISIBLE
                tvToday.visibility = View.INVISIBLE
                tvHottestShot.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
//                logD("userActionLiveData $actionData")
                val isDoing = actionData.isDoing
                swipeRefreshLayout.isRefreshing = isDoing == true

                if (isDoing == false && actionData.isSuccess == true) {
                    val listGirlPage = actionData.data
//                    logD("listGirlPage " + Gson().toJson(listGirlPage))
                    if (listGirlPage.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        totalPage = actionData.totalPages ?: 0

                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlProgressAdapter?.let {
                            mergeAdapter?.removeAdapter(it)
                        }
                        girlHeaderAdapter?.setData(girlPage = listGirlPage.random())
                        girlAlbumAdapter?.setData(listGirlPage = listGirlPage, isSwipeToRefresh = actionData.isSwipeToRefresh
                                ?: false)

                    }
                }
            })
        }
    }
}
