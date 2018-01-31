package vn.loitp.app.activity.home.allmanga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.comicinfo.ComicInfoActivity;
import vn.loitp.app.activity.home.HomeMenuActivity;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.ComicData;
import vn.loitp.app.data.EventBusData;
import vn.loitp.app.helper.ComicUtils;
import vn.loitp.app.helper.comiclist.GetComicTask;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.app.model.comictype.ComicType;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxRecyclerView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmAllManga extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private Button btSelect;
    private List<ComicType> comicTypeList;
    private GetComicTask getComicTask;
    private DatabaseHandler db;
    private ParallaxRecyclerView recyclerView;
    private ComicAllAdapter comicAllAdapter;

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
        View view = inflater.inflate(R.layout.frm_all_manga, container, false);
        db = new DatabaseHandler(getActivity());
        btSelect = (Button) view.findViewById(R.id.bt_select);
        recyclerView = (ParallaxRecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        comicTypeList = ComicUtils.getComicTypeList();

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelect();
            }
        });

        run(0);

        return view;
    }

    private void showDialogSelect() {
        String[] items = new String[comicTypeList.size()];
        for (int i = 0; i < comicTypeList.size(); i++) {
            //LLog.d(TAG, i + " : " + photosetList.get(i).getTitle().getContent());
            items[i] = comicTypeList.get(i).getType();
        }
        LDialogUtil.showDialogList(getActivity(), getString(R.string.choose_type), items, new LDialogUtil.CallbackList() {
            @Override
            public void onClick(int position) {
                LLog.d(TAG, "onClick " + position);
                run(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (getComicTask != null) {
            getComicTask.cancel(true);
        }
        super.onDestroyView();
    }

    private void run(int position) {
        if (ComicData.getInstance().getComicList() == null || ComicData.getInstance().getComicList().isEmpty()) {
            getComicTask = new GetComicTask(getActivity(), db, comicTypeList.get(position).getUrl(), new GetComicTask.Callback() {
                @Override
                public void onSuccess(List<Comic> comicList) {
                    //LLog.d(TAG, "onSuccess: " + LSApplication.getInstance().getGson().toJson(comicList.get(0)));
                    ComicData.getInstance().setComicList(comicList);
                    setupUI();
                }

                @Override
                public void onError() {
                    ToastUtils.showShort("Error");
                }
            });
            if (getComicTask != null) {
                getComicTask.execute();
            }
        } else {
            setupUI();
        }
    }

    private void setupUI() {
        if (comicAllAdapter == null) {
            comicAllAdapter = new ComicAllAdapter(getActivity(), new ComicAllAdapter.Callback() {
                @Override
                public void onClick(Comic comic, int position) {
                    LLog.d(TAG, "onClick " + comic.getTitle());
                    Intent intent = new Intent(getActivity(), ComicInfoActivity.class);
                    intent.putExtra(Constants.KEY_COMIC, comic);
                    startActivity(intent);
                    LActivityUtil.tranIn(getActivity());
                }

                @Override
                public void onLongClick(Comic comic, int position) {
                    LLog.d(TAG, "onLongClick " + comic.getTitle() + ", isFav " + comic.isFav() + ", position: " + position);
                    showDialogFav(comic, position);
                }
            });
            recyclerView.setAdapter(comicAllAdapter);
        }
        if (ComicData.getInstance().getComicList() == null || ComicData.getInstance().getComicList().isEmpty()) {
            showDialogError(getString(R.string.cannot_get_comic_list));
        } else {
            LLog.d(TAG, "size: " + ComicData.getInstance().getComicList().size());
            comicAllAdapter.notifyDataSetChanged();
        }
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

                    comicAllAdapter.notifyItemChanged(position);
                    ComicData.getInstance().setComicList(db.getAllComic());

                    EventBusData.getInstance().sendComicChange(Constants.COMIC_IS_REMOVE, comic, TAG);
                }
            });
        } else {
            LDialogUtil.showDialog2(getActivity(), getString(R.string.warning), "Bạn muốn thêm " + comic.getTitle() + " vào danh sách yêu thích? ", getString(R.string.no), getString(R.string.insert), new LDialogUtil.Callback2() {
                @Override
                public void onClick1() {
                    //do nothing
                }

                @Override
                public void onClick2() {
                    comic.setFav(Constants.IS_FAV);
                    db.updateComic(comic);

                    comicAllAdapter.notifyItemChanged(position);
                    ComicData.getInstance().setComicList(db.getAllComic());

                    EventBusData.getInstance().sendComicChange(Constants.COMIC_IS_INSERT, comic, TAG);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData.ComicChangeEvent comicChangeEvent) {
        LLog.d(TAG, TAG + "onMessageEvent comicChangeEvent");
        if (getActivity() instanceof HomeMenuActivity) {
            if (!((HomeMenuActivity) getActivity()).getCurrentSearchKeyword().isEmpty()) {
                LLog.d(TAG, "dont update list because user is searching");
                return;
            }
        }
        if (comicChangeEvent != null) {
            if (comicChangeEvent.getTag().equalsIgnoreCase(TAG)) {
                LLog.d(TAG, "event from " + TAG + " -> do nothing");
            } else {
                LLog.d(TAG, "need to update UI");
                ComicData.getInstance().setComicList(db.getAllComic());
                comicAllAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusData.SearchEvent searchEvent) {
        LLog.d(TAG, TAG + "onMessageEvent searchEvent " + LSApplication.getInstance().getGson().toJson(searchEvent));
        if (searchEvent != null) {
            if (searchEvent.getTAG().equalsIgnoreCase(Constants.MENU_ALL_MANGA)) {
                LLog.d(TAG, "filter with " + searchEvent.getKeyword());
                ComicData.getInstance().filterComicListWithKeyword(searchEvent.getKeyword());
                if (comicAllAdapter != null) {
                    comicAllAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        }
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