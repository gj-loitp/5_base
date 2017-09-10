package vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.rank.WrapperRank;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<WrapperRank> wrapperRankArrayList;
    private Activity activity;

    private int colorBlue;
    private int colorWhite;

    public interface RankingCallback {
        public void onClickMain(WrapperRank wrapperRank);

        public void onClickFollow(WrapperRank wrapperRank);
    }

    private RankingCallback rankingCallback;

    public RankingAdapter(Activity activity, ArrayList<WrapperRank> wrapperRankArrayList, RankingCallback rankingCallback) {
        this.wrapperRankArrayList = wrapperRankArrayList;
        this.activity = activity;
        this.rankingCallback = rankingCallback;
        colorBlue = ContextCompat.getColor(activity, R.color.Blue);
        colorWhite = ContextCompat.getColor(activity, R.color.White);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = null;
        if (viewType == Constants.TYPE_1ST) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_ranking_1st, null);
            return new Rank1stHolder(v);
        } else if (viewType == Constants.TYPE_2ND_3RD) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_ranking_2nd3rd, null);
            return new Rank2nd3rdHolder(v);
        } else if (viewType == Constants.TYPE_OTHER) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_ranking_other, null);
            //int height = viewGroup.getMeasuredHeight() / 3;
            //v.setMinimumHeight(height);
            return new RankOtherHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (position == 0) {
            return Constants.TYPE_1ST;
        } else if (position == 1 || position == 2) {
            return Constants.TYPE_2ND_3RD;
        } else {
            return Constants.TYPE_OTHER;
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        WrapperRank wrapperRank = wrapperRankArrayList.get(position);
        if (baseHolder instanceof Rank1stHolder) {
            Rank1stHolder rank1stHolder = (Rank1stHolder) baseHolder;
            if (wrapperRank == null || wrapperRank.getUser() == null || wrapperRank.getUser().getAvatarsPath() == null) {
                return;
            }
            LImageUtils.loadImage(rank1stHolder.simpleDraweeView, wrapperRank.getUser().getAvatarsPath().getW512h512());
            rank1stHolder.tvHeart.setText(String.valueOf(wrapperRank.getUser().getHeart()));
            rank1stHolder.tvName.setText(wrapperRank.getUser().getName());
            rank1stHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LAnimationUtil.play(rank1stHolder.viewGroupMain, Techniques.Pulse, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            if (rankingCallback != null) {
                                rankingCallback.onClickMain(wrapperRank);
                            }
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
            });
            rank1stHolder.lTextFollow.setImageSize(10);
            if (wrapperRank.getUser().getRoom().isIsFollow()) {
                rank1stHolder.lTextFollow.setText(activity.getString(R.string.followed));
                rank1stHolder.lTextFollow.setImage(R.drawable.tick);
                rank1stHolder.lTextFollow.setBackground(R.drawable.bt_followed);
            } else {
                rank1stHolder.lTextFollow.setText(activity.getString(R.string.follow));
                rank1stHolder.lTextFollow.setImage(R.drawable.ic_add_white_48dp);
                rank1stHolder.lTextFollow.setBackground(R.drawable.bt_follow);
            }
            rank1stHolder.lTextFollow.setOnItemClick(new LTextViewHorizontal.Callback() {
                @Override
                public void OnClickItem() {
                    if (rankingCallback != null) {
                        LAnimationUtil.play(rank1stHolder.lTextFollow, Techniques.Flash);
                        rankingCallback.onClickFollow(wrapperRank);
                    }
                }
            });
        } else if (baseHolder instanceof Rank2nd3rdHolder) {
            Rank2nd3rdHolder rank2nd3rdHolder = (Rank2nd3rdHolder) baseHolder;
            LImageUtils.loadImage(rank2nd3rdHolder.simpleDraweeView, wrapperRank.getUser().getAvatarsPath().getW512h512());
            rank2nd3rdHolder.tvHeart.setText(String.valueOf(wrapperRank.getUser().getHeart()));
            rank2nd3rdHolder.tvName.setText(wrapperRank.getUser().getName());
            rank2nd3rdHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LAnimationUtil.play(rank2nd3rdHolder.viewGroupMain, Techniques.Pulse, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            if (rankingCallback != null) {
                                rankingCallback.onClickMain(wrapperRank);
                            }
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
            });
            rank2nd3rdHolder.lTextFollow.setImageSize(10);
            if (wrapperRank.getUser().getRoom().isIsFollow()) {
                rank2nd3rdHolder.lTextFollow.setText("Đang theo dõi");
                rank2nd3rdHolder.lTextFollow.setImage(R.drawable.tick);
                rank2nd3rdHolder.lTextFollow.setBackground(R.drawable.bt_followed);
            } else {
                rank2nd3rdHolder.lTextFollow.setText("Theo dõi");
                rank2nd3rdHolder.lTextFollow.setImage(R.drawable.ic_add_white_48dp);
                rank2nd3rdHolder.lTextFollow.setBackground(R.drawable.bt_follow);
            }
            rank2nd3rdHolder.lTextFollow.setOnItemClick(new LTextViewHorizontal.Callback() {
                @Override
                public void OnClickItem() {
                    if (rankingCallback != null) {
                        LAnimationUtil.play(rank2nd3rdHolder.lTextFollow, Techniques.Flash);
                        rankingCallback.onClickFollow(wrapperRank);
                    }
                }
            });
            if (position == 1) {
                rank2nd3rdHolder.ivNumber.setImageResource(R.drawable.no2);
                rank2nd3rdHolder.ivRoundCircle.setImageResource(R.drawable.number_two);
            } else if (position == 2) {
                rank2nd3rdHolder.ivNumber.setImageResource(R.drawable.no3);
                rank2nd3rdHolder.ivRoundCircle.setImageResource(R.drawable.number_three);
            }
        } else if (baseHolder instanceof RankOtherHolder) {
            RankOtherHolder rankOtherHolder = (RankOtherHolder) baseHolder;
            LImageUtils.loadImage(rankOtherHolder.simpleDraweeView, wrapperRank.getUser().getAvatarsPath().getW512h512());
            rankOtherHolder.tvHeart.setText(String.valueOf(wrapperRank.getUser().getHeart()));
            rankOtherHolder.tvName.setText(wrapperRank.getUser().getName());
            rankOtherHolder.tvNumber.setText(String.valueOf(position));
            LUIUtil.setCircleViewWithColor(rankOtherHolder.tvNumber, colorBlue, colorWhite);
            rankOtherHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LAnimationUtil.play(rankOtherHolder.viewGroupMain, Techniques.Pulse, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            if (rankingCallback != null) {
                                rankingCallback.onClickMain(wrapperRank);
                            }
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
            });
            rankOtherHolder.lTextFollow.setImageSize(10);
            if (wrapperRank.getUser().getRoom().isIsFollow()) {
                rankOtherHolder.lTextFollow.setText("Đang theo dõi");
                rankOtherHolder.lTextFollow.setImage(R.drawable.tick);
                rankOtherHolder.lTextFollow.setBackground(R.drawable.bt_followed);
            } else {
                rankOtherHolder.lTextFollow.setText("Theo dõi");
                rankOtherHolder.lTextFollow.setImage(R.drawable.ic_add_white_48dp);
                rankOtherHolder.lTextFollow.setBackground(R.drawable.bt_follow);
            }
            rankOtherHolder.lTextFollow.setOnItemClick(new LTextViewHorizontal.Callback() {
                @Override
                public void OnClickItem() {
                    if (rankingCallback != null) {
                        LAnimationUtil.play(rankOtherHolder.lTextFollow, Techniques.Flash);
                        rankingCallback.onClickFollow(wrapperRank);
                    }
                }
            });
            if (position == 3) {
                rankOtherHolder.viewShadow.setVisibility(View.VISIBLE);
            } else {
                rankOtherHolder.viewShadow.setVisibility(View.GONE);
            }

            if (position == 9) {
                rankOtherHolder.viewSpace.setVisibility(View.VISIBLE);
            } else {
                rankOtherHolder.viewShadow.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != wrapperRankArrayList ? wrapperRankArrayList.size() : 0);
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class Rank1stHolder extends BaseHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView tvName;
        private TextView tvHeart;
        private RelativeLayout viewGroupMain;
        private LTextViewHorizontal lTextFollow;
        //private View viewSpace;

        public Rank1stHolder(View view) {
            super(view);
            this.simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvHeart = (TextView) view.findViewById(R.id.tv_heart);
            this.viewGroupMain = (RelativeLayout) view.findViewById(R.id.view_group_main);
            this.lTextFollow = (LTextViewHorizontal) view.findViewById(R.id.l_text_follow);
            //this.viewSpace = (View) view.findViewById(R.id.view_space);
        }
    }

    public class Rank2nd3rdHolder extends BaseHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView tvName;
        private TextView tvHeart;
        private RelativeLayout viewGroupMain;
        private LTextViewHorizontal lTextFollow;
        private ImageView ivNumber;
        private ImageView ivRoundCircle;

        public Rank2nd3rdHolder(View view) {
            super(view);
            this.simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvHeart = (TextView) view.findViewById(R.id.tv_heart);
            this.viewGroupMain = (RelativeLayout) view.findViewById(R.id.view_group_main);
            this.lTextFollow = (LTextViewHorizontal) view.findViewById(R.id.l_text_follow);
            this.ivNumber = (ImageView) view.findViewById(R.id.iv_number);
            this.ivRoundCircle = (ImageView) view.findViewById(R.id.iv_round_circle);
        }
    }

    public class RankOtherHolder extends BaseHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView tvName;
        private TextView tvHeart;
        private TextView tvNumber;
        private LinearLayout viewGroupMain;
        private LTextViewHorizontal lTextFollow;
        private LinearLayout viewShadow;
        private View viewSpace;

        public RankOtherHolder(View view) {
            super(view);
            this.simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvHeart = (TextView) view.findViewById(R.id.tv_heart);
            this.tvNumber = (TextView) view.findViewById(R.id.tv_number);
            this.viewGroupMain = (LinearLayout) view.findViewById(R.id.view_group_main);
            this.lTextFollow = (LTextViewHorizontal) view.findViewById(R.id.l_text_follow);
            this.viewShadow = (LinearLayout) view.findViewById(R.id.view_shadow);
            this.viewSpace = (View) view.findViewById(R.id.view_space);
        }
    }
}