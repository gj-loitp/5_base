package vn.loitp.up.a.cv.rv.normalRv

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showPopup
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import vn.loitp.R
import vn.loitp.databinding.ARvBinding
import vn.loitp.up.common.Constants

// https://github.com/wasabeef/recyclerview-animators
@LogTag("RecyclerViewActivity")
@IsFullScreen(false)
class RecyclerViewActivity : BaseActivityFont() {
    private lateinit var binding: ARvBinding

    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecyclerViewActivity::class.java.simpleName
        }

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 300
        binding.rv.itemAnimator = animator
        binding.btAdd3.setOnClickListener {
            val movie = Movie()
            movie.title = "Add TITLE 3"
            movie.year = "Add YEAR 3"
            movie.genre = "Add GENRE 3"
            movieList.add(index = 3, element = movie)
            moviesAdapter?.notifyItemInserted(3)
        }
        binding.btRemove1.setOnClickListener {
            movieList.removeAt(index = 1)
            moviesAdapter?.notifyItemRemoved(1)
        }
        moviesAdapter = MoviesAdapter(
            moviesList = movieList,
            callback = object : MoviesAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    val isRemoved = movieList.remove(movie)
                    if (isRemoved) {
                        moviesAdapter?.notifyItemRemoved(position)
                        moviesAdapter?.notifyItemRangeChanged(position, movieList.size)
                    }
                }

                override fun onLoadMore() {
                    loadMore()
                }
            }
        )
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = mLayoutManager

        moviesAdapter?.let {
            val scaleAdapter = ScaleInAnimationAdapter(it)
            scaleAdapter.setDuration(1000)
            scaleAdapter.setInterpolator(OvershootInterpolator())
            scaleAdapter.setFirstOnly(true)
            binding.rv.adapter = scaleAdapter
        }
        prepareMovieData()
        binding.btSetting.setOnClickListener {
            this.showPopup(
                showOnView = it,
                menuRes = R.menu.menu_recycler_view,
                callback = { menuItem ->
                    binding.tvType.text = menuItem.title.toString()
                    when (menuItem.itemId) {
                        R.id.menuLinearVertical -> {
                            val lmVertical: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@RecyclerViewActivity)
                            binding.rv.layoutManager = lmVertical
                        }
                        R.id.menuLinearHorizontal -> {
                            val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(
                                this@RecyclerViewActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            binding.rv.layoutManager = lmHorizontal
                        }
                        R.id.menuGridLayoutManager2 ->
                            binding.rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewActivity, 2)
                        R.id.menuGridLayoutManager3 ->
                            binding.rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewActivity, 3)
                        R.id.menuStaggeredGridLayoutManager2 ->
                            binding.rv.layoutManager =
                                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        R.id.menuStaggeredGridLayoutManager4 ->
                            binding.rv.layoutManager =
                                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                    }
                }
            )
        }
    }

    private fun loadMore() {
        setDelay(
            mls = 2000,
            runnable = {
                val newSize = 5
                for (i in 0 until newSize) {
                    val movie = Movie(
                        title = "Add new $i",
                        genre = "Add new $i",
                        year = "Add new: $i",
                        cover = Constants.URL_IMG
                    )
                    movieList.add(movie)
                }
                moviesAdapter?.notifyAllViews()
                showShortInformation("Finish loadMore")
            }
        )
    }

    private fun prepareMovieData() {
        for (i in 0..99) {
            val movie = Movie(
                title = "Loitp $i",
                genre = "Action & Adventure $i",
                year = "Year: $i",
                cover = Constants.URL_IMG
            )
            movieList.add(movie)
        }
        moviesAdapter?.notifyAllViews()
    }
}
