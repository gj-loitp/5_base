package com.loitpcore.core.base

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loitpcore.BuildConfig
import com.loitpcore.R
import com.loitp.annotation.LogTag
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LLog
import com.loitpcore.data.EventBusData
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class BaseFragment : Fragment() {
    protected var logTag: String? = null
    protected var compositeDisposable = CompositeDisposable()
    protected var frmRootView: View? = null

    companion object {
        private const val DEFAULT_CHILD_ANIMATION_DURATION = 400
    }

    protected abstract fun setLayoutResourceId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frmRootView = inflater.inflate(setLayoutResourceId(), container, false)
        val tmpLogTag = javaClass.getAnnotation(LogTag::class.java)
        logTag = "logTag" + tmpLogTag?.value
        EventBus.getDefault().register(this)
        return frmRootView
    }

    override fun onDestroyView() {
        LDialogUtil.clearAll()
        compositeDisposable.clear()
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    protected fun logD(msg: String) {
        logTag?.let {
            LLog.d(tag = it, msg = msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            LLog.e(tag = it, msg = msg)
        }
    }

    open fun handleException(throwable: Throwable) {
        throwable.message?.let {
            showDialogError(errMsg = it)
        }
    }

    open fun showDialogError(
        title: String = getString(R.string.warning),
        errMsg: String,
        runnable: Runnable? = null
    ) {
        context?.let {
            LDialogUtil.showDialog1(
                context = it,
                title = title,
                msg = errMsg,
                button1 = getString(R.string.confirm),
                onClickButton1 = {
                    runnable?.run()
                }
            )
        }
    }

    open fun showDialogMsg(
        title: String = getString(R.string.app_name),
        msg: String,
        runnable: Runnable? = null
    ) {
        context?.let {
            LDialogUtil.showDialog1(
                context = it,
                title = title,
                msg = msg,
                button1 = getString(R.string.confirm),
                onClickButton1 = {
                    runnable?.run()
                }
            )
        }
    }

    // https://stackoverflow.com/questions/14900738/nested-fragments-disappear-during-transition-animation
    override fun onCreateAnimation(
        transit: Int,
        enter: Boolean,
        nextAnim: Int
    ): Animation? {
        val parent = parentFragment

        // Apply the workaround only if this is a child fragment, and the parent
        // is being removed.
        return if (!enter && parent != null && parent.isRemoving) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            val doNothingAnim = AlphaAnimation(1f, 1f)
            doNothingAnim.duration =
                getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION.toLong())
            doNothingAnim
        } else {
            super.onCreateAnimation(transit, enter, nextAnim)
        }
    }

    @Suppress("unused")
    private fun getNextAnimationDuration(
        fragment: Fragment,
        defValue: Long
    ): Long {
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
            // logD("Unable to load next animation from parent.", ex)
            ex.printStackTrace()
            return defValue
        } catch (ex: IllegalAccessException) {
            ex.printStackTrace()
            return defValue
        } catch (ex: Resources.NotFoundException) {
            ex.printStackTrace()
            return defValue
        }
    }

    protected fun showShortInformation(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showShortInformation(msg = msg, isTopAnchor = isTopAnchor)
        }
    }

    protected fun showShortWarning(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showShortWarning(msg = msg, isTopAnchor = isTopAnchor)
        }
    }

    protected fun showShortError(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showShortError(msg = msg, isTopAnchor = isTopAnchor)
        }
    }

    protected fun showLongInformation(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLongInformation(msg = msg, isTopAnchor = isTopAnchor)
        }
    }

    protected fun showLongWarning(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLongWarning(msg = msg, isTopAnchor = isTopAnchor)
        }
    }

    protected fun showLongError(
        msg: String?,
        isTopAnchor: Boolean = true
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLongError(msg = msg, isTopAnchor = isTopAnchor)
        }
    }

    protected fun showShortDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            if (activity is BaseActivity) {
                (activity as BaseActivity).showShortDebug(msg = msg)
            }
        }
    }

    protected fun showLongDebug(msg: String?) {
        if (BuildConfig.DEBUG) {
            if (activity is BaseActivity) {
                (activity as BaseActivity).showLongInformation(msg = msg)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusData.ConnectEvent) {
        onNetworkChange(event)
    }

    open fun onNetworkChange(event: EventBusData.ConnectEvent) {
    }

    protected fun <T : ViewModel> getViewModel(className: Class<T>): T? {
        return activity?.let {
            ViewModelProvider(it)[className]
        }
    }

    protected fun <T : ViewModel> getSelfViewModel(className: Class<T>): T {
        return ViewModelProvider(this)[className]
    }

    fun showBottomSheetOptionFragment(
        isCancelableFragment: Boolean = true,
        isShowIvClose: Boolean = true,
        title: String,
        message: String,
        textButton1: String? = null,
        textButton2: String? = null,
        textButton3: String? = null,
        onClickButton1: ((Unit) -> Unit)? = null,
        onClickButton2: ((Unit) -> Unit)? = null,
        onClickButton3: ((Unit) -> Unit)? = null,
        onDismiss: ((Unit) -> Unit)? = null
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showBottomSheetOptionFragment(
                isCancelableFragment = isCancelableFragment,
                isShowIvClose = isShowIvClose,
                title = title,
                message = message,
                textButton1 = textButton1,
                textButton2 = textButton2,
                textButton3 = textButton3,
                onClickButton1 = onClickButton1,
                onClickButton2 = onClickButton2,
                onClickButton3 = onClickButton3,
                onDismiss = onDismiss
            )
        }
    }

    fun showSnackBarInfor(
        msg: String,
        isFullWidth: Boolean = false
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showSnackBarInfor(msg = msg, isFullWidth = isFullWidth)
        }
    }

    fun showSnackBarWarning(
        msg: String,
        isFullWidth: Boolean = false
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showSnackBarWarning(msg = msg, isFullWidth = isFullWidth)
        }
    }

    fun showSnackBarError(
        msg: String,
        isFullWidth: Boolean = false
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showSnackBarError(msg = msg, isFullWidth = isFullWidth)
        }
    }

    fun showDialogProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showDialogProgress()
        }
    }

    fun hideDialogProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideDialogProgress()
        }
    }
}
