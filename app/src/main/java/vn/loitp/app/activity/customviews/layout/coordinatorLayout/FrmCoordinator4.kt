package vn.loitp.app.activity.customviews.layout.coordinatorLayout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.frm_coordinator_4.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.Movie
import vn.loitp.app.common.Constants

@LogTag("FrmCoordinator4")
class FrmCoordinator4 : BaseFragment() {
    private var mAdapter: MultiAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coordinator_4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        logD("getHeightOfView recyclerView " + LUIUtil.getHeightOfView(recyclerView))
    }
}
