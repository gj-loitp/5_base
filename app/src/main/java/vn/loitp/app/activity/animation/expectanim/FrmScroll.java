package vn.loitp.app.activity.animation.expectanim;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.core.base.BaseFragment;
import com.function.expectanim.ExpectAnim;
import com.utils.util.ConvertUtils;

import loitp.basemaster.R;

import static com.function.expectanim.core.Expectations.alpha;
import static com.function.expectanim.core.Expectations.height;
import static com.function.expectanim.core.Expectations.leftOfParent;
import static com.function.expectanim.core.Expectations.rightOfParent;
import static com.function.expectanim.core.Expectations.sameCenterVerticalAs;
import static com.function.expectanim.core.Expectations.scale;
import static com.function.expectanim.core.Expectations.toRightOf;
import static com.function.expectanim.core.Expectations.topOfParent;

public class FrmScroll extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private View username;
    private View avatar;
    private View follow;
    private View backbground;
    private NestedScrollView scrollView;

    private ExpectAnim expectAnimMove;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = getFrmRootView().findViewById(R.id.username);
        avatar = getFrmRootView().findViewById(R.id.avatar);
        follow = getFrmRootView().findViewById(R.id.follow);
        backbground = getFrmRootView().findViewById(R.id.background);
        scrollView = getFrmRootView().findViewById(R.id.scrollview);

        this.expectAnimMove = new ExpectAnim()
                .expect(avatar)
                .toBe(
                        topOfParent().withMarginDp(20),
                        leftOfParent().withMarginDp(20),
                        scale(0.5f, 0.5f)
                )
                .expect(username)
                .toBe(
                        toRightOf(avatar).withMarginDp(16),
                        sameCenterVerticalAs(avatar),

                        alpha(0.5f)
                )
                .expect(follow)
                .toBe(
                        rightOfParent().withMarginDp(20),
                        sameCenterVerticalAs(avatar)
                )
                .expect(backbground)
                .toBe(
                        height(ConvertUtils.dp2px(90)).withGravity(Gravity.LEFT, Gravity.TOP)
                )
                .toAnimation();

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            final float percent = (scrollY * 1f) / v.getMaxScrollAmount();
            expectAnimMove.setPercent(percent);
        });
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_expect_anim_scroll;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}