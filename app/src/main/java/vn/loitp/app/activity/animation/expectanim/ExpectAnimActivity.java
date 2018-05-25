package vn.loitp.app.activity.animation.expectanim;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.function.expectanim.ExpectAnim;
import vn.loitp.utils.util.ConvertUtils;

import static vn.loitp.function.expectanim.core.Expectations.alpha;
import static vn.loitp.function.expectanim.core.Expectations.height;
import static vn.loitp.function.expectanim.core.Expectations.leftOfParent;
import static vn.loitp.function.expectanim.core.Expectations.rightOfParent;
import static vn.loitp.function.expectanim.core.Expectations.sameCenterVerticalAs;
import static vn.loitp.function.expectanim.core.Expectations.scale;
import static vn.loitp.function.expectanim.core.Expectations.toRightOf;
import static vn.loitp.function.expectanim.core.Expectations.topOfParent;

//https://github.com/florent37/ExpectAnim/?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5335
public class ExpectAnimActivity extends BaseActivity {
    private View username;
    private View avatar;
    private View follow;
    private View backbground;
    private NestedScrollView scrollView;

    private ExpectAnim expectAnimMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = (View) findViewById(R.id.username);
        avatar = (View) findViewById(R.id.avatar);
        follow = (View) findViewById(R.id.follow);
        backbground = (View) findViewById(R.id.background);
        scrollView = (NestedScrollView) findViewById(R.id.scrollview);

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

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                final float percent = (scrollY * 1f) / v.getMaxScrollAmount();
                expectAnimMove.setPercent(percent);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_expect_anim;
    }
}
