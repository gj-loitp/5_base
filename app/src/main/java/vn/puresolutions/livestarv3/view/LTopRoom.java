package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomfindbyID.RoomFindByID;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.puresolutions.livestarv3.activity.room.adapter.AudienceHorizontalAdapter;
import vn.puresolutions.livestarv3.activity.room.adapter.AudienceVerticalAdapter;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LTopRoom extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private SimpleDraweeView ivAvatar;
    private TextView tvName;
    private TextView tvAudiences;
    private TextView tvFollow;
    private LinearLayout viewGroupBottom;
    private ArrayList<User> lstUser = new ArrayList<User>();
    private LinearLayout viewGroupAllAudiences;
    private Button btCloseListAudiences;
    private ImageView btBack;
    private AudienceHorizontalAdapter audienceHorizontalAdapter;
    private AudienceVerticalAdapter audienceVerticalAdapter;
    private RoomFindByID roomFindByID;
    private Callback callback;
    private RecyclerView recyclerViewListAudiencesHorizontal;
    private LinearLayout viewGroupTop;

    public interface Callback {
        public void onClickBack();

        public void onClickFollow();

        public void onClickUserHorizontal(User user);

        public void onClickUserVertical(User user);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public LTopRoom(Context context) {
        super(context);
        init();
    }

    public LTopRoom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTopRoom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_top_room, this);

        viewGroupTop = (LinearLayout) findViewById(R.id.view_group_top);
        btBack = (ImageView) findViewById(R.id.bt_back);
        btBack.setOnClickListener(new OnClickListener() {
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
        tvFollow = (TextView) findViewById(R.id.tv_follow);
        LUIUtil.setMarquee(tvFollow);

        //set width of ll_user_information programlly
        /*LinearLayout llUserInformation = (LinearLayout) findViewById(R.id.ll_user_information);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        ViewGroup.LayoutParams layoutParams = llUserInformation.getLayoutParams();
        layoutParams.width = width / 2;
        llUserInformation.setLayoutParams(layoutParams);*/
        //end set width of ll_user_information programlly

        viewGroupBottom = (LinearLayout) findViewById(R.id.view_group_bottom);
        viewGroupAllAudiences = (LinearLayout) findViewById(R.id.view_group_all_audiences);
        btCloseListAudiences = (Button) findViewById(R.id.bt_close_list_audiences);

        tvFollow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    if (roomFindByID != null) {
                        callback.onClickFollow();
                    }
                }
            }
        });

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
        //recyclerViewListAudiencesHorizontal.setHasFixedSize(true);
        recyclerViewListAudiencesHorizontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        audienceHorizontalAdapter = new AudienceHorizontalAdapter(getContext(), lstUser, new AudienceHorizontalAdapter.Callback() {
            @Override
            public void onClick(User user) {
                //LLog.d(TAG, "onClick " + LSApplication.getInstance().getGson().toJson(user));
                if (callback != null) {
                    callback.onClickUserHorizontal(user);
                }
            }
        });
        recyclerViewListAudiencesHorizontal.setAdapter(audienceHorizontalAdapter);

        //init list audiences vertical
        RecyclerView recyclerViewListAudiencesVertical = (RecyclerView) findViewById(R.id.recycler_view_list_audiences_vertical);
        recyclerViewListAudiencesVertical.setHasFixedSize(true);
        recyclerViewListAudiencesVertical.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        audienceVerticalAdapter = new AudienceVerticalAdapter(getContext(), lstUser, new AudienceVerticalAdapter.Callback() {
            @Override
            public void onClick(User user) {
                if (callback != null) {
                    callback.onClickUserVertical(user);
                }
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

    public void setData(RoomFindByID rfbID) {
        if (rfbID == null) {
            LLog.d(TAG, "cannot setData because rfbID or r == null");
            return;
        }
        //LLog.d(TAG, "setData " + rfbID.getId());
        roomFindByID = rfbID;
        updateViewFollowUnfollow();
        setTvAudiences();
        setIdolName(roomFindByID.getTitle());
        setIvAvatar(roomFindByID.getBanners().getW330h330());
    }

    public void setupFirstList(ArrayList<User> list) {
        lstUser.clear();
        lstUser.addAll(list);
        if (audienceHorizontalAdapter != null) {
            audienceHorizontalAdapter.notifyDataSetChanged();
            setTvAudiences();
        }
        LLog.d(TAG, "setupFirstList size: " + lstUser.size());
        if (Constants.IS_DEBUG) {
            for (int i = 0; i < lstUser.size(); i++) {
                LLog.d(TAG, ">>>>>>>setupFirstList " + lstUser.get(i).getRoom().getTitle() + " -> " + lstUser.get(i).getRoom().isIsFollow());
            }
        }
    }

    public int getListSize() {
        if (lstUser != null) {
            return lstUser.size();
        }
        return 0;
    }

    public void addItemFirst(User user) {
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

        final int pos = 0;
        lstUser.add(pos, user);
        if (audienceHorizontalAdapter != null && recyclerViewListAudiencesHorizontal != null) {
            audienceHorizontalAdapter.notifyItemInserted(pos);
            audienceHorizontalAdapter.notifyItemRangeChanged(pos, lstUser.size());
            recyclerViewListAudiencesHorizontal.scrollToPosition(pos);
        }
        LLog.d(TAG, "addItemFirst size: " + lstUser.size());
        //LLog.d(TAG, "addItemFirst " + LSApplication.getInstance().getGson().toJson(user));
    }

    public void removeItem(User user) {
        //boolean result = dummyPersonArrayList.remove(dummyPerson);

        int pos = Constants.NOT_FOUND;
        for (int i = 0; i < lstUser.size(); i++) {
            if (user.getId().equals(lstUser.get(i).getId())) {
                pos = i;
                break;
            }
        }
        //LLog.d(TAG, "removeItem pos " + pos);
        if (pos != Constants.NOT_FOUND) {
            lstUser.remove(pos);
            if (audienceHorizontalAdapter != null && recyclerViewListAudiencesHorizontal != null) {
                audienceHorizontalAdapter.notifyItemRemoved(pos);
                audienceHorizontalAdapter.notifyItemRangeChanged(pos, lstUser.size());
            }
        }
    }

    public void updateViewFollowUnfollow() {
        if (roomFindByID.isIsFollow()) {
            tvFollow.setText(getContext().getString(R.string.followed));
            tvFollow.setBackgroundResource(R.drawable.bt_followed);
        } else {
            tvFollow.setText(getContext().getString(R.string.follow));
            tvFollow.setBackgroundResource(R.drawable.bt_follow);
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

    private void setTvAudiences(int audiences) {
        if (audiences < 0 || tvAudiences == null) {
            throw new NullPointerException();
        }
        tvAudiences.setText(String.valueOf(audiences));
    }

    public void hideAllView() {
        viewGroupTop.setVisibility(GONE);
        viewGroupBottom.setVisibility(GONE);
    }

    public void setTvAudiences() {
        int size = getListSize();
        LLog.d(TAG, ">>>setTvAudiences size: " + size);
        if (size < 0 || tvAudiences == null) {
            throw new NullPointerException();
        }
        tvAudiences.setText(String.valueOf(size));
        LAnimationUtil.play(tvAudiences, Techniques.FlipInY);
    }

    public void updateUIFollowUnfollowVertical() {
        LLog.d(TAG, "updateUIFollowUnfollowVertical");
    }
}