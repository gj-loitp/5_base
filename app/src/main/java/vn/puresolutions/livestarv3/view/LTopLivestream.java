package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.puresolutions.livestarv3.activity.room.adapter.AudienceHorizontalAdapter;
import vn.puresolutions.livestarv3.activity.room.adapter.AudienceVerticalAdapter;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LTopLivestream extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private SimpleDraweeView ivAvatar;
    private TextView tvName;
    private TextView tvAudiences;
    private LinearLayout viewGroupBottom;
    private ArrayList<User> lstUser = new ArrayList<User>();
    private AudienceHorizontalAdapter audienceHorizontalAdapter;
    private LinearLayout viewGroupAllAudiences;
    private Button btCloseListAudiences;
    private Button btExitLiveStream;
    private Callback callback;
    private UserProfile mUserProfile;
    private RecyclerView recyclerViewListAudiencesHorizontal;

    public interface Callback {
        public void onClickBack();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public LTopLivestream(Context context) {
        super(context);
        init();
    }

    public LTopLivestream(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTopLivestream(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_top_livestream, this);

        btExitLiveStream = (Button) findViewById(R.id.bt_exit_live_stream);
        btExitLiveStream.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickBack();
                }
            }
        });
        ivAvatar = (SimpleDraweeView) findViewById(R.id.iv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        LUIUtil.setMarquee(tvName);
        tvAudiences = (TextView) findViewById(R.id.tv_audiences);
        viewGroupBottom = (LinearLayout) findViewById(R.id.view_group_bottom);
        viewGroupAllAudiences = (LinearLayout) findViewById(R.id.view_group_all_audiences);
        btCloseListAudiences = (Button) findViewById(R.id.bt_close_list_audiences);

        viewGroupBottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewAllAudiences();
            }
        });
        viewGroupAllAudiences.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //showViewAllAudiences();
            }
        });
        btCloseListAudiences.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewAllAudiences();
            }
        });
        //init list audiences horizontal
        recyclerViewListAudiencesHorizontal = (RecyclerView) findViewById(R.id.recycler_view_list_audiences_horizontal);
        recyclerViewListAudiencesHorizontal.setHasFixedSize(true);
        recyclerViewListAudiencesHorizontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        audienceHorizontalAdapter = new AudienceHorizontalAdapter(getContext(), lstUser, new AudienceHorizontalAdapter.Callback() {
            @Override
            public void onClick(User user) {
                AlertMessage.showSuccess(getContext(), "Click horizontal dummyPerson " + user.getName());
            }
        });
        recyclerViewListAudiencesHorizontal.setAdapter(audienceHorizontalAdapter);

        //init list audiences vertical
        RecyclerView recyclerViewListAudiencesVertical = (RecyclerView) findViewById(R.id.recycler_view_list_audiences_vertical);
        recyclerViewListAudiencesVertical.setHasFixedSize(true);
        recyclerViewListAudiencesVertical.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        AudienceVerticalAdapter audienceVerticalAdapter = new AudienceVerticalAdapter(getContext(), lstUser, new AudienceVerticalAdapter.Callback() {
            @Override
            public void onClick(User user) {
                AlertMessage.showSuccess(getContext(), "Click vertical dummyPerson " + user.getName());
            }
        });
        recyclerViewListAudiencesVertical.setAdapter(audienceVerticalAdapter);
    }

    private void showViewAllAudiences() {
        if (viewGroupAllAudiences.getVisibility() == GONE) {
            viewGroupAllAudiences.setVisibility(VISIBLE);
            LAnimationUtil.play(viewGroupAllAudiences, Techniques.SlideInRight, new LAnimationUtil.Callback() {
                @Override
                public void onCancel() {
                    //do nothing
                }

                @Override
                public void onEnd() {
                    viewGroupAllAudiences.setEnabled(true);
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
        } else {
            viewGroupAllAudiences.setEnabled(false);
            LAnimationUtil.play(viewGroupAllAudiences, Techniques.SlideOutRight, new LAnimationUtil.Callback() {
                @Override
                public void onCancel() {
                    //do nothing
                }

                @Override
                public void onEnd() {
                    viewGroupAllAudiences.setVisibility(GONE);
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

    public void setData(UserProfile userProfile) {
        mUserProfile = userProfile;

        //TODO get real information by calling rect api cor
        setTvAudiences();
        setIdolName(userProfile.getRoom().getTitle());
        setIvAvatar(userProfile.getRoom().getBanners().getW330h330());
    }

    public void setupFirstList(ArrayList<User> list) {
        lstUser.clear();
        lstUser.addAll(list);
        if (audienceHorizontalAdapter != null) {
            audienceHorizontalAdapter.notifyDataSetChanged();
            setTvAudiences();
        }
    }

    public int getListSize() {
        if (lstUser != null) {
            return lstUser.size();
        }
        return 0;
    }

    public void addItemFirst(User user) {
        //LLog.d(TAG, "addItemFirst " + user.getId());
        //check duplicate
        boolean isExist = false;
        for (int i = 0; i < lstUser.size(); i++) {
            if (user.getId().equals(lstUser.get(i).getId())) {
                isExist = true;
                break;
            }
        }
        //end check duplicate

        if (isExist) {
            //current user is exist in list
            return;
        }

        final int posAdd = 0;
        lstUser.add(posAdd, user);
        if (audienceHorizontalAdapter != null && recyclerViewListAudiencesHorizontal != null) {
            audienceHorizontalAdapter.notifyItemInserted(posAdd);
            audienceHorizontalAdapter.notifyItemRangeChanged(posAdd, lstUser.size());
            recyclerViewListAudiencesHorizontal.scrollToPosition(posAdd);
        }
        //LLog.d(TAG, "addItemFirst");
    }

    public void removeItem(User user) {
        //LLog.d(TAG, "removeItem " + user.getId());

        int pos = Constants.NOT_FOUND;
        for (int i = 0; i < lstUser.size(); i++) {
            if (user.getId().equals(lstUser.get(i).getId())) {
                pos = i;
                break;
            }
        }
        LLog.d(TAG, "removeItem pos " + pos);
        if (pos != Constants.NOT_FOUND) {
            lstUser.remove(pos);
            if (audienceHorizontalAdapter != null && recyclerViewListAudiencesHorizontal != null) {
                audienceHorizontalAdapter.notifyItemRemoved(pos);
                audienceHorizontalAdapter.notifyItemRangeChanged(pos, lstUser.size());
            }
        }
    }

    private void setIvAvatar(String urlAvatar) {
        if (ivAvatar == null) {
            throw new NullPointerException();
        }
        LImageUtils.loadImage(ivAvatar, urlAvatar);
    }

    private void setIdolName(String idolName) {
        if (idolName == null || tvName == null) {
            throw new NullPointerException();
        }
        tvName.setText(idolName);
    }

    private void setTvAudiences(String audiences) {
        if (audiences == null || tvAudiences == null) {
            throw new NullPointerException();
        }
        tvAudiences.setText(audiences);
    }

    public void setTvAudiences() {
        int audiences = getListSize();
        if (audiences < 0 || tvAudiences == null) {
            throw new NullPointerException();
        }
        tvAudiences.setText(String.valueOf(audiences));
        LAnimationUtil.play(tvAudiences, Techniques.FlipInY);
    }
}