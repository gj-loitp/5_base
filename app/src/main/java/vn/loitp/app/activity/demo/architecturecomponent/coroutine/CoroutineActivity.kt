package vn.loitp.app.activity.demo.architecturecomponent.coroutine

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import vn.loitp.app.R
import kotlin.system.measureTimeMillis

//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-1-gioi-thieu-kotlin-coroutine-va-ky-thuat-lap-trinh-bat-dong-bo-gGJ59xajlX2
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-2-build-first-coroutine-with-kotlin-Do7544Ee5M6
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-3-coroutine-context-va-dispatcher-Qbq5QkDzZD8
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-4-job-join-cancellation-and-timeouts-Do75463QZM6
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-5-async-await-naQZRxGm5vx
//https://viblo.asia/p/cung-hoc-kotlin-coroutine-phan-6-coroutine-scope-aWj536n8l6m

//https://medium.com/androiddevelopers/coroutines-on-android-part-ii-getting-started-3bff117176dd
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btTestBlocking.setSafeOnClickListener {
            testBlocking()
        }
        btTestWithContext.setSafeOnClickListener {
            testWithContext()
        }
        btTestJoin.setSafeOnClickListener {
            testJoin()
        }
        btTestCancel.setSafeOnClickListener {
            testCancel()
        }
        btTestCompose.setSafeOnClickListener {
            testCompose()
        }
        btTestTimeOut.setSafeOnClickListener {
            testTimeOut()
        }
        btTestConvertAsyncTaskToCoroutine.setSafeOnClickListener {
            convertAsyncTaskToCoroutine()
        }
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

    private fun testJoin() {
        runBlocking {
            val job = GlobalScope.launch {
                // launch a new coroutine and keep a reference to its Job
                delay(5000L)
                LLog.d(TAG, "World!")
            }
            LLog.d(TAG, "Hello,")
            job.join() // wait until child coroutine completes
            LLog.d(TAG, "Kotlin")
        }
    }

    private fun testCancel() {
        runBlocking {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        LLog.d(TAG, "I'm sleeping $i")
                        delay(500L)
                    }
                } finally {
                    // Tranh thủ close resource trong này đi nha :D
                    LLog.d(TAG, "I'm running finally")
                }
            }
            delay(1300L) // delay a bit
            LLog.d(TAG, "I'm tired of waiting!")
            job.cancel() // cancels the job
            LLog.d(TAG, "Now I can quit.")
        }
    }

    private fun testCompose() {
        suspend fun getOne(): Int {
            delay(1000)
            return 10
        }

        suspend fun getTwo(): Int {
            delay(1000)
            return 20
        }
        runBlocking {
            val time = measureTimeMillis {
                val one = async { getOne() }
                val two = async { getTwo() }
                LLog.d(TAG, "total: ${one.await() + two.await()}")
            }
            LLog.d(TAG, "testCompose time: $time ms")
        }
    }

    private fun testTimeOut() {
        runBlocking {
            val result = withTimeoutOrNull(timeMillis = 10000) {
                repeat(1000) { i ->
                    LLog.d(TAG, "testTimeOut $i: " + System.currentTimeMillis())
                    delay(500)
                }
            }
            LLog.d(TAG, "testTimeOut result: $result")
        }
    }

    private fun convertAsyncTaskToCoroutine() {
        val coroutineTask = CoroutineTask()
        coroutineTask.startTask()
        LUIUtil.setDelay(2000, Runnable {
            //coroutineTask.cancel()
        })
    }
}
