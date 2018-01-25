package vn.loitp.app.activity.comicinfo.chap;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmChap extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private AdView adView;

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_comic_chap, container, false);
        adView = (AdView) view.findViewById(R.id.adView);
        LUIUtil.createAdBanner(adView);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(DummyData.getInstance().getMovieList(), new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getActivity(), "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                //do nothing
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
        return view;
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

    private void loadMore() {
        LLog.d(TAG, "loadMore");
    }

    private void prepareMovieData() {
        if (DummyData.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 100; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i);
                DummyData.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}