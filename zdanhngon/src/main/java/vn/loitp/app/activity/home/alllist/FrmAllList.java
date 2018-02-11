package vn.loitp.app.activity.home.alllist;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.DataManager;
import vn.loitp.app.model.Idea;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmAllList extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private AdView adView;
    private DataManager dataManager;
    private RecyclerView recyclerView;
    private IdeaAdapter ideaAdapter;
    private String currentTableName;

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
        View view = inflater.inflate(R.layout.frm_all_list, container, false);
        adView = (AdView) view.findViewById(R.id.adView);
        LUIUtil.createAdBanner(adView);

        Bundle bundle = getArguments();
        if (bundle == null) {
            LLog.d(TAG, "bundle == null");
            showDialogError(getString(R.string.err_unknow));
            return view;
        }
        currentTableName = bundle.getString(Constants.MENU_TABLE_NAME);
        if (currentTableName == null || currentTableName.isEmpty()) {
            LLog.d(TAG, "currentTableName == null || currentTableName.isEmpty()");
            showDialogError(getString(R.string.err_unknow));
            return view;
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        dataManager = new DataManager(getActivity());
        try {
            dataManager.createDatabase();
            LLog.d(TAG, "init dtb success");
        } catch (IOException e) {
            LLog.d(TAG, "init dtb failed: " + e.toString());
        }
        List<Idea> ideaList = new ArrayList<>();
        ideaList.addAll(dataManager.getAllIdea(currentTableName));

        LLog.d(TAG, "size: " + ideaList.size());

        ideaAdapter = new IdeaAdapter(getActivity(), ideaList, new IdeaAdapter.Callback() {

            @Override
            public void onClickCopy(Idea idea, int position) {
                LDeviceUtil.setClipboard(getActivity(), idea.getContent());
                LToast.show(getActivity(), getString(R.string.copied));
            }

            @Override
            public void onClickShare(Idea idea, int position) {
                LSocialUtil.share(getActivity(), idea.getContent());
            }

            @Override
            public void onClickFav(Idea idea, int position) {
                if (idea.getIsFav() == Constants.IS_FAV) {
                    idea.setIsFav(Constants.IS_NOT_FAV);
                } else {
                    idea.setIsFav(Constants.IS_FAV);
                }
                dataManager.updateIdea(currentTableName, idea);
                ideaAdapter.notifyItemChanged(position);
            }

            @Override
            public void onLoadMore() {
                //do nothing
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ideaAdapter);
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
}