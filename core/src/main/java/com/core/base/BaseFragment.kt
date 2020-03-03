package com.core.base

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
import com.R
import com.core.utilities.LDialogUtil
import com.core.utilities.LLog
import com.data.EventBusData
import com.views.LToast
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by loitp on 2019/7/12
 */
abstract class BaseFragment : Fragment() {
    protected var TAG: String? = null
    protected var compositeDisposable = CompositeDisposable()
    protected var frmRootView: View? = null
    protected var fragmentCallback: FragmentCallback? = null

    private val DEFAULT_CHILD_ANIMATION_DURATION = 400

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCallback?.onViewCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(setLayoutResourceId(), container, false)
        TAG = "TAG" + setTag()
        EventBus.getDefault().register(this)
        return frmRootView
    }

    protected abstract fun setLayoutResourceId(): Int
    protected abstract fun setTag(): String?

    override fun onDestroyView() {
        LDialogUtil.clearAll()
        compositeDisposable.clear()
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    protected fun logD(msg: String) {
        TAG?.let {
            LLog.d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        TAG?.let {
            LLog.e(it, msg)
        }
    }

    /*override fun onAttach(context: Context?) {
        super.onAttach(context)
    }*/

    open fun handleException(throwable: Throwable) {
        throwable.message?.let {
            showDialogError(it)
        }
    }

    open fun showDialogError(errMsg: String, runnable: Runnable? = null) {
        context?.let {
            LDialogUtil.showDialog1(it, getString(R.string.warning), errMsg, getString(R.string.confirm),
                    object : LDialogUtil.Callback1 {
                        override fun onClick1() {
                            runnable?.run()
                        }
                    })
        }
    }

    open fun showDialogMsg(msg: String, runnable: Runnable? = null) {
        context?.let {
            LDialogUtil.showDialog1(it, getString(R.string.app_name), msg, getString(R.string.confirm),
                    object : LDialogUtil.Callback1 {
                        override fun onClick1() {
                            runnable?.run()
                        }
                    }
            )
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
        return if (!enter && parent != null && parent.isRemoving) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            val doNothingAnim = AlphaAnimation(1f, 1f)
            doNothingAnim.duration = getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION.toLong())
            doNothingAnim
        } else {
            super.onCreateAnimation(transit, enter, nextAnim)
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
            //logD("Unable to load next animation from parent.", ex)
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

    open fun showShort(msg: String) {
        context?.let { LToast.showShort(it, msg, R.drawable.l_bkg_horizontal) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusData.ConnectEvent) {
        //TAG = "onMessageEvent"
        //logD(TAG, "onMessageEvent " + event.isConnected())
        onNetworkChange(event)
    }

    open fun onNetworkChange(event: EventBusData.ConnectEvent) {
        //showToastLongDebug("onNetworkChange isConnected: ${event.isConnected}")
    }

    protected fun <T : ViewModel> getViewModel(className: Class<T>): T {
        return ViewModelProvider(this).get(className)
    }
}
