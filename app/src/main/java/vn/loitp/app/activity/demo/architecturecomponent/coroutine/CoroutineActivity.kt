package vn.loitp.app.activity.demo.architecturecomponent.coroutine

import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import loitp.basemaster.R

//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-1-gioi-thieu-kotlin-coroutine-va-ky-thuat-lap-trinh-bat-dong-bo-gGJ59xajlX2
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-2-build-first-coroutine-with-kotlin-Do7544Ee5M6
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-3-coroutine-context-va-dispatcher-Qbq5QkDzZD8
class CoroutineActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coroutine
    }

    //test blocking
    private fun testBlocking() = runBlocking {
        repeat(100_000) {
            // launch 100_000 coroutines
            launch {
                LLog.d(TAG, "hello " + System.currentTimeMillis())
            }
        }
    }

    private fun testWithContext() {
        newSingleThreadContext("thread1").use { ctx1 ->
            // tạo một context là ctx1 chứ chưa launch coroutine.
            // ctx1 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread1
            LLog.d(TAG, "ctx1 - ${Thread.currentThread().name}")

            newSingleThreadContext("thread2").use { ctx2 ->
                // tạo một context là ctx2 chứ vẫn chưa launch coroutine
                // ctx2 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread2
                LLog.d(TAG, "ctx2 - ${Thread.currentThread().name}")

                // bắt đầu chạy coroutine với context là ctx1
                runBlocking(ctx1) {
                    // coroutine đang chạy trên context ctx1 và trên thread thread1
                    LLog.d(TAG, "Started in ctx1 - ${Thread.currentThread().name}")

                    // sử dụng hàm withContext để chuyển đổi context từ ctx1 qua ctx2
                    withContext(ctx2) {
                        // coroutine đang chạy với context ctx2 và trên thread thread2
                        LLog.d(TAG, "Working in ctx2 - ${Thread.currentThread().name}")
                    }

                    // coroutine đã thoát ra block withContext nên sẽ chạy lại với context ctx1 và trên thread thread1
                    LLog.d(TAG, "Back to ctx1 - ${Thread.currentThread().name}")
                }
            }
            LLog.d(TAG, "out of ctx2 block - ${Thread.currentThread().name}")
        }
        LLog.d(TAG, "out of ctx1 block - ${Thread.currentThread().name}")
    }
}
