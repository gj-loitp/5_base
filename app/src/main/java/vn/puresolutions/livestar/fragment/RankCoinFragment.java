package vn.puresolutions.livestar.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.RankAdapter;
import vn.puresolutions.livestar.core.api.model.LoyalFan;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.RankingService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankCoinFragment extends BaseFragment {
    RankAdapter RankAdapter;
    @BindView(R.id.rvRankCoin)
    RecyclerView rvRankCoin;
    @BindView(R.id.rankCoin_prgLoading)
    ProgressBar prgLoading;
    public RankCoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("RankCoinFragment","onCreateView");
        View view=inflater.inflate(R.layout.fragment_rank_coin, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("RankCoinFragment","onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setRetainInstance(true);

        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvRankCoin.setLayoutManager(layout);
        rvRankCoin.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }
    private void loadData() {
        prgLoading.setVisibility(View.VISIBLE);
        Log.i("RankCoinFragment","loadData");
        RankingService rankingService = RestClient.createService(RankingService.class);
        subscribe(rankingService.getTopGiftBroadcaster("month"), new ApiSubscriber<List<LoyalFan>>() {
            @Override
            public void onSuccess(List<LoyalFan> result) {
                Log.i("RankCoinFragment","onSuccess: "+result.size());
                RankAdapter= new RankAdapter(getActivity(),result);
                rvRankCoin.setAdapter(RankAdapter);
                prgLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Throwable e) {
                prgLoading.setVisibility(View.GONE);
                handleException(e);
                Log.i("RankHeartFragment","onFail: "+e.toString());
            }
        });
    }
}
