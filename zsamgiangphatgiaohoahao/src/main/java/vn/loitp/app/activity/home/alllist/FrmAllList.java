package vn.loitp.app.activity.home.alllist;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.HomeMenuActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.DataManager;
import vn.loitp.app.model.Category;
import vn.loitp.app.model.Msg;
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
    private RecyclerView recyclerView;
    private IdeaAdapter ideaAdapter;
    private Category currentCategory;
    private DataManager dataManager;

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

        dataManager = ((HomeMenuActivity) getActivity()).getDataManager();
        if (dataManager == null) {
            showDialogError(getString(R.string.err_unknow));
            return view;
        }

        Bundle bundle = getArguments();
        if (bundle == null) {
            LLog.d(TAG, "bundle == null");
            showDialogError(getString(R.string.err_unknow));
            return view;
        }
        currentCategory = (Category) bundle.getSerializable(Constants.MENU_CATEGORY);
        if (currentCategory == null) {
            LLog.d(TAG, "currentCategory == null || currentCategory.isEmpty()");
            showDialogError(getString(R.string.err_unknow));
            return view;
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        List<Msg> ideaList = new ArrayList<>();
        ideaList.addAll(dataManager.getAllMsg(currentCategory.getCategoryId()));

        LLog.d(TAG, "size: " + ideaList.size());

        ideaAdapter = new IdeaAdapter(getActivity(), ideaList, new IdeaAdapter.Callback() {

            @Override
            public void onClickCopy(Msg idea, int position) {
                LDeviceUtil.setClipboard(getActivity(), idea.getContent());
                LToast.show(getActivity(), getString(R.string.copied));
            }

            @Override
            public void onClickShare(Msg idea, int position) {
                LSocialUtil.share(getActivity(), idea.getContent());
            }

            @Override
            public void onClickFav(Msg idea, int position) {
                if (idea.getIsFav() == Constants.IS_FAV) {
                    idea.setIsFav(Constants.IS_NOT_FAV);
                } else {
                    idea.setIsFav(Constants.IS_FAV);
                }
                dataManager.updateMsg(idea);
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