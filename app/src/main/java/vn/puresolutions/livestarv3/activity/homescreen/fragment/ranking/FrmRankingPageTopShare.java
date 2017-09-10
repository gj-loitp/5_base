package vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.followidol.FollowIdol;
import vn.puresolutions.livestar.corev3.api.model.v3.rank.WrapperRank;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.activity.userprofile.LiveUserProfileActivity;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LHomeUtil;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmRankingPageTopShare extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<WrapperRank> wrapperRankArrayList = new ArrayList<WrapperRank>();
    private RankingAdapter rankingAdapter;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private RecyclerView recyclerView;
    private TextView tvMsg;
    private boolean isLoaded = false;

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
        View view = inflater.inflate(R.layout.frm_ranking_page_top_share, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        //recyclerView.setLayoutManager(linearLayoutManager, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(gridLayoutManager);
        rankingAdapter = new RankingAdapter(getActivity(), wrapperRankArrayList, new RankingAdapter.RankingCallback() {
            @Override
            public void onClickMain(WrapperRank wrapperRank) {
                Intent intent = new Intent(getActivity(), LiveUserProfileActivity.class);
                intent.putExtra(Constants.KEY_USER_TO_PROFILE, wrapperRank.getUserId());
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }

            @Override
            public void onClickFollow(WrapperRank wrapperRank) {
                if (!LPref.isUserLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra(Constants.LOGIN_IS_CALL_FROM_HOME_MAIN_LIVE_STREAM, true);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    if (wrapperRank.getUser().getRoom().isIsFollow()) {
                        unfollowIdol(wrapperRank);
                    } else {
                        followIdol(wrapperRank);
                    }
                }
            }
        });
        recyclerView.setAdapter(rankingAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (rankingAdapter.getItemViewType(position)) {
                    case Constants.TYPE_1ST:
                        return 2;
                    case Constants.TYPE_2ND_3RD:
                        return 1;
                    default:
                        return 2;
                }
            }
        });
        if (!isLoaded) {
            rankShareFacebook();
        }
        return view;
    }

    private void rankShareFacebook() {
        //LLog.d(TAG, "rankShareFacebook");
        avLoadingIndicatorView.smoothToShow();
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.rankShareFB(), new ApiSubscriber<WrapperRank[]>() {
            @Override
            public void onSuccess(WrapperRank[] wrapperRank) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(wrapperRank));
                for (int i = 0; i < wrapperRank.length; i++) {
                    wrapperRankArrayList.add(wrapperRank[i]);
                }

                if (rankingAdapter != null) {
                    rankingAdapter.notifyDataSetChanged();
                }
                tvMsg.setVisibility(wrapperRankArrayList.isEmpty() ? View.VISIBLE : View.GONE);
                avLoadingIndicatorView.smoothToHide();
                isLoaded = true;
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avLoadingIndicatorView.smoothToHide();
            }
        });
    }

    //start follow and unfollow
    private void followIdol(WrapperRank wrapperRank) {
        //LLog.d(TAG, "followIdol roomID " + wrapperRank.getUser().getRoom().getId());
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.followIdol(wrapperRank.getUser().getRoom().getId()), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol followIdol) {
                if (followIdol != null && followIdol.getMessage() != null) {
                    AlertMessage.showSuccess(getActivity(), followIdol.getMessage() + " " + wrapperRank.getUser().getName());
                    //LLog.d(TAG, "onSuccess followIdol " + followIdol.getMessage());
                    wrapperRank.getUser().getRoom().setIsFollow(!wrapperRank.getUser().getRoom().isIsFollow());
                    rankingAdapter.notifyDataSetChanged();
                    LHomeUtil.reloadDataFollowOrSuggest();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void unfollowIdol(WrapperRank wrapperRank) {
        //LLog.d(TAG, "unfollowIdol roomID " + wrapperRank.getUser().getRoom().getId());
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.unfollowIdol(wrapperRank.getUser().getRoom().getId()), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol followIdol) {
                if (followIdol != null && followIdol.getMessage() != null) {
                    AlertMessage.showSuccess(getActivity(), followIdol.getMessage() + " " + wrapperRank.getUser().getName());
                    //LLog.d(TAG, "onSuccess unfollowIdol " + followIdol.getMessage());
                    wrapperRank.getUser().getRoom().setIsFollow(!wrapperRank.getUser().getRoom().isIsFollow());
                    rankingAdapter.notifyDataSetChanged();
                    LHomeUtil.reloadDataFollowOrSuggest();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
    //end follow and unfollow
}

