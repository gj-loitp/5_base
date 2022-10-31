package com.loitpcore.utils.util;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public final class FragmentUtils {

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final int TYPE_ADD_FRAGMENT = 0x01;
    private static final int TYPE_REMOVE_FRAGMENT = 0x01 << 1;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 2;
    private static final int TYPE_REPLACE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_POP_ADD_FRAGMENT = 0x01 << 4;
    private static final int TYPE_HIDE_FRAGMENT = 0x01 << 5;
    private static final int TYPE_SHOW_FRAGMENT = 0x01 << 6;
    private static final int TYPE_HIDE_SHOW_FRAGMENT = 0x01 << 7;

    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    @Suppress(names = "unused")
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId) {
        return addFragment(fragmentManager, fragment, containerId, false);
    }

    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId,
                                       boolean isHide) {
        return addFragment(fragmentManager, fragment, containerId, isHide, false);
    }

    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId,
                                       boolean isHide,
                                       boolean isAddStack) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT);
    }

    @Suppress(names = "unused")
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       @IdRes int containerId,
                                       boolean isHide,
                                       boolean isAddStack,
                                       SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, sharedElement);
    }

    @Suppress(names = "unused")
    public static Fragment hideAddFragment(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment hideFragment,
                                           @NonNull Fragment addFragment,
                                           @IdRes int containerId,
                                           boolean isHide,
                                           boolean isAddStack,
                                           SharedElement... sharedElement) {
        putArgs(addFragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, hideFragment, addFragment, TYPE_ADD_FRAGMENT, sharedElement);
    }

    @Suppress(names = "unused")
    public static Fragment addFragments(@NonNull FragmentManager fragmentManager,
                                        @NonNull List<Fragment> fragments,
                                        @IdRes int containerId,
                                        int showIndex) {
        for (int i = 0, size = fragments.size(); i < size; ++i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                addFragment(fragmentManager, fragment, containerId, showIndex != i, false);
            }
        }
        return fragments.get(showIndex);
    }

    public static Fragment addFragments(@NonNull FragmentManager fragmentManager,
                                        @NonNull List<Fragment> fragments,
                                        @IdRes int containerId,
                                        int showIndex,
                                        @NonNull List<SharedElement>... lists) {
        for (int i = 0, size = fragments.size(); i < size; ++i) {
            Fragment fragment = fragments.get(i);
            List<SharedElement> list = lists[i];
            if (fragment != null) {
                if (list != null) {
                    putArgs(fragment, new Args(containerId, showIndex != i, false));
                    return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, list.toArray(new SharedElement[0]));
                }
            }
        }
        return fragments.get(showIndex);
    }

    public static void removeFragment(@NonNull Fragment fragment) {
        assert fragment.getFragmentManager() != null;
        operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_REMOVE_FRAGMENT);
    }

    @Suppress(names = "unused")
    public static void removeToFragment(@NonNull Fragment fragment, boolean isIncludeSelf) {
        assert fragment.getFragmentManager() != null;
        operateFragment(fragment.getFragmentManager(), isIncludeSelf ? fragment : null, fragment, TYPE_REMOVE_TO_FRAGMENT);
    }

    @Suppress(names = "unused")
    public static void removeFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) removeFragment(fragment);
        }
    }

    public static void removeAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                removeAllFragments(fragment.getChildFragmentManager());
                removeFragment(fragment);
            }
        }
    }

    @Suppress(names = "unused")
    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        assert srcFragment.getFragmentManager() != null;
        return replaceFragment(srcFragment.getFragmentManager(), destFragment, containerId, isAddStack);
    }

    @Suppress(names = "unused")
    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack,
                                           SharedElement... sharedElement) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        assert srcFragment.getFragmentManager() != null;
        return replaceFragment(srcFragment.getFragmentManager(), destFragment, containerId, isAddStack, sharedElement);
    }

    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment fragment,
                                           @IdRes int containerId,
                                           boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT);
    }

    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment fragment,
                                           @IdRes int containerId,
                                           boolean isAddStack,
                                           SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT, sharedElement);
    }

    public static boolean popFragment(@NonNull FragmentManager fragmentManager) {
        return fragmentManager.popBackStackImmediate();
    }

    @Suppress(names = "unused")
    public static boolean popToFragment(@NonNull FragmentManager fragmentManager,
                                        Class<? extends Fragment> fragmentClass,
                                        boolean isIncludeSelf) {
        return fragmentManager.popBackStackImmediate(fragmentClass.getName(), isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
    }

    @Suppress(names = "unused")
    public static void popFragments(@NonNull FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Suppress(names = "unused")
    public static void popAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) popAllFragments(fragment.getChildFragmentManager());
        }
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Suppress(names = "unused")
    public static Fragment popAddFragment(@NonNull FragmentManager fragmentManager,
                                          @NonNull Fragment fragment,
                                          @IdRes int containerId,
                                          boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_POP_ADD_FRAGMENT);
    }

    @Suppress(names = "unused")
    public static Fragment popAddFragment(@NonNull FragmentManager fragmentManager,
                                          @NonNull Fragment fragment,
                                          @IdRes int containerId,
                                          boolean isAddStack,
                                          SharedElement... sharedElements) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_POP_ADD_FRAGMENT, sharedElements);
    }

    public static Fragment hideFragment(@NonNull Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, true, args.isAddStack));
        }
        assert fragment.getFragmentManager() != null;
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_HIDE_FRAGMENT);
    }

    public static void hideFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) hideFragment(fragment);
        }
    }

    @Suppress(names = "unused")
    public static Fragment showFragment(@NonNull Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, false, args.isAddStack));
        }
        assert fragment.getFragmentManager() != null;
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    @Suppress(names = "unused")
    public static Fragment hideAllShowFragment(@NonNull Fragment fragment) {
        assert fragment.getFragmentManager() != null;
        hideFragments(fragment.getFragmentManager());
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    @Suppress(names = "unused")
    public static Fragment hideShowFragment(@NonNull Fragment hideFragment,
                                            @NonNull Fragment showFragment) {
        Args args = getArgs(hideFragment);
        if (args != null) {
            putArgs(hideFragment, new Args(args.id, true, args.isAddStack));
        }
        args = getArgs(showFragment);
        if (args != null) {
            putArgs(showFragment, new Args(args.id, false, args.isAddStack));
        }
        assert showFragment.getFragmentManager() != null;
        return operateFragment(showFragment.getFragmentManager(), hideFragment, showFragment, TYPE_HIDE_SHOW_FRAGMENT);
    }

    private static void putArgs(@NonNull Fragment fragment, Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    private static Args getArgs(@NonNull Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null || bundle.getInt(ARGS_ID) == 0) return null;
        return new Args(bundle.getInt(ARGS_ID), bundle.getBoolean(ARGS_IS_HIDE), bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    private static Fragment operateFragment(@NonNull FragmentManager fragmentManager,
                                            Fragment srcFragment,
                                            @NonNull Fragment destFragment,
                                            int type,
                                            SharedElement... sharedElements) {
        if (srcFragment == destFragment) return null;
        if (srcFragment != null && srcFragment.isRemoving()) {
            return null;
        }
        String name = destFragment.getClass().getName();
        Bundle args = destFragment.getArguments();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (sharedElements == null || sharedElements.length == 0) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else {
            for (SharedElement element : sharedElements) {// 添加共享元素动画
                ft.addSharedElement(element.sharedElement, element.name);
            }
        }
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                if (srcFragment != null) ft.hide(srcFragment);
                assert args != null;
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(destFragment);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                ft.remove(destFragment);
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                List<Fragment> fragments = getFragments(fragmentManager);
                for (int i = fragments.size() - 1; i >= 0; --i) {
                    Fragment fragment = fragments.get(i);
                    if (fragment == destFragment) {
                        if (srcFragment != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                ft.replace(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_POP_ADD_FRAGMENT:
                popFragment(fragmentManager);
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_HIDE_FRAGMENT:
                ft.hide(destFragment);
                break;
            case TYPE_SHOW_FRAGMENT:
                ft.show(destFragment);
                break;
            case TYPE_HIDE_SHOW_FRAGMENT:
                assert srcFragment != null;
                ft.hide(srcFragment).show(destFragment);
                break;
        }
        ft.commitAllowingStateLoss();
        return destFragment;
    }

    @Suppress(names = "unused")
    public static Fragment getLastAddFragment(@NonNull FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, false);
    }

    @Suppress(names = "unused")
    public static Fragment getLastAddFragmentInStack(@NonNull FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, true);
    }

    private static Fragment getLastAddFragmentIsInStack(@NonNull FragmentManager fragmentManager,
                                                        boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return null;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    assert fragment.getArguments() != null;
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    @Suppress(names = "unused")
    public static Fragment getTopShowFragment(@NonNull FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, false);
    }

    @Suppress(names = "unused")
    public static Fragment getTopShowFragmentInStack(@NonNull FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, true);
    }

    private static Fragment getTopShowFragmentIsInStack(@NonNull FragmentManager fragmentManager,
                                                        Fragment parentFragment,
                                                        boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return parentFragment;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    assert fragment.getArguments() != null;
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, true);
                    }
                } else {
                    return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, false);
                }
            }
        }
        return parentFragment;
    }

    public static List<Fragment> getFragments(@NonNull FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, false);
    }

    @Suppress(names = "unused")
    public static List<Fragment> getFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, true);
    }

    private static List<Fragment> getFragmentsIsInStack(@NonNull FragmentManager fragmentManager, boolean isInStack) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) return Collections.emptyList();
        List<Fragment> result = new ArrayList<>();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    assert fragment.getArguments() != null;
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(fragment);
                    }
                } else {
                    result.add(fragment);
                }
            }
        }
        return result;
    }

    @Suppress(names = "unused")
    public static List<FragmentNode> getAllFragments(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), false);
    }

    @Suppress(names = "unused")
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), true);
    }

    private static List<FragmentNode> getAllFragmentsIsInStack(@NonNull FragmentManager fragmentManager,
                                                               List<FragmentNode> result,
                                                               boolean isInStack) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) return Collections.emptyList();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    assert fragment.getArguments() != null;
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), true)));
                    }
                } else {
                    result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), false)));
                }
            }
        }
        return result;
    }

    @Suppress(names = "unused")
    public static Fragment getPreFragment(@NonNull Fragment destFragment) {
        FragmentManager fragmentManager = destFragment.getFragmentManager();
        if (fragmentManager == null) return null;
        List<Fragment> fragments = getFragments(fragmentManager);
        boolean flag = false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (flag && fragment != null) {
                return fragment;
            }
            if (fragment == destFragment) {
                flag = true;
            }
        }
        return null;
    }

    @Suppress(names = "unused")
    public static Fragment findFragment(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return null;
        return fragmentManager.findFragmentByTag(fragmentClass.getName());
    }

    @Suppress(names = "unused")
    public static boolean dispatchBackPress(@NonNull Fragment fragment) {
        assert fragment.getFragmentManager() != null;
        return dispatchBackPress(fragment.getFragmentManager());
    }

    public static boolean dispatchBackPress(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    public static void setBackgroundColor(@NonNull Fragment fragment, @ColorInt int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    public static void setBackgroundResource(@NonNull Fragment fragment, @DrawableRes int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    public static void setBackground(@NonNull Fragment fragment, Drawable background) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackground(background);
        }
    }

    static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;

        Args(int id, boolean isHide, boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    public static class SharedElement {
        View sharedElement;
        String name;

        public SharedElement(View sharedElement, String name) {
            this.sharedElement = sharedElement;
            this.name = name;
        }
    }

    static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;

        public FragmentNode(Fragment fragment, List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }

        @NonNull
        @Override
        public String toString() {
            return fragment.getClass().getSimpleName() + "->" + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }
}
