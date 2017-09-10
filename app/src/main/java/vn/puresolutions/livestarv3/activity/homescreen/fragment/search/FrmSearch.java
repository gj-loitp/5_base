package vn.puresolutions.livestarv3.activity.homescreen.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.search.RoomResult;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolsearch.IdolSearchResultAdapter;
import vn.puresolutions.livestarv3.activity.room.BaseFragmentForLOnlyViewPager;
import vn.puresolutions.livestarv3.activity.room.RoomActivity;
import vn.puresolutions.livestarv3.activity.userprofile.LiveUserProfileActivity;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmSearch extends BaseFragmentForLOnlyViewPager {
    private String TAG = getClass().getSimpleName();
    private LinearLayoutManager linearLayoutManager;
    private String keyword;
    private int page = 1;
    private ArrayList<Room> lstRoom;
    private boolean isLoading = true;
    private IdolSearchResultAdapter idolSearchResultAdapter;
    @BindView(R.id.rvSearchScreen_lstSearchResult)
    RecyclerView rvSearchResult;
    @BindView(R.id.tvSearchScreen_SearchQuantity)
    TextView tvSearchScreen_SearchQuantity;
    @BindView(R.id.tvSearchScreen_NoResult)
    TextView tvSearchScreen_NoResult;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.bt_clear_text)
    ImageView btClearText;
    @BindView(R.id.rl_tv_result_search)
    RelativeLayout rlTvResultSearch;

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
        View view = inflater.inflate(R.layout.frm_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        lstRoom = new ArrayList<>();
        rvSearchResult.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSearchResult.setLayoutManager(linearLayoutManager);
        idolSearchResultAdapter = new IdolSearchResultAdapter(getContext(), lstRoom, new IdolSearchResultAdapter.Callback() {
            @Override
            public void onClick(Room room) {
                //LLog.d(TAG, "Onclick");
                if (room.isOnAir()) {
                    ArrayList<Room> rooms = new ArrayList<>();
                    rooms.add(room);
                    Intent intent = new Intent(getActivity(), RoomActivity.class);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM, room);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, 0);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) rooms);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    Intent intent = new Intent(getActivity(), LiveUserProfileActivity.class);
                    intent.putExtra(Constants.KEY_USER_TO_PROFILE, room.getUserId());
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                }
            }
        });
        rvSearchResult.setAdapter(idolSearchResultAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    if (btClearText.getVisibility() != View.GONE) {
                        btClearText.setVisibility(View.GONE);
                    }
                } else {
                    if (btClearText.getVisibility() != View.VISIBLE) {
                        btClearText.setVisibility(View.VISIBLE);
                        LAnimationUtil.play(btClearText, Techniques.FadeIn);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etSearch.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            keyword = etSearch.getText().toString();
                            page = 1;
                            lstRoom.clear();
                            idolSearchResultAdapter.notifyDataSetChanged();
                            loadResult(keyword);
                            return true;
                        }
                        return false;
                    }
                });

        rvSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisiblesItems, visibleItemCount, totalItemCount;
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findLastVisibleItemPosition();
                LLog.d(TAG, "visibleItemCount: " + visibleItemCount);
                LLog.d(TAG, "totalItemCount: " + totalItemCount);
                LLog.d(TAG, "pastVisiblesItems: " + pastVisiblesItems);
                if (isLoading) {
                    if (pastVisiblesItems + visibleItemCount >= totalItemCount) {
                        isLoading = false;
                        loadResult(keyword);
                        LLog.d(TAG, "onScrolled-page: " + page);
                    }
                }
            }
        });
    }

    private void loadResult(String keyword) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.search(keyword, page), new ApiSubscriber<RoomResult>() {
            @Override
            public void onSuccess(RoomResult result) {
                if (result == null || result.getAttr() == null) {
                    return;
                }
                if (result.getAttr().getPage() > 0) {
                    //if (lstRoom.size() < result.getAttr().get)
                    rlTvResultSearch.setVisibility(View.VISIBLE);
                    tvSearchScreen_SearchQuantity.setText(String.format(getString(R.string.searchscreen_amount_result), result.getAttr().getTotalItem()));
                    page++;
                    if (lstRoom.size() >= result.getAttr().getTotalItem()) {
                        return;
                    } else {
                        lstRoom.addAll(result.getRooms());
                        //LLog.d("FrmSearch", "size: " + lstRoom.size());
                        idolSearchResultAdapter.notifyDataSetChanged();
                        tvSearchScreen_NoResult.setVisibility(View.GONE);
                    }
                } else {
                    lstRoom.clear();
                    idolSearchResultAdapter.notifyDataSetChanged();
                    tvSearchScreen_NoResult.setVisibility(View.VISIBLE);
                    rlTvResultSearch.setVisibility(View.GONE);
                }
                isLoading = true;
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    @OnClick(R.id.bt_clear_text)
    void onClearText() {
        LAnimationUtil.play(btClearText, Techniques.FadeOut, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                etSearch.setText("");
                lstRoom.clear();
                idolSearchResultAdapter.notifyDataSetChanged();
                tvSearchScreen_NoResult.setVisibility(View.GONE);
                rlTvResultSearch.setVisibility(View.GONE);
            }

            @Override
            public void onRepeat() {
                //do nothing
            }

            @Override
            public void onStart() {
                //do nothing
            }
        });
    }
}

