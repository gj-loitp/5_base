package com.core.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.core.utilities.LDialogUtil
import io.reactivex.disposables.CompositeDisposable
import loitp.core.R

/**
 * Created by loitp on 2019/7/12
 */
abstract class BaseFragment : Fragment() {
    protected var compositeDisposable = CompositeDisposable()
    protected val TAG = "TAG" + BaseFragment::class.java.simpleName
    protected lateinit var frmRootView: View
    protected var fragmentCallback: FragmentCallback? = null

    private val DEFAULT_CHILD_ANIMATION_DURATION = 400

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCallback?.onViewCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(setLayoutResourceId(), container, false)
        return frmRootView
    }

    protected abstract fun setLayoutResourceId(): Int

    override fun onDestroyView() {
        LDialogUtil.clearAll()
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    protected fun handleException(throwable: Throwable) {
        throwable.message?.let {
            showDialogError(it)
        }
    }

    protected fun showDialogError(errMsg: String) {
        LDialogUtil.showDialog1(activity, getString(R.string.warning), errMsg, getString(R.string.confirm)) {
            //getActivity().onBackPressed();
        }
    }

    protected fun showDialogMsg(msg: String) {
        LDialogUtil.showDialog1(activity, getString(R.string.app_name), msg, getString(R.string.confirm)) {
            //getActivity().onBackPressed();
        }
    }

    interface FragmentCallback {
        fun onViewCreated()
    }

    //https://stackoverflow.com/questions/14900738/nested-fragments-disappear-during-transition-animation
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        val parent = parentFragment

        // Apply the workaround only if this is a child fragment, and the parent
        // is being removed.
        if (!enter && parent != null && parent.isRemoving) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            val doNothingAnim = AlphaAnimation(1f, 1f)
            doNothingAnim.duration = getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION.toLong())
            return doNothingAnim
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim)
        }
    }

    private fun getNextAnimationDuration(fragment: Fragment, defValue: Long): Long {
        try {
            // Attempt to get the resource ID of the next animation that
            // will be applied to the given fragment.
            val nextAnimField = Fragment::class.java.getDeclaredField("mNextAnim")
            nextAnimField.isAccessible = true
            val nextAnimResource = nextAnimField.getInt(fragment)
            val nextAnim = AnimationUtils.loadAnimation(fragment.activity, nextAnimResource)

            // ...and if it can be loaded, return that animation's duration
            return nextAnim?.duration ?: defValue
        } catch (ex: NoSuchFieldException) {
            //LLog.d(TAG, "Unable to load next animation from parent.", ex);
            return defValue
        } catch (ex: IllegalAccessException) {
            return defValue
        } catch (ex: Resources.NotFoundException) {
            return defValue
        }

    }
}
