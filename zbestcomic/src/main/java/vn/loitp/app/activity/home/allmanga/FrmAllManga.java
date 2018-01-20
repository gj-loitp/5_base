package vn.loitp.app.activity.home.allmanga;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.view.ComicItem;
import vn.loitp.app.data.ComicData;
import vn.loitp.app.helper.ComicUtils;
import vn.loitp.app.helper.comiclist.GetComicTask;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.app.model.comictype.ComicType;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmAllManga extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private Button btSelect;

    private List<ComicType> comicTypeList;
    private PlaceHolderView placeHolderView;

    private GetComicTask getComicTask;
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
        View view = inflater.inflate(R.layout.frm_all_manga, container, false);
        db = new DatabaseHandler(getActivity());
        btSelect = (Button) view.findViewById(R.id.bt_select);
        placeHolderView = (PlaceHolderView) view.findViewById(R.id.place_hoder_view);

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
        List<Comic> comicList = ComicData.getInstance().getComicList();
        if (comicList == null || comicList.isEmpty()) {
            showDialogError(getString(R.string.cannot_get_comic_list));
        } else {
            for (int i = 0; i < comicList.size(); i++) {
                placeHolderView.addView(new ComicItem(getActivity(), comicList.get(i), i, new ComicItem.Callback() {
                    @Override
                    public void onClick(Comic comic, int position) {
                        LLog.d(TAG, "onClick " + comic.getTitle());
                    }
                }));
            }
        }
    }
}