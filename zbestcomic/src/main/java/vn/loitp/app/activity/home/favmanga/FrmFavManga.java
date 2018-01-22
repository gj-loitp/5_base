package vn.loitp.app.activity.home.favmanga;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.allmanga.DatabaseHandler;
import vn.loitp.app.activity.view.ComicItem;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.ComicData;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmFavManga extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private PlaceHolderView placeHolderView;
    private TextView tvMsg;
    private AVLoadingIndicatorView avi;
    private DatabaseHandler db;

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
        placeHolderView = (PlaceHolderView) view.findViewById(R.id.place_hoder_view);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        placeHolderView.getBuilder().setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        run();
        return view;
    }

    private void run() {
        if (ComicData.getInstance().getComicFavList() == null) {
            LLog.d(TAG, "run if");
            ComicData.getInstance().setComicFavList(db.getAllComicFav());
            setupUI();
        } else {
            LLog.d(TAG, "run else");
            setupUI();
        }
    }

    private void checkToShowMsg() {
        if (ComicData.getInstance().getComicFavList() == null || ComicData.getInstance().getComicFavList().isEmpty()) {
            tvMsg.setVisibility(View.VISIBLE);
        } else {
            tvMsg.setVisibility(View.GONE);
        }
    }

    private void setupUI() {
        //List<Comic> comicList = ComicData.getInstance().getComicFavList();
        if (ComicData.getInstance().getComicFavList() != null) {
            LLog.d(TAG, "setupUI size: " + ComicData.getInstance().getComicFavList().size());
            for (int i = 0; i < ComicData.getInstance().getComicFavList().size(); i++) {
                placeHolderView.addView(new ComicItem(getActivity(), ComicData.getInstance().getComicFavList().get(i), new ComicItem.Callback() {
                    @Override
                    public void onClick(Comic comic, int position) {
                        LLog.d(TAG, "onClick " + comic.getTitle());
                    }

                    @Override
                    public void onLongClick(Comic comic, int position) {
                        LLog.d(TAG, "onLongClick " + comic.getTitle() + ", isFav " + comic.isFav() + ", position: " + position);
                        showDialogFav(comic, position);
                    }
                }));
            }
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
                    db.updateComic(comic);

                    //ComicData.getInstance().getComicFavList().get(position).setFav(Constants.IS_NOT_FAV);

                    boolean isRemoved = ComicData.getInstance().getComicFavList().remove(comic);
                    if (isRemoved) {
                        placeHolderView.removeView(position);
                        //placeHolderView.refresh();
                        checkToShowMsg();
                    }

                    //placeHolderView.refresh();

                    //placeHolderView.refreshView(position);
                }
            });
        }
    }
}