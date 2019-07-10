package com.core.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.core.utilities.LDialogUtil;

import java.lang.reflect.Field;

import io.reactivex.disposables.CompositeDisposable;
import loitp.core.R;

/**
 * Created by loitp on 7/31/16.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    @NonNull
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected final String TAG = BaseFragment.class.getSimpleName();
    protected View frmRootView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentCallback != null) {
            fragmentCallback.onViewCreated();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frmRootView = inflater.inflate(setLayoutResourceId(), container, false);
        return frmRootView;
    }

    protected abstract int setLayoutResourceId();

    @Override
    public void onDestroyView() {
        LDialogUtil.clearAll();
        compositeDisposable.clear();
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    protected void handleException(@NonNull final Throwable throwable) {
        showDialogError(throwable.getMessage());
    }

    protected void showDialogError(@NonNull final String errMsg) {
        LDialogUtil.showDialog1(getActivity(), getString(R.string.warning), errMsg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                //getActivity().onBackPressed();
            }
        });
    }

    protected void showDialogMsg(@NonNull final String msg) {
        LDialogUtil.showDialog1(getActivity(), getString(R.string.app_name), msg, getString(R.string.confirm), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                //getActivity().onBackPressed();
            }
        });
    }

    public interface FragmentCallback {
        public void onViewCreated();
    }

    protected FragmentCallback fragmentCallback;

    public void setFragmentCallback(@NonNull final FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    private final int DEFAULT_CHILD_ANIMATION_DURATION = 400;

    //https://stackoverflow.com/questions/14900738/nested-fragments-disappear-during-transition-animation
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        final Fragment parent = getParentFragment();

        // Apply the workaround only if this is a child fragment, and the parent
        // is being removed.
        if (!enter && parent != null && parent.isRemoving()) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            final Animation doNothingAnim = new AlphaAnimation(1, 1);
            doNothingAnim.setDuration(getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION));
            return doNothingAnim;
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

    private static long getNextAnimationDuration(@NonNull final Fragment fragment, final long defValue) {
        try {
            // Attempt to get the resource ID of the next animation that
            // will be applied to the given fragment.
            final Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
            nextAnimField.setAccessible(true);
            final int nextAnimResource = nextAnimField.getInt(fragment);
            final Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);

            // ...and if it can be loaded, return that animation's duration
            return (nextAnim == null) ? defValue : nextAnim.getDuration();
        } catch (NoSuchFieldException | IllegalAccessException | Resources.NotFoundException ex) {
            //LLog.d(TAG, "Unable to load next animation from parent.", ex);
            return defValue;
        }
    }

    /*private void s() {
        compositeDisposable.add(service.photosetsGetList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {

                }, () -> {

                }));
    }*/
}
