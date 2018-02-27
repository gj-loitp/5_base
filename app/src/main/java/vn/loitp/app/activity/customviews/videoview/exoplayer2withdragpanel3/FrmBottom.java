package vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel3;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.views.LToast;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmBottom extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
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
        View view = inflater.inflate(R.layout.frm_bottom, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(DummyData.getInstance().getMovieList(), new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getActivity(), "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                boolean isRemoved = DummyData.getInstance().getMovieList().remove(movie);
                if (isRemoved) {
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, DummyData.getInstance().getMovieList().size());
                }
            }

            @Override
            public void onLoadMore() {
                //do nothing
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //LUIUtil.setPullLikeIOSHorizontal(recyclerView);

        //NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();

        LinearLayout llHorizontal = (LinearLayout) view.findViewById(R.id.ll_horizontal);
        for (int i = 0; i < 20; i++) {
            Button button = new Button(getActivity());
            button.setText("Button " + i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LToast.show(getActivity(), "Click " + button.getText().toString());
                }
            });
            llHorizontal.addView(button);
        }

        return view;
    }

    private void prepareMovieData() {
        if (DummyData.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 20; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i);
                DummyData.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}