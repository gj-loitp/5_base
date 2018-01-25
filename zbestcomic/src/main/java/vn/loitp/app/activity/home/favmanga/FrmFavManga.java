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
import vn.loitp.app.app.LSApplication;
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
                    LLog.d(TAG, "onLongClick " + comic.getTitle() + ", isFav " + comic.isFav() + ", position: " + position);
                    showDialogFav(comic);
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

    private void showDialogFav(Comic comic) {
        if (comic.isFav() == Constants.IS_FAV) {
            LDialogUtil.showDialog2(getActivity(), getString(R.string.warning), "Bạn muốn xóa " + comic.getTitle() + " khỏi danh sách yêu thích? ", getString(R.string.no), getString(R.string.delete), new LDialogUtil.Callback2() {
                @Override
                public void onClick1() {
                    //do nothing
                }

                @Override
                public void onClick2() {
                    EventBusData.getInstance().sendComicChange(true, comic, TAG);
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
            }
        }
        /*if (comicChangeEvent != null) {
            LLog.d(TAG, "onMessageEvent comicChangeEvent " + comicChangeEvent.getComic().getTitle());
            Comic comic = comicChangeEvent.getComic();
            if (comic == null) {
                LLog.d(TAG, "comic null -> return");
                return;
            }
            if (comicChangeEvent.isRemoved()) {
                LLog.d(TAG, comic.getTitle() + " isRemoved true -> remove");
                int position = ComicData.getInstance().getComicFavList().indexOf(comic);
                LLog.d(TAG, "position " + position);
                if (position == -1) {
                    LLog.d(TAG, "Do not contain this comic in fav list -> try one more time");
                    comic.setFav(comic.isFav() == Constants.IS_FAV ? Constants.IS_NOT_FAV : Constants.IS_NOT_FAV);
                    position = ComicData.getInstance().getComicFavList().indexOf(comic);
                    LLog.d(TAG, "position 2nd: " + position);
                    if (position == -1) {
                        LLog.d(TAG, "Do not contain this comic in list after trying 2 times :(");
                        return;
                    }
                }
                boolean isRemoved = ComicData.getInstance().getComicFavList().remove(comic);
                LLog.d(TAG, "isRemoved getComicFavList " + isRemoved);
                if (isRemoved) {
                    comicFavAdapter.notifyItemRemoved(position);
                    comicFavAdapter.notifyItemRangeChanged(position, ComicData.getInstance().getComicFavList().size());
                }
                comic.setFav(Constants.IS_NOT_FAV);
                int rowEffected = db.updateComic(comic);
                LLog.d(TAG, "rowEffected " + rowEffected);
                LLog.d(TAG, "----------->Removed at position " + position);
            } else {
                LLog.d(TAG, comic.getTitle() + " isRemoved false -> add");
                comic.setFav(Constants.IS_FAV);
                db.updateComic(comic);

                //ComicData.getInstance().setComicFavList(db.getAllComicFav());

                comicFavAdapter.notifyItemInserted(ComicData.getInstance().getComicFavList().size() - 1);
                comicFavAdapter.notifyItemRangeChanged(ComicData.getInstance().getComicFavList().size() - 1, ComicData.getInstance().getComicFavList().size());
                LLog.d(TAG, "----------->Added " + comic.getTitle() + " in " + (ComicData.getInstance().getComicFavList().size() - 1));
            }
            checkToShowMsg();
        }*/
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