package vn.loitp.app.activity.home.favmanga;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.allmanga.DatabaseHandler;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.ComicData;
import vn.loitp.app.data.EventBusData;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxRecyclerView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmFavManga extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView tvMsg;
    private AVLoadingIndicatorView avi;
    private DatabaseHandler db;
    private ParallaxRecyclerView recyclerView;
    private ComicFavAdapter comicFavAdapter;

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
        View view = inflater.inflate(R.layout.frm_fav_manga, container, false);
        db = new DatabaseHandler(getActivity());
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        recyclerView = (ParallaxRecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        run();
        return view;
    }

    private void run() {
        if (ComicData.getInstance().getComicFavList() == null) {
            LLog.d(TAG, "run if");
            ComicData.getInstance().setComicFavList(db.getAllComicFav());
        } else {
            LLog.d(TAG, "run else");
        }
        setupUI();
    }

    private void checkToShowMsg() {
        if (ComicData.getInstance().getComicFavList() == null || ComicData.getInstance().getComicFavList().isEmpty()) {
            tvMsg.setVisibility(View.VISIBLE);
        } else {
            tvMsg.setVisibility(View.GONE);
        }
    }

    private void setupUI() {
        if (comicFavAdapter == null) {
            comicFavAdapter = new ComicFavAdapter(getActivity(), new ComicFavAdapter.Callback() {
                @Override
                public void onClick(Comic comic, int position) {
                    LLog.d(TAG, "onClick " + comic.getTitle());
                }

                @Override
                public void onLongClick(Comic comic, int position) {
                    //LLog.d(TAG, "onLongClick " + comic.getTitle() + ", isFav " + comic.isFav() + ", position: " + position);
                    showDialogFav(comic, position);
                }
            });
            recyclerView.setAdapter(comicFavAdapter);
        }
        if (ComicData.getInstance().getComicFavList() != null) {
            LLog.d(TAG, "setupUI size: " + ComicData.getInstance().getComicFavList().size());
            comicFavAdapter.notifyDataSetChanged();
        }
        avi.smoothToHide();
        checkToShowMsg();
    }

    private void showDialogFav(Comic comic, int position) {
        if (comic.isFav() == Constants.IS_FAV) {
            LDialogUtil.showDialog2(getActivity(), getString(R.string.warning), "Bạn muốn xóa " + comic.getTitle() + " khỏi danh sách yêu thích? ", getString(R.string.no), getString(R.string.delete), new LDialogUtil.Callback2() {
                @Override
                public void onClick1() {
                    //do nothing
                }

                @Override
                public void onClick2() {
                    comic.setFav(Constants.IS_NOT_FAV);
                    int rowEffected = db.updateComic(comic);
                    LLog.d(TAG, "rowEffected " + rowEffected);
                    if (rowEffected == 1) {
                        boolean isRemoved = ComicData.getInstance().getComicFavList().remove(comic);
                        LLog.d(TAG, "isRemoved getComicFavList " + isRemoved);
                        if (isRemoved) {
                            comicFavAdapter.notifyItemRemoved(position);
                            comicFavAdapter.notifyItemRangeChanged(position, ComicData.getInstance().getComicFavList().size());
                            ComicData.getInstance().setComicFavList(db.getAllComicFav());
                            EventBusData.getInstance().sendComicChange(Constants.COMIC_IS_REMOVE, comic, TAG);
                        }
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData.ComicChangeEvent comicChangeEvent) {
        LLog.d(TAG, TAG + "onMessageEvent comicChangeEvent");
        if (comicChangeEvent != null) {
            if (comicChangeEvent.getTag().equalsIgnoreCase(TAG)) {
                LLog.d(TAG, "event from " + TAG + " -> do nothing");
            } else {
                LLog.d(TAG, "need to update UI");
                ComicData.getInstance().setComicFavList(db.getAllComicFav());
                comicFavAdapter.notifyDataSetChanged();
            }
        }
        checkToShowMsg();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}