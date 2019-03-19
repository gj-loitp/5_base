package vn.loitp.app.activity.demo.film.grouprecyclerviewvertical;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.common.Constants;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGReVVertical extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();

    public interface Callback {
        public void onClickRemove();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public VGReVVertical(Context context) {
        super(context);
        findViews();
    }

    public VGReVVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        findViews();
    }

    public VGReVVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        findViews();
    }

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    private void findViews() {
        inflate(getContext(), R.layout.vg_rv_vertical, this);

        findViewById(R.id.bt_remove).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickRemove();
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        //recyclerView.getItemAnimator().setAddDuration(1000);
        mAdapter = new MoviesAdapter(getContext(), movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getContext(), "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                boolean isRemoved = movieList.remove(movie);
                if (isRemoved) {
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, movieList.size());
                }
            }

            @Override
            public void onLoadMore() {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //recyclerView.setAdapter(mAdapter);

        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //alphaAdapter.setDuration(1000);
        //alphaAdapter.setInterpolator(new OvershootInterpolator());
        //alphaAdapter.setFirstOnly(true);
        //recyclerView.setAdapter(alphaAdapter);

        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
    }

    private void prepareMovieData() {
        for (int i = 0; i < 50; i++) {
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.URL_IMG);
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}