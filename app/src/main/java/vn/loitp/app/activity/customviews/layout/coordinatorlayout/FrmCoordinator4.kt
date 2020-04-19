package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_coordinator_4.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants.URL_IMG

class FrmCoordinator4 : BaseFragment() {
    private val movieList = ArrayList<Movie>()
    private var mAdapter: MultiAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coordinator_4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = MultiAdapter(activity, movieList)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        prepareMovieData()
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private fun prepareMovieData() {
        for (i in 0..100) {
            val movie = Movie("Loitp $i", "Action & Adventure $i", "Year: $i", URL_IMG)
            movieList.add(movie)
        }
        mAdapter?.notifyDataSetChanged()
    }
}
