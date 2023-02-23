package vn.loitp.up.a.cv.iv.stfaiconIv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.stfalcon.imageviewer.StfalconImageViewer
import vn.loitp.R
import vn.loitp.databinding.AIvStfaiconListBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie
import vn.loitp.up.common.Constants

@LogTag("ListActivity")
@IsFullScreen(false)
class ListActivity : BaseActivityFont() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var stfAdapter: StfAdapter? = null
    private lateinit var binding: AIvStfaiconListBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvStfaiconListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private var stf: StfalconImageViewer<Movie>? = null
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ListActivity::class.java.simpleName
        }
        stfAdapter = StfAdapter(
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
                        .inflate(R.layout.v_stf_overlay, null)
                    val bt = viewOverLay.findViewById<AppCompatButton>(R.id.bt)
                    bt.setOnClickListener {
                        showShortInformation("Click " + stf?.currentPosition())
                    }

                    stf = StfalconImageViewer.Builder(
                        this@ListActivity,
                        moviesList
                    ) { imageView, mv ->
                        imageView.loadGlide(
                            mv.cover,
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

                            val updateIv = binding.rv.layoutManager?.findViewByPosition(pos)
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
        binding.rv.layoutManager = GridLayoutManager(this, 5)
        binding.rv.adapter = stfAdapter

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
