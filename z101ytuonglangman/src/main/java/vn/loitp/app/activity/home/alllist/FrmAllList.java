package vn.loitp.app.activity.home.alllist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.data.DataManager;
import vn.loitp.app.model.Idea;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmAllList extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private AdView adView;
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

        dataManager = new DataManager(getActivity());
        try {
            dataManager.createDatabase();
            LLog.d(TAG, "init dtb success");
        } catch (IOException e) {
            LLog.d(TAG, "init dtb failed: " + e.toString());
        }
        List<Idea> ideaList = new ArrayList<>();
        ideaList.addAll(dataManager.getAllIdea());

        LLog.d(TAG, "size: " + ideaList.size());
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