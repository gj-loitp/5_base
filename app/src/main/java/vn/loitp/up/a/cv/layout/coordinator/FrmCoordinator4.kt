package vn.loitp.up.a.cv.layout.coordinator

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.getHeightOfView
import kotlinx.android.synthetic.main.f_coordinator_4.*
import vn.loitp.R
import vn.loitp.up.a.cv.rv.normalRv.Movie
import vn.loitp.up.common.Constants

@LogTag("FrmCoordinator4")
class FrmCoordinator4 : BaseFragment() {
    private var mAdapter: MultiAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.f_coordinator_4
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        prepareMovieData()
    }

    private fun setupViews() {
        mAdapter = MultiAdapter()
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter
    }

    private fun prepareMovieData() {
        val movieList = ArrayList<Movie>()
        for (i in 0..20) {
            val movie = Movie("Loitp $i", "Action & Adventure $i", "Year: $i", Constants.URL_IMG)
            movieList.add(movie)
        }
        mAdapter?.setList(movieList)
        logD("getHeightOfView recyclerView " + recyclerView.getHeightOfView())
    }
}
