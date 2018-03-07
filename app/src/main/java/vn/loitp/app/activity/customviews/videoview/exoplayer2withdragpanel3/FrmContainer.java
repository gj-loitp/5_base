package vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel3;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData;
import vn.loitp.app.data.EventBusData;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDisplayUtils;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.draggablepanel.DraggablePanel;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmContainer extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private DraggablePanel draggablePanel;

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private TextView tvType;

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
        View view = inflater.inflate(R.layout.frm_container, container, false);
        draggablePanel = (DraggablePanel) view.findViewById(R.id.draggable_panel);
        initializeDraggablePanel();

        /*draggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                LLog.d(TAG, "onMaximized");
            }

            @Override
            public void onMinimized() {
                LLog.d(TAG, "onMinimized");
            }

            @Override
            public void onClosedToLeft() {
                LLog.d(TAG, "onClosedToLeft");
            }

            @Override
            public void onClosedToRight() {
                LLog.d(TAG, "onClosedToRight");
            }
        });*/

        setupUI(view);
        return view;
    }

    private void initializeDraggablePanel() throws Resources.NotFoundException {
        FrmTop frmTop = new FrmTop();
        FrmBottom frmBottom = new FrmBottom();
        draggablePanel.setFragmentManager(getActivity().getSupportFragmentManager());
        draggablePanel.setTopFragment(frmTop);
        draggablePanel.setBottomFragment(frmBottom);

        //draggablePanel.setXScaleFactor(xScaleFactor);
        //draggablePanel.setYScaleFactor(yScaleFactor);

        int widthScreen = LDisplayUtils.getScreenW(getActivity());
        int heightOfVideo = widthScreen * 9 / 16;

        draggablePanel.setTopViewHeight(heightOfVideo);//px
        draggablePanel.setEnableHorizontalAlphaEffect(false);
        //draggablePanel.setTopFragmentMarginRight(topViewMarginRight);
        //draggablePanel.setTopFragmentMarginBottom(topViewMargnBottom);
        draggablePanel.setClickToMaximizeEnabled(true);
        draggablePanel.setClickToMinimizeEnabled(false);
        draggablePanel.initializeView();
    }

    private void setupUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        tvType = (TextView) view.findViewById(R.id.tv_type);

        mAdapter = new MoviesAdapter(DummyData.getInstance().getMovieList(), new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                if (draggablePanel.isClosedAtLeft() || draggablePanel.isClosedAtRight()) {
                    LLog.d(TAG, "isClosedAtLeft || isClosedAtRight");
                    draggablePanel.minimize();
                    if (draggablePanel.getVisibility() != View.VISIBLE) {
                        draggablePanel.setVisibility(View.VISIBLE);
                    }
                } else {
                    LLog.d(TAG, "do nothing");
                }
                EventBusData.getInstance().sendClickVideoEvent(movie, position);
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
                loadMore();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();

        view.findViewById(R.id.bt_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LPopupMenu.show(getActivity(), v, R.menu.menu_recycler_view, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        tvType.setText(menuItem.getTitle().toString());
                        switch (menuItem.getItemId()) {
                            case R.id.menu_linear_vertical:
                                RecyclerView.LayoutManager lmVertical = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(lmVertical);
                                break;
                            case R.id.menu_linear_horizontal:
                                RecyclerView.LayoutManager lmHorizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(lmHorizontal);
                                break;
                            case R.id.menu_gridlayoutmanager_2:
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                                break;
                            case R.id.menu_gridlayoutmanager_3:
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                                break;
                            case R.id.menu_staggeredgridlayoutmanager_2:
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                break;
                            case R.id.menu_staggeredgridlayoutmanager_4:
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
                                break;
                        }
                    }
                });
            }
        });
    }

    private void loadMore() {
        LLog.d(TAG, "loadMore");
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                int newSize = 5;
                for (int i = 0; i < newSize; i++) {
                    Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i);
                    DummyData.getInstance().getMovieList().add(movie);
                }
                mAdapter.notifyDataSetChanged();
                LToast.show(getActivity(), "Finish loadMore");
            }
        });
    }

    private void prepareMovieData() {
        if (DummyData.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i);
                DummyData.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}