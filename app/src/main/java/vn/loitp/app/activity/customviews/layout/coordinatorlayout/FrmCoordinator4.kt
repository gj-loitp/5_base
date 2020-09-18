package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LayoutId
import com.core.base.BaseFragment
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.frm_coordinator_4.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants

@LayoutId(R.layout.frm_coordinator_4)
class FrmCoordinator4 : BaseFragment() {
    private var mAdapter: MultiAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = MultiAdapter()
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        prepareMovieData()
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private fun prepareMovieData() {
        val movieList = ArrayList<Movie>()
        for (i in 0..200) {
            val movie = Movie("Loitp $i", "Action & Adventure $i", "Year: $i", Constants.URL_IMG)
            movieList.add(movie)
        }
        mAdapter?.setList(movieList)
        logD("getHeightOfView recyclerView " + LUIUtil.getHeightOfView(recyclerView))
    }
}
