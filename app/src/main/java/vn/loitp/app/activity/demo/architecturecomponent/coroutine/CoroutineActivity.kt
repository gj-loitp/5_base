package vn.loitp.app.activity.demo.architecturecomponent.coroutine

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_demo_coroutine.*
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

@LayoutId(R.layout.activity_demo_coroutine)
@LogTag("CoroutineActivity")
class CoroutineActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
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
                logD("hello " + System.currentTimeMillis())
            }
        }
    }

    private fun testWithContext() {
        newSingleThreadContext("thread1").use { ctx1 ->
            // tạo một context là ctx1 chứ chưa launch coroutine.
            // ctx1 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread1
            logD("ctx1 - ${Thread.currentThread().name}")

            newSingleThreadContext("thread2").use { ctx2 ->
                // tạo một context là ctx2 chứ vẫn chưa launch coroutine
                // ctx2 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread2
                logD("ctx2 - ${Thread.currentThread().name}")

                // bắt đầu chạy coroutine với context là ctx1
                runBlocking(ctx1) {
                    // coroutine đang chạy trên context ctx1 và trên thread thread1
                    logD("Started in ctx1 - ${Thread.currentThread().name}")

                    // sử dụng hàm withContext để chuyển đổi context từ ctx1 qua ctx2
                    withContext(ctx2) {
                        // coroutine đang chạy với context ctx2 và trên thread thread2
                        logD("Working in ctx2 - ${Thread.currentThread().name}")
                    }

                    // coroutine đã thoát ra block withContext nên sẽ chạy lại với context ctx1 và trên thread thread1
                    logD("Back to ctx1 - ${Thread.currentThread().name}")
                }
            }
            logD("out of ctx2 block - ${Thread.currentThread().name}")
        }
        logD("out of ctx1 block - ${Thread.currentThread().name}")
    }

    private fun testJoin() {
        runBlocking {
            val job = GlobalScope.launch {
                // launch a new coroutine and keep a reference to its Job
                delay(5000L)
                logD("World!")
            }
            logD("Hello,")
            job.join() // wait until child coroutine completes
            logD("Kotlin")
        }
    }

    private fun testCancel() {
        runBlocking {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        logD("I'm sleeping $i")
                        delay(500L)
                    }
                } finally {
                    // Tranh thủ close resource trong này đi nha :D
                    logD("I'm running finally")
                }
            }
            delay(1300L) // delay a bit
            logD("I'm tired of waiting!")
            job.cancel() // cancels the job
            logD("Now I can quit.")
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
                logD("total: ${one.await() + two.await()}")
            }
            logD("testCompose time: $time ms")
        }
    }

    private fun testTimeOut() {
        runBlocking {
            val result = withTimeoutOrNull(timeMillis = 10000) {
                repeat(1000) { i ->
                    logD("testTimeOut $i: " + System.currentTimeMillis())
                    delay(500)
                }
            }
            logD("testTimeOut result: $result")
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
