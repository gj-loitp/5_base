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
import vn.puresolutions.livestar.core.api.model.Broadcaster;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.RankingService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankShareFragment extends BaseFragment {

    RankAdapter RankAdapter;
    @BindView(R.id.rvRankShare)
    RecyclerView rvRankShare;
    @BindView(R.id.rankShare_prgLoading)
    ProgressBar prgLoading;
    public RankShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_rank_share, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setRetainInstance(true);

        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvRankShare.setLayoutManager(layout);
        rvRankShare.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }
    private void loadData() {
        prgLoading.setVisibility(View.VISIBLE);
        Log.i("RankShareFragment","loadData");
        RankingService rankingService = RestClient.createService(RankingService.class);
        subscribe(rankingService.getTopShareBroadcaster("all"), new ApiSubscriber<List<Broadcaster>>() {
            @Override
            public void onSuccess(List<Broadcaster> result) {
                Log.i("RankShareFragment","onSuccess: "+ result.size());
                RankAdapter= new RankAdapter(getActivity(),result);
                rvRankShare.setAdapter(RankAdapter);
                prgLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                prgLoading.setVisibility(View.GONE);
                Log.i("RankShareFragment","onFail: "+e.toString());
            }
        });
    }
}
