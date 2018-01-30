package vn.loitp.app.activity.comicinfo.chap;

import android.content.Intent;
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
import vn.loitp.app.activity.read.ReadActivity;
import vn.loitp.app.data.ComicInfoData;
import vn.loitp.app.model.chap.Chap;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmChap extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private AdView adView;

    private RecyclerView recyclerView;
    private ChapAdapter mAdapter;
    private List<Chap> chapList = new ArrayList<Chap>();

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
        View view = inflater.inflate(R.layout.frm_comic_chap, container, false);
        adView = (AdView) view.findViewById(R.id.adView);
        LUIUtil.createAdBanner(adView);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        mAdapter = new ChapAdapter(chapList, new ChapAdapter.Callback() {
            @Override
            public void onClick(Chap chap, int position) {
                ComicInfoData.getInstance().setPosCurrentChap(position);
                Intent intent = new Intent(getActivity(), ReadActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(getActivity());
            }

            @Override
            public void onLongClick(Chap chap, int position) {
                //do nothing
            }

            @Override
            public void onLoadMore() {
                //loadMore();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareData();
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

    private void prepareData() {
        try {
            chapList.addAll(ComicInfoData.getInstance().getTttChap().getChaps().getChap());
        } catch (NullPointerException e) {
            LLog.e(TAG, "NullPointerException " + e.toString());
        }
        if (chapList != null && mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}