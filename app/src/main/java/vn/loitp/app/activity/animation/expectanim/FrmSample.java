package vn.loitp.app.activity.animation.expectanim;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;
import com.function.expectanim.ExpectAnim;

import vn.loitp.app.R;

import static com.function.expectanim.core.Expectations.aboveOf;
import static com.function.expectanim.core.Expectations.atItsOriginalPosition;
import static com.function.expectanim.core.Expectations.bottomOfParent;
import static com.function.expectanim.core.Expectations.invisible;
import static com.function.expectanim.core.Expectations.leftOfParent;
import static com.function.expectanim.core.Expectations.outOfScreen;
import static com.function.expectanim.core.Expectations.rightOfParent;
import static com.function.expectanim.core.Expectations.sameCenterVerticalAs;
import static com.function.expectanim.core.Expectations.toHaveBackgroundAlpha;
import static com.function.expectanim.core.Expectations.toHaveTextColor;
import static com.function.expectanim.core.Expectations.toRightOf;
import static com.function.expectanim.core.Expectations.visible;
import static com.function.expectanim.core.Expectations.width;

public class FrmSample extends BaseFragment {
    private View name;
    private View avatar;
    private View subname;
    private View follow;
    private View message;
    private View bottomLayout;
    private View content;
    private ExpectAnim expectAnimMove;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = getFrmRootView().findViewById(R.id.name);
        avatar = getFrmRootView().findViewById(R.id.avatar);
        subname = getFrmRootView().findViewById(R.id.subname);
        follow = getFrmRootView().findViewById(R.id.follow);
        message = getFrmRootView().findViewById(R.id.message);
        bottomLayout = getFrmRootView().findViewById(R.id.bottomLayout);
        content = getFrmRootView().findViewById(R.id.content);

        new ExpectAnim()
                .expect(bottomLayout)
                .toBe(
                        outOfScreen(Gravity.BOTTOM)
                )
                .expect(content)
                .toBe(
                        outOfScreen(Gravity.BOTTOM),
                        invisible()
                )
                .toAnimation()
                .setNow();

        this.expectAnimMove = new ExpectAnim()

                .expect(avatar)
                .toBe(
                        bottomOfParent().withMarginDp(36),
                        leftOfParent().withMarginDp(16),
                        width(40).toDp().keepRatio()
                )

                .expect(name)
                .toBe(
                        toRightOf(avatar).withMarginDp(16),
                        sameCenterVerticalAs(avatar),
                        toHaveTextColor(Color.WHITE)
                )

                .expect(subname)
                .toBe(
                        toRightOf(name).withMarginDp(5),
                        sameCenterVerticalAs(name),
                        toHaveTextColor(Color.WHITE)
                )

                .expect(follow)
                .toBe(
                        rightOfParent().withMarginDp(4),
                        bottomOfParent().withMarginDp(12),
                        toHaveBackgroundAlpha(0f)
                )

                .expect(message)
                .toBe(
                        aboveOf(follow).withMarginDp(4),
                        rightOfParent().withMarginDp(4),
                        toHaveBackgroundAlpha(0f)
                )

                .expect(bottomLayout)
                .toBe(
                        atItsOriginalPosition()
                )

                .expect(content)
                .toBe(
                        atItsOriginalPosition(),
                        visible()
                )

                .toAnimation()
                .setDuration(1500);

        message.setOnClickListener(v -> expectAnimMove.start());
        follow.setOnClickListener(v -> expectAnimMove.reset());
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_expect_anim_sample;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}