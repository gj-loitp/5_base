package vn.loitp.app.activity.demo.architecturecomponent.coroutine

import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import loitp.basemaster.R

class CoroutineActivity : BaseFontActivity() {

    //test blocking
    private fun main() = runBlocking {
        repeat(100_000) {
            // launch 100_000 coroutines
            launch {
                LLog.d(TAG, "hello " + System.currentTimeMillis())
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coroutine
    }
}
