package vn.loitp.app.a.cv.iv.stfaiconIv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_stfaicon_image_viewer_list.*
import kotlinx.android.synthetic.main.view_stf_overlay.view.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.normalRv.Movie
import vn.loitp.common.Constants

@LogTag("ListActivity")
@IsFullScreen(false)
class ListActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var stfAdapter: StfAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_stfaicon_image_viewer_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private var stf: StfalconImageViewer<Movie>? = null
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ListActivity::class.java.simpleName
        }
        stfAdapter = StfAdapter(
            context = this,
            moviesList = movieList,
            callback = object : StfAdapter.Callback {
                @SuppressLint("InflateParams")
                override fun onClick(
                    iv: ImageView,
                    movie: Movie,
                    moviesList: MutableList<Movie>,
                    position: Int
                ) {
                    val viewOverLay = LayoutInflater.from(this@ListActivity)
                        .inflate(R.layout.view_stf_overlay, null)
                    viewOverLay.bt.setOnClickListener {
                        showShortInformation("Click " + stf?.currentPosition())
                    }

                    stf = StfalconImageViewer.Builder(
                        this@ListActivity,
                        moviesList
                    ) { imageView, mv ->
                        LImageUtil.load(
                            this@ListActivity,
                            mv.cover,
                            imageView
                        )
                    }
                        .withBackgroundColorResource(R.color.black85)
                        .allowSwipeToDismiss(true)
                        .allowZooming(true)
                        .withHiddenStatusBar(false)
                        .withStartPosition(position)
                        .withOverlayView(viewOverLay)
                        .withTransitionFrom(iv)
                        .withImageChangeListener { pos ->
                            logD("withImageChangeListener pos $pos")

                            val updateIv = rv.layoutManager?.findViewByPosition(pos)
                                ?.findViewById(R.id.imageView) as ImageView?
                            updateIv?.let {
                                stf?.updateTransitionImage(it)
                            }
                        }
                        .withDismissListener {
                            logD("withDismissListener")
                        }
                        .show(true)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    val isRemoved = movieList.remove(movie)
                    if (isRemoved) {
                        stfAdapter?.apply {
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, movieList.size)
                        }
                    }
                }

                override fun onLoadMore() {
                    // do nothing
                }
            }
        )
        rv.layoutManager = GridLayoutManager(this, 5)
        rv.adapter = stfAdapter

        prepareMovieData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        var cover: String
        for (i in 0..99) {
            cover = if (i % 2 == 0) {
                Constants.URL_IMG_1
            } else {
                Constants.URL_IMG_2
            }
            val movie = Movie(
                title = "Loitp $i",
                genre = "Action & Adventure $i",
                year = "Year: $i",
                cover = cover
            )
            movieList.add(movie)
        }
        stfAdapter?.notifyDataSetChanged()
    }
}
